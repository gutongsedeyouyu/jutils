package test.jutils.text;

import com.jutils.text.EditDistanceCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditDistanceCulatorTest {
    private static EditDistanceCalculator editDistanceCalculator;

    @BeforeAll
    public static void init() {
        editDistanceCalculator = new EditDistanceCalculator();
    }

    @Test
    public void testEmpty() {
        assertThrows(NullPointerException.class, () -> editDistanceCalculator.calculate(null, null));
        assertThrows(NullPointerException.class, () -> editDistanceCalculator.calculate("", null));
        assertThrows(NullPointerException.class, () -> editDistanceCalculator.calculate(null, ""));
        assertEquals(0, editDistanceCalculator.calculate("", ""));
    }

    @Test
    public void test1() {
        assertEquals(1, editDistanceCalculator.calculate("a", ""));
        assertEquals(1, editDistanceCalculator.calculate("", "a"));
        assertEquals(0, editDistanceCalculator.calculate("a", "a"));
        assertEquals(1, editDistanceCalculator.calculate("a", "b"));
    }

    @Test
    public void testNormal() {
        assertEquals(0, editDistanceCalculator.calculate("Hello", "Hello"));
        assertEquals(4, editDistanceCalculator.calculate("Hello", "world"));
        assertEquals(1, editDistanceCalculator.calculate("Hello", "hello"));
        assertEquals(6, editDistanceCalculator.calculate("123456", "456123"));
        assertEquals(2, editDistanceCalculator.calculate("12345", "2145"));
        assertEquals(2, editDistanceCalculator.calculate("123", "321"));
        assertEquals(2, editDistanceCalculator.calculate("12345", "124"));
        assertEquals(2, editDistanceCalculator.calculate("124", "12345"));
    }

    @Test
    public void testRandom() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int minLength = 10;
        Random random = new Random();
        for (int round = 0; round < 10; round++) {
            int length = minLength + random.nextInt(alphabet.length() - minLength + 1);
            char[] chars = new char[length];
            for (int i = 0; i < length; i++) {
                chars[i] = alphabet.charAt(i);
            }
            String s1 = new String(chars);
            while (true) {
                int swapIndex1 = random.nextInt(length);
                int swapIndex2 = random.nextInt(length);
                if (swapIndex1 == swapIndex2) {
                    continue;
                }
                char swap = chars[swapIndex1];
                chars[swapIndex1] = chars[swapIndex2];
                chars[swapIndex2] = swap;
                break;
            }
            String s2 = new String(chars);
            assertEquals(2, editDistanceCalculator.calculate(s1, s2));
        }
    }
}
