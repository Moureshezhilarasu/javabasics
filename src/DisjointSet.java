/**
 * Disjoint Set Union (Union-Find) with path compression and union by rank.
 * Provides find, union, and ability to check if two nodes are connected.
 */
public class DisjointSet {
    private final int[] parent;
    private final int[] rank;

    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    /** Find representative (with path compression). */
    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    /** Union by rank. Returns true if union changed the structure (merged two sets). */
    public boolean union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return false;
        if (rank[ra] < rank[rb]) {
            parent[ra] = rb;
        } else if (rank[rb] < rank[ra]) {
            parent[rb] = ra;
        } else {
            parent[rb] = ra;
            rank[ra]++;
        }
        return true;
    }

    /** Check if two elements are in the same set. */
    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    // Demo
    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet(7);
        ds.union(0, 1);
        ds.union(1, 2);
        ds.union(3, 4);
        ds.union(5, 6);

        System.out.println("connected(0,2): " + ds.connected(0, 2)); // true
        System.out.println("connected(0,3): " + ds.connected(0, 3)); // false

        ds.union(2, 3);
        System.out.println("After union(2,3), connected(0,4): " + ds.connected(0, 4)); // true

        // show representatives
        for (int i = 0; i < 7; i++) {
            System.out.println("rep(" + i + ") = " + ds.find(i));
        }
    }
}
