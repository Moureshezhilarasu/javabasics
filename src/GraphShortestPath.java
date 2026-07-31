import java.util.*;

public class GraphShortestPath {
    static class Edge {
        int src, dest, weight;
        Edge(int s, int d, int w) {
            src = s;
            dest = d;
            weight = w;
        }
    }

    static class Graph {
        int V;
        List<List<Edge>> adjList;
        List<Edge> edgeList;

        Graph(int V) {
            this.V = V;
            adjList = new ArrayList<>();
            edgeList = new ArrayList<>();
            for (int i = 0; i < V; i++)
                adjList.add(new ArrayList<>());
        }

        void addEdge(int src, int dest, int weight) {
            adjList.get(src).add(new Edge(src, dest, weight));
            edgeList.add(new Edge(src, dest, weight));
        }

        // Dijkstra's Algorithm
        void dijkstra(int start) {
            int[] dist = new int[V];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[start] = 0;

            PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
            pq.add(new Edge(-1, start, 0));

            while (!pq.isEmpty()) {
                Edge current = pq.poll();
                for (Edge edge : adjList.get(current.dest)) {
                    if (dist[edge.dest] > dist[current.dest] + edge.weight) {
                        dist[edge.dest] = dist[current.dest] + edge.weight;
                        pq.add(new Edge(edge.src, edge.dest, dist[edge.dest]));
                    }
                }
            }

            System.out.println("Dijkstra's shortest paths from node " + start + ":");
            for (int i = 0; i < V; i++)
                System.out.println("To " + i + " -> " + dist[i]);
        }

        // Bellman-Ford Algorithm
        void bellmanFord(int start) {
            int[] dist = new int[V];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[start] = 0;

            for (int i = 1; i < V; i++) {
                for (Edge edge : edgeList) {
                    if (dist[edge.src] != Integer.MAX_VALUE && dist[edge.dest] > dist[edge.src] + edge.weight) {
                        dist[edge.dest] = dist[edge.src] + edge.weight;
                    }
                }
            }
            // Check for negative weight cycles
            for (Edge edge : edgeList) {
                if (dist[edge.src] != Integer.MAX_VALUE && dist[edge.dest] > dist[edge.src] + edge.weight) {
                    System.out.println("Graph contains a negative weight cycle!");
                    return;
                }
            }

            System.out.println("Bellman-Ford shortest paths from node " + start + ":");
            for (int i = 0; i < V; i++)
                System.out.println("To " + i + " -> " + dist[i]);
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 6);
        graph.addEdge(0, 2, 7);
        graph.addEdge(1, 2, 8);
        graph.addEdge(1, 3, 5);
        graph.addEdge(1, 4, -4);
        graph.addEdge(2, 3, -3);
        graph.addEdge(2, 4, 9);
        graph.addEdge(3, 1, -2);
        graph.addEdge(4, 0, 2);
        graph.addEdge(4, 3, 7);

        graph.dijkstra(0);
        System.out.println();
        graph.bellmanFord(3);
    }
}
