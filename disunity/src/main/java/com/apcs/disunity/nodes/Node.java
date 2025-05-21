package com.apcs.disunity.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.apcs.disunity.annotations.Requires;
import com.apcs.disunity.math.Transform;

/**
 * The base class for all nodes in the game
 *
 * @author Qinzhao Li
 * @author Toshiki Takeuchi
 */
public abstract class Node {

    // TODO: remove this
    public int owner;

    /* ================ [ FIELDS ] ================ */

    /** Whether or not the node is visible */
    private boolean isVisible = true;

    /** The parent of the node */
    private Node parent;

    /** Lists the children of the node */
    private final List<Node> children;

    /** Whether or not the node is initialized */
    private boolean isInitialized = false;

    /** Create a Node */
    public Node() {
        this(true);
    }
    /**
     * Create a Node with the given children
     * 
     * @param children The children of this node
     */
    @SafeVarargs
    public Node(Node... children) {
        this(true, children);
    }
    /**
     * Create a Node with the given visiblility and children
     * 
     * @param isVisible Whether or not the node is visible
     * @param children The children of this node
     */
    public Node(boolean isVisible, Node... children) {
        this.isVisible = isVisible;
        this.children = new ArrayList<>(Arrays.asList(children));
    }

    /* ================ [ METHODS ] ================ */
    
    /**
     * Get whether or not the node is visible
     * 
     * @return Whether or not the node is visible
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Set whether or not the node is visible
     * 
     * @param isVisible Whether or not the node is visible
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Get the parent of this node
     * 
     * @return The parent of this node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Set the parent of this node
     * 
     * @param parent The parent of this node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Add a child node to this node
     * 
     * @param node The child node
     */
    public void addChild(Node node) {
        children.add(node);
        node.setParent(this);
        isInitialized = false;
    }

    /**
     * Add multiple child nodes to this node
     * 
     * @param nodes The child nodes
     */
    @SafeVarargs
    public final void addChildren(Node... nodes) {
        for (Node child : nodes) {
            addChild(child);
        }
    }

    /**
     * Remove a child node from this node
     *
     * @param node The child node
     */
    public void removeChild(Node node) {
        children.remove(node);
    }

    /** Remove all child nodes from this node */
    public void clearChildren() {
        children.clear();
    }

    /**
     * Get the first child node of a certain type
     * 
     * @param <T> The type of the child node
     * @param type The type of the child node
     * @return The first child node of the specified type
     */
    public <T extends Node> T getChild(Class<T> type) {
        for (Node node : children) {
            if (type.isInstance(node)) {
                return type.cast(node);
            }
        }
        return null;
    }

    /**
     * Get all child nodes
     * 
     * @return All child nodes
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * Get all child nodes of a certain type
     *
     * @param <T> The type of the child nodes
     * @param type The type of the child nodes
     * @return All child nodes of the specified type
     */
    public <T> List<T> getChildren(Class<T> type) {
        List<T> children = new ArrayList<>();
        for (Node node : getChildren()) {
            if (type.isInstance(node)) {
                children.add(type.cast(node));
            }
        }
        return children;
    }

    /* ================ [ NODE ] ================ */

    /** Initialize the node */
    public void initialize() {
        // Check if node meets requirements
        if (this.getClass().isAnnotationPresent(Requires.class)) {
            // Grab nodes from annotation
            Requires requires = this.getClass().getAnnotation(Requires.class);
            Set<Class<? extends Node>> requirements = new HashSet<>(
                Arrays.asList(requires.nodes())
            );

            // Check children
            for (Node node : children) {
                requirements.removeIf(required ->
                    required.isAssignableFrom(node.getClass())
                );
            }

            // Throw exception if requirements are not met
            if (!requirements.isEmpty()) {
                throw new RuntimeException(
                    "Node " +
                    this.getClass().getSimpleName() +
                    " requires " +
                    requirements.iterator().next().getSimpleName() +
                    " to be initialized."
                );
            }
        }

        // Set initialized
        isInitialized = true;
    }

    /**
     * Tick the node and its children
     *
     * @param offset The offset of the node
     * @param delta The time since the last update
     */
    public final void tick(Transform offset, double delta) {
        // Re-initialize node
        if (!isInitialized) initialize();

        // Update node
        update(offset, delta);
    }

    /**
     * Update the node and its children
     *
     * @param offset The offset of the node
     * @param delta The time since the last update
     */
    public void update(Transform offset, double delta) {
        // Update children
        for (Node node : children) node.tick(offset, delta);
    }

    /**
     * Draw the node and its children
     * 
     * @param offset
     */
    public void draw(Transform offset) {
        // Draw visible children
        for (Node node : getChildren()) {
            if (node.isVisible()) {
                node.draw(offset);
            }
        }
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
    private void print(
        boolean isLast,
        Function<Node, String> formatter,
        List<String> indent
    ) {
        indent.forEach(System.out::print);
        System.out.print(isLast ? "└ " : "├ ");
        System.out.println(formatter.apply(this));

        if (isLast) indent.add("  ");
        else indent.add("│ ");

        for (int i = 0; i < children.size() - 1; i++) {
            ((Node) children.get(i)).print(false, formatter, indent);
        }
        if (!children.isEmpty()) {
            ((Node) children.getLast()).print(true, formatter, indent);
        }

        indent.removeLast();
    }
}
