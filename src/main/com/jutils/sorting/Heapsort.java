package com.jutils.sorting;

import java.util.Comparator;

/**
 * Heapsort.
 *
 * Given a parent node with index i, its child nodes' indices are i * 2 + 1 and i * 2 + 2
 */
public class Heapsort<T> implements Sort<T> {
    /*
     * Repair the max heap.
     */
    private void siftDown(T[] items, int parent, int max, Comparator<T> comparator) {
        while (true) {
            int child = parent * 2 + 1;
            if (child >= max) {
                return;
            }
            if (child + 1 < max && comparator.compare(items[child], items[child + 1]) < 0) {
                child++;
            }
            if (comparator.compare(items[parent], items[child]) > 0) {
                return;
            }
            T swap = items[parent];
            items[parent] = items[child];
            items[child] = swap;
            parent = child;
        }
    }

    @Override
    public void sort(T[] items, Comparator<T> comparator) {
        // 1. Heapify the array.
        int length = items.length;
        for (int i = length / 2 - 1; i > -1; i--) {
            siftDown(items, i, length, comparator);
        }
        // 2. Swap elements and repair the heap repeatedly.
        for (int i = length - 1; i > 0; i--) {
            T swap = items[0];
            items[0] = items[i];
            items[i] = swap;
            siftDown(items, 0, i, comparator);
        }
    }
}
