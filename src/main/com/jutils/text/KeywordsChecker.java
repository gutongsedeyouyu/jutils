package com.jutils.text;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Prefix trie based keywords checker.
 */
public class KeywordsChecker {
    private static final Object END_OF_KEYWORD = new Object();

    private Map<Object, Object> keywordsTrie;

    private Pattern trimPattern;

    public void setKeywords(Collection<String> keywords) {
        keywordsTrie = new HashMap<>();
        Map<Object, Object> currentNode;
        for (String keyword : keywords) {
            currentNode = keywordsTrie;
            for (char c : keyword.toCharArray()) {
                if (!currentNode.containsKey(c)) {
                    Map<Object, Object> newNode = new HashMap<>();
                    newNode.put(END_OF_KEYWORD, Boolean.FALSE);
                    currentNode.put(c, newNode);
                    currentNode = newNode;
                } else {
                    currentNode = (Map<Object, Object>) currentNode.get(c);
                }
            }
            currentNode.put(END_OF_KEYWORD, Boolean.TRUE);
        }
    }

    public void setCharsToTrim(String charsToTrim) {
        if (charsToTrim == null || charsToTrim.length() == 0) {
            trimPattern = null;
        } else {
            trimPattern = Pattern.compile(String.format("[%s]", Pattern.quote(charsToTrim)));
        }
    }

    public boolean containsKeywords(String s) {
        if (trimPattern != null) {
            s = trimPattern.matcher(s).replaceAll("");
        }
        for (int i = 0; i < s.length(); i++) {
            if (calculateMatchingLength(s, i, true) > 0) {
                return true;
            }
        }
        return false;
    }

    public List<String> getContainedKeywords(String s) {
        return getContainedKeywords(s, true);
    }

    public List<String> getContainedKeywords(String s, boolean maximumMatch) {
        if (trimPattern != null) {
            s = trimPattern.matcher(s).replaceAll("");
        }
        List<String> containedKeywords = new ArrayList<>();
        for (int i = 0; i < s.length();) {
            int matchingLength = calculateMatchingLength(s, i, maximumMatch);
            if (matchingLength == 0) {
                i++;
            } else {
                containedKeywords.add(s.substring(i, i + matchingLength));
                i += matchingLength;
            }
        }
        return containedKeywords;
    }

    private int calculateMatchingLength(String s, int begin, boolean maximumMatch) {
        Map<Object, Object> currentNode = keywordsTrie;
        int matchingLength = 0;
        for (int i = begin; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!currentNode.containsKey(c)) {
                break;
            }
            currentNode = (Map<Object, Object>) currentNode.get(c);
            if ((Boolean) currentNode.get(END_OF_KEYWORD)) {
                matchingLength = i - begin + 1;
                if (!maximumMatch) {
                    break;
                }
            }
        }
        return matchingLength;
    }
}
