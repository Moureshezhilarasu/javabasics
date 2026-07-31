import java.util.LinkedList;
/**
 * A Hash Table demonstration using both Chaining and Linear Probing.
 * 
 * Definition: A data structure that maps keys to values using a 
 * "hash function" to compute an index into an array of buckets.
 */
public class HashTableDemo {

    // --- 1. CHAINING IMPLEMENTATION ---
    // Handles collisions by storing multiple elements in a Linked List at each index.
    static class ChainingHash {
        private LinkedList<Node>[] table;
        private int size;

        static class Node {
            String key;
            int value;
            Node(String key, int value) { 
                this.key = key; 
                this.value = value; }
        }

        public ChainingHash(int size) {
            this.size = size;
            table = new LinkedList[size];
            for (int i = 0; i < size; i++) 
                table[i] = new LinkedList<>();
        }

        // Hashing Function: Simple modular arithmetic on the string's hashCode7
        private int hash(String key) {
            return Math.abs(key.hashCode()) % size;
        }

        public void put(String key, int value) {
            int idx = hash(key);
            for (Node node : table[idx]) {
                if (node.key.equals(key)) {
                    node.value = value;
                    return;
                }
            }
            table[idx].add(new Node(key, value));
        }

        public Integer get(String key) {
            int idx = hash(key);
            for (Node node : table[idx]) {
                if (node.key.equals(key)) 
                    return node.value;
            }
            return null;
        }
    }

    // --- 2. LINEAR PROBING IMPLEMENTATION ---
    // Handles collisions by searching for the next empty slot in the array.
    static class LinearProbingHash {
        private String[] keys;
        private Integer[] values;
        private int size;

        public LinearProbingHash(int size) {
            this.size = size;
            keys = new String[size];
            values = new Integer[size];
        }

        private int hash(String key) {
            return Math.abs(key.hashCode()) % size;
        }

        public void put(String key, int value) {
            int i = hash(key);
            // Linear Probing: move to next index if slot is occupied
            while (keys[i] != null) {
                if (keys[i].equals(key)) {
                    values[i] = value;
                    return;
                }
                i = (i + 1) % size;
            }
            keys[i] = key;
            values[i] = value;
        }

        public Integer get(String key) {
            int i = hash(key);
            while (keys[i] != null) {
                if (keys[i].equals(key)) 
                    return values[i];
                i = (i + 1) % size;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        ChainingHash map = new ChainingHash(10);
        map.put("User123", 95);
        System.out.println("Chaining Get: " + map.get("User123"));

        LinearProbingHash lpMap = new LinearProbingHash(10);
        lpMap.put("ID_45", 100);
        System.out.println("Linear Probing Get: " + lpMap.get("ID_45"));
    }
}
