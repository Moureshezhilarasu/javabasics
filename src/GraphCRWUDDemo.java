class GraphNode {
    int id;
    String data;
    boolean visited;
    GraphNode next;  // for linked list implementation
    
    GraphNode(int id, String data) {
        this.id = id;
        this.data = data;
        this.visited = false;
        this.next = null;
    }
}

// Edge class representing a connection between nodes
class Edge {
    int sourceId;
    int destinationId;
    int weight;
    Edge next;
    
    Edge(int sourceId, int destinationId, int weight) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.weight = weight;
        this.next = null;
    }
}

// Custom Queue for BFS
class IntQueue {
    private int[] arr;
    private int front;
    private int rear;
    private int capacity;
    private int size;
    
    IntQueue(int capacity) {
        this.capacity = capacity;
        this.arr = new int[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }
    
    void enqueue(int value) {
        if (isFull()) {
            expandCapacity();
        }
        rear = (rear + 1) % capacity;
        arr[rear] = value;
        size++;
    }
    
    int dequeue() {
        if (isEmpty()) {
            return -1;
        }
        int value = arr[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }
    
    boolean isEmpty() {
        return size == 0;
    }
    
    boolean isFull() {
        return size == capacity;
    }
    
    private void expandCapacity() {
        int newCapacity = capacity * 2;
        int[] newArr = new int[newCapacity];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[(front + i) % capacity];
        }
        arr = newArr;
        front = 0;
        rear = size - 1;
        capacity = newCapacity;
    }
}

// Custom Stack for DFS
class IntStack {
    private int[] arr;
    private int top;
    private int capacity;
    
    IntStack(int capacity) {
        this.capacity = capacity;
        this.arr = new int[capacity];
        this.top = -1;
    }
    
    void push(int value) {
        if (isFull()) {
            expandCapacity();
        }
        arr[++top] = value;
    }
    
    int pop() {
        if (isEmpty()) {
            return -1;
        }
        return arr[top--];
    }
    
    int peek() {
        if (isEmpty()) {
            return -1;
        }
        return arr[top];
    }
    
    boolean isEmpty() {
        return top == -1;
    }
    
    boolean isFull() {
        return top == capacity - 1;
    }
    
    private void expandCapacity() {
        int newCapacity = capacity * 2;
        int[] newArr = new int[newCapacity];
        for (int i = 0; i <= top; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
        capacity = newCapacity;
    }
}

// Main Graph class
class Graph {
    private GraphNode[] nodes;
    private Edge[] adjacencyList;
    private int nodeCount;
    private int maxNodes;
    private int edgeCount;
    
    Graph(int maxNodes) {
        this.maxNodes = maxNodes;
        this.nodes = new GraphNode[maxNodes];
        this.adjacencyList = new Edge[maxNodes];
        this.nodeCount = 0;
        this.edgeCount = 0;
        
        // Initialize adjacency list
        for (int i = 0; i < maxNodes; i++) {
            adjacencyList[i] = null;
        }
    }
    
    // CREATE: Add a new node
    boolean addNode(int id, String data) {
        if (nodeCount >= maxNodes) {
            System.out.println("Graph is full!");
            return false;
        }
        
        if (findNodeIndex(id) != -1) {
            System.out.println("Node with ID " + id + " already exists!");
            return false;
        }
        
        nodes[nodeCount] = new GraphNode(id, data);
        nodeCount++;
        System.out.println("Node added: ID=" + id + ", Data=" + data);
        return true;
    }
    
    // CREATE: Add an edge between two nodes
    boolean addEdge(int sourceId, int destinationId, int weight) {
        int sourceIndex = findNodeIndex(sourceId);
        int destIndex = findNodeIndex(destinationId);
        
        if (sourceIndex == -1 || destIndex == -1) {
            System.out.println("Source or destination node not found!");
            return false;
        }
        
        // Check if edge already exists
        if (hasEdge(sourceId, destinationId)) {
            System.out.println("Edge already exists!");
            return false;
        }
        
        // Add edge to adjacency list
        Edge newEdge = new Edge(sourceId, destinationId, weight);
        newEdge.next = adjacencyList[sourceIndex];
        adjacencyList[sourceIndex] = newEdge;
        
        edgeCount++;
        System.out.println("Edge added: " + sourceId + " -> " + destinationId + " (weight=" + weight + ")");
        return true;
    }
    
    // READ: Get node by ID
    GraphNode getNode(int id) {
        int index = findNodeIndex(id);
        if (index != -1) {
            return nodes[index];
        }
        return null;
    }
    
    // READ: Get all nodes
    void getAllNodes() {
        System.out.println("\nAll Nodes:");
        for (int i = 0; i < nodeCount; i++) {
            System.out.println("  Node ID: " + nodes[i].id + ", Data: " + nodes[i].data);
        }
    }
    
