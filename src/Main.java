/*
 * Name: Austyn Streeter
 * Course: CS203
 * Term: Fall 26
 * Project Activity # 2
 *
 * Contains: Selection Sort, Bubble Sort, Insertion Sort, Heap Sort.
 * Each sort counts key comparisons and element moves (swaps/assignments)
 * so the experimental data can be compared with the theoretical analysis.
 */

public class Main {

    // ----- Counters (reset before every run) -----
    public static long comparisons = 0;   // key comparisons between array elements
    public static long moves = 0;         // swaps and element assignments

    // Clears both counters before a new experiment
    public static void resetCounters() {
        comparisons = 0;
        moves = 0;
    }

    // Swaps two elements and counts it as one move
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        moves++;
    }

    // ----- Selection Sort: always Theta(n^2) comparisons -----
    public static void selectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {          // at most n-1 swaps total
                swap(a, i, minIndex);
            }
        }
    }

    // ----- Bubble Sort (with early-exit flag): best case O(n) on sorted input -----
    public static void bubbleSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {               // no swaps means already sorted
                break;
            }
        }
    }

    // ----- Insertion Sort: best O(n), average/worst O(n^2) -----
    public static void insertionSort(int[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            int key = a[i];
            int j = i - 1;
            // Shift larger elements one position to the right
            while (j >= 0) {
                comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    moves++;
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
            moves++;
        }
    }

     // ----- Heap Sort: O(n log n) in all cases -----
    public static void heapSort(int[] a) {
        int n = a.length;

        // Phase 1: build a max heap from the bottom up
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(a, n, i);
        }

        // Phase 2: repeatedly move the max to the end and restore the heap
        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end);
            heapify(a, end, 0);
        }
    }

    // Sifts a[i] down so the subtree rooted at i is a max heap (iterative)
    private static void heapify(int[] a, int heapSize, int i) {
        while (true) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < heapSize) {
                comparisons++;
                if (a[left] > a[largest]) {
                    largest = left;
                }
            }
            if (right < heapSize) {
                comparisons++;
                if (a[right] > a[largest]) {
                    largest = right;
                }
            }
            if (largest == i) {
                return;
            }
            swap(a, i, largest);
            i = largest;
        }
    }

    // ----- Helpers for the experiments -----

    // Returns true if the array is sorted in ascending order (use to verify output)
    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
      
    }
}