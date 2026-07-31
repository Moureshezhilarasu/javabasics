/**
 * AdjacencyMatrixGraph.java
 * Simple graph implementation using an adjacency matrix.
 *
 * Features
 * - Supports directed or undirected graphs
 * - Add / remove edges
 * - Check adjacency
 * - Print adjacency matrix
 * - DFS and BFS traversals
 * - Get degree (in/out/undirected degree)
 *
 * Compile: javac AdjacencyMatrixGraph.java
 * Run:     java AdjacencyMatrixGraph
 */

import java.util.*;

public class AdjacencyMatrixGraph {
    private final int n;                // number of vertices (0..n-1)
    private final int[][] matrix;       // adjacency matrix (0/1 or weights)
    private final boolean directed;

    // Constructor
    public AdjacencyMatrixGraph(int n, boolean directed) {
        if (n <= 0) throw new IllegalArgumentException("Number of vertices must be > 0");
        this.n = n;
        this.directed = directed;
        this.matrix = new int[n][n];
    }

    // Add an edge (unweighted). For weighted graphs you can change type and store weight.
    public void addEdge(int u, int v) {
        checkVertex(u);
        checkVertex(v);
        matrix[u][v] = 1;
        if (!directed) matrix[v][u] = 1;
    }

    // Remove an edge
    public void removeEdge(int u, int v) {
        checkVertex(u);
        checkVertex(v);
        matrix[u][v] = 0;
        if (!directed) matrix[v][u] = 0;
    }

    // Check if there is an edge u -> v
    public boolean hasEdge(int u, int v) {
        checkVertex(u);
        checkVertex(v);
        return matrix[u][v] != 0;
    }

    // Print adjacency matrix
    public void printMatrix() {
        System.out.println("Adjacency Matrix" + (directed ? " (directed)" : " (undirected)"));
        System.out.print("    ");
        for (int j = 0; j < n; j++) System.out.printf("%3d", j);
        System.out.println();
        System.out.println("   " + "-".repeat(n * 3));
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < n; j++) {
                System.out.printf("%3d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    // Depth-first search (recursive)
    public List<Integer> dfs(int start) {
        checkVertex(start);
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();
        dfsHelper(start, visited, order);
        return order;
    }

    private void dfsHelper(int u, boolean[] visited, List<Integer> order) {
        visited[u] = true;
        order.add(u);
        for (int v = 0; v < n; v++) {
            if (matrix[u][v] != 0 && !visited[v]) dfsHelper(v, visited, order);
        }
    }

    // Breadth-first search
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
            for (int v = 0; v < n; v++) {
                if (matrix[u][v] != 0 && !visited[v]) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }
        return order;
    }

    // For directed graphs: get in-degree and out-degree; for undirected: degree
    public int outDegree(int u) {
        checkVertex(u);
        int deg = 0;
        for (int v = 0; v < n; v++) if (matrix[u][v] != 0) deg++;
        return deg;
    }

    public int inDegree(int u) {
        checkVertex(u);
        int deg = 0;
        for (int v = 0; v < n; v++) if (matrix[v][u] != 0) deg++;
        return deg;
    }

    public int degree(int u) {
        checkVertex(u);
        if (directed) return inDegree(u) + outDegree(u);
        return outDegree(u);
    }

    // Helper to validate vertex index
    private void checkVertex(int v) {
        if (v < 0 || v >= n) throw new IllegalArgumentException("Vertex " + v + " is out of bounds (0.." + (n-1) + ")");
    }

    // Example usage and simple tests
    public static void main(String[] args) {
        // Example: undirected graph with 6 vertices (0..5)
        AdjacencyMatrixGraph g = new AdjacencyMatrixGraph(6, false);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);

        g.printMatrix();

        System.out.println();
        System.out.println("DFS from 0: " + g.dfs(0));
        System.out.println("BFS from 0: " + g.bfs(0));

        System.out.println();
        System.out.println("Degrees:");
        for (int i = 0; i < 6; i++) {
            System.out.printf("Vertex %d : degree = %d\n", i, g.degree(i));
        }

        // Directed example
        AdjacencyMatrixGraph dg = new AdjacencyMatrixGraph(4, true);
        dg.addEdge(0, 1);
        dg.addEdge(0, 2);
        dg.addEdge(2, 1);
        dg.addEdge(1, 3);

        System.out.println();
        dg.printMatrix();
        System.out.println("Directed DFS from 0: " + dg.dfs(0));
        System.out.println("Directed BFS from 0: " + dg.bfs(0));
        System.out.printf("Vertex 1 in-degree = %d out-degree = %d\n", dg.inDegree(1), dg.outDegree(1));
    }
}
