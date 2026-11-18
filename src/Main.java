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

        String activeFile = "test";

        Main main = new Main();
        main.printGui();

        System.out.println(activeFile);
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

                    applySearch(sc, activeFile);
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
                    System.out.println("Run merge sort");
                    break;
                case 5:
                    int[] arr;

                    try {
                        // read file and split into an array (input is expected to be text file of comma separated numbers)
                        String content = Files.readString(Path.of(activeFile));
                        String[] values = content.split(",");

                        // initialize arr to prepare for mapping values to arr
                        arr = new int[values.length];

                        // map the String integers into int integers
                        for (int i = 0; i < values.length; i++) {
                            arr[i] = Integer.parseInt(values[i].trim());
                        }
                    } catch (Exception ex) {
                        // if it fails report the error
                        System.out.println("Error reading file into array");
                        break;
                    }

                    // track the time elapsed
                    long startTime = System.nanoTime();

                    quicksort(arr, 0, arr.length-1);

                    // track basic operations and elapsed time
                    long endTime = System.nanoTime();
                    double elapsedTime = (endTime - startTime) / 1_000_000.0;
                    System.out.printf("Basic operations: %s\n" +
                            "Elapsed time: %s ms\n", basicOperation, elapsedTime);

                    logSort(arr, activeFile, elapsedTime, "quicksort");

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

    void logSort(int[] arr, String activeFile, double elapsedTime, String sortType) {
        File file;
        File file2;

        // write sorted array into file, write time elapsed and basic operation count and save new file, report new file's name too
        try {
            file = new File(activeFile.substring(0, activeFile.length()-4) + "-" + sortType + ".txt");
            file2 = new File(activeFile.substring(0, activeFile.length()-4) + "-" + sortType + "-log.txt");
            if (file.createNewFile()) {
                System.out.printf("File %s and %s were successfully created\n", file.getName(), file2.getName());
                activeFile = file.getName();
            } else {
                System.out.printf("Creation of file %s or %s failed %s\n", file.getName(), file2.getName(), file.exists() || file2.exists()? " one file already exists": "");
                return;
            }
        } catch(IOException ex) {
            System.out.println("Error: " + ex);
            return;
        }

        try {
            FileWriter myWriter = new FileWriter(file.getName());
            FileWriter myWriter2 = new FileWriter(file2.getName());

            myWriter2.write("Basic operations: " + basicOperation + "\n" + "Elapsed time: " + elapsedTime +" ms\n");

            for(int i = 0; i < arr.length; i++) {
                // write n random numbers between 0 and n-1, comma separated
                if(i != 0) {myWriter.write(",");}

                myWriter.write(String.valueOf(arr[i]));
            }

            // close file writer
            myWriter.close();
            myWriter2.close();
        } catch (IOException e) {
            System.out.printf("Failed to write to file %s\n", file.getName());
        }
    }

    // the gui for the search menu
    void applySearch(Scanner sc, String activeFile) {
        int input = 0;
        basicOperation = 0;

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
                    System.out.println("Run selection search");
                    break;
                case 2:
                    System.out.println("Run binary search");
                    break;
                case 3:
                    System.out.println("Returning to main menu");
                    break mainLoop;
                default:
                    System.out.println("Invalid value inputted");
                    continue;            }
        }
    }

    void mergesort() {

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
}