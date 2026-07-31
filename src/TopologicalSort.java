import java.util.*;
/**
 * Kahn's algorithm for topological sort (BFS / indegree method).
 * Produces a topological order for a directed acyclic graph (DAG).
 * If the graph has a cycle, the returned list will have size < n.
 */
public class TopologicalSort {
    /**
     * Compute topological order of a directed graph with n vertices (0..n-1).
     * @param n number of vertices
     * @param edges list of directed edges [u, v] meaning u -> v
     * @return list with topological order, or a list with size < n if cycle exists
     */
    public static List<Integer> topoSort(int n, List<int[]> edges) {
        List<List<Integer>> g = new ArrayList<>(n);
        int[] indeg = new int[n];
        for (int i = 0; i < n; i++) 
            g.add(new ArrayList<>());
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            g.get(u).add(v);
            indeg[v]++;
        }

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) 
            if (indeg[i] == 0) 
                q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : g.get(u)) {
                if (--indeg[v] == 0) q.add(v);
            }
        }
        return order;
    }

    // Demo
    public static void main(String[] args) {
        // Example DAG:
        // 5 -> 2, 5 -> 0, 4 -> 0, 4 -> 1, 2 -> 3, 3 -> 1
        int n = 6;
        List<int[]> edges = Arrays.asList(
                new int[]{5,2}, new int[]{5,0}, new int[]{4,0},
                new int[]{4,1}, new int[]{2,3}, new int[]{3,1}
        );

        List<Integer> order = topoSort(n, edges);
        if (order.size() < n) {
            System.out.println("Graph has a cycle; topological order not possible.");
        } else {
            System.out.println("Topological order: " + order);
        }

        // Example with a cycle:
        List<int[]> cyc = Arrays.asList(new int[]{0,1}, new int[]{1,2}, new int[]{2,0});
        List<Integer> order2 = topoSort(3, cyc);
        System.out.println("Order for cyclic graph (size " + order2.size() + "): " + order2);
    }
}
