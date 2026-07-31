
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BinaryHeap<E> {
    private final List<E> heap;
    private final Comparator<? super E> cmp;

    // Construct empty heap with comparator
    public BinaryHeap(Comparator<? super E> comparator) {
        this.heap = new ArrayList<>();
        this.cmp = comparator;
    }

    // Construct heap from array (build-heap O(n))
    @SafeVarargs
    public BinaryHeap(Comparator<? super E> comparator, E... items) {
        this.heap = new ArrayList<>(Arrays.asList(items));
        this.cmp = comparator;
        buildHeap();
    }

    // Add element (O(log n))
    public void add(E value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    // Peek root (min or max depending on comparator) (O(1))
    public E peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    // Poll root (remove and return) (O(log n))
    public E poll() {
        if (heap.isEmpty()) return null;
        return removeAt(0);
    }

    // Remove first occurrence of value (O(n) to find + O(log n) to fix)
    public boolean remove(E value) {
        int idx = heap.indexOf(value);
        if (idx == -1) return false;
        removeAt(idx);
        return true;
    }

    // Size and emptiness helpers
    public int size() { return heap.size(); }
    public boolean isEmpty() { return heap.isEmpty(); }

    // Build heap in O(n)
    private void buildHeap() {
        for (int i = parentIndex(heap.size() - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    // Remove element at index and return it
    private E removeAt(int index) {
        int lastIndex = heap.size() - 1;
        E removed = heap.get(index);
        swap(index, lastIndex);
        heap.remove(lastIndex);
        // If we removed the last element, we are done
        if (index == lastIndex) return removed;
        // Try siftDown then siftUp (one of them will restore heap)
        siftDown(index);
        siftUp(index);
        return removed;
    }

    // Sift up (heapify upward)
    private void siftUp(int idx) {
        while (idx > 0) {
            int p = parentIndex(idx);
            if (compare(heap.get(idx), heap.get(p)) >= 0) break;
            swap(idx, p);
            idx = p;
        }
    }

    // Sift down (heapify downward)
    private void siftDown(int idx) {
        int n = heap.size();
        while (true) {
            int left = leftChildIndex(idx);
            int right = rightChildIndex(idx);
            int smallestOrLargest = idx;

            if (left < n && compare(heap.get(left), heap.get(smallestOrLargest)) < 0)
                smallestOrLargest = left;
            if (right < n && compare(heap.get(right), heap.get(smallestOrLargest)) < 0)
                smallestOrLargest = right;

            if (smallestOrLargest == idx) break;
            swap(idx, smallestOrLargest);
            idx = smallestOrLargest;
        }
    }

    // Compare using comparator; comparator defines heap order
    private int compare(E a, E b) {
        return cmp.compare(a, b);
    }

    // Index helpers
    private int parentIndex(int i) { return (i - 1) / 2; }
    private int leftChildIndex(int i) { return 2 * i + 1; }
    private int rightChildIndex(int i) { return 2 * i + 2; }

    // Swap two elements
    private void swap(int i, int j) {
        E tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    // Return internal list (read-only copy) for inspection
    public List<E> toList() {
        return new ArrayList<>(heap);
    }

    // Example usage and simple test
    public static void main(String[] args) {
        // Min-heap for integers (natural order)
        BinaryHeap<Integer> minHeap = new BinaryHeap<>(Comparator.naturalOrder(), 5, 3, 8, 1, 2);
        System.out.println("Min-heap initial (array order): " + minHeap.toList());
        System.out.println("Min peek: " + minHeap.peek());
        minHeap.add(0);
        System.out.println("After add(0): " + minHeap.toList());
        System.out.println("Poll repeatedly:");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }
        System.out.println("\n");

        // Max-heap for integers (reverse order)
        BinaryHeap<Integer> maxHeap = new BinaryHeap<>(Comparator.reverseOrder());
        int[] values = {10, 4, 7, 1, 9, 20};
        for (int v : values) maxHeap.add(v);
        System.out.println("Max-heap array representation: " + maxHeap.toList());
        System.out.println("Max peek: " + maxHeap.peek());
        maxHeap.remove(7);
        System.out.println("After remove(7): " + maxHeap.toList());
        System.out.println("Poll max: " + maxHeap.poll());
        System.out.println("Remaining: " + maxHeap.toList());
    }
}
