package com.jutils.text;

/**
 * Levenshtein Distance Calculator
 */
public class EditDistanceCalculator {
    /**
     * Calculate edit distance between s1 and s2.
     * @param s1 a string
     * @param s2 another string
     * @return edit distance between s1 and s2
     */
    public int calculate(CharSequence s1, CharSequence s2) {
        return calculate(s1, s2, Integer.MAX_VALUE);
    }

    /**
     * Calculate edit distance between s1 and s2. If length difference between s1 and s2 is greater than or equal to
     * <code>ignoreIfLenDiffGE</code>, ignore the actual edit distance and just return <code>ignoreIfLenDiffGE</code>.
     * @param s1 a string
     * @param s2 another string
     * @param ignoreIfLenDiffGE if length difference between s1 and s2 greater than or equal to
     *                         <code>ignoreIfLenDiffGE</code>, ignore the actual edit distance
     * @return edit distance between s1 and s2 or <code>ignoreIfLenDiffGE</code>
     */
    public int calculate(CharSequence s1, CharSequence s2, int ignoreIfLenDiffGE) {
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 == 0 || len2 == 0) {
            return Math.max(len1, len2);
        }
        if (Math.abs(len1 - len2) >= ignoreIfLenDiffGE) {
            return ignoreIfLenDiffGE;
        }
        if (len1 < len2) {
            CharSequence swapS = s1;
            s1 = s2;
            s2 = swapS;
            int swapLen = len1;
            len1 = len2;
            len2 = swapLen;
        }
        int[] d0 = new int[len2 + 1];
        int[] d1 = new int[len2 + 1];
        for (int i = 0; i < len2 + 1; i++) {
            d0[i] = i;
            d1[i] = 0;
        }
        for (int i = 0; i < len1; i++) {
            d1[0] = i + 1;
            for (int j = 0; j < len2; j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    d1[j + 1] = d0[j];
                } else {
                    d1[j + 1] = Math.min(Math.min(d0[j] + 1, d0[j + 1] + 1), d1[j] + 1);
                }
            }
            int[] swap = d0;
            d0 = d1;
            d1 = swap;
        }
        return d0[len2];
    }
}
