package com.apcs.disunity.game.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.apcs.disunity.app.network.Util;
import com.apcs.disunity.game.selector.Indexed;
import com.apcs.disunity.math.Transform;

/**
 * The base class for all nodes in the game
 *
 * @author Qinzhao Li
 * @author Toshiki Takeuchi
 */
@SuppressWarnings("rawtypes")
public abstract class Node<T extends Node> {

    // MUST DO: remove this
    public int owner;

    /* ================ [ FIELDS ] ================ */

    // List of children
    private final List<T> children = new ArrayList<>();

    // Parent
    Node<?> parent;

    // Constructors
    @SafeVarargs
    public Node(T... children) { addChildren(children); }

    /* ================ [ METHODS ] ================ */

    // Add child
    public void addChild(T node) {
        node.parent = this;
        children.add(node);
    }

    @SafeVarargs
    public final void addChildren(T... nodes) {
        for (T child : nodes) {
            addChild(child);
        }
    }

    // Remove child
    public void removeChild(T node) { getChildren().remove(node); }

    // Clear children
    public void clearChildren() { getChildren().clear(); }

    // Get children
    public List<T> getDynamicChildren() { return children; }

    public List<T> getFieldChildren() {
        return Util.getAnnotatedFields(this.getClass(), FieldChild.class).map(f -> {
            try {
                return (T) f.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    public List<T> getChildren() {
        return Stream.concat(getDynamicChildren().stream(), getFieldChildren().stream()).toList();
    }

    /* ================ [ NODE ] ================ */

    // Update node
    public void update(double delta) {
        // Update children
        for (T node : getChildren())
            node.update(delta);
    }

    public void draw(Transform transform) { getChildren().forEach(n -> n.draw(transform)); }

    /* ================ [ PRINTING ] ================ */

    /// Overload for default behavior of {@link #print(boolean, Function, List)}.
    /// Prints node names in tree structure
    public void print() { print(true, Node::defaultLabel, new ArrayList<>()); }

    private static String defaultLabel(Node<?> node) {
        StringBuilder builder = new StringBuilder();
        builder.append("( ) ");
        if (node instanceof Indexed<?> indexed)
            builder.append(indexed.index().toString());
        else
            builder.append(node.getClass().getName().substring(node.getClass().getPackageName().length() + 1));
        return builder.toString();
    }

    /// method that prints information and children of node recursively in a tree
    /// format.
    /// @param isLast indicates if this node is last child of parent
    /// @param formatter function that formats provided node to printed string
    /// @param indent string used to indent this node
    private void print(boolean isLast, Function<Node<?>, String> formatter, List<String> indent) {
        indent.forEach(System.out::print);
        System.out.print(isLast ? " '--" : " |--");
        System.out.println(formatter.apply(this));

        if (isLast)
            indent.add("    ");
        else
            indent.add(" |  ");

        for (int i = 0; i < getChildren().size() - 1; i++) {
            ((Node<?>) getChildren().get(i)).print(false, formatter, indent);
        }
        if (!getChildren().isEmpty()) {
            ((Node<?>) getChildren().getLast()).print(true, formatter, indent);
        }

        indent.removeLast();
    }

    public Node<?> getRoot() {
        if (getParent() == null) {
            return this;
        }
        return getParent().getRoot();
    }

    public <U extends Node<?>> U getRootAs(Class<U> clazz) {
        return clazz.cast(getRoot());
    }

    public Node<?> getParent() {
        return parent;
    }

    public <U extends Node<?>> U getParentAs(Class<U> clazz) {
        return clazz.cast(getParent());
    }

}
