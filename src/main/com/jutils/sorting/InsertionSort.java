package com.jutils.sorting;

import java.util.Comparator;

/**
 * Insertion sort.
 */
public class InsertionSort<T> implements Sort<T> {
    @Override
    public void sort(T[] items, Comparator<T> comparator) {
        for (int i = 1; i < items.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(items[j - 1], items[j]) > 0) {
                    T swap = items[j - 1];
                    items[j - 1] = items[j];
                    items[j] = swap;
                } else {
                    break;
                }
            }
        }
    }
}
