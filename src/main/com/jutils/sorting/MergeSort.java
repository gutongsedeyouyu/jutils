package com.jutils.sorting;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Bottom-up merge sort.
 */
public class MergeSort<T> implements Sort<T> {
    @Override
    public void sort(T[] items, Comparator<T> comparator) {
        // 1. Subarrays of length 1 are already sorted.
        int length = items.length;
        T[] temp1 = items;
        T[] temp2 = Arrays.copyOf(items, length);
        // 2. Repeatedly merge subarrays until number of merged subarrays is 1.
        for (int width = 1; width < length; width *= 2) {
            // 2.1 Do merge subarrays.
            for (int left = 0; left < length; left += width * 2) {
                int pivot = Math.min(left + width, length);
                int right = Math.min(left + width * 2, length);
                int i = left, j = pivot;
                for (int k = left; k < right; k++) {
                    if ((j >= right) || (i < pivot && comparator.compare(temp1[i], temp1[j]) <= 0)) {
                        temp2[k] = temp1[i++];
                    } else {
                        temp2[k] = temp1[j++];
                    }
                }
            }
            // 2.2 Swap variables instead of simply copy data back.
            T[] swap = temp1;
            temp1 = temp2;
            temp2 = swap;
        }
        // 3. Copy data back.
        for (int i = 0; i < length; i++) {
            items[i] = temp1[i];
        }
    }
}
