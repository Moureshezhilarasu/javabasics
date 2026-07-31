import java.util.*;
/**
 * Real-World Implementations of Sorting Algorithms and Data Structures
 * Demonstrates practical applications in business, computing, and networking
 */
public class RealWorldAlgorithmsAndDS {
    
    // ==================== SORTING ALGORITHMS ====================
    
    /**
     * BUBBLE SORT - Real-world: Sorting student records by roll number in small classes
     * Time Complexity: O(n²) - Only for small datasets
     */
    static class BubbleSort {
        public static void sortStudentRecords(Student[] students) {
            int n = students.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (students[j].rollNumber > students[j + 1].rollNumber) {
                        Student temp = students[j];
                        students[j] = students[j + 1];
                        students[j + 1] = temp;
                    }
                }
            }
        }
    }
    
    /**
     * INSERTION SORT - Real-world: Sorting playing cards in a card game
     * Time Complexity: O(n²) but efficient for nearly sorted data
     */
    static class InsertionSort {
        public static void sortTransactions(Transaction[] transactions) {
            for (int i = 1; i < transactions.length; i++) {
                Transaction key = transactions[i];
                int j = i - 1;
                while (j >= 0 && transactions[j].amount > key.amount) {
                    transactions[j + 1] = transactions[j];
                    j--;
                }
                transactions[j + 1] = key;
            }
        }
    }
    
    /**
     * SELECTION SORT - Real-world: Sorting products by price in an e-commerce inventory
     * Time Complexity: O(n²) - Useful when memory writes are expensive
     */
    static class SelectionSort {
        public static void sortProducts(Product[] products) {
            for (int i = 0; i < products.length - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < products.length; j++) {
                    if (products[j].price < products[minIndex].price) {
                        minIndex = j;
                    }
                }
                Product temp = products[minIndex];
                products[minIndex] = products[i];
                products[i] = temp;
            }
        }
    }
    
    /**
     * MERGE SORT - Real-world: Sorting large datasets like employee records
     * Time Complexity: O(n log n) - Stable sort, good for linked lists
     */
    static class MergeSort {
        public static void sortEmployees(Employee[] employees, int left, int right) {
            if (left < right) {
                int mid = left + (right - left) / 2;
                sortEmployees(employees, left, mid);
                sortEmployees(employees, mid + 1, right);
                merge(employees, left, mid, right);
            }
        }
        
        private static void merge(Employee[] employees, int left, int mid, int right) {
            int n1 = mid - left + 1;
            int n2 = right - mid;
            
            Employee[] leftArray = new Employee[n1];
            Employee[] rightArray = new Employee[n2];
            
            System.arraycopy(employees, left, leftArray, 0, n1);
            System.arraycopy(employees, mid + 1, rightArray, 0, n2);
            
            int i = 0, j = 0, k = left;
            while (i < n1 && j < n2) {
                if (leftArray[i].salary <= rightArray[j].salary) {
                    employees[k] = leftArray[i];
                    i++;
                } else {
                    employees[k] = rightArray[j];
                    j++;
                }
                k++;
            }
            
            while (i < n1) {
                employees[k] = leftArray[i];
                i++;
                k++;
            }
            
            while (j < n2) {
                employees[k] = rightArray[j];
                j++;
                k++;
            }
        }
    }
    
    /**
     * QUICK SORT - Real-world: Sorting large datasets in memory (database indexing)
     * Time Complexity: O(n log n) average, O(n²) worst - In-place sort
     */
    static class QuickSort {
        public static void sortDatabaseRecords(Record[] records, int low, int high) {
            if (low < high) {
                int pi = partition(records, low, high);
                sortDatabaseRecords(records, low, pi - 1);
                sortDatabaseRecords(records, pi + 1, high);
            }
        }
        
        private static int partition(Record[] records, int low, int high) {
            int pivot = records[high].id;
            int i = low - 1;
            
            for (int j = low; j < high; j++) {
                if (records[j].id <= pivot) {
                    i++;
                    Record temp = records[i];
                    records[i] = records[j];
                    records[j] = temp;
                }
            }
            
            Record temp = records[i + 1];
            records[i + 1] = records[high];
            records[high] = temp;
            
            return i + 1;
        }
    }
    
    /**
     * HEAP SORT - Real-world: Priority scheduling in operating systems
     * Time Complexity: O(n log n) - In-place, not stable
     */
    static class HeapSort {
        public static void sortProcesses(Process[] processes) {
            int n = processes.length;
            
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(processes, n, i);
            }
            
            for (int i = n - 1; i > 0; i--) {
                Process temp = processes[0];
                processes[0] = processes[i];
                processes[i] = temp;
                heapify(processes, i, 0);
            }
        }
        
        private static void heapify(Process[] processes, int n, int i) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            
            if (left < n && processes[left].priority > processes[largest].priority) {
                largest = left;
            }
            
            if (right < n && processes[right].priority > processes[largest].priority) {
                largest = right;
            }
            
            if (largest != i) {
                Process swap = processes[i];
                processes[i] = processes[largest];
                processes[largest] = swap;
                heapify(processes, n, largest);
            }
        }
    }
    
    // ==================== DATA STRUCTURES WITH REAL-WORLD APPLICATIONS ====================
    
    /**
     * STACK - Real-world: Expression evaluation (Calculator)
     * Application: Evaluating mathematical expressions like (2+3)*4
     */
    static class ExpressionEvaluator {
        public static int evaluateExpression(String expression) {
            Deque<Integer> values = new ArrayDeque<>();
            Deque<Character> operators = new ArrayDeque<>();
            
            for (int i = 0; i < expression.length(); i++) {
                char ch = expression.charAt(i);
                
                if (Character.isDigit(ch)) {
                    int num = 0;
                    while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                        num = num * 10 + (expression.charAt(i) - '0');
                        i++;
                    }
                    values.addFirst(num);
                    i--;
                } else if (ch == '(') {
                    operators.addFirst(ch);
                } else if (ch == ')') {
                    while (!operators.isEmpty() && operators.peekFirst() != '(') {
                        values.addFirst(applyOperator(operators.removeFirst(), values.removeFirst(), values.removeFirst()));
                    }
                    operators.removeFirst();
                } else if (isOperator(ch)) {
                    while (!operators.isEmpty() && precedence(operators.peekFirst()) >= precedence(ch)) {
                        values.addFirst(applyOperator(operators.removeFirst(), values.removeFirst(), values.removeFirst()));
                    }
                    operators.addFirst(ch);
                }
            }
            
            while (!operators.isEmpty()) {
                values.addFirst(applyOperator(operators.removeFirst(), values.removeFirst(), values.removeFirst()));
            }
            
            return values.removeFirst();
        }
        
        private static boolean isOperator(char ch) {
            return ch == '+' || ch == '-' || ch == '*' || ch == '/';
        }
        
        private static int precedence(char op) {
            if (op == '+' || op == '-') return 1;
            if (op == '*' || op == '/') return 2;
            return 0;
        }
        
        private static int applyOperator(char op, int b, int a) {
            return switch (op) {
                case '+' -> a + b;
                case '-' -> a - b;
                case '*' -> a * b;
                case '/' -> a / b;
                default -> 0;
            };
        }
    }
    
    /**
     * QUEUE - Real-world: Job scheduling in printer spooler
     * Application: Managing print jobs in FIFO order
     */
    static class PrinterSpooler {
        private final LinkedList<PrintJob> jobQueue = new LinkedList<>();
        
        public void addJob(String documentName, int pages) {
            PrintJob job = new PrintJob(documentName, pages);
            jobQueue.offer(job);
            System.out.println("Added to queue: " + job);
        }
        
        public void processJobs() {
            while (!jobQueue.isEmpty()) {
                PrintJob job = jobQueue.poll();
                System.out.println("Printing: " + job);
            }
            System.out.println("All jobs completed!");
        }
    }
    
    /**
     * LINKED LIST - Real-world: Music player playlist
     * Application: Implementing playlists with next/previous navigation
     */
    static class Playlist {
        private class Song {
            String title;
            String artist;
            Song next;
            Song prev;
            
            Song(String title, String artist) {
                this.title = title;
                this.artist = artist;
            }
        }
        
        private Song head;
        private Song tail;
        private Song current;
        
        public void addSong(String title, String artist) {
            Song newSong = new Song(title, artist);
            if (head == null) {
                head = tail = current = newSong;
            } else {
                tail.next = newSong;
                newSong.prev = tail;
                tail = newSong;
            }
            System.out.println("Added to playlist: " + title + " by " + artist);
        }
        
        public void playNext() {
            if (current != null && current.next != null) {
                current = current.next;
                System.out.println("Now playing: " + current.title + " by " + current.artist);
            } else {
                System.out.println("End of playlist");
            }
        }
        
        public void playPrevious() {
            if (current != null && current.prev != null) {
                current = current.prev;
                System.out.println("Now playing: " + current.title + " by " + current.artist);
            } else {
                System.out.println("Beginning of playlist");
            }
        }
        
        public void displayPlaylist() {
            Song temp = head;
            System.out.println("\n=== PLAYLIST ===");
            while (temp != null) {
                System.out.println(temp.title + " - " + temp.artist);
                temp = temp.next;
            }
            System.out.println("===============\n");
        }
    }
    
    /**
     * TREE - Real-world: File system hierarchy
     * Application: Representing directory structure
     */
    static class FileSystem {
        static class FileNode {
            String name;
            boolean isDirectory;
            List<FileNode> children;
            
            FileNode(String name, boolean isDirectory) {
                this.name = name;
                this.isDirectory = isDirectory;
                this.children = new ArrayList<>();
            }
            
            void addChild(FileNode child) {
                children.add(child);
            }
            
            void display(String indent) {
                System.out.println(indent + (isDirectory ? "📁 " : "📄 ") + name);
                for (FileNode child : children) {
                    child.display(indent + "  ");
                }
            }
            
            int getSize() {
                if (!isDirectory) return 1;
                int size = 0;
                for (FileNode child : children) {
                    size += child.getSize();
                }
                return size;
            }
        }
        
        public static void demonstrateFileSystem() {
            FileNode root = new FileNode("Root", true);
            FileNode documents = new FileNode("Documents", true);
            FileNode pictures = new FileNode("Pictures", true);
            FileNode resume = new FileNode("resume.pdf", false);
            FileNode vacation = new FileNode("vacation.jpg", false);
            
            root.addChild(documents);
            root.addChild(pictures);
            documents.addChild(resume);
            pictures.addChild(vacation);
            
            System.out.println("\n=== FILE SYSTEM HIERARCHY ===");
            root.display("");
            System.out.println("Total files: " + root.getSize());
        }
    }
    
    /**
     * GRAPH - Real-world: Shortest path finding in maps (Dijkstra's Algorithm)
     * Application: Google Maps / Navigation systems
     */
    static class NavigationSystem {
        static class City {
            String name;
            Map<City, Integer> connections = new HashMap<>();
            
            City(String name) {
                this.name = name;
            }
            
            void addConnection(City city, int distance) {
                connections.put(city, distance);
                city.connections.put(this, distance);
            }
        }
        
        public static void findShortestPath(City start, City end) {
            Map<City, Integer> distances = new HashMap<>();
            Map<City, City> previous = new HashMap<>();
            PriorityQueue<City> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
            
            distances.put(start, 0);
            pq.offer(start);
            
            while (!pq.isEmpty()) {
                City current = pq.poll();
                
                if (current == end) break;
                
                for (Map.Entry<City, Integer> entry : current.connections.entrySet()) {
                    City neighbor = entry.getKey();
                    int newDist = distances.get(current) + entry.getValue();
                    
                    if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        distances.put(neighbor, newDist);
                        previous.put(neighbor, current);
                        pq.offer(neighbor);
                    }
                }
            }
            
            // Print path
            System.out.println("\n=== SHORTEST PATH ===");
            List<City> path = new ArrayList<>();
            City current = end;
            while (current != null) {
                path.add(0, current);
                current = previous.get(current);
            }
            
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i).name);
                if (i < path.size() - 1) System.out.print(" → ");
            }
            System.out.println("\nTotal distance: " + distances.get(end) + " km");
        }
    }
    
    // ==================== HELPER CLASSES ====================
    
    static class Student {
        int rollNumber;
        String name;
        Student(int rollNumber, String name) {
            this.rollNumber = rollNumber;
            this.name = name;
        }
        @Override
        public String toString() {
            return rollNumber + ": " + name;
        }
    }
    
    static class Transaction {
        double amount;
        String description;
        Transaction(double amount, String description) {
            this.amount = amount;
            this.description = description;
        }
        @Override
        public String toString() {
            return "$" + amount + " - " + description;
        }
    }
    
    static class Product {
        String name;
        double price;
        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }
        @Override
        public String toString() {
            return name + ": $" + price;
        }
    }
    
    static class Employee {
        String name;
        double salary;
        Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }
        @Override
        public String toString() {
            return name + ": $" + salary;
        }
    }
    
    static class Record {
        int id;
        String data;
        Record(int id, String data) {
            this.id = id;
            this.data = data;
        }
        @Override
        public String toString() {
            return id + ": " + data;
        }
    }
    
    static class Process {
        String name;
        int priority;
        Process(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }
        @Override
        public String toString() {
            return name + " (Priority: " + priority + ")";
        }
    }
    
    static class PrintJob {
        String documentName;
        int pages;
        PrintJob(String documentName, int pages) {
            this.documentName = documentName;
            this.pages = pages;
        }
        @Override
        public String toString() {
            return documentName + " (" + pages + " pages)";
        }
    }
    
    // ==================== MAIN METHOD ====================
    
    public static void main(String[] args) {
        System.out.println("=== REAL-WORLD IMPLEMENTATIONS OF ALGORITHMS & DATA STRUCTURES ===\n");
        
        // SORTING DEMONSTRATIONS
        System.out.println("--- SORTING ALGORITHMS ---\n");
        
        // Bubble Sort: Student records
        Student[] students = {
            new Student(103, "Alice"),
            new Student(101, "Bob"),
            new Student(105, "Charlie"),
            new Student(102, "David"),
            new Student(104, "Eve")
        };
        System.out.println("Bubble Sort - Student Records (Before): " + Arrays.toString(students));
        BubbleSort.sortStudentRecords(students);
        System.out.println("After sorting by roll number: " + Arrays.toString(students) + "\n");
        
        // Insertion Sort: Bank transactions
        Transaction[] transactions = {
            new Transaction(500.0, "Salary"),
            new Transaction(50.0, "Groceries"),
            new Transaction(1000.0, "Rent"),
            new Transaction(25.0, "Coffee"),
            new Transaction(200.0, "Utilities")
        };
        System.out.println("Insertion Sort - Transactions (Before): " + Arrays.toString(transactions));
        InsertionSort.sortTransactions(transactions);
        System.out.println("After sorting by amount: " + Arrays.toString(transactions) + "\n");
        
        // Selection Sort: Products
        Product[] products = {
            new Product("Laptop", 1200.0),
            new Product("Mouse", 25.0),
            new Product("Monitor", 300.0),
            new Product("Keyboard", 75.0)
        };
        System.out.println("Selection Sort - Products (Before): " + Arrays.toString(products));
        SelectionSort.sortProducts(products);
        System.out.println("After sorting by price: " + Arrays.toString(products) + "\n");
        
        // Merge Sort: Employees
        Employee[] employees = {
            new Employee("John", 75000),
            new Employee("Sarah", 85000),
            new Employee("Mike", 65000),
            new Employee("Lisa", 90000)
        };
        System.out.println("Merge Sort - Employees (Before): " + Arrays.toString(employees));
        MergeSort.sortEmployees(employees, 0, employees.length - 1);
        System.out.println("After sorting by salary: " + Arrays.toString(employees) + "\n");
        
        // Quick Sort: Database records
        Record[] records = {
            new Record(5, "Data E"),
            new Record(2, "Data B"),
            new Record(8, "Data H"),
            new Record(1, "Data A"),
            new Record(3, "Data C")
        };
        System.out.println("Quick Sort - Database Records (Before): " + Arrays.toString(records));
        QuickSort.sortDatabaseRecords(records, 0, records.length - 1);
        System.out.println("After sorting by ID: " + Arrays.toString(records) + "\n");
        
        // Heap Sort: Process scheduling
        Process[] processes = {
            new Process("Browser", 3),
            new Process("Editor", 5),
            new Process("Compiler", 1),
            new Process("Media Player", 4)
        };
        System.out.println("Heap Sort - Processes (Before): " + Arrays.toString(processes));
        HeapSort.sortProcesses(processes);
        System.out.println("After sorting by priority: " + Arrays.toString(processes) + "\n");
        
        // DATA STRUCTURES DEMONSTRATIONS
        System.out.println("--- DATA STRUCTURES ---\n");
        
        // Stack: Expression evaluation
        String expression = "2+3*4";
        System.out.println("Stack - Expression Evaluation:");
        System.out.println("Expression: " + expression + " = " + ExpressionEvaluator.evaluateExpression(expression) + "\n");
        
        // Queue: Printer spooler
        System.out.println("Queue - Printer Spooler (Job Scheduling):");
        PrinterSpooler spooler = new PrinterSpooler();
        spooler.addJob("Report.pdf", 3);
        spooler.addJob("Invoice.doc", 2);
        spooler.addJob("Photo.jpg", 5);
        spooler.processJobs();
        System.out.println();
        
        // Linked List: Music playlist
        System.out.println("Linked List - Music Player Playlist:");
        Playlist playlist = new Playlist();
        playlist.addSong("Bohemian Rhapsody", "Queen");
        playlist.addSong("Imagine", "John Lennon");
        playlist.addSong("Hotel California", "Eagles");
        playlist.displayPlaylist();
        playlist.playNext();
        playlist.playNext();
        playlist.playPrevious();
        System.out.println();
        
        // Tree: File system
        FileSystem.demonstrateFileSystem();
        
        // Graph: Shortest path finding
        NavigationSystem.City ny = new NavigationSystem.City("New York");
        NavigationSystem.City boston = new NavigationSystem.City("Boston");
        NavigationSystem.City philly = new NavigationSystem.City("Philadelphia");
        NavigationSystem.City dc = new NavigationSystem.City("Washington DC");
        
        ny.addConnection(boston, 350);
        ny.addConnection(philly, 150);
        philly.addConnection(dc, 200);
        boston.addConnection(dc, 700);
        
        NavigationSystem.findShortestPath(ny, dc);
    }
}