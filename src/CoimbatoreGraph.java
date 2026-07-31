import java.util.*;

public class CoimbatoreGraph {
    static class Node implements Comparable<Node> {
        int id;
        int distance;

        Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }

        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    static class Edge {
        int target;
        int weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    private int vertices;
    private List<List<Edge>> adj;
    private String[] townNames;

    public CoimbatoreGraph(String[] names) {
        this.vertices = names.length;
        this.townNames = names;
        adj = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) adj.add(new ArrayList<>());
    }

    public void addRoad(int u, int v, int weight) {
        adj.get(u).add(new Edge(v, weight));
        adj.get(v).add(new Edge(u, weight));
    }

    public void findShortestPath(int startNode, int endNode) {
        int[] dist = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[startNode] = 0;
        pq.add(new Node(startNode, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.id;

            if (u == endNode) break;

            for (Edge edge : adj.get(u)) {
                if (dist[u] + edge.weight < dist[edge.target]) {
                    dist[edge.target] = dist[u] + edge.weight;
                    parent[edge.target] = u;
                    pq.add(new Node(edge.target, dist[edge.target]));
                }
            }
        }

        printResult(startNode, endNode, dist, parent);
    }

    private void printResult(int start, int end, int[] dist, int[] parent) {
        if (dist[end] == Integer.MAX_VALUE) {
            System.out.println("No path found.");
            return;
        }
        System.out.println("Shortest Distance from " + townNames[start] + " to " + townNames[end] + ": " + dist[end] + " km");
        System.out.print("Route: ");
        printPath(end, parent);
        System.out.println();
    }

    private void printPath(int current, int[] parent) {
        if (current == -1) return;
        printPath(parent[current], parent);
        System.out.print(townNames[current] + (parent[current] != -1 ? " " : ""));
        if (current != -1 && getTarget(current, parent)) System.out.print("-> ");
    }

    private boolean getTarget(int curr, int[] p) {
        for (int i : p) if (i == curr) return true;
        return false;
    }

    public static void main(String[] args) {
        String[] towns = {"Coimbatore", "Pollachi", "Mettupalayam", "Sulur", "Annur", "Kinathukadavu"};
        CoimbatoreGraph g = new CoimbatoreGraph(towns);

        // Approximate road distances in km
        g.addRoad(0, 1, 40); // Coimbatore to Pollachi
        g.addRoad(0, 2, 35); // Coimbatore to Mettupalayam
        g.addRoad(0, 3, 19); // Coimbatore to Sulur
        g.addRoad(0, 4, 30); // Coimbatore to Annur
        g.addRoad(0, 5, 23); // Coimbatore to Kinathukadavu
        g.addRoad(1, 5, 22); // Pollachi to Kinathukadavu
        g.addRoad(3, 2, 46); // Sulur to Mettupalayam

        g.findShortestPath(1, 2); // Pollachi to Mettupalayam
        g.findShortestPath(3, 1); // Sulur to Pollachi
    }
}
