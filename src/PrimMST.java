import java.util.*;

public class PrimMST {
    static class Node {
        int to;
        long w;
        Node(int to, long w) { this.to = to; this.w = w; }
    }

    static class PQNode implements Comparable<PQNode> {
        int v;
        long key;
        int parent;
        PQNode(int v, long key, int parent) { this.v = v; this.key = key; this.parent = parent; }
        public int compareTo(PQNode o) { return Long.compare(this.key, o.key); }
    }

    public static List<int[]> prim(int n, List<List<Node>> graph, int start) {
        boolean[] used = new boolean[n];
        long[] key = new long[n];
        int[] parent = new int[n];
        Arrays.fill(key, Long.MAX_VALUE);
        Arrays.fill(parent, -1);

        PriorityQueue<PQNode> pq = new PriorityQueue<>();
        key[start] = 0;
        pq.add(new PQNode(start, 0, -1));

        List<int[]> mst = new ArrayList<>();

        while (!pq.isEmpty()) {
            PQNode cur = pq.poll();
            if (used[cur.v]) continue;
            used[cur.v] = true;
            if (cur.parent != -1) {
                mst.add(new int[]{cur.parent, cur.v, (int)cur.key});
            }
            for (Node e : graph.get(cur.v)) {
                if (!used[e.to] && e.w < key[e.to]) {
                    key[e.to] = e.w;
                    parent[e.to] = cur.v;
                    pq.add(new PQNode(e.to, key[e.to], cur.v));
                }
            }
        }

        return mst;
    }

    // Example usage
    public static void main(String[] args) {
        int n = 5;
        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        // undirected edges
        addEdge(graph,0,1,2);
        addEdge(graph,0,3,6);
        addEdge(graph,1,2,3);
        addEdge(graph,1,3,8);
        addEdge(graph,1,4,5);
        addEdge(graph,2,4,7);
        addEdge(graph,3,4,9);

        List<int[]> mst = prim(n, graph, 0);
        long total = 0;
        System.out.println("Prim MST edges:");
        for (int[] e : mst) {
            System.out.printf("  %d - %d : %d%n", e[0], e[1], e[2]);
            total += e[2];
        }
        System.out.println("Total weight: " + total);
    }

    static void addEdge(List<List<Node>> g, int u, int v, long w) {
        g.get(u).add(new Node(v, w));
        g.get(v).add(new Node(u, w));
    }
}
