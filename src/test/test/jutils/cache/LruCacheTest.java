package test.jutils.cache;

import com.jutils.cache.LruCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LruCacheTest {
    @Test
    public void testCapacityNonPositive() {
        assertThrows(IllegalArgumentException.class, () -> new LruCache<Integer, Integer>(-1));
        assertThrows(IllegalArgumentException.class, () -> new LruCache<Integer, Integer>(0));
    }

    @Test
    public void testCapacityPositive() {
        for (int i = 0; i < 10; i++) {
            doTestCapacityPositive(i + 1);
        }
    }

    private void doTestCapacityPositive(int capacity) {
        // Initialization
        LruCache<Integer, Integer> lruCache = new LruCache<>(capacity);
        assertEquals(0, lruCache.size());
        // When no evict happens
        for (int noEvict = 0; noEvict < capacity; noEvict++) {
            lruCache.put(noEvict, noEvict);
            assertEquals(noEvict + 1, lruCache.size());
            for (int i = 0; i < noEvict + 1; i++) {
                assertEquals(i, lruCache.get(i));
            }
        }
        assertEquals(capacity, lruCache.size());
        for (int i = 0; i < capacity; i++) {
            assertEquals(i, lruCache.get(i));
        }
        // When evict happens
        for (int evict = 0; evict < capacity; evict++) {
            lruCache.put(capacity + evict, capacity + evict);
            assertEquals(capacity, lruCache.size());
            for (int i = 0; i < capacity; i++) {
                assertEquals(1 + evict + i, lruCache.get(1 + evict + i));
            }
        }
        // Clear
        lruCache.clear();
        assertEquals(0, lruCache.size());
    }
}
