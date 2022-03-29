package test.jutils.arrays.sorting;

import com.jutils.arrays.sorting.BubbleSort;
import com.jutils.arrays.sorting.Heapsort;
import com.jutils.arrays.sorting.InsertionSort;
import com.jutils.arrays.sorting.MergeSort;
import com.jutils.arrays.sorting.Quicksort;
import com.jutils.arrays.sorting.SelectionSort;
import com.jutils.arrays.sorting.Sort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SortTest {
    private static List<Sort<Item>> stableSorts;
    private static List<Sort<Item>> allSorts;

    @BeforeAll
    public static void init() {
        List<Sort<Item>> unstableSorts = List.of(new SelectionSort<>(), new Heapsort<>(), new Quicksort<>());
        stableSorts = List.of(new BubbleSort<>(), new InsertionSort<>(), new MergeSort<>());
        allSorts = new ArrayList<>();
        allSorts.addAll(unstableSorts);
        allSorts.addAll(stableSorts);
    }

    @Test
    public void testItemsNull() {
        for (Sort<Item> sort : allSorts) {
            assertThrows(NullPointerException.class, () -> sort.sort(null, ItemComparator.getInstance()));
        }
    }

    @Test
    public void testComparatorNull() {
        for (Sort<Item> sort : allSorts) {
            assertThrows(NullPointerException.class, () -> sort.sort(new Item[2], null));
        }
    }

    @Test
    public void testAllItemsNull() {
        for (Sort<Item> sort : allSorts) {
            assertDoesNotThrow(() -> sort.sort(new Item[] {null, null}, ItemComparator.getInstance()));
        }
    }

    @Test
    public void testSomeItemNull() {
        for (Sort<Item> sort : allSorts) {
            Item[] items = new Item[] {new Item(0, 0), null};
            sort.sort(items, ItemComparator.getInstance());
            assertNull(items[0]);
            assertTrue(items[1].value == 0 && items[1].originalIndex == 0);
        }
    }

    @Test
    public void testEmpty() {
        for (Sort<Item> sort : allSorts) {
            assertDoesNotThrow(() -> sort.sort(new Item[] {}, ItemComparator.getInstance()));
        }
    }

    @Test
    public void testSort() {
        for (int i = 1; i < 1024; i++) {
            for (Sort<Item> sort : allSorts) {
                doTestSort(sort, i);
            }
        }
    }

    @Test
    public void testStable() {
        for (int i = 1; i < 1024; i++) {
            for (Sort<Item> sort : stableSorts) {
                doTestStable(sort, i);
            }
        }
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
            assertEquals(sourceItems[i].value, items[i].value);
            assertEquals(sourceItems[i].originalIndex, items[i].originalIndex);
        }
    }

    private void doTestStable(Sort<Item> sort, int length) {
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
                    || (last.value == current.value && last.originalIndex < current.originalIndex));
            last = current;
        }
    }
}
