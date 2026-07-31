import java.util.*;

public class KruskalMST {
    static class Edge implements Comparable<Edge> {
        int u, v;
        long w;
        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w; }
        public int compareTo(Edge other) {
            return Long.compare(this.w, other.w); }
        public String toString() {
            return String.format("%d - %d : %d", u, v, w); }
    }

    static class DSU {
        int[] p;
        int[] r;
        DSU(int n) {
            p = new int[n];
            r = new int[n];
            for (int i = 0; i < n; i++)
                p[i] = i; }
        int find(int x) {
            return p[x] == x ? x : (p[x] = find(p[x])); }
        boolean union(int a, int b) {
            a = find(a); b = find(b);
            if (a == b)
                return false;
            if (r[a] < r[b]) {
                p[a] = b; }
            else if (r[b] < r[a]) {
                p[b] = a; }
            else {
                p[b] = a; r[a]++; }
            return true;
        }
    }

    public static List<Edge> kruskal(int n, List<Edge> edges) {
        Collections.sort(edges);
        DSU dsu = new DSU(n);
        List<Edge> mst = new ArrayList<>();
        for (Edge e : edges) {
            if (dsu.union(e.u, e.v)) {
                mst.add(e);
                if (mst.size() == n - 1)
                    break;
            }
        }
        return mst;
    }

    // Example usage
    public static void main(String[] args) {
        int n = 5;
        List<Edge> edges = Arrays.asList(
                new Edge(0,1,2),
                new Edge(0,3,6),
                new Edge(1,2,3),
                new Edge(1,3,8),
                new Edge(1,4,5),
                new Edge(2,4,7),
                new Edge(3,4,9)
        );

        List<Edge> mst = kruskal(n, new ArrayList<>(edges));
        long total = 0;
        System.out.println("Kruskal MST edges:");
        for (Edge e : mst) { System.out.println("  " + e); total += e.w; }
        System.out.println("Total weight: " + total);
    }
}
