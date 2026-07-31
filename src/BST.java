import java.util.Scanner;
public class BST {

    public static class Node {

        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    public Node root;

    public BST() {
        root = null;
    }

    public void insert(int data) {
        Node nn = new Node(data);

        if (root == null) {
            root = nn;
            return;
        } else {
            Node currentNode = root, parentNode = null;
            while (true) {
                parentNode = currentNode;
                if (data < currentNode.data) {
                    currentNode = currentNode.left;
                    if (currentNode == null) {
                        parentNode.left = nn;
                        return;

                    }
                } else {
                    currentNode = currentNode.right;
                    if (currentNode == null) {
                        parentNode.right = nn;
                        return;
                    }
                }
            }
        }
    }

    public Node minNode(Node root) {
        if (root.left != null) {
            return minNode(root.left);
        } else {
            return root;
        }
    }

    public Node delete(Node node, int value) {
        if (node == null) {
            return null;
        } else {
            if (value < node.data) {
                node.left = delete(node.left, value);
            } else if (value > node.data) {
                node.right = delete(node.right, value);
            } else {
                if (node.left == null && node.right == null) {
                    node = null;
                } else if (node.left == null) {
                    node = node.left;
                } else {
                    Node temp1 = minNode(node.right);
                    node.data = temp1.data;
                    node.right = delete(node.right, temp1.data);
                }
            }
            return node;
        }
    }

    public void inOrderTraversal(Node node) {
        if (root == null) {
            System.out.print("Tree is Empty!");
            return;
        } else {
            if (node.left != null) {
                inOrderTraversal(node.left);
            }
            System.out.println(node.data + " ");
            if (node.right != null) {
                inOrderTraversal(node.right);
            }
        }
    }

    public void preOrderTraversal(Node node) {
        if (root == null) {
            System.out.print("Tree is Empty!");
            return;
        } else {
            System.out.println(node.data + " ");
            if (node.left != null) {
                preOrderTraversal(node.left);
            }
            if (node.right != null) {
                preOrderTraversal(node.right);
            }
        }
    }

    public void postOrderTraversal(Node node) {
        if (root == null) {
            System.out.print("Tree is Empty!");
            return;
        } else {
            if (node.left != null) {
                postOrderTraversal(node.left);
            }
            if (node.right != null) {
                postOrderTraversal(node.right);
            }
            System.out.print(node.data + " ");
        }
    }

    public static void main(String[] args) {

        BST bt = new BST();

        Scanner sc = new Scanner(System.in);

        boolean flagnew = false;

        while (flagnew == false) {
            System.out.println();
            System.out.println("****************************************");
            System.out.println("1.Insert\n2.Delete\n3.Inorder\n"
                    + "4.Preorder\n5.Postorder\n6.Exit\n");
            System.out.println("Select an option: ");
            System.out.println("****************************************");
            int n = sc.nextInt();
            switch (n) {
                case 1:
                    System.out.println("Enter data to be inserted: ");
                    int val1 = sc.nextInt();
                    bt.insert(val1);
                    System.out.println("\nAfter Insertion: ");
                    bt.inOrderTraversal(bt.root);
                    break;

                case 2:
                    System.out.println("Enter data to be deleted: ");
                    Node temp = null;
                    int val2 = sc.nextInt();
                    temp = bt.delete(bt.root, val2);
                    System.out.println("\nAfter Deletion: ");
                    bt.inOrderTraversal(bt.root);
                    break;

                case 3:
                    System.out.println("InOrder Traversal: ");
                    bt.inOrderTraversal(bt.root);
                    break;

                case 4:
                    System.out.println("PreOrder Traversal: ");
                    bt.preOrderTraversal(bt.root);
                    break;

                case 5:
                    System.out.println("PostOrder Traversal: ");
                    bt.postOrderTraversal(bt.root);
                    break;

                case 6:
                    System.out.println("Thank You!");
                    flagnew = true;
                    break;
            }
        }
    }

}
