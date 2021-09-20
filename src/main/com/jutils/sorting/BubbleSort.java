package com.jutils.sorting;

import java.util.Comparator;

/**
 * Bubble sort.
 */
public class BubbleSort<T> implements Sort<T> {
    @Override
    public void sort(T[] items, Comparator<T> comparator) {
        for (int i = items.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (comparator.compare(items[j], items[j + 1]) > 0) {
                    T swap = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = swap;
                }
            }
        }
    }
}
