package com.jutils.sorting;

import java.util.Comparator;

/**
 * Selection sort.
 */
public class SelectionSort<T> implements Sort<T> {
    @Override
    public void sort(T[] items, Comparator<T> comparator) {
        for (int i = 0; i < items.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < items.length; j++) {
                if (comparator.compare(items[min], items[j]) > 0) {
                    min = j;
                }
            }
            if (min != i) {
                T swap = items[min];
                items[min] = items[i];
                items[i] = swap;
            }
        }
    }
}
