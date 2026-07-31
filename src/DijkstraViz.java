import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class DijkstraViz extends JFrame {

    // Graph Data
    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Algorithm State
    private int currentStep = 0;
    private int[] dist;
    private String[] paths;
    private PriorityQueue<PathNode> pq;
    private Set<Integer> visited;
    private String logMessage = "Click 'Next Step' to start Dijkstra's Algorithm.";
    private Node activeNode = null;
    private Edge activeEdge = null;
    private boolean isFinished = false;

    // GUI Components
    private DrawingPanel canvas;
    private JTextArea logArea;
    private JLabel statusLabel;

    public DijkstraViz() {
        initGraph();
        initAlgo();
        initGUI();
    }

    private void initGraph() {
        // Hardcoded Nodes based on your layout
        nodes.add(new Node(0, 50, 150));
        nodes.add(new Node(1, 150, 50));
        nodes.add(new Node(2, 150, 250));
        nodes.add(new Node(3, 250, 150));
        nodes.add(new Node(4, 350, 50));
        nodes.add(new Node(5, 450, 150));

        // Hardcoded Edges
        edges.add(new Edge(0, 1, 4));
        edges.add(new Edge(0, 2, 1));
        edges.add(new Edge(1, 0, 4));
        edges.add(new Edge(1, 3, 1));
        edges.add(new Edge(2, 0, 1));
        edges.add(new Edge(2, 3, 5));
        edges.add(new Edge(3, 1, 1));
        edges.add(new Edge(3, 2, 5));
        edges.add(new Edge(3, 4, 3));
        edges.add(new Edge(4, 3, 3));
        edges.add(new Edge(4, 5, 2));
        edges.add(new Edge(5, 4, 2));
    }

    private void initAlgo() {
        dist = new int[nodes.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        
        visited = new HashSet<>();
        pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        pq.add(new PathNode(0, 0));
    }

    private void stepAlgo() {
        if (isFinished || pq.isEmpty()) {
            logMessage = "Algorithm Complete!";
            isFinished = true;
            activeNode = null;
            activeEdge = null;
            repaint();
            return;
        }

        // 1. Poll
        PathNode current = pq.poll();
        int u = current.id;
        activeNode = nodes.get(u);
        
        // Stale check
        if (current.distance > dist[u]) {
            logMessage = "Skipping Node " + u + " (Stale path: " + current.distance + " > " + dist[u] + ")";
            stepAlgo(); // Auto-skip stale nodes for smoother UX
            return; 
        }

        visited.add(u);
        logMessage = "Processing Node " + u + " (Dist: " + dist[u] + ")";

        // 2. Relax Neighbors
        for (Edge e : edges) {
            if (e.source == u) {
                int v = e.target;
                int newDist = dist[u] + e.weight;
                
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.add(new PathNode(v, newDist));
                    logMessage += "\n  -> Updated Node " + v + " to dist " + newDist;
                }
            }
        }
        canvas.repaint();
        updateStatus();
    }

    private void initGUI() {
        setTitle("Dijkstra Trace Simulator");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center: Visualization
        canvas = new DrawingPanel();
        add(canvas, BorderLayout.CENTER);

        // Bottom: Controls
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        JButton nextBtn = new JButton("NEXT STEP >>");
        nextBtn.setFont(new Font("Arial", Font.BOLD, 14));
        nextBtn.addActionListener(e -> {
            stepAlgo();
            logArea.setText(logMessage);
            logArea.append("\n\nPriority Queue: " + pq.toString());
        });
        
        statusLabel = new JLabel("Distances: " + Arrays.toString(dist));
        statusLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        logArea = new JTextArea(5, 40);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        bottomPanel.add(nextBtn, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateStatus() {
        StringBuilder sb = new StringBuilder("Distances: [");
        for(int i=0; i<dist.length; i++) {
            sb.append(dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]);
            if(i < dist.length -1) sb.append(", ");
        }
        sb.append("]");
        statusLabel.setText(sb.toString());
    }

    // --- Helper Classes ---

    class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw Edges
            for (Edge e : edges) {
                Node n1 = nodes.get(e.source);
                Node n2 = nodes.get(e.target);
                
                // Highlight edge if part of active processing could be added here
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Line2D.Double(n1.x, n1.y, n2.x, n2.y));
                
                // Draw Weight
                int midX = (n1.x + n2.x) / 2;
                int midY = (n1.y + n2.y) / 2;
                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf(e.weight), midX, midY);
            }

            // Draw Nodes
            for (Node n : nodes) {
                int r = 20; // radius
                if (n == activeNode) g2.setColor(Color.ORANGE); // Active
                else if (dist[n.id] != Integer.MAX_VALUE && visited.contains(n.id)) g2.setColor(Color.GREEN); // Visited
                else if (dist[n.id] != Integer.MAX_VALUE) g2.setColor(Color.CYAN); // Discovered
                else g2.setColor(Color.LIGHT_GRAY); // Unvisited

                g2.fillOval(n.x - r, n.y - r, 2 * r, 2 * r);
                g2.setColor(Color.BLACK);
                g2.drawOval(n.x - r, n.y - r, 2 * r, 2 * r);
                
                // Label (ID : Dist)
                String dStr = (dist[n.id] == Integer.MAX_VALUE) ? "INF" : String.valueOf(dist[n.id]);
                g2.drawString(n.id + " (" + dStr + ")", n.x - 15, n.y - 25);
            }
        }
    }

    static class Node {
        int id, x, y;
        Node(int id, int x, int y) { this.id = id; this.x = x; this.y = y; }
    }

    static class Edge {
        int source, target, weight;
        Edge(int s, int t, int w) { this.source = s; this.target = t; this.weight = w; }
    }

    static class PathNode {
        int id, distance;
        PathNode(int id, int distance) { this.id = id; this.distance = distance; }
        @Override public String toString() { return "(" + id + "," + distance + ")"; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DijkstraViz().setVisible(true);
        });
    }
}
