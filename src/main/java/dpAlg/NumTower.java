package dpAlg;

public class NumTower {
    public static void main(String[] args) {
        int[][] data = {{1}, {3, 5}, {7, 5, 2}, {9, 3, 4, 11}, {11, 1, 2, 5, 7}};
        System.out.println(maxDis_dp(data));
        System.out.println(recur(data));
    }

    public static int maxDis_dp(int[][] tower) {
        int rows = tower.length;
        int[][] dp = new int[rows][rows];
        dp[0][0] = tower[0][0];

        int max = 0;
        for(int i = 1; i < rows; i++) {
            for(int j = 0; j <= i; j++) {
                if(j == 0)
                    dp[i][j] = dp[i - 1][j] + tower[i][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i - 1][j]) + tower[i][j];
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }

    // 递归解法，有很多重复计算
    public static int recur(int[][] tower) {
        int max = 0;
        // 假设都是正整数，若有负数，需要遍历全部
        for(int j = 0; j < tower[tower.length - 1].length; j++)
            max = Math.max(max, recur(tower, tower.length - 1, j));
        return max;
    }

    public static int recur(int[][] tower, int i, int j) {
        if(i == 0 && j == 0)
            return tower[0][0];
        if(j == 0)
            return recur(tower, i - 1, j) + tower[i][j];
        else if(j == tower[i].length - 1)
            return recur(tower, i - 1, j - 1) + tower[i][j];
        else
            return Math.max(recur(tower, i - 1, j - 1), recur(tower, i - 1, j)) + tower[i][j];
    }
}
