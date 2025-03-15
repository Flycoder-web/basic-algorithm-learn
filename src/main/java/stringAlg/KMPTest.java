package stringAlg;

import java.util.Arrays;

public class KMPTest {
    public static void main(String[] args) {
        System.out.println(isMatch("BABA", "ABAB")); // true
        System.out.println(isMatch("1234", "3412")); // true
        System.out.println(isMatch("1234", "4312")); // false
        System.out.println(isMatch("ABCBD", "BDABC")); // true
        System.out.println(isMatch("ABCBDACB", "ABCDABDC")); // false
    }

    public static boolean isMatch(String strA, String strB) {
        if(strA.length() != strB.length())
            return false;
        // 拼接成为主串
        String bigStr = strA + strA;
        return kmp(bigStr, strB) != -1;
    }

    public static int kmp(String source, String pattern) {
        // next 数组，保存已匹配的字符串的最长公共前后缀
        int[] next = new int[pattern.length()];
        getNext(next, pattern);
        int i = 0;
        int j = 0;
        while(i < source.length() && j < pattern.length()) {
            if(j == -1 || source.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                // 模式串上的指针回溯到j位置
                j = next[j];
            }
        }
        return j == pattern.length() ? (i - j) : -1;
    }

    // 求模式串的最长前后缀匹配长度数组
    public static void getNext(int[] next, String pattern) {
        int j = 0, k = -1;
        next[0] = -1;
        while(j < pattern.length() - 1) {
            if(k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
                // 前后缀匹配，即next[j + 1] = k + 1
                k++;
                j++;
                next[j] = k;
            } else {
                // 不相等时，需要看前面已匹配的字符串的最长公共前后缀，即 next[k]
                k = next[k];
            }
        }
        System.out.println(Arrays.toString(next));
    }
}
