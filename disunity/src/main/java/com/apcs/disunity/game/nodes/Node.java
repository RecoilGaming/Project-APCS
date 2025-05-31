package com.apcs.disunity.game.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.apcs.disunity.game.selector.Indexed;
import com.apcs.disunity.math.Transform;

/**
 * The base class for all nodes in the game
 *
 * @author Qinzhao Li
 * @author Toshiki Takeuchi
 */
public abstract class Node<T extends Node> {

    /* ================ [ FIELDS ] ================ */

    // The id of the client that owns this node
    public int owner;

    // Parent node
    private Node<?> parent;

    // List of children nodes
    private final List<T> children = new ArrayList<>();

    // Constructors
    public Node(T... children) { addChildren(children); }

    /* ================ [ METHODS ] ================ */

    // Set parent node
    public void setParent(Node<?> parent) { this.parent = parent; }

    // Get parent node
    public Node<?> getParent() { return parent; }

    // Get parent node as a specific type
    public <U extends Node<?>> U getParentAs(Class<U> t) { return t.cast(getParent()); }

    // Add a child node
    public void addChild(T node) {
        node.setParent(this);
        children.add(node);
    }

    // Add multiple child nodes
    public final void addChildren(T... nodes) {
        synchronized (nodes) {
            for (T child : nodes) {
                this.addChild(child);
            }
        }
    }

    // Remove child node
    public void removeChild(T node) {
        node.setParent(null);
        getAllChildren().remove(node);
    }

    // Clear chi.d nodes
    public void clearChildren() {
        for (T child : getAllChildren()) {
            child.setParent(null);
        }
        getAllChildren().clear();
    }

    // Get dynamic child nodes
    public List<T> getDynamicChildren() { return children; }

    // Get static child nodes
    public List<T> getStaticChildren() {
        return NodeUtil.getAnnotatedFields(this.getClass(), FieldChild.class).map(field -> {
            try {
                return (T) field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    // Get all child nodes
    public List<T> getAllChildren() {
        return Stream.concat(getDynamicChildren().stream(), getStaticChildren().stream()).toList();
    }

    /* ================ [ NODE ] ================ */

    // Update node
    public void update(double delta) { getAllChildren().forEach(child -> child.update(delta)); }

    // Draw node
    public void draw(Transform offset) { getAllChildren().forEach(child -> child.draw(offset)); }

    /* ================ [ PRINT ] ================ */

    /// Overload for default behavior of {@link #print(boolean, Function, List)}.
    /// Prints node names in tree structure
    public void print() { print(true, Node::defaultLabel, new ArrayList<>()); }
    public void print(Function<Node<?>, String> formatter) { print(true, formatter, new ArrayList<>()); }

    public static String defaultLabel(Node<?> node) {
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

        for (int i = 0; i < getAllChildren().size() - 1; i++) {
            ((Node<?>) getAllChildren().get(i)).print(false, formatter, indent);
        }
        if (!getAllChildren().isEmpty()) {
            ((Node<?>) getAllChildren().getLast()).print(true, formatter, indent);
        }

        indent.removeLast();
    }

}
