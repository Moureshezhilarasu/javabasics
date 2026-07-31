import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class BPlusTree {
    private final int m; // max children per internal node
    private Node root;

    private abstract class Node {
        List<Integer> keys = new ArrayList<>();
        abstract boolean isLeaf();
    }

    private class InternalNode extends Node {
        List<Node> children = new ArrayList<>();
        boolean isLeaf() {
            return false; }
    }

    private class LeafNode extends Node {
        List<Object> values = new ArrayList<>();
        LeafNode next;
        boolean isLeaf() {
            return true; }
    }

    public BPlusTree(int m) {
        if (m < 3) throw new IllegalArgumentException("m must be >= 3");
        this.m = m;
        root = new LeafNode();
    }

    public Object search(int key) {
        LeafNode leaf = findLeaf(root, key);
        for (int i = 0; i < leaf.keys.size(); i++) {
            if (leaf.keys.get(i) == key) return leaf.values.get(i);
        }
        return null;
    }

    private LeafNode findLeaf(Node node, int key) {
        if (node.isLeaf()) return (LeafNode) node;
        InternalNode in = (InternalNode) node;
        int idx = 0;
        while (idx < in.keys.size() && key >= in.keys.get(idx)) idx++;
        return findLeaf(in.children.get(idx), key);
    }

    public void insert(int key, Object value) {
        Node r = root;
        if (r.isLeaf()) {
            LeafNode leaf = (LeafNode) r;
            insertIntoLeaf(leaf, key, value);
            if (leaf.keys.size() >= m) splitLeaf(null, leaf, null);
        } else {
            InsertResult res = insertRecursive(null, (InternalNode) r, key, value);
            if (res != null && res.newChild != null) {
                // root split
                InternalNode newRoot = new InternalNode();
                newRoot.keys.add(res.splitKey);
                newRoot.children.add(r);
                newRoot.children.add(res.newChild);
                root = newRoot;
            }
        }
    }

    private void insertIntoLeaf(LeafNode leaf, int key, Object value) {
        int i = 0;
        while (i < leaf.keys.size() && leaf.keys.get(i) < key) i++;
        leaf.keys.add(i, key);
        leaf.values.add(i, value);
    }

    private class InsertResult { int splitKey; Node newChild; }

    private InsertResult insertRecursive(InternalNode parent, InternalNode node, int key, Object value) {
        int idx = 0;
        while (idx < node.keys.size() && key >= node.keys.get(idx)) idx++;
        Node child = node.children.get(idx);
        if (child.isLeaf()) {
            LeafNode leaf = (LeafNode) child;
            insertIntoLeaf(leaf, key, value);
            if (leaf.keys.size() >= m) {
                return splitLeaf(node, leaf, idx);
            }
            return null;
        } else {
            InternalNode childInternal = (InternalNode) child;
            InsertResult res = insertRecursive(node, childInternal, key, value);
            if (res != null && res.newChild != null) {
                node.keys.add(idx, res.splitKey);
                node.children.add(idx+1, res.newChild);
                if (node.children.size() > m) {
                    return splitInternal(node);
                }
            }
            return null;
        }
    }

    private InsertResult splitLeaf(InternalNode parent, LeafNode leaf, Integer childIndex) {
        int mid = (m+1)/2;
        LeafNode newLeaf = new LeafNode();
        for (int i = mid; i < leaf.keys.size(); ) {
            newLeaf.keys.add(leaf.keys.remove(mid));
            newLeaf.values.add(leaf.values.remove(mid));
        }
        newLeaf.next = leaf.next;
        leaf.next = newLeaf;
        int splitKey = newLeaf.keys.get(0);
        if (parent != null) {
            return new InsertResult(){ { splitKey = splitKey; newChild = newLeaf; } };
        } else {
            // root was leaf: create new root
            InternalNode newRoot = new InternalNode();
            newRoot.keys.add(splitKey);
            newRoot.children.add(leaf);
            newRoot.children.add(newLeaf);
            root = newRoot;
            return null;
        }
    }

    private InsertResult splitInternal(InternalNode node) {
        int midIndex = node.keys.size() / 2;
        int splitKey = node.keys.get(midIndex);
        InternalNode newNode = new InternalNode();
        // keys after midIndex move to new node
        for (int i = midIndex + 1; i < node.keys.size(); ) {
            newNode.keys.add(node.keys.remove(midIndex+1));
        }
        // children after midIndex move
        while (node.children.size() > midIndex + 1) {
            newNode.children.add(node.children.remove(midIndex+1));
        }
        // remove the splitKey from original
        node.keys.remove(midIndex);
        InsertResult res = new InsertResult();
        res.splitKey = splitKey;
        res.newChild = newNode;
        return res;
    }

    // For demonstration: iterate leaves
    public void printLeaves() {
        Node n = root;
        while (!n.isLeaf()) n = ((InternalNode)n).children.get(0);
        LeafNode leaf = (LeafNode)n;
        while (leaf != null) {
            System.out.println(leaf.keys);
            leaf = leaf.next;
        }
    }
    public static void main(String[] args) {
        BPlusTree b = new BPlusTree(3); // t=3
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int k : keys)
            b.insert(k, "V"+k);
        System.out.println("leaves:");
        b.printLeaves();
        System.out.println("Element:"+b.search(5));
        System.out.println("Search 6 -> " + b.search(6));
        System.out.println("Search 15 -> " + b.search(15));
    }
}
