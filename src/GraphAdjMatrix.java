// File: GraphAdjMatrix.java
import java.util.*;

public class GraphAdjMatrix {
    private final int n;
    private final int[][] mat; // use Integer.MAX_VALUE to mean no edge for weighted graphs

    public GraphAdjMatrix(int n) {
        this.n = n;
        mat = new int[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(mat[i], Integer.MAX_VALUE);
    }

    public void addEdge(int u, int v, int weight) {
        mat[u][v] = weight;
    }

    public void addUndirectedEdge(int u, int v, int weight) {
        addEdge(u, v, weight);
        addEdge(v, u, weight);
    }

    public boolean hasEdge(int u, int v) { return mat[u][v] != Integer.MAX_VALUE; }

    public int weight(int u, int v) { return mat[u][v]; }

    public int size() { return n; }
}