    // READ: Get all edges of a node
    void getEdgesOfNode(int nodeId) {
        int index = findNodeIndex(nodeId);
        if (index == -1) {
            System.out.println("Node not found!");
            return;
        }
        
        System.out.println("\nEdges from node " + nodeId + ":");
        Edge current = adjacencyList[index];
        if (current == null) {
            System.out.println("  No outgoing edges");
            return;
        }
        
        while (current != null) {
            System.out.println("  -> Node " + current.destinationId + " (weight=" + current.weight + ")");
            current = current.next;
        }
    }
    
    // READ: Display entire graph
    void displayGraph() {
        System.out.println("\n=== Graph Structure ===");
        for (int i = 0; i < nodeCount; i++) {
            System.out.print("Node " + nodes[i].id + " [" + nodes[i].data + "]: ");
            Edge current = adjacencyList[i];
            if (current == null) {
                System.out.println("No edges");
            } else {
                while (current != null) {
                    System.out.print("->" + current.destinationId + "(" + current.weight + ") ");
                    current = current.next;
                }
                System.out.println();
            }
        }
        System.out.println("=====================");
    }
    
    // UPDATE: Update node data
    boolean updateNode(int id, String newData) {
        int index = findNodeIndex(id);
        if (index == -1) {
            System.out.println("Node not found!");
            return false;
        }
        
        String oldData = nodes[index].data;
        nodes[index].data = newData;
        System.out.println("Node updated: ID=" + id + ", Old Data=" + oldData + ", New Data=" + newData);
        return true;
    }
    
    // UPDATE: Update edge weight
    boolean updateEdgeWeight(int sourceId, int destinationId, int newWeight) {
        int sourceIndex = findNodeIndex(sourceId);
        if (sourceIndex == -1) {
            System.out.println("Source node not found!");
            return false;
        }
        
        Edge current = adjacencyList[sourceIndex];
        while (current != null) {
            if (current.destinationId == destinationId) {
                int oldWeight = current.weight;
                current.weight = newWeight;
                System.out.println("Edge weight updated: " + sourceId + "->" + destinationId + ", Old Weight=" + oldWeight + ", New Weight=" + newWeight);
                return true;
            }
            current = current.next;
        }
        
        System.out.println("Edge not found!");
        return false;
    }
    
    // DELETE: Remove a node and all its edges
    boolean deleteNode(int id) {
        int index = findNodeIndex(id);
        if (index == -1) {
            System.out.println("Node not found!");
            return false;
        }
        
        // Remove all edges from this node
        adjacencyList[index] = null;
        
        // Remove edges pointing to this node from other nodes
        for (int i = 0; i < nodeCount; i++) {
            if (i != index && adjacencyList[i] != null) {
                removeEdgeFromList(i, id);
            }
        }
        
        // Shift nodes array to remove the node
        for (int i = index; i < nodeCount - 1; i++) {
            nodes[i] = nodes[i + 1];
            adjacencyList[i] = adjacencyList[i + 1];
        }
        
        nodes[nodeCount - 1] = null;
        adjacencyList[nodeCount - 1] = null;
        nodeCount--;
        
        System.out.println("Node " + id + " and all its edges deleted");
        return true;
    }
    
    // DELETE: Remove a specific edge
    boolean deleteEdge(int sourceId, int destinationId) {
        int sourceIndex = findNodeIndex(sourceId);
        if (sourceIndex == -1) {
            System.out.println("Source node not found!");
            return false;
        }
        
        return removeEdgeFromList(sourceIndex, destinationId);
    }
    
