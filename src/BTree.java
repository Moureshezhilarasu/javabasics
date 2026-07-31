/* Simple BTree implementation (order = max children)
* BALANCED M(ORDER)-WAY TREE
* GENERALIZATION OF BST IN WHICH A NODE CAN HAVE MORE THAN ONE KEY MORE THAN 2 CHILD
* MAINTAINS SORTED DATA
* ALL LEAF NODE MUST BE @ SAME LEVEL
* B-TREE OF ORDER M HAS FOLLOWING PROPERTIES:
*   1.EVERY NODE HAS MAX M CHILD
*   2.MIN.CHILD:
*       (I)     LEAF=0
*       (II)    ROOT=2
*       (III)   INTERNAL NODE=(M/2)
*   3.EVERY NODE HAS MAX(M-1) KEYS
*   4.MIN.KEY:
*       (I)     ROOT NODE   =L
*       (II)    ALL OTHER NODES =(M/2)-1
* */
import java.util.Arrays;

public class BTree {
    private final int t; // minimum degree (t >= 2)
    private Node root;
/*
* class node{
* int data;
* node left , right}
* */
    private static class Node {
        int n; // number of keys
        int[] keys;
        Object[] values;
        Node[] children;
        boolean leaf;

        Node(int t, boolean leaf) {
            this.leaf = leaf;
            keys = new int[2*t - 1];
            values = new Object[2*t - 1];
            children = new Node[2*t];
            n = 0;
        }
    }

    public BTree(int t) {
        if (t < 2) throw new IllegalArgumentException("t must be >= 2");
        this.t = t;
        root = new Node(t, true);
    }

    public Object search(int key) {

        return search(root, key);
    }

    private Object search(Node x, int key) {
        int i = 0;
        while (i < x.n && key > x.keys[i]) i++;
        if (i < x.n && key == x.keys[i]) return x.values[i];
        if (x.leaf) return null;
        return search(x.children[i], key);
    }

    public void insert(int key, Object value) {
        Node r = root;
        if (r.n == 2*t - 1) {
            Node s = new Node(t, false);
            root = s;
            s.children[0] = r;
            splitChild(s, 0, r);
            insertNonFull(s, key, value);
        } else {
            insertNonFull(r, key, value);
        }
    }

    private void splitChild(Node parent, int i, Node y) {
        Node z = new Node(t, y.leaf);
        z.n = t - 1;
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
            z.values[j] = y.values[j + t];
        }
        if (!y.leaf) {
            for (int j = 0; j < t; j++)
                z.children[j] = y.children[j + t];
        }
        y.n = t - 1;
        for (int j = parent.n; j >= i+1; j--)
            parent.children[j+1] = parent.children[j];
        parent.children[i+1] = z;
        for (int j = parent.n - 1; j >= i; j--)
            parent.keys[j+1] = parent.keys[j];
        for (int j = parent.n - 1; j >= i; j--)
            parent.values[j+1] = parent.values[j];
        parent.keys[i] = y.keys[t-1];
        parent.values[i] = y.values[t-1];
        parent.n += 1;
    }

    private void insertNonFull(Node x, int key, Object value) {
        int i = x.n - 1;
        if (x.leaf) {
            while (i >= 0 && key < x.keys[i]) {
                x.keys[i+1] = x.keys[i];
                x.values[i+1] = x.values[i];
                i--;
            }
            x.keys[i+1] = key;
            x.values[i+1] = value;
            x.n += 1;
        } else {
            while (i >= 0 && key < x.keys[i])
                i--;
            i++;
            if (x.children[i].n == 2*t - 1) {
                splitChild(x, i, x.children[i]);
                if (key > x.keys[i]) i++;
            }
            insertNonFull(x.children[i], key, value);
        }
    }

    // For debugging: inorder traversal
    public void traverse() {
        traverse(root); System.out.println(); }
    private void traverse(Node x) {
        int i;
        for (i = 0; i < x.n; i++) {
            if (!x.leaf) traverse(x.children[i]);
            System.out.print(x.keys[i] + " ");
        }
        if (!x.leaf) traverse(x.children[i]);
    }

    public static void main(String[] args) {
        BTree b = new BTree(3); // t=3
        int[] keys = {10, 20,35, 5, 6, 12, 30, 7, 17,-4,99};
        for (int k : keys)
            b.insert(k, "V"+k);
        b.traverse(); // prints keys in sorted order
        System.out.println("Search 6 -> " + b.search(6));
        System.out.println("Search 15 -> " + b.search(15));
    }
}
