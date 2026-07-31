
public class AVLTree<K extends Comparable<K>, V> {

    private class Node {
        K key;
        V value;
        Node left, right;
        int height;
        Node(K k, V v) {
            key = k;
            value = v;
            height = 1; }
    }

    private Node root=null;

    // Public API
    public void insert(K key, V value) {
        root = insert(root, key, value); }
    public boolean contains(K key) {
        return get(root, key) != null; }
    public V get(K key) {
        Node n = get(root, key);
        return n == null ? null : n.value; }
    public void delete(K key) {
        root = delete(root, key); }
    public void inorder() {
        inorder(root); System.out.println(); }

    // Internal helpers
    private Node get(Node node, K key) {
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) return node;
            node = cmp < 0 ? node.left : node.right;
        }
        return null;
    }

    private Node insert(Node node, K key, V value) {
        if (node == null)
            return new Node(key, value);
        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = insert(node.left, key, value);
        else if (cmp > 0)
            node.right = insert(node.right, key, value);
        else
            node.value = value;
        updateHeight(node);
        return rebalance(node);
    }

    private Node delete(Node node, K key) {
        if (node == null)
            return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = delete(node.left, key);
        else if (cmp > 0)
            node.right = delete(node.right, key);
        else {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            Node successor = min(node.right);
            node.key = successor.key;
            node.value = successor.value;
            node.right = delete(node.right, successor.key);
        }
        updateHeight(node);
        return rebalance(node);
    }

    private Node min(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private void inorder(Node node) {
        if (node == null) return;
        inorder(node.left);
        System.out.print(node.key + " ");
        inorder(node.right);
    }

    // AVL utilities
    private void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right)); }
    private int height(Node n) {
        return n == null ? 0 : n.height; }
    private int balanceFactor(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right); }

    private Node rebalance(Node node) {
        int bf = balanceFactor(node);
        if (bf > 1) {
            if (balanceFactor(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (bf < -1) {
            if (balanceFactor(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    // Example usage
    public static void main(String[] args) {
        AVLTree<Integer, String> avl = new AVLTree<>();
        avl.insert(10, "ten");
        avl.insert(20, "twenty");
        avl.insert(30, "thirty");
        avl.insert(40, "forty");
        avl.insert(50, "fifty");
        avl.insert(25, "twenty-five");
        System.out.print("Inorder AVL: ");
        avl.inorder();
        System.out.println("Contains 25: " + avl.contains(25));
        avl.delete(20);
        System.out.print("Inorder after deleting 20: ");
        avl.inorder();
    }
}
