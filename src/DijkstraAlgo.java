import java.util.*;

public class DijkstraAlgo {
    
    // Class to represent an edge or a node-distance pair
    static class Node implements Comparable<Node> {
        int target;
        int weight;

        Node(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }

        // Compare nodes based on distance/weight for the Min-Heap  r
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    public static int[] dijkstra(int vertices, List<List<Node>> adj, int source) {
        // Distance array to store the shortest distance to each vertex
        int[] dist = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        // PriorityQueue acting as a Min-Heap
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // Initialize source vertex
        dist[source] = 0;
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.target;
            int d = current.weight;

            // Optimization: Skip processing if we found a shorter path to u already
            if (d > dist[u]) {
                continue;
            }

            // Relaxation step for all neighbors of u
            for (Node neighbor : adj.get(u)) {
                int v = neighbor.target;
                int weight = neighbor.weight;

                // Check if a shorter path to v exists through u
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        int vertices = 5;
        List<List<Node>> adj = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }

        // Graph construction (Directed or Undirected)
        adj.get(0).add(new Node(1, 9));
        adj.get(0).add(new Node(2, 6));
        adj.get(0).add(new Node(3, 5));
        adj.get(0).add(new Node(1, 2)); // Example multi-edge connection
        adj.get(2).add(new Node(1, 2));
        adj.get(2).add(new Node(3, 4));
        adj.get(3).add(new Node(4, 1));

        int source = 0;
        int[] distances = dijkstra(vertices, adj, source);

        // Print the shortest paths
        System.out.println("Shortest distances from source vertex " + source + ":");
        for (int i = 0; i < vertices; i++) {
            String value = (distances[i] == Integer.MAX_VALUE) ? "INF" : String.valueOf(distances[i]);
            System.out.println("To vertex " + i + " -> " + value);
        }
    }
}
