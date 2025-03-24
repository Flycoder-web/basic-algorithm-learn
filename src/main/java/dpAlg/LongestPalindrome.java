package dpAlg;

public class LongestPalindrome {
    public static void main(String[] args) {
        String s = "abdbdc";
        String s2 = "babad";
        System.out.println(find(s));
        System.out.println(find(s2));
    }

    public static String find(String s) {
        if(s == null || s.length() < 2)
            return s;

        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLen = 1;

        // 单个字符的子串都是回文串
        for(int i = 0; i < n; i++)
            dp[i][i] = true;

        // 递推填表
        for(int len = 2; len <= n; len++) { // 子串长度
            for(int i = 0; i < n - len; i++) { // 子串起点
                int j = i + len - 1; // 子串终点
                if(s.charAt(i) == s.charAt(j)) {
                    if(len == 2)
                        dp[i][j] = true;
                    else
                        dp[i][j] = dp[i + 1][j - 1];
                }

                if(dp[i][j] && maxLen < len) {
                    start = i;
                    maxLen = len;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }
}
