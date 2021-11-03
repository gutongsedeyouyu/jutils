package test.jutils.arrays.shuffle;

import com.jutils.arrays.shuffle.Shuffle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ShuffleTest {
    private static Shuffle<Integer> shuffle;

    @BeforeAll
    public static void init() {
        shuffle = new Shuffle<>();
    }

    @Test
    public void testNull() {
        assertThrows(NullPointerException.class, () -> shuffle.shuffle(null));
    }

    @Test
    public void testEmpty() {
        assertDoesNotThrow(() -> shuffle.shuffle(new Integer[0]));
    }

    @Test
    public void testShuffle() {
        for (int length = 1; length < 100; length++) {
            Integer[] items = new Integer[length];
            for (int i = 0; i < length; i++) {
                items[i] = i;
            }
            shuffle.shuffle(items);
            Arrays.sort(items);
            for (int i = 0; i < length; i++) {
                assertEquals(i, items[i]);
            }
        }
    }
}