    // Helper: Remove edge from adjacency list
    private boolean removeEdgeFromList(int nodeIndex, int destId) {
        Edge current = adjacencyList[nodeIndex];
        Edge prev = null;
        
        while (current != null) {
            if (current.destinationId == destId) {
                if (prev == null) {
                    adjacencyList[nodeIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                edgeCount--;
                System.out.println("Edge deleted: " + nodes[nodeIndex].id + " -> " + destId);
                return true;
            }
            prev = current;
            current = current.next;
        }
        
        System.out.println("Edge not found!");
        return false;
    }
    
    // Helper: Find node index by ID
    private int findNodeIndex(int id) {
        for (int i = 0; i < nodeCount; i++) {
            if (nodes[i].id == id) {
                return i;
            }
        }
        return -1;
    }
    
    // Helper: Check if edge exists
    private boolean hasEdge(int sourceId, int destinationId) {
        int sourceIndex = findNodeIndex(sourceId);
        if (sourceIndex == -1) return false;
        
        Edge current = adjacencyList[sourceIndex];
        while (current != null) {
            if (current.destinationId == destinationId) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    // TRAVERSAL: Breadth First Search
    void bfs(int startId) {
        int startIndex = findNodeIndex(startId);
        if (startIndex == -1) {
            System.out.println("Start node not found!");
            return;
        }
        
        // Reset visited flags
        resetVisited();
        
        IntQueue queue = new IntQueue(maxNodes);
        System.out.print("BFS Traversal: ");
        
        queue.enqueue(nodes[startIndex].id);
        nodes[startIndex].visited = true;
        
        while (!queue.isEmpty()) {
            int currentId = queue.dequeue();
            int currentIndex = findNodeIndex(currentId);
            System.out.print(currentId + " ");
            
            // Add all unvisited neighbors to queue
            Edge currentEdge = adjacencyList[currentIndex];
            while (currentEdge != null) {
                int neighborIndex = findNodeIndex(currentEdge.destinationId);
                if (neighborIndex != -1 && !nodes[neighborIndex].visited) {
                    queue.enqueue(currentEdge.destinationId);
                    nodes[neighborIndex].visited = true;
                }
                currentEdge = currentEdge.next;
            }
        }
        System.out.println();
    }
    
    // TRAVERSAL: Depth First Search
    void dfs(int startId) {
        int startIndex = findNodeIndex(startId);
        if (startIndex == -1) {
            System.out.println("Start node not found!");
            return;
        }
        
        // Reset visited flags
        resetVisited();
        
        IntStack stack = new IntStack(maxNodes);
        System.out.print("DFS Traversal: ");
        
        stack.push(nodes[startIndex].id);
        
        while (!stack.isEmpty()) {
            int currentId = stack.pop();
            int currentIndex = findNodeIndex(currentId);
            
            if (!nodes[currentIndex].visited) {
                nodes[currentIndex].visited = true;
                System.out.print(currentId + " ");
                
                // Add all neighbors to stack (reverse order for natural DFS)
                Edge currentEdge = adjacencyList[currentIndex];
                Edge[] neighbors = new Edge[maxNodes];
                int neighborCount = 0;
                
                while (currentEdge != null) {
                    neighbors[neighborCount++] = currentEdge;
                    currentEdge = currentEdge.next;
                }
                
                // Push in reverse order
                for (int i = neighborCount - 1; i >= 0; i--) {
                    int neighborIndex = findNodeIndex(neighbors[i].destinationId);
                    if (neighborIndex != -1 && !nodes[neighborIndex].visited) {
                        stack.push(neighbors[i].destinationId);
                    }
                }
            }
        }
        System.out.println();
    }
    
    // Helper: Reset visited flags
    private void resetVisited() {
        for (int i = 0; i < nodeCount; i++) {
            nodes[i].visited = false;
        }
    }
    
    // Utility: Get node count
    int getNodeCount() {
        return nodeCount;
    }
    
    // Utility: Get edge count
    int getEdgeCount() {
        return edgeCount;
    }
    
    // Utility: Check if graph is empty
    boolean isEmpty() {
        return nodeCount == 0;
    }
    
    // Utility: Clear entire graph
    void clearGraph() {
        for (int i = 0; i < maxNodes; i++) {
            nodes[i] = null;
            adjacencyList[i] = null;
        }
        nodeCount = 0;
        edgeCount = 0;
        System.out.println("Graph cleared completely!");
    }
}

// Main class to demonstrate all operations
public class GraphCRWUDDemo {
    public static void main(String[] args) {
        // Create graph with capacity 10
        Graph graph = new Graph(10);
        
        System.out.println("========== GRAPH CRWUD OPERATIONS DEMO ==========" + "\n");
        
        // CREATE Operations
        System.out.println("--- CREATE Operations ---");
        graph.addNode(1, "Node A");
        graph.addNode(2, "Node B");
        graph.addNode(3, "Node C");
        graph.addNode(4, "Node D");
        graph.addNode(5, "Node E");
        
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 3);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 4, 7);
        graph.addEdge(4, 5, 4);
        graph.addEdge(2, 5, 6);
        
        // READ Operations
        System.out.println("\n--- READ Operations ---");
        graph.getAllNodes();
        graph.getEdgesOfNode(1);
        graph.getEdgesOfNode(2);
        graph.displayGraph();
        
        // READ: Get specific node
        GraphNode node = graph.getNode(3);
        if (node != null) {
            System.out.println("\nRetrieved node: ID=" + node.id + ", Data=" + node.data);
        }
        
        // UPDATE Operations
        System.out.println("\n--- UPDATE Operations ---");
        graph.updateNode(3, "Node C Updated");
        graph.updateEdgeWeight(1, 3, 10);
        graph.displayGraph();
        
        // WRITE Operations (Traversals)
        System.out.println("\n--- WRITE/Traversal Operations ---");
        graph.bfs(1);
        graph.dfs(1);
        
        // DELETE Operations
        System.out.println("\n--- DELETE Operations ---");
        graph.deleteEdge(1, 2);
        graph.displayGraph();
        
        graph.deleteNode(4);
        graph.displayGraph();
        
        // Utility methods
        System.out.println("\n--- Utility Information ---");
        System.out.println("Node count: " + graph.getNodeCount());
        System.out.println("Edge count: " + graph.getEdgeCount());
        System.out.println("Is graph empty? " + graph.isEmpty());
        
        // Clear graph
        System.out.println("\n--- Clear Graph ---");
        graph.clearGraph();
        System.out.println("Graph cleared. Node count: " + graph.getNodeCount());
    }
}
