import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sort {
    public static void insertionSort(int[] arr) { // insertion sort method
        int j;
        for (int i = 1; i < arr.length; i++) { //pass through array
            int temp = arr[i];
            for (j = i; j > 0 && temp > arr[j - 1]; j--) // 'slide' smaller elements to front of array
                arr[j] = arr[j - 1];
            arr[j] = temp;
        }
    }

    public static void bubbleSort(int[] arr) { // bubble sort method with O(n) best-case
        for (int i = arr.length - 1; i > 0; i--) { // start at end
            boolean swap = false; // tracks if elements were moved during an inner pass
            for (int j = 0; j < i; j++) { // move elements within the unsorted part of the array
                if (arr[j] < arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j+1] = temp;
                    swap = true;
                }
            }
            if (!swap) // if no moves were made in the inner pass, array is sorted
                return;
        
        }
    }

    public static void shellSort(int[] arr) { //shell sort method using hibbard's sequence
        int gap = 1; // use hibbard's to determine gap size
        while (gap * 2 <= arr.length)
            gap = gap * 2;
        gap = gap - 1;
        int j;
        while(gap >= 1) {
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                for (j = i; j >= gap && temp > arr[j-gap]; j = j - gap) // insertion comparisons with increment
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
            gap = gap / 2; // half increment
        }
    }

    public static void quickSort(int[] arr) { // quick sort driver
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) { // recursive quickSort helper method
        if (left < right) {
            int split = partition(arr, left, right);
            quickSort(arr, left, split);
            quickSort(arr, split + 1, right);
        }

    }

    private static int partition(int[] arr, int left, int right) { // partition method
        int pivot = arr[(left + right) / 2];
        int i = left - 1;
        int j = right + 1;
        while (true) {
            do { 
                i++; 
            } while (arr[i] > pivot);
            do {
                j--;
            } while (arr[j] < pivot);
            if (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
            else
                return j;
        }
    }

    public static void mergeSort(int[] arr) { // mergeSort driver method
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int[] temp, int left, int right) { // mergeSort helper method
        if (left >= right)
            return;
        int mid = (left + right) / 2;
        mergeSort(arr, temp, left, mid);
        mergeSort(arr, temp, mid + 1, right);
        merge(arr, temp, left, mid, mid + 1, right);
    }

    private static void merge(int[] arr, int[] temp, int leftStart, int leftEnd, int rightStart, int rightEnd) {
        int i = leftStart;
        int k = leftStart;
        int j = rightStart;
        while (i <= leftEnd && j <= rightEnd) {
            if (arr[i] >= arr[j]) {
                temp[k] = arr[i];
                i++;
            }
            else {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }
        while(i <= leftEnd) {
			temp[k] = arr[i];
			i++;
			k++;
		}
		while(j <= rightEnd) {
			temp[k] = arr[j];
			j++;
			k++;
		}
        for (int l = leftStart; l <= rightEnd; l++) {
            arr[l] = temp[l];
        }
    }

    public static void upgradedQuickSort(int[] arr, int depth, int k) { // driver method
        UQSHelper(arr, 0, arr.length - 1, depth, k); // helper method for upgraded quick sort
    }

    private static void UQSHelper(int[] arr, int left, int right, int depth, int k) {
        if (right - left > k) { // if number of elements in subarray exceeds k
            if (depth == 0) { // if depth has been reached, then merge sort subarrays
                int[] temp = new int[arr.length];
                mergeSort(arr, temp, left, right);
                return;
            }
            int split = partition(arr, left, right); // else, call quick sort on subarrays and reduce depth
            UQSHelper(arr, left, split, depth - 1, k);
            UQSHelper(arr, split + 1, right, depth - 1, k);
        }
        else { // if the number of elements in the subarray is at or less than k
            UQSInsertion(arr, left, right); // call special insertion sort method on subarrays
        }
    }

    private static void UQSInsertion(int[] arr, int left, int right) { // special insertion sort method that sort only a specified part of the original array i.e. subarrays
        int j;
        for (int i = left; i <= right; i++) { //pass through array
            int temp = arr[i];
            for (j = i; j > 0 && temp > arr[j - 1]; j--) // 'slide' smaller elements to front of array
                arr[j] = arr[j - 1];
            arr[j] = temp;
        }
    }

    public static int[] generateRandomArray(int n)
    {
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++)
        {
            boolean neg = Math.random() > 0.5;
            arr[i] = (int) (Math.random() * 10000); //inserts random integer in [0,9999]
            if (neg)
                arr[i] = arr[i] * -1;
        }
        return arr;
    }

    public static void readCommands(String filepath) { // method to read commands of format: sortingAlg: [1,2,...] or upgradedQuickSort: d ,  k [1,2,...]
        try {
            File text = new File(filepath); // turn filepath into File
            Scanner reader = new Scanner(text);
            while (reader.hasNextLine()) { // read through file line-by-line
                String data = reader.nextLine();
                if (data.equals("")) //break condition for empty-line, incorrect formatting
                    break;
                int index = data.indexOf("[") + 1; //get array part of command and turn it into an int array
                String[] strArr = data.substring(index, data.length() - 1).split(",");
                int[] arr = new int[strArr.length];
                for (int i = 0; i < strArr.length; i++) {
                    arr[i] = Integer.parseInt(strArr[i]);
                }
                if (data.contains("insertion")) // run sorting algorithm depending on commands
                    insertionSort(arr); 
                else if (data.contains("bubble")) 
                    bubbleSort(arr); 
                else if (data.contains("shell")) 
                    shellSort(arr); 
                else if (data.contains("quick"))
                    quickSort(arr);
                else if (data.contains("merge")) 
                    mergeSort(arr); 
                else if (data.contains("upgraded")) {
                    int colon = data.indexOf(":");
                    int depth = Integer.parseInt(data.substring(colon + 2, colon + 3));
                    int k = Integer.parseInt(data.substring(colon + 6, colon + 7));

                    upgradedQuickSort(arr, depth, k); 
                }
                else {
                    System.out.print("Invalid Input. Unable to sort: "); // invalid input handling
                }

                System.out.print("[ "); // print out sorted arrays
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i]);
                    if (i != arr.length - 1)
                        System.out.print(", ");
                }
                System.out.print(" ]");
                System.out.println();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found!");
            e.printStackTrace();
        }
        


    }
}