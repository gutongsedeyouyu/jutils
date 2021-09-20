package test.jutils.sorting;

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
        if (item1.value == item2.value) {
            return 0;
        }
        return item1.value < item2.value ? -1 : 1;
    }
}
