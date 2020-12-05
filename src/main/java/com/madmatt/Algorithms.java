package com.madmatt;

/**
 * Implementation of classic algorithms
 */
public class Algorithms {


    /**
     * LINEAR SEARCH ====================================
     */
    public static int search(int[] array, int value) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * BINARY SEARCH ========================================================
     */
    public static int binarySearch(int[] array, int elem, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2; // the index of the middle element

            if (elem == array[mid]) {
                return mid; // the element is found, return its index
            } else if (elem < array[mid]) {
                right = mid - 1; // go to the left subarray
            } else {
                left = mid + 1;  // go the the right subarray
            }
        }
        return -1; // the element is not found
    }

    /**
     * JUMP SEARCH ==============================================
     */
    public static int jumpSearch(int[] array, int target) {
        int currentRight = 0; // right border of the current block
        int prevRight = 0; // right border of the previous block

        /* If array is empty, the element is not found */
        if (array.length == 0) {
            return -1;
        }

        /* Check the first element */
        if (array[currentRight] == target) {
            return 0;
        }

        /* Calculating the jump length over array elements */
        int jumpLength = (int) Math.sqrt(array.length);

        /* Finding a block where the element may be present */
        while (currentRight < array.length - 1) {

            /* Calculating the right border of the following block */
            currentRight = Math.min(array.length - 1, currentRight + jumpLength);

            if (array[currentRight] >= target) {
                break; // Found a block that may contain the target element
            }

            prevRight = currentRight; // update the previous right block border
        }

        /* If the last block is reached and it cannot contain the target value => not found */
        if ((currentRight == array.length - 1) && target > array[currentRight]) {
            return -1;
        }

        /* Doing linear search in the found block */
        return backwardSearch(array, target, prevRight, currentRight);
    }

    public static int backwardSearch(int[] array, int target, int leftExcl, int rightIncl) {
        for (int i = rightIncl; i > leftExcl; i--) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * SELECTION SORT =========================================
     */
    public static int[] selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int index = i; // the index of the found min

            /* Iterating over the unsorted subarray to find the min */
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[index]) {
                    index = j;
                }
            }

            /* Exchanging the found min and the current element */
            int min = array[index];
            array[index] = array[i];
            array[i] = min;
        }

        return array;
    }

    /**
     * BUBBLE SORT ===============================================
     */
    public static int[] bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                /* if a pair of adjacent elements has the wrong order it swaps them */
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        return array;
    }

    /**
     * INSERTION SORT =============================================
     */
    public static int[] insertionSort(int[] array) {
        /* iterating over elements in the unsorted part */
        for (int i = 1; i < array.length; i++) {
            int elem = array[i]; // take the next element
            int j = i - 1;

            /* find a suitable position to insert and shift elements to the right */
            while (j >= 0 && array[j] > elem) {
                array[j + 1] = array[j]; // shifting
                j--;
            }
            array[j + 1] = elem; // insert the element in the found position in the sorted part
        }

        return array;
    }

    /**
     * COUNTING SORT - (UNSTABLE) ===========================================
     */
    public static int[] countingSort(int[] numbers) {
        int maxVal = 10; // we suppose the maximum is 10
        int k = maxVal + 1; // the length of the array containing counts
        int[] counts = new int[k]; // it stores 11 zeros with indexes from 0 to k-1

        /* in this loop we count distinct numbers in the input array */
        for (int i = 0; i < numbers.length; i++) {
            counts[numbers[i]]++;
        }

        int pos = 0; // a position in the numbers array

        /* in this loop we modify the input array to make it sorted */
        for (int num = 0; num < k; num++) { // get the next element
            int count = counts[num]; // get the count of the element
            while (count > 0) {
                numbers[pos] = num; // write it in the numbers array
                pos++;
                count--;
            }
        }

        return numbers;
    }

    /**
     * STABLE COUNTING SORT ============================================
     */
    public static int[] stableCountingSort(int[] numbers, int max) {
        int k = max + 1; // the length of the array containing counts
        int[] counts = new int[k]; // it stores counts with indexes from 0 to k-1

        for (int i = 0; i < numbers.length; i++) {
            counts[numbers[i]]++;
        }

        for (int i = 1; i < counts.length; i++) {
            counts[i] = counts[i - 1] + counts[i]; // cumulative counts
        }

        int[] sortedNumbers = new int[numbers.length];

        for (int i = numbers.length - 1; i >= 0; i--) {  // go through input array in backward
            int rightmostIndex = counts[numbers[i]] - 1; // get the rightmost index
            sortedNumbers[rightmostIndex] = numbers[i];
            counts[numbers[i]]--; // decrease the index to calculate the previous occurrence
        }

        return sortedNumbers;
    }

    /**
     * FIND INDEX OF MAX =================================
     */
    public static int findIndexOfMax(int[] numbers) {
        int index = 0;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > numbers[index]) {
                index = i;
            }
        }
        return index;
    }

    /**
     * MERGE SORT ============================================================
     */
    public static void mergeSort(int[] array, int leftIncl, int rightExcl) {
        // the base case: if subarray contains <= 1 items, stop dividing because it's sorted
        if (rightExcl <= leftIncl + 1) {
            return;
        }

        /* divide: calculate the index of the middle element */
        int middle = leftIncl + (rightExcl - leftIncl) / 2;

        mergeSort(array, leftIncl, middle);  // conquer: sort the left subarray
        mergeSort(array, middle, rightExcl); // conquer: sort the right subarray

        /* combine: merge both sorted subarrays into sorted one */
        merge(array, leftIncl, middle, rightExcl);
    }

    private static void merge(int[] array, int left, int middle, int right) {
        int i = left;   // index for the left subarray
        int j = middle; // index for the right subarray
        int k = 0;      // index for the temp subarray

        int[] temp = new int[right - left]; // temporary array for merging

    /* get the next lesser element from one of two subarrays
       and then insert it in the array until one of the subarrays is empty */
        while (i < middle && j < right) {
            if (array[i] <= array[j]) {
                temp[k] = array[i];
                i++;
            } else {
                temp[k] = array[j];
                j++;
            }
            k++;
        }

        /* insert all the remaining elements of the left subarray in the array */
        for (; i < middle; i++, k++) {
            temp[k] = array[i];
        }

        /* insert all the remaining elements of the right subarray in the array */
        for (; j < right; j++, k++) {
            temp[k] = array[j];
        }

        /* effective copying elements from temp to array */
        System.arraycopy(temp, 0, array, left, temp.length);
    }

    /**
     * QUICK SORT ===================================================
     */
    public static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right); // the pivot is already on its place
            quickSort(array, left, pivotIndex - 1);  // sort the left subarray
            quickSort(array, pivotIndex + 1, right); // sort the right subarray
        }
    }

    private static int partition(int[] array, int left, int right) {
        int pivot = array[right];  // choose the rightmost element as the pivot
        int partitionIndex = left; // the first element greater than the pivot

        /* move large values into the right side of the array */
        for (int i = left; i < right; i++) {
            if (array[i] <= pivot) { // may be used '<' as well
                swap(array, i, partitionIndex);
                partitionIndex++;
            }
        }

        swap(array, partitionIndex, right); // put the pivot on a suitable position

        return partitionIndex;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
