package dpAlg;

import java.util.HashMap;
import java.util.Map;

public class RegexMatch {
    public static void main(String[] args) {
        System.out.println(match("ab", "a."));
        System.out.println(match("aaa", "a*a"));
        System.out.println(match("ab", "."));
        System.out.println(match("ab", ".*"));
        System.out.println(match("aab", "c*a*b"));
        System.out.println(match("mississippi", "mis*is*p*."));
    }

    public static boolean match(String str, String pattern) {
//        return recursive(str.toCharArray(), pattern.toCharArray(), 0, 0);
        // return recur_memo(str.toCharArray(), pattern.toCharArray(), 0, 0, new HashMap<>());
        return match_dp(str, pattern);
    }

    public static boolean recursive(char[] str, char[] pattern, int strIndex, int pIndex) {
        if(pattern.length == pIndex) {
            return str.length == strIndex;
        }

        // 当前字符匹配或者模式中为.
        boolean firstMatch = strIndex < str.length && str[strIndex] == pattern[pIndex] || pattern[pIndex] == '.';

        if(pIndex + 1 < pattern.length && pattern[pIndex + 1] == '*')
            // 匹配0次或多次
            return recursive(str, pattern, strIndex, pIndex + 2) || (firstMatch && recursive(str, pattern, strIndex + 1, pIndex));
        else
            // 正常匹配
            return firstMatch && recursive(str, pattern, strIndex + 1, pIndex + 1);
    }

    public static boolean recur_memo(char[] str, char[] pattern, int strIndex, int pIndex, Map<String, Boolean> memo) {
        String key = strIndex + "#" + pIndex;
        if(memo.containsKey(key))
            return memo.get(key);

        if(pattern.length == pIndex) {
            return str.length == strIndex;
        }

        boolean firstMatch = strIndex < str.length && str[strIndex] == pattern[pIndex] || pattern[pIndex] == '.';

        boolean result;
        if(pIndex + 1 < pattern.length && pattern[pIndex + 1] == '*')
            result = recur_memo(str, pattern, strIndex, pIndex + 2, memo) ||
                    (firstMatch && recur_memo(str, pattern, strIndex + 1, pIndex, memo));
        else
            result = firstMatch && recur_memo(str, pattern, strIndex + 1, pIndex + 1, memo);

        memo.put(key, result);
        return result;
    }

    public static boolean match_dp(String str, String pattern) {
        int sLen = str.length();
        int pLen = pattern.length();
        // 表示字符串前i个字符和模式串前j个字符是否匹配
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];

        dp[0][0] = true;

        // 模式串中的 * 可匹配空字符串
        for(int j = 2; j <= pLen; j++)
            if(pattern.charAt(j - 1) == '*')
                dp[0][j] = dp[0][j - 2];

        for(int i = 1; i <= sLen; i++) {
            for(int j = 1; j <= pLen; j++) {
                // i-1表示当前字符
                if(str.charAt(i - 1) == pattern.charAt(j - 1) || pattern.charAt(j - 1) == '.')
                    dp[i][j] = dp[i - 1][j - 1];
                else if(j > 1 && pattern.charAt(j - 1) == '*')
                    // 匹配0个或者多个
                    dp[i][j] = dp[i][j - 2] || (dp[i - 1][j] &&
                            (str.charAt(i - 1) == pattern.charAt(j - 2) || pattern.charAt(j - 2) == '.'));
            }
        }
        return dp[sLen][pLen];
    }
}
