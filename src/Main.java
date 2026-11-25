import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.io.*;

public class Main {
    // tracks basic operation iterations
    static long basicOperation = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Main main = new Main();
        main.printGui();
    }

    void printGui() {
        // initialize variables
        int input = 0;
        Scanner sc = new Scanner(System.in);
        String activeFile = "";
        File file;

        // named while loop, this contains the main GUI code, in a loop so desired behavior for invalid inputs happens (repeats the code)
        mainLoop : while (true) {
            System.out.printf("""
                    \nActive file: %s
                    1) Load/create file
                    2) Apply sort
                    3) Apply search
                    4) Quit
                    """, activeFile.isEmpty()? "[None selected]": activeFile);

            // validate user input
            try {
                input = sc.nextInt();
            } catch (Exception ex) {
                System.out.println("Non-numeric input detected. Please try again.");

                // clear scanner
                sc.nextLine();
            }

            // switch case that aligns with the menu previously shown
            switch (input) {
                // load/create file
                case 1:
                    activeFile = loadCreateFile(sc, activeFile);
                    break;
                // apply sort
                case 2:
                    // create file object and check that file exists
                    file = new File(activeFile);
                    if(!file.exists()) {
                        System.out.printf("Error: File %s does not exist\n", file.getName());
                        continue;
                    }

                    applySort(sc, activeFile);
                    break;
                // apply search
                case 3:
                    // create file object and check that file exists
                    file = new File(activeFile);
                    if(!file.exists()) {
                        System.out.printf("Error: File %s does not exist\n", file.getName());
                        continue;
                    }

                    // ask if the file is sorted
                    System.out.println("Is the provided file sorted?");
                    boolean sorted = false;
                    try {
                        if (sc.nextLine().substring(0, 1).equalsIgnoreCase("y")) {
                            sorted = true;
                        }
                    } catch (Exception ex) {}

                    applySearch(sc, activeFile, sorted);
                    break;
                // quit
                case 4:
                    System.out.println("4");
                    break mainLoop;
                // reprints the gui if invalid input is provided
                default:
                    System.out.println("Invalid value inputted\n");
                    break;
            }

        }
    }

    // GUI for case 1 of printGUI, separated into another function for clean code. Returns activeFile to update in printGUI.
    String loadCreateFile(Scanner sc, String activeFile) {
        int input = 0;
        String tempFile = "";
        File file;

        // used so that if invalid input is given, it just repeats this section of code
        mainLoop : while (true) {
            System.out.printf("""
                    \nActive file: %s
                    1) Load Existing File
                    2) Generate New Random Input
                    3) Generate Sorted Input (Ascending)
                    4) Generate Sorted Input (Descending)
                    5) Return to Main Menu
                    """, activeFile.isEmpty()? "[None selected]": activeFile);

            // validate user input
            try {
                input = sc.nextInt();
            } catch (Exception ex) {
                System.out.println("Non-numeric input detected. Please try again.");
                sc.nextLine();
            }

            // switch case that aligns with provided GUI
            switch (input) {
                // load existing file
                case 1:
                    // user input for existing file
                    System.out.println("Input the name of file you wish to use (with extension): ");

                    // clear scanner and get temporary variable to contain input
                    sc.nextLine();
                    tempFile = sc.nextLine();

                    // check that file exists. If it doesnt, report the issue. If it does, assign tempFile to activeFile and return it
                    file = new File(tempFile);
                    if(!file.exists()) {
                        System.out.printf("File %s not found\n", activeFile);
                        continue;
                    } else {
                        activeFile = tempFile;

                        // if file is valid, return the file's name so printGUI can use this value
                        return activeFile;
                    }
                case 2:
                    System.out.println("\nCreating text file, please enter name (extension already provided)");

                    // clear scanner and get user input for file to be created
                    sc.nextLine();
                    tempFile = sc.nextLine();

                    try {
                        file = new File(tempFile + ".txt");
                        if (file.createNewFile()) {
                            System.out.printf("File %s was successfully created\n", file.getName());
                            activeFile = tempFile + ".txt";
                        } else {
                            System.out.printf("Creation of file %s failed %s\n", file.getName(), file.exists()? "file already exists": "");
                            continue;
                        }
                    } catch(IOException ex) {
                        System.out.println("Error: " + ex);
                        continue;
                    }
                    // if file creation fails then continue to effectively restart interaction

                    System.out.println("\nGenerating n numbers, what is the desired n?");

                    // get user input for n
                    input = sc.nextInt();

                    try {
                        FileWriter myWriter = new FileWriter(file.getName());
                        Random r = new Random();

                        for(int i = 0; i < input; i++) {
                            // write n random numbers between 0 and n-1, comma separated
                            if(i != 0) {myWriter.write(",");}

                            myWriter.write(String.valueOf(r.nextInt(input)));
                        }

                        // close file writer
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.printf("Failed to write to file %s\n", file.getName());
                    }

                    return activeFile;
                case 3:
                    System.out.println("\nCreating text file, please enter name (extension already provided)");

                    // clear scanner and get user input for file to be created
                    sc.nextLine();
                    tempFile = sc.nextLine();

                    try {
                        file = new File(tempFile + ".txt");
                        if (file.createNewFile()) {
                            System.out.printf("File %s was successfully created\n", file.getName());
                            activeFile = tempFile + ".txt";
                        } else {
                            System.out.printf("Creation of file %s failed %s\n", file.getName(), file.exists()? "file already exists": "");
                            continue;
                        }
                    } catch(IOException ex) {
                        System.out.println("Error: " + ex);
                        continue;
                    }
                    // if file creation fails then continue to effectively restart interaction

                    System.out.println("\nGenerating n numbers, what is the desired n?");

                    // get user input for n
                    input = sc.nextInt();

                    try {
                        FileWriter myWriter = new FileWriter(file.getName());

                        for(int i = 0; i < input; i++) {
                            // write n numbers between 0 and n-1, comma separated and ascending
                            if(i != 0) {myWriter.write(",");}

                            myWriter.write(String.valueOf(i));
                        }

                        // close file writer
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.printf("Failed to write to file %s\n", file.getName());
                    }

                    return activeFile;
                case 4:
                    System.out.println("\nCreating text file, please enter name (extension already provided)");

                    // clear scanner and get user input for file to be created
                    sc.nextLine();
                    tempFile = sc.nextLine();

                    try {
                        file = new File(tempFile + ".txt");
                        if (file.createNewFile()) {
                            System.out.printf("File %s was successfully created\n", file.getName());
                            activeFile = tempFile + ".txt";
                        } else {
                            System.out.printf("Creation of file %s failed %s\n", file.getName(), file.exists()? "file already exists": "");
                            continue;
                        }
                    } catch(IOException ex) {
                        System.out.println("Error: " + ex);
                        continue;
                    }
                    // if file creation fails then continue to effectively restart interaction

                    System.out.println("\nGenerating n numbers, what is the desired n?");

                    // get user input for n
                    input = sc.nextInt();

                    try {
                        FileWriter myWriter = new FileWriter(file.getName());

                        for(int i = input - 1; i >= 0; i--) {
                            // write n numbers between 0 and n-1, comma separated and ascending
                            if(i != input - 1) {myWriter.write(",");}

                            myWriter.write(String.valueOf(i));
                        }

                        // close file writer
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.printf("Failed to write to file %s\n", file.getName());
                    }

                    return activeFile;
                case 5:
                    System.out.println("Returning to main menu");
                    return activeFile;
                default:
                    System.out.println("Invalid value inputted");
                    continue;
            }
        }
    }

    // the gui for the sort menu
    void applySort(Scanner sc, String activeFile) {
        int input = 0;
        basicOperation = 0;

        // initialize variables for the switch case
        int[] arr;
        long startTime, endTime;
        double elapsedTime;

        // used so that if invalid input is given, it just repeats this section of code
        mainLoop : while (true) {
            System.out.printf("""
                    \nActive file: %s
                    1) Selection sort
                    2) Bubble sort
                    3) Insertion sort
                    4) Merge sort
                    5) Quick sort
                    6) Heap sort
                    7) Run all
                    8) Return to Main Menu
                    """, activeFile.isEmpty() ? "[None selected]" : activeFile);

            // validate user input
            try {
                input = sc.nextInt();
            } catch (Exception ex) {
                System.out.println("Non-numeric input detected. Please try again.");
                sc.nextLine();
            }

            // switch case that aligns with provided GUI
            switch (input) {
                case 1:
                    System.out.println("Run selection sort");
                    break;
                case 2:
                    System.out.println("Run bubble sort");
                    break;
                case 3:
                    System.out.println("Run insertion sort");
                    break;
                case 4:
                    try {
                        arr = readArrayFromFile(activeFile);
                    } catch (Exception ex) {
                        // if it fails report the error
                        System.out.println("Error reading file into array");
                        break;
                    }

                    // track the time elapsed
                    startTime = System.nanoTime();

                    arr = mergesort(arr, 0, arr.length);

                    // track basic operations and elapsed time
                    endTime = System.nanoTime();
                    elapsedTime = (endTime - startTime) / 1_000_000.0;
                    System.out.printf("Basic operations: %s\n" +
                            "Elapsed time: %s ms\n", basicOperation, elapsedTime);

                    log(arr, activeFile, elapsedTime, "merge sort", true, 0, 0);

                    break mainLoop;
                case 5:
                    try {
                        arr = readArrayFromFile(activeFile);
                    } catch (Exception ex) {
                        // if it fails report the error
                        System.out.println("Error reading file into array");
                        break;
                    }

                    // track the time elapsed
                    startTime = System.nanoTime();

                    quicksort(arr, 0, arr.length-1);

                    // track basic operations and elapsed time
                    endTime = System.nanoTime();
                    elapsedTime = (endTime - startTime) / 1_000_000.0;
                    System.out.printf("Basic operations: %s\n" +
                            "Elapsed time: %s ms\n", basicOperation, elapsedTime);

                    log(arr, activeFile, elapsedTime, "quicksort",true, 0, 0);

                    break mainLoop;
                case 6:
                    System.out.println("Run heap sort");
                    break;
                case 7:
                    System.out.println("Run all");
                    break;
                case 8:
                    System.out.println("Returning to main menu");
                    break mainLoop;
                default:
                    System.out.println("Invalid value inputted");
                    continue;            }
        }
    }

    // reads file of comma separated integers from file into an array, which it returns
    int[] readArrayFromFile(String activeFile) throws IOException {
        int[] arr;

        // read file and split into an array (input is expected to be text file of comma separated numbers)
        String content = Files.readString(Path.of(activeFile));
        String[] values = content.split(",");

        // initialize arr to prepare for mapping values to arr
        arr = new int[values.length];

        // map the String integers into int integers
        for (int i = 0; i < values.length; i++) {
            arr[i] = Integer.parseInt(values[i].trim());
        }

        return arr;
    }

    // saves a file of sorted comma separated integers and a file containing how long in ms it took to run the operation and how many basic operations
    void log(int[] arr, String activeFile, double elapsedTime, String sortType, boolean sort, int value, int index) {
        File file;
        File file2;

        if (sort) {
            // write sorted array into file, write time elapsed and basic operation count and save new file, report new file's name too
            try {
                file = new File(activeFile.substring(0, activeFile.length() - 4) + "-" + sortType + ".txt");
                if (file.createNewFile()) {
                    System.out.printf("File %s was successfully created\n", file.getName());
                    activeFile = file.getName();
                } else {
                    System.out.printf("Creation of file %s failed %s%s\n", file.getName(), file.getName(), file.exists() ? " already exists" : "");
                    return;
                }
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
                return;
            }

            try {
                FileWriter myWriter = new FileWriter(file.getName());

                for (int i = 0; i < arr.length; i++) {
                    // write n random numbers between 0 and n-1, comma separated
                    if (i != 0) {
                        myWriter.write(",");
                    }

                    myWriter.write(String.valueOf(arr[i]));
                }

                // close file writer
                myWriter.close();
            } catch (IOException e) {
                System.out.printf("Failed to write to file %s\n", file.getName());
            }
        }

        try {
            file2 = new File(activeFile.substring(0, activeFile.length()-4) + "-" + sortType + "-log.txt");
            if (file2.createNewFile()) {
                System.out.printf("File %s was successfully created\n", file2.getName());
            } else {
                System.out.printf("Creation of file %s failed %s%s\n", file2.getName(), file2.getName(), file2.exists()? " already exists": "");
                return;
            }
        } catch(IOException ex) {
            System.out.println("Error: " + ex);
            return;
        }

        try {
            FileWriter myWriter2 = new FileWriter(file2.getName());
            myWriter2.write("Basic operations: " + basicOperation + "\n" + "Elapsed time: " + elapsedTime +" ms\n");
            if (!sort) {
                myWriter2.write("Value " + value + " was found at index " + index);
            }

            // close file writer
            myWriter2.close();
        } catch (IOException e) {
            System.out.printf("Failed to write to file %s\n", file2.getName());
        }
    }

    // the gui for the search menu
    void applySearch(Scanner sc, String activeFile, boolean sorted) {
        int input = 0;
        basicOperation = 0;

        // initialize variables for the switch case
        int result;
        int[] arr;
        long startTime, endTime;
        double elapsedTime;

        // used so that if invalid input is given, it just repeats this section of code
        mainLoop : while (true) {
            System.out.printf("""
                    \nActive file: %s
                    1) Selection search
                    2) Binary search
                    3) Return to Main Menu
                    """, activeFile.isEmpty() ? "[None selected]" : activeFile);

            // validate user input
            try {
                input = sc.nextInt();
            } catch (Exception ex) {
                System.out.println("Non-numeric input detected. Please try again.");
                sc.nextLine();
            }

            // switch case that aligns with provided GUI
            switch (input) {
                case 1:
                    try {
                        arr = readArrayFromFile(activeFile);
                    } catch (Exception ex) {
                        // if it fails report the error
                        System.out.println("Error reading file into array");
                        break;
                    }

                    System.out.println("Enter desired value to search for: ");
                    // get user input for desired value
                    try {
                        input = sc.nextInt();
                    } catch (Exception ex) {
                        System.out.println("Non-numeric input detected. Please try again.");
                        sc.nextLine();
                        break;
                    }

                    // track the time elapsed
                    startTime = System.nanoTime();

                    result = selectionSearch(arr, input, sorted);

                    // track basic operations and elapsed time
                    endTime = System.nanoTime();
                    elapsedTime = (endTime - startTime) / 1_000_000.0;
                    System.out.printf("Basic operations: %s\n" +
                            "Elapsed time: %s ms\n" +
                            "Value %d was found at index %d\n", basicOperation, elapsedTime, input, result);

                    log(arr, activeFile, elapsedTime, "selection search", false, input, result);
                    
                    break mainLoop;
                case 2:
                    try {
                        arr = readArrayFromFile(activeFile);
                    } catch (Exception ex) {
                        // if it fails report the error
                        System.out.println("Error reading file into array");
                        break;
                    }

                    System.out.println("Enter desired value to search for: ");
                    // get user input for desired value
                    try {
                        input = sc.nextInt();
                    } catch (Exception ex) {
                        System.out.println("Non-numeric input detected. Please try again.");
                        sc.nextLine();
                        break;
                    }

                    // track the time elapsed
                    startTime = System.nanoTime();

                    result = binarySearch(arr, input, 0, arr.length);

                    // track basic operations and elapsed time
                    endTime = System.nanoTime();
                    elapsedTime = (endTime - startTime) / 1_000_000.0;
                    System.out.printf("Basic operations: %s\n" +
                            "Elapsed time: %s ms\n" +
                            "Value %d was found at index %d\n", basicOperation, elapsedTime, input, result);

                    log(arr, activeFile, elapsedTime, "binary search", false, input, result);

                    break mainLoop;
                case 3:
                    System.out.println("Returning to main menu");
                    break mainLoop;
                default:
                    System.out.println("Invalid value inputted");
                    continue;            }
        }
    }


    int[] mergesort(int[] arr, int left, int right) {
        // if only one element return
        if (right - left <= 1) {return new int[] {arr[left]};}

        int midpoint = ((right - left) / 2) + left;

        // divide the array into subarrays and recursively call
        int[] leftHalf = mergesort(arr, left, midpoint);
        int[] rightHalf = mergesort(arr, midpoint, right);
        int[] whole = new int[leftHalf.length + rightHalf.length];

        // merge the arrays
        int i = 0, j = 0, k = 0;
        while(i < leftHalf.length && j < rightHalf.length) {
            if (leftHalf[i] <= rightHalf[j]) {
                whole[k] = leftHalf[i];
                i++;
            } else {
                whole[k] = rightHalf[j];
                j++;
            }
            k++;
            basicOperation++;
        }

        // copy remaining elements of leftHalf into whole
        while (i < leftHalf.length) {
            whole[k] = leftHalf[i];
            i++;
            k++;
            basicOperation++;
        }

        // copy remaining elements of rightHalf into whole
        while (j < rightHalf.length) {
            whole[k] = rightHalf[j];
            j++;
            k++;
            basicOperation++;
        }

        return whole;
    }

    // standard lomuto partition code
    int lomutoPartition(int[] array, int left, int right) {
        // pivot is the pivot, and flag is the last value <= the pivot
        int pivot = array[left], flag = left;

        // iterate through and check if current value is less than or equal to partition
        for(int i = left + 1; i <= right; i++) {
            if(array[i] <= pivot) {
                ++flag;

                // swap the value with the first value greater than the partition
                int temp = array[i];
                array[i] = array[flag];
                array[flag] = temp;
            }
            // increment basic operation counter
            ++basicOperation;
        }
        // move the partition into place
        int temp = array[left];
        array[left] = array[flag];
        array[flag] = temp;

        // return the index the partition went into
        return flag;
    }

    // standard recursive implementation of a quicksort algorithm
    void quicksort(int[] array, int left, int right) {
        // check that at least 2 elements are present and the right bound is greater than the left bound
        if (left >= right) {
            return;
        }

        // run the lomuto partition
        int partition = lomutoPartition(array, left, right);

        // recursive call to itself
        quicksort(array, left, partition - 1);
        quicksort(array, partition + 1, right);
    }

    // standard selection search
    int selectionSearch(int[] arr, int value, boolean sorted) {
        // iterate through array and if the value at index i = value return the index, if sorted and arr[i] > value stop early. If not found return -1
        for(int i = 0; i < arr.length; i++) {
            ++basicOperation;
            if(sorted && arr[i] > value) {
                return -1;
            } else if(arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    // standard binary search
    int binarySearch(int[] arr, int value, int left, int right) {
        ++basicOperation;
        int middleIndex = (right + left)/2;
        // base case
        if (left >= right) {
            return -1;
        }

        // if value = arr[index] return index, if value > arr[index] check the upper half of the array, otherwise check lower half
        if(arr[middleIndex] == value) {
            return middleIndex;
        } else if(arr[middleIndex] < value) {
            return binarySearch(arr, value, middleIndex + 1, right);
        } else {
            return binarySearch(arr, value, left, middleIndex);
        }
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
}