package com.jutils.sorting;

import java.util.Comparator;
import java.util.Random;
import java.util.Stack;

/**
 * Non-recursive quicksort.
 */
public class Quicksort<T> implements Sort<T> {
    @Override
    public void sort(T[] items, Comparator<T> comparator) {
        if (items.length == 0) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.add(items.length - 1);
        stack.add(0);
        Random random = new Random();
        while (!stack.isEmpty()) {
            // 1. Choose a pivot.
            int left = stack.pop(), right = stack.pop();
            int pivot = left + random.nextInt(right - left + 1);
            // 2. Swap elements to make all elements in the left part are no greater than the pivot,
            //    as well as all elements in the right part are no less than the pivot.
            int i = left, j = right;
            T item = items[pivot];
            while (i < j) {
                while (j > pivot && comparator.compare(items[j], item) >= 0) {
                    j--;
                }
                if (j > pivot) {
                    items[pivot] = items[j];
                    pivot = j;
                }
                while (i < pivot && comparator.compare(items[i], item) <= 0) {
                    i++;
                }
                if (i < pivot) {
                    items[pivot] = items[i];
                    pivot = i;
                }
            }
            items[pivot] = item;
            // 3. Deal with the left part and the right part separately.
            if (left < pivot - 1) {
                stack.add(pivot - 1);
                stack.add(left);
            }
            if (right > pivot + 1) {
                stack.add(right);
                stack.add(pivot + 1);
            }
        }
    }
}
