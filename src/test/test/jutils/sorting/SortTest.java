package test.jutils.sorting;

import com.jutils.sorting.*;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SortTest {
    @Test
    public void testEmpty() {
        assertDoesNotThrow(() ->
                new BubbleSort<Item>().sort(new Item[] {}, ItemComparator.getInstance())
        );
        assertDoesNotThrow(() ->
                new SelectionSort<Item>().sort(new Item[] {}, ItemComparator.getInstance())
        );
        assertDoesNotThrow(() ->
                new InsertionSort<Item>().sort(new Item[] {}, ItemComparator.getInstance())
        );
        assertDoesNotThrow(() ->
                new Heapsort<Item>().sort(new Item[] {}, ItemComparator.getInstance())
        );
        assertDoesNotThrow(() ->
                new Quicksort<Item>().sort(new Item[] {}, ItemComparator.getInstance())
        );
        assertDoesNotThrow(() ->
                new MergeSort<Item>().sort(new Item[] {}, ItemComparator.getInstance())
        );
    }

    private void doTestSort(Sort<Item> sort, int length) {
        // 1.1 Generate a sorted source array.
        Item[] sourceItems = new Item[length];
        for (int i = 0; i < length; i++) {
            sourceItems[i] = new Item(i, i);
        }
        // 1.2 Create a shuffled array based on the source array.
        Item[] items = new Item[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int j = random.nextInt(i + 1);
            if (j != i) {
                items[i] = items[j];
            }
            items[j] = sourceItems[i];
        }
        // 2. Sort the shuffled array.
        sort.sort(items, ItemComparator.getInstance());
        // 3. Compare the sort result with the source array.
        for (int i = 0; i < length; i++) {
            assertEquals(items[i].value, sourceItems[i].value);
            assertEquals(items[i].index, sourceItems[i].index);
        }
    }

    @Test
    public void testSort() {
        for (int i = 1; i < 1024; i++) {
            doTestSort(new BubbleSort<>(), i);
            doTestSort(new SelectionSort<>(), i);
            doTestSort(new InsertionSort<>(), i);
            doTestSort(new Heapsort<>(), i);
            doTestSort(new Quicksort<>(), i);
            doTestSort(new MergeSort<>(), i);
        }
    }

    public void doTestStable(Sort<Item> sort, int length) {
        // 1. Generate a random array.
        Random random = new Random();
        Item[] items = new Item[length];
        for (int i = 0; i < length; i++) {
            items[i] = new Item(random.nextInt(length), i);
        }
        // 2. Sort the array.
        sort.sort(items, ItemComparator.getInstance());
        // 3. Assert the sort result is stable.
        Item last = items[0];
        for (int i = 1; i < items.length; i++) {
            Item current = items[i];
            assertTrue((last.value < current.value)
                    || (last.value == current.value && last.index < current.index));
            last = current;
        }
    }

    @Test
    public void testStable() {
        for (int i = 1; i < 1024; i++) {
            doTestStable(new BubbleSort<>(), i);
            doTestStable(new InsertionSort<>(), i);
            doTestStable(new MergeSort<>(), i);
        }
    }
}
