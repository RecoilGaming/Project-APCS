package com.apcs.disunity.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.apcs.disunity.annotations.Requires;

/**
 * The base class for all nodes in the game
 * 
 * @author Qinzhao Li
 * @author Toshiki Takeuchi
 */
public abstract class Node<T extends Node<?>> {

    /* ================ [ FIELDS ] ================ */

    // List of children
    private final List<T> children;

    // Triggers initialize
    private boolean isInitialized = false;

    // Constructors
    public Node() { this.children = new ArrayList<>(); }
    @SafeVarargs
    public Node(T... children) { this.children = new ArrayList<>(Arrays.asList(children)); }

    /* ================ [ METHODS ] ================ */

    // Add child
    public void addChild(T node) {
        children.add(node);
        isInitialized = false;
    }

    @SafeVarargs
    public final void addChildren(T... nodes) {
        for(T child: nodes) {
            addChild(child);
        }
    }

    // Remove child
    public void removeChild(T node) { children.remove(node); }

    // Clear children
    public void clearChildren() { children.clear(); }

    // Get child of a certain type
    public <U extends T> U getChild(Class<U> type) {
        for (T node : children) {
            if (type.isInstance(node)) {
                return type.cast(node);
            }
        } return null;
    }

    // Get children
    public List<T> getChildren() { return children; }

    // Get children of a certain type
    public <U> List<U> getChildren(Class<U> type) {
        List<U> children = new ArrayList<>();
        for (T node : getChildren()) {
            if (type.isInstance(node)) {
                children.add(type.cast(node));
            }
        } return children;
    }

    /* ================ [ NODE ] ================ */

    // Initialize node
    public void initialize() {
        // Check if node meets requirements
        if (this.getClass().isAnnotationPresent(Requires.class)) {
            // Grab nodes from annotation
            Requires requires = this.getClass().getAnnotation(Requires.class);
            Set<Class<? extends Node>> requirements = new HashSet<>(Arrays.asList(requires.nodes()));

            // Check children
            for (T node : children) {
                requirements.removeIf(required -> required.isAssignableFrom(node.getClass()));
            }

            // Throw exception if requirements are not met
            if (!requirements.isEmpty()) {
                throw new RuntimeException(
                    "Node " + this.getClass().getSimpleName() +
                    " requires " + requirements.iterator().next().getSimpleName() + 
                    " to be initialized."
                );
            }
        }

        // Set initialized
        isInitialized = true;
    }

    // Tick node
    public final void tick(double delta) {
        // Re-initialize node
        if (!isInitialized) initialize();

        // Update node
        update(delta);
    }

    // Update node
    public void update(double delta) {
        // Update children
        for (T node : children) node.tick(delta);
    }

    /* ================ [ PRINTING ] ================ */

    /// Overload for default behavior of {@link #print(boolean, Function, List)}.
    /// Prints node names in tree structure
    public void print() {
        print(
          true,
          n -> "○ " + n.getClass().getSimpleName(),
          new ArrayList<>()
        );
    }

    /// method that prints information and children of node recursively in a tree format.
    /// @param isLast indicates if this node is last child of parent
    /// @param formatter function that formats provided node to printed string
    /// @param indent string used to indent this node
    private void print(boolean isLast, Function<Node<?>, String> formatter, List<String> indent) {
        indent.forEach(System.out::print);
        System.out.print(isLast ? "└ " : "├ ");
        System.out.println(formatter.apply(this));

        if (isLast) indent.add("  ");
        else indent.add("│ ");

        for (int i = 0; i < children.size() - 1; i++) {
            ((Node<?>) children.get(i)).print(false, formatter, indent);
        }
        if (!children.isEmpty()) {
            ((Node<?>) children.getLast()).print(true, formatter, indent);
        }

        indent.removeLast();
    }

}