import java.util.ArrayList;
import java.util.List;

public class DataStructuresDemo {
    // ==========================================
    // 1. HEAP IMPLEMENTATION (Min-Heap)
    // Note: A Max-Heap uses the exact same logic, 
    // but the comparison operators (< and >) are reversed.
    // ==========================================
    static class MinHeap {
        private int[] heap;
        private int size;
        private int capacity;

        public MinHeap(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.heap = new int[capacity];
        }

        private int parent(int i) { 
            return (i - 1) / 2; }
        private int leftChild(int i) { 
            return 2 * i + 1; }
        private int rightChild(int i) { 
            return 2 * i + 2; }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        // Operation: Insertion
        public void insert(int element) {
            if (size >= capacity) {
                System.out.println("Heap is full!");
                return;
            }
            // Insert at the end
            heap[size] = element;
            int current = size;
            size++;

            // Heapify Up: Fix the min-heap property if it is violated
            while (current != 0 && heap[current] < heap[parent(current)]) {
                swap(current, parent(current));
                current = parent(current);
            }
        }

        // Operation: Deletion (Extract Min - Application for Priority Queue)
        public int extractMin() {
            if (size <= 0) 
                return Integer.MAX_VALUE;
            if (size == 1) {
                size--;
                return heap[0];
            }

            // Store the minimum value, and remove it from heap
            int root = heap[0];
            heap[0] = heap[size - 1]; // Move last element to root
            size--;
            heapifyDown(0); // Fix the min-heap property

            return root;
        }

        // Heapify Down
        private void heapifyDown(int i) {
            int smallest = i;
            int left = leftChild(i);
            int right = rightChild(i);

            if (left < size && heap[left] < heap[smallest])
                smallest = left;
            if (right < size && heap[right] < heap[smallest])
                smallest = right;

            if (smallest != i) {
                swap(i, smallest);
                heapifyDown(smallest);
            }
        }
    }

    // Application: Heap Sort (Using a Max-Heap approach mapped to an array)
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // Extract elements one by one from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) 
            largest = left;
        if (right < n && arr[right] > arr[largest]) 
            largest = right;

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }

    // ==========================================
    // 2. TRIE IMPLEMENTATION
    // ==========================================
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEndOfWord;

        public TrieNode() {
            isEndOfWord = false;
            for (int i = 0; i < 26; i++) {
                children[i] = null;
            }
        }
    }

    static class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // Operation: Insertion
        public void insert(String word) {
            TrieNode current = root;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }
                current = current.children[index];
            }
            current.isEndOfWord = true;
        }

        // Operation: Search (Application: Exact String Matching)
        public boolean search(String word) {
            TrieNode current = root;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                if (current.children[index] == null) 
                    return false;
                current = current.children[index];
            }
            return current.isEndOfWord;
        }

        // Operation: Deletion
        public void delete(String word) {
            deleteHelper(root, word, 0);
        }

        private boolean deleteHelper(TrieNode current, String word, int depth) {
            if (current == null) 
                return false;

            // Base case: Reached the end of the word
            if (depth == word.length()) {
                if (!current.isEndOfWord) return false; // Word doesn't exist
                current.isEndOfWord = false; // Unmark end of word
                return isEmpty(current); // If it has no children, it can be deleted
            }

            int index = word.charAt(depth) - 'a';
            boolean shouldDeleteChildNode = deleteHelper(current.children[index], word, depth + 1);

            if (shouldDeleteChildNode) {
                current.children[index] = null; // Delete child reference
                return !current.isEndOfWord && isEmpty(current); // Check if current node can also be deleted
            }
            return false;
        }

        private boolean isEmpty(TrieNode node) {
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) 
                    return false;
            }
            return true;
        }

        // Application: Autocomplete (Prefix Matching)
        public List<String> autocomplete(String prefix) {
            List<String> results = new ArrayList<>();
            TrieNode current = root;

            // Traverse to the end of the prefix
            for (int i = 0; i < prefix.length(); i++) {
                int index = prefix.charAt(i) - 'a';
                if (current.children[index] == null) {
                    return results; // Prefix not found
                }
                current = current.children[index];
            }

            // Perform Depth First Search from the end of the prefix
            findAllWords(current, new StringBuilder(prefix), results);
            return results;
        }

        private void findAllWords(TrieNode node, StringBuilder currentWord, List<String> results) {
            if (node.isEndOfWord) {
                results.add(currentWord.toString());
            }

            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    currentWord.append((char) (i + 'a'));
                    findAllWords(node.children[i], currentWord, results);
                    currentWord.deleteCharAt(currentWord.length() - 1); // Backtrack
                }
            }
        }
    }

    // ==========================================
    // 3. MAIN DEMONSTRATION
    // ==========================================
    public static void main(String[] args) {
        System.out.println("=== HEAP DEMONSTRATION ===");
        
        // Priority Queue Simulation (Min-Heap)
        MinHeap priorityQueue = new MinHeap(10);
        System.out.println("Inserting into Priority Queue: 15, 10, 30, 5, 20");
        priorityQueue.insert(15);
        priorityQueue.insert(10);
        priorityQueue.insert(30);
        priorityQueue.insert(5);
        priorityQueue.insert(20);
        
        System.out.println("Processing Priority Queue (Extracting Min):");
        System.out.print(priorityQueue.extractMin() + " "); // 5
        System.out.print(priorityQueue.extractMin() + " "); // 10
        System.out.println(priorityQueue.extractMin());     // 15

        // Heap Sort Demonstration
        int[] arrToSort = {12, 11, 13, 5, 6, 7};
        System.out.println("\nArray before Heap Sort:");
        for (int j : arrToSort) 
            System.out.print(j + " ");
        
        heapSort(arrToSort);
        
        System.out.println("\nArray after Heap Sort:");
        for (int j : arrToSort) 
            System.out.print(j + " ");
        System.out.println("\n");

        System.out.println("=== TRIE DEMONSTRATION ===");
        Trie trie = new Trie();
        
        // Insertion
        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");
        trie.insert("bat");
        trie.insert("batch");

        // String Matching (Search)
        System.out.println("Search 'app': " + trie.search("app")); // true
        System.out.println("Search 'appl': " + trie.search("appl")); // false

        // Autocomplete
        System.out.println("\nAutocomplete for prefix 'app':");
        List<String> suggestions = trie.autocomplete("app");
        for (String word : suggestions) {
            System.out.println("- " + word);
        }

        // Deletion
        System.out.println("\nDeleting 'app'...");
        trie.delete("app");
        System.out.println("Search 'app' after deletion: " + trie.search("app")); // false
        System.out.println("Search 'apple' after deleting 'app': " + trie.search("apple")); // true
        
        System.out.println("\nAutocomplete for prefix 'app' after deletion:");
        suggestions = trie.autocomplete("app");
        for (String word : suggestions) {
            System.out.println("- " + word);
        }
    }
}