package com.jutils.arrays.shuffle;

import java.util.Random;

/**
 * Fisher-Yates shuffle.
 */
public class Shuffle<T> {
    private final Random random = new Random();

    public void shuffle(T[] items) {
        for (int i = items.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            if (j != i) {
                T swap = items[j];
                items[j] = items[i];
                items[i] = swap;
            }
        }
    }
}
