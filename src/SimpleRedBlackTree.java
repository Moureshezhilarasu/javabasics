/*
* Node color — Every node is either red or black.

Root property — The root is always black.

Leaf (NIL) property — All external leaves (NIL nodes) are black.

Red property — If a node is red, then both its children are black (no two red nodes adjacent).

Black-height property — Every path from a node to its descendant
* NIL leaves contains the same number of black nodes (same black-height)*/
public class SimpleRedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    class Node {
        int key;
        Node left, right;
        boolean color;

        Node(int key) {
            this.key = key;
            this.color = RED;
        }
    }

    private Node root;

    // Public insert method
    public void insert(int key) {
        root = insert(root, key);
        root.color = BLACK;
    }

    // Internal insert with balancing
    private Node insert(Node h, int key) {
        if (h == null) return new Node(key);

        if (key < h.key)
            h.left = insert(h.left, key);
        else if (key > h.key)
            h.right = insert(h.right, key);
        else
            return h; // duplicate keys not allowed

        // Fix right-leaning links
        if (isRed(h.right) && !isRed(h.left))
            h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))
            flipColors(h);

        return h;
    }

    // Check if node is red
    private boolean isRed(Node x) {
        return (x != null && x.color == RED);
    }

    // Rotate left
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    // Rotate right
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    // Flip colors
    private void flipColors(Node h) {
        h.color = RED;
        if (h.left != null)
            h.left.color = BLACK;
        if (h.right != null)
            h.right.color = BLACK;
    }

    // In-order traversal
    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(Node node) {
        if (node == null) return;
        inorder(node.left);
        System.out.print(node.key + (node.color == RED ? "R " : "B "));
        inorder(node.right);
    }

    // Search for a key
    public boolean contains(int key) {
        Node x = root;
        while (x != null) {
            if (key < x.key) x = x.left;
            else if (key > x.key) x = x.right;
            else return true;
        }
        return false;
    }

    // Main method to test
    public static void main(String[] args) {
        SimpleRedBlackTree tree = new SimpleRedBlackTree();
        int[] keys = {10, 20, 30,40,50,15, 25, 5};

        for (int key : keys)
            tree.insert(key);

        System.out.print("In-order traversal with colors: ");
        tree.inorder();

        System.out.println("Contains 15? " + tree.contains(15));
        System.out.println("Contains 100? " + tree.contains(100));
    }
}
