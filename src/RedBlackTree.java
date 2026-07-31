/*
1.IF TREE IS EMPTY . CREATE NEW NODE AS ROOT WITH COLOR BLACK
2.IF TREE IS NOT EMPTY, CREATE NEW NODE AS LEAF NODE WITH COLOR RED
3.IF PARENT OF NEW NODE IS BLACK EXIT
4.IF PARENT OF NEW NODE IS RED THEN CHECK THE COLOR IF PARENT'S SIBLING OF NEW NODE
    a. IF COLOR IS BLACK OR NULL THEN DO SUITABLE ROTATION & RECOLOR
    b.IF COLOR IS RED THEN RECOLOR & ALSO CHECK IF PARENT'S OF NEW NODE IS NOT ROOT NODE THEN RECOLOR & RECHECK

 */
public class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        K key;
        V val;
        Node left, right;
        boolean color;
        Node(K k, V v, boolean c) {
            key = k;
            val = v;
            color = c; }
    }

    private Node root;

    // Public API
    public void insert(K key, V value) {
        root = insert(root, key, value);
        root.color = BLACK; }
    public boolean contains(K key) {
        return get(root, key) != null; }
    public V get(K key) {
        Node n = get(root, key);
        return n == null ? null : n.val; }
    public void inorder() {
        inorder(root);
        System.out.println(); }

    // Helpers
    private Node get(Node x, K key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0)
                return x;
            x = cmp < 0 ? x.left : x.right;
        }
        return null;
    }

    private Node insert(Node h, K key, V val) {
        if (h == null)
            return new Node(key, val, RED);
        int cmp = key.compareTo(h.key);
        if (cmp < 0)
            h.left = insert(h.left, key, val);
        else if (cmp > 0)
            h.right = insert(h.right, key, val);
        else
            h.val = val;

        // Fix right-leaning links and eliminate 4-nodes on the way up
        if (isRed(h.right) && !isRed(h.left))
            h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))
            flipColors(h);

        return h;
    }

    private boolean isRed(Node n) {
        return n != null && n.color == RED; }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        if (h.left != null)
            h.left.color = BLACK;
        if (h.right != null)
            h.right.color = BLACK;
    }

    private void inorder(Node node) {
        if (node == null)
            return;
        inorder(node.left);
        System.out.print(node.key + (node.color == RED ? "R " : "B "));
        inorder(node.right);
    }

    // Example usage
    public static void main(String[] args) {
        RedBlackTree<Integer,String> rbt = new RedBlackTree<>();
        int[] keys = {10, 20, 30, 40, 50, 25};
        for (int k : keys)
            rbt.insert(k, "v" + k);
        System.out.print("Inorder RedBlack keyColor: ");
        rbt.inorder();
        System.out.println("Contains 25: " + rbt.contains(25));
        System.out.println("Value for 30: " + rbt.get(30));
    }
}
