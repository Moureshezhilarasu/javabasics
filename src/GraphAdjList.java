
import java.util.*;

public class GraphAdjList {
    public static class Edge {
        public final int to;
        public final int weight; // use 1 for unweighted
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight; }
        public String toString() {
            return "(" + to + "," + weight + ")"; }
    }

    private final int n;
    private final List<List<Edge>> adj;
    public GraphAdjList(int n) {
        this.n = n;
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public int size() { return n; }

    // add directed edge u -> v
    public void addDirectedEdge(int u, int v, int weight) {

        adj.get(u).add(new Edge(v, weight));
    }

    // add undirected edge u <-> v
    public void addUndirectedEdge(int u, int v, int weight) {
        addDirectedEdge(u, v, weight);
        addDirectedEdge(v, u, weight);
    }

    public List<Edge> neighbors(int u) {
        return Collections.unmodifiableList(adj.get(u)); }

    public void print() {
        for (int i = 0; i < n; i++) {
            System.out.print(i + ": ");
            for (Edge e : adj.get(i))
                System.out.print(e + " ");
            System.out.println();
        }
    }

    // Example usage
    public static void main(String[] args) {
        GraphAdjList g = new GraphAdjList(6);
        g.addUndirectedEdge(0,1,1);
        g.addUndirectedEdge(0,2,1);
        g.addUndirectedEdge(1,3,1);
        g.addUndirectedEdge(2,3,1);
        g.addUndirectedEdge(3,4,1);
        g.addUndirectedEdge(4,5,1);
        g.print();
    }
}
