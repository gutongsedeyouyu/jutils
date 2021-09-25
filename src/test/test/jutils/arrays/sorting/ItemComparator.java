package test.jutils.arrays.sorting;

import java.util.Comparator;

class ItemComparator implements Comparator<Item> {
    private static final ItemComparator INSTANCE = new ItemComparator();

    private ItemComparator() {
    }

    public static ItemComparator getInstance() {
        return INSTANCE;
    }

    @Override
    public int compare(Item item1, Item item2) {
        if (item1 == null || item2 == null) {
            if (item1 == null && item2 == null) {
                return 0;
            }
            return item1 == null ? -1 : 1;
        }
        if (item1.value == item2.value) {
            return 0;
        }
        return item1.value < item2.value ? -1 : 1;
    }
}
