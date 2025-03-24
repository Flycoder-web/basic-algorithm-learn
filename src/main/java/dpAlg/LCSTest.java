package dpAlg;

public class LCSTest {
    public static void main(String[] args) {
        String s1 = "abdbcabd";
        String s2 = "abbcdd";
        System.out.println(find_dp(s1, s2));
    }

    public static int find_dp(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                // 注意这里的索引与字符串中索引错开
                if(s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
