/**
 * AdjacencyListGraph.java
 * Graph implementation using adjacency lists.
 *
 * Features
 * - Supports directed or undirected graphs
 * - Add / remove edges
 * - Check adjacency
 * - Print adjacency lists
 * - DFS and BFS traversals
 * - Shortest path in unweighted graph (BFS)
 *
 * Compile: javac AdjacencyListGraph.java
 * Run:     java AdjacencyListGraph
 */

import java.util.*;
public class AdjacencyListGraph {
    private final int n;                     // number of vertices (0..n-1)
    private final List<List<Integer>> adj;   // adjacency lists
    private final boolean directed;

    // Constructor
    public AdjacencyListGraph(int n, boolean directed) {
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");
        this.n = n;
        this.directed = directed;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    // Add edge u -> v (for undirected also adds v -> u)
    public void addEdge(int u, int v) {
        checkVertex(u);
        checkVertex(v);
        if (!adj.get(u).contains(v)) adj.get(u).add(v);
        if (!directed && !adj.get(v).contains(u)) adj.get(v).add(u);
    }

    // Remove edge u -> v
    public void removeEdge(int u, int v) {
        checkVertex(u);
        checkVertex(v);
        adj.get(u).remove(Integer.valueOf(v));
        if (!directed) adj.get(v).remove(Integer.valueOf(u));
    }

    // Check if u -> v exists
    public boolean hasEdge(int u, int v) {
        checkVertex(u);
        checkVertex(v);
        return adj.get(u).contains(v);
    }

    // Get neighbors of vertex u
    public List<Integer> neighbors(int u) {
        checkVertex(u);
        return Collections.unmodifiableList(adj.get(u));
    }

    // Print adjacency lists
    public void printAdjacencyLists() {
        System.out.println("Adjacency Lists" + (directed ? " directed" : " undirected"));
        for (int u = 0; u < n; u++) {
            System.out.print(u + ": ");
            List<Integer> list = adj.get(u);
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i));
                if (i < list.size() - 1) System.out.print(" -> ");
            }
            System.out.println();
        }
    }

    // DFS (iterative) returning traversal order
    public List<Integer> dfs(int start) {
        checkVertex(start);
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (visited[u]) continue;
            visited[u] = true;
            order.add(u);
            List<Integer> nbrs = adj.get(u);
            // push neighbors in reverse order to preserve natural iteration order
            for (int i = nbrs.size() - 1; i >= 0; i--) {
                int v = nbrs.get(i);
                if (!visited[v]) stack.push(v);
            }
        }
        return order;
    }

    // BFS returning traversal order
    public List<Integer> bfs(int start) {
        checkVertex(start);
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();
        Queue<Integer> q = new ArrayDeque<>();
        visited[start] = true;
        q.add(start);
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }
        return order;
    }

    // Shortest path in unweighted graph using BFS
    // Returns list of vertices from start to target or empty list if no path
    public List<Integer> shortestPathUnweighted(int start, int target) {
        checkVertex(start);
        checkVertex(target);
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        Queue<Integer> q = new ArrayDeque<>();
        visited[start] = true;
        q.add(start);
        boolean found = false;
        while (!q.isEmpty()) {
            int u = q.poll();
            if (u == target) { found = true; break; }
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    q.add(v);
                }
            }
        }
        if (!found) return Collections.emptyList();
        // Reconstruct path
        LinkedList<Integer> path = new LinkedList<>();
        for (int cur = target; cur != -1; cur = parent[cur]) path.addFirst(cur);
        return path;
    }

    // Degree methods
    public int outDegree(int u) {
        checkVertex(u);
        return adj.get(u).size();
    }

    public int inDegree(int u) {
        checkVertex(u);
        if (!directed) return outDegree(u);
        int deg = 0;
        for (int i = 0; i < n; i++) if (adj.get(i).contains(u)) deg++;
        return deg;
    }

    // Validate vertex index
    private void checkVertex(int v) {
        if (v < 0 || v >= n) throw new IllegalArgumentException("Vertex " + v + " out of bounds");
    }

    // Demo and simple tests
    public static void main(String[] args) {
        AdjacencyListGraph g = new AdjacencyListGraph(7, false); // undirected
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(2, 6);
        g.addEdge(4, 5);

        g.printAdjacencyLists();

        System.out.println();
        System.out.println("DFS from 0: " + g.dfs(0));
        System.out.println("BFS from 0: " + g.bfs(0));

        System.out.println();
        System.out.println("Shortest path from 0 to 6: " + g.shortestPathUnweighted(0, 6));

        System.out.println();
        System.out.println("Degrees:");
        for (int i = 0; i < 7; i++) {
            System.out.printf("Vertex %d out-degree = %d in-degree = %d%n", i, g.outDegree(i), g.inDegree(i));
        }

        // Directed example
        AdjacencyListGraph dg = new AdjacencyListGraph(5, true);
        dg.addEdge(0, 1);
        dg.addEdge(0, 2);
        dg.addEdge(2, 3);
        dg.addEdge(1, 3);
        dg.addEdge(3, 4);

        System.out.println();
        dg.printAdjacencyLists();
        System.out.println("Directed BFS from 0: " + dg.bfs(0));
        System.out.println("Directed shortest path 0 to 4: " + dg.shortestPathUnweighted(0, 4));
    }
}
