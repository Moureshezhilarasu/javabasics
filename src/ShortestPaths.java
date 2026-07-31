// File: ShortestPaths.java
import java.util.*;

public class ShortestPaths {
    // Dijkstra for non-negative weights
    public static class Result {
        public final int[] dist;
        public final int[] parent;
        public Result(int[] dist, int[] parent) { 
            this.dist = dist; 
            this.parent = parent; }
    }

    public static Result dijkstra(GraphAdjList g, int src) {
        int n = g.size();
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.add(new int[]{0, src});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0], u = cur[1];
            if (d > dist[u]) continue;
            for (GraphAdjList.Edge e : g.neighbors(u)) {
                int v = e.to, w = e.weight;
                if (w == Integer.MAX_VALUE) continue;
                long nd = (long) d + w;
                if (nd < dist[v]) {
                    dist[v] = (int) nd;
                    parent[v] = u;
                    pq.add(new int[]{dist[v], v});
                }
            }
        }
        return new Result(dist, parent);
    }

    // Bellman-Ford: supports negative weights; returns null if negative cycle reachable
    public static Result bellmanFord(GraphAdjList g, int src) {
        int n = g.size();
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean updated = false;
            for (int u = 0; u < n; u++) {
                if (dist[u] == Integer.MAX_VALUE) continue;
                for (GraphAdjList.Edge e : g.neighbors(u)) {
                    int v = e.to, w = e.weight;
                    if (w == Integer.MAX_VALUE) continue;
                    if (dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                        parent[v] = u;
                        updated = true;
                    }
                }
            }
            if (!updated) break;
        }
        // check negative cycle
        for (int u = 0; u < n; u++) {
            if (dist[u] == Integer.MAX_VALUE) continue;
            for (GraphAdjList.Edge e : g.neighbors(u)) {
                int v = e.to, w = e.weight;
                if (w == Integer.MAX_VALUE) continue;
                if (dist[u] + w < dist[v]) return null; // negative cycle
            }
        }
        return new Result(dist, parent);
    }

    // Utility to reconstruct path from parent[]
    public static List<Integer> reconstructPath(int[] parent, int target) {
        List<Integer> path = new ArrayList<>();
        for (int v = target; v != -1; v = parent[v]) path.add(v);
        Collections.reverse(path);
        return path;
    }

    // Example
    public static void main(String[] args) {
        GraphAdjList g = new GraphAdjList(6);
        g.addDirectedEdge(0,1,7);
        g.addDirectedEdge(0,2,9);
        g.addDirectedEdge(0,5,14);
        g.addDirectedEdge(1,2,10);
        g.addDirectedEdge(1,3,15);
        g.addDirectedEdge(2,3,11);
        g.addDirectedEdge(2,5,2);
        g.addDirectedEdge(3,4,6);
        g.addDirectedEdge(4,5,9);

        Result r = dijkstra(g, 0);
        System.out.println("Dijkstra dist: " + Arrays.toString(r.dist));
        System.out.println("Path 0->4: " + reconstructPath(r.parent, 4));

        Result r2 = bellmanFord(g, 0);
        System.out.println("Bellman-Ford dist: " + Arrays.toString(r2.dist));
    }
}
