import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class FloydWarshall {
    private static final long INF = Long.MAX_VALUE / 4;

    private final int n;
    private final long[][] dist;
    private final int[][] next;

    public FloydWarshall(int n) {
        this.n = n;
        dist = new long[n][n];
        next = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            for (int j = 0; j < n; j++) next[i][j] = -1;
            dist[i][i] = 0;
            next[i][i] = i;
        }
    }

    public void addEdge(int u, int v, long w) {
        if (w < dist[u][v]) {
            dist[u][v] = w;
            next[u][v] = v;
        }
    }

    public void compute() {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (dist[i][k] == INF) continue;
                for (int j = 0; j < n; j++) {
                    if (dist[k][j] == INF) continue;
                    long throughK = dist[i][k] + dist[k][j];
                    if (throughK < dist[i][j]) {
                        dist[i][j] = throughK;
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public boolean hasPath(int u, int v) {
        return dist[u][v] != INF;
    }

    public long distance(int u, int v) {
        return dist[u][v];
    }

    public List<Integer> reconstructPath(int u, int v) {
        List<Integer> path = new ArrayList<>();
        if (!hasPath(u, v)) return path;
        int at = u;
        while (at != v) {
            if (at == -1) return new ArrayList<>();
            path.add(at);
            at = next[at][v];
        }
        path.add(v);
        return path;
    }

    public boolean hasNegativeCycle() {
        for (int i = 0; i < n; i++) {
            if (dist[i][i] < 0) return true;
        }
        return false;
    }

    // Example and simple test
    public static void main(String[] args) {
        int nodes = 4;
        FloydWarshall fw = new FloydWarshall(nodes);

        // Example directed weighted edges: (u, v, weight)
        fw.addEdge(0, 1, 5);
        fw.addEdge(0, 3, 10);
        fw.addEdge(1, 2, 3);
        fw.addEdge(2, 3, 1);
        fw.addEdge(3, 0, -2); // negative edge allowed if no negative cycle

        fw.compute();

        if (fw.hasNegativeCycle()) {
            System.out.println("Graph contains a negative cycle");
            return;
        }

        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                System.out.print("From " + i + " to " + j + ": ");
                if (!fw.hasPath(i, j)) {
                    System.out.println("no path");
                } else {
                    System.out.print("distance = " + fw.distance(i, j) + ", path = ");
                    System.out.println(fw.reconstructPath(i, j));
                }
            }
        }

        // Example single query
        int u = 0, v = 3;
        System.out.println("Path 0 -> 3: " + fw.reconstructPath(u, v) + ", dist = " + fw.distance(u, v));
    }
}
