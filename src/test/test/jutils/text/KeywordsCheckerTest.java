package test.jutils.text;

import com.jutils.text.KeywordsChecker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class KeywordsCheckerTest {
    private static String charsToTrim;
    private static KeywordsChecker keywordsChecker;
    private static KeywordsChecker keywordsCheckerTrim;

    @BeforeAll
    public static void init() {
        List<String> keywords = List.of("敏感", "敏感词", "关键字");
        charsToTrim = " \t\r\n`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?｀～！＃¥％…＊（）－—＝＋［｛］｝、｜；：‘’“”，《。》／？";
        keywordsChecker = new KeywordsChecker();
        keywordsChecker.setKeywords(keywords);
        keywordsCheckerTrim = new KeywordsChecker();
        keywordsCheckerTrim.setKeywords(keywords);
        keywordsCheckerTrim.setCharsToTrim(charsToTrim);
    }

    @Test
    public void testNotInitialized() {
        KeywordsChecker keywordsCheckerNotInitialized = new KeywordsChecker();
        assertThrows(RuntimeException.class, () -> keywordsCheckerNotInitialized.getContainedKeywords("没有设置初始化"));
    }

    @Test
    public void testNoKeywords() {
        KeywordsChecker keywordsCheckerNoKeywords = new KeywordsChecker();
        keywordsCheckerNoKeywords.setKeywords(Collections.emptyList());
        assertFalse(keywordsCheckerNoKeywords.containsKeywords("没有设置关键字"));
    }

    @Test
    public void testNull() {
        assertThrows(NullPointerException.class, () -> keywordsChecker.containsKeywords(null));
        assertThrows(NullPointerException.class, () -> keywordsChecker.getContainedKeywords(null));
        assertThrows(NullPointerException.class, () -> keywordsCheckerTrim.containsKeywords(null));
        assertThrows(NullPointerException.class, () -> keywordsCheckerTrim.getContainedKeywords(null));
    }

    @Test
    public void testEmpty() {
        assertFalse(keywordsChecker.containsKeywords(""));
        assertFalse(keywordsCheckerTrim.containsKeywords(""));
    }

    @Test
    public void testContains() {
        assertTrue(keywordsChecker.containsKeywords("敏感词测试"));
        assertTrue(keywordsChecker.containsKeywords("这句话包含敏感词么？"));
        assertTrue(keywordsChecker.containsKeywords("这句话包含敏感词"));
        assertFalse(keywordsChecker.containsKeywords("尝试绕过敏*感词检测。"));
        assertFalse(keywordsChecker.containsKeywords("尝试绕过关键 字检测。"));
        assertFalse(keywordsChecker.containsKeywords("一句普普通通的话。"));
    }

    @RepeatedTest(10)
    public void testContainsTrim() {
        assertTrue(keywordsCheckerTrim.containsKeywords(insertCharsToTrim("增加各种字符，各种尝试绕过敏感词检测。")));
        assertFalse(keywordsCheckerTrim.containsKeywords(insertCharsToTrim("给一句普普通通的话增加各种字符。")));
    }

    @Test
    public void testContainedKeywords() {
        List<String> containedKeywords;
        containedKeywords = keywordsChecker.getContainedKeywords("敏感词测试", false);
        assertTrue(containedKeywords.size() == 1
                && containedKeywords.contains("敏感"));
        containedKeywords = keywordsChecker.getContainedKeywords("敏感词和关键字测试", false);
        assertTrue(containedKeywords.size() == 2
                && containedKeywords.contains("敏感")
                && containedKeywords.contains("关键字"));
        containedKeywords = keywordsChecker.getContainedKeywords("敏感词和关键字测试");
        assertTrue(containedKeywords.size() == 2
                && containedKeywords.contains("敏感词")
                && containedKeywords.contains("关键字"));
    }

    private String insertCharsToTrim(String text) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (char c : text.toCharArray()) {
            builder.append(c);
            builder.append(charsToTrim.charAt(random.nextInt(charsToTrim.length())));
        }
        return builder.toString();
    }
}
