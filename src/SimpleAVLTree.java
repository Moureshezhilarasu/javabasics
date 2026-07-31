public class SimpleAVLTree {
    class Node {
        int key, height;
        Node left, right;
        Node(int d) {
            key = d;
            height = 1;
            left=null;
            right=null;
        }
    }
    private Node root;
    // Get height of a node
    int height(Node N) {
        return (N == null) ? 0 : N.height;
    }
    // Get balance factor
    int getBalance(Node N) {

        return (N == null) ? 0 : height(N.left) - height(N.right);
    }

    // Right rotate
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotate
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Insert a key
    Node insert(Node node, int key) {
        // 1. Perform normal BST insert
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Duplicate keys not allowed
            return node;

        // 2. Update height
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 3. Get balance factor
        int balance = getBalance(node);

        // 4. Balance the tree
        // Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Public insert method
    public void insert(int key) {
        root = insert(root, key);
    }

    // In-order traversal
    void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    public void printInOrder() {
        inorder(root);
        System.out.println();
    }
    // Main method to test
    public static void main(String[] args) {
        SimpleAVLTree vishal = new SimpleAVLTree();

        int[] keys = { 10, 20, 30, 40, 50, 25 };

        for (int key : keys)
            vishal.insert(key);

        System.out.print("In-order traversal of AVL tree: ");
        vishal.printInOrder();
    }
}
