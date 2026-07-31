import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
public class Graph {
    private int vertices;
    private List<List<Edge>> adjList;

    static class Edge {
        int target;
        int weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    public Graph(int vertices) {
        this.vertices = vertices;
        adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++)
            adjList.add(new ArrayList<>());
    }

    public void addEdge(int src, int dest, int weight) {
        adjList.get(src).add(new Edge(dest, weight));
        adjList.get(dest).add(new Edge(src, weight)); // For undirected graph
    }

    // Depth-First Search
    public void dfs(int start) {
        boolean[] visited = new boolean[vertices];
        System.out.print("DFS: ");
        dfsUtil(start, visited);
        System.out.println();
    }

    private void dfsUtil(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");
        for (Edge edge : adjList.get(v)) {
            if (!visited[edge.target])
                dfsUtil(edge.target, visited);
        }
    }

    // Breadth-First Search
    public void bfs(int start) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.add(start);

        System.out.print("BFS: ");
        while (!queue.isEmpty()) {
            int v = queue.poll();
            System.out.print(v + " ");
            for (Edge edge : adjList.get(v)) {
                if (!visited[edge.target]) {
                    visited[edge.target] = true;
                    queue.add(edge.target);
                }
            }
        }
        System.out.println();
    }
 /* Dijkstra's Algorithm 

Node 0 → [1 (weight 4), 2 (weight 1)]
Node 1 → [0 (weight 4), 3 (weight 1)]
Node 2 → [0 (weight 1), 3 (weight 5)]
Node 3 → [1 (weight 1), 2 (weight 5), 4 (weight 3)]
Node 4 → [3 (weight 3), 5 (weight 2)]
Node 5 → [4 (weight 2)]

*/

    public void dijkstra(int start) {
        int[] dist = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            for (Edge edge : adjList.get(current.target)) {
                int newDist = dist[current.target] + edge.weight;
                if (newDist < dist[edge.target]) {
                    dist[edge.target] = newDist;
                    pq.add(new Edge(edge.target, newDist));
                }
            }
        }

        System.out.println("Dijkstra's shortest paths from node " + start + ":");
        for (int i = 0; i < vertices; i++)
            System.out.println("To " + i + " -> " + dist[i]);
    }

    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 5, 2);

        graph.dfs(0);
        graph.bfs(0);
        graph.dijkstra(0);
        graph.dijkstra(4);
    }
}
