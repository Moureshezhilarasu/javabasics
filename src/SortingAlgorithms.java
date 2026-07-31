
public class SortingAlgorithms{

    public static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap array[j] and array[j + 1]
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            // Assume the current position holds the minimum element
            int min_idx = i;
            // Iterate through the unsorted portion to find the actual minimum
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min_idx]) {
                    // Update min_idx if a smaller elementis found
                    min_idx = j;
                }
            }
            // Move minimum element to its correct position
            int temp = arr[i];
            arr[i] = arr[min_idx];
            arr[min_idx] = temp;
        }
    }

    public static void InsertionSort(int arr[]) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            /* Move elements of arr[0..i-1], that are greater than key, to one position ahead of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    static void merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
        // Create temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];
        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }

        // Merge the temp arrays
        // Initial indices of first and second subarrays
        int i = 0, j = 0;
        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using merge()
    static void Mergesort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;

            // Sort first and second halves
            Mergesort(arr, l, m);
            Mergesort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    // A utility function to print array of size n
    static void printArray(int[] arr) {
        for (int val : arr) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11};

        System.out.print("Original array: ");
        printArray(arr);

        selectionSort(arr);

        System.out.print("SelectionSorted array: ");
        printArray(arr);

        int crr[] = {-40, 65, 8, 0, 85, -78};
        System.out.print("Original array: ");
        printArray(crr);

        InsertionSort(crr);

        System.out.print("InsertionSorted array: ");
        printArray(crr);

        int drr[] = {12, 11, 13, 5, 6, 7};

        System.out.println("Given array is");
        printArray(drr);

        Mergesort(arr, 0, arr.length - 1);

        System.out.println("Merge9Sorted array is");
        printArray(drr);

    }
}
