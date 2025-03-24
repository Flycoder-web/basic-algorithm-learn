package dpAlg;

import java.util.HashMap;
import java.util.Map;

public class Knapsack01 {
    public static void main(String[] args) {
        int[] weights = {3, 4, 5, 7, 2};
        int[] values = {3, 5, 4, 6, 1};
        int capacity = 10;
        System.out.println(recursive(weights, values, capacity, weights.length));
        System.out.println(recur_memo(weights, values, capacity, weights.length, new HashMap<>()));
        System.out.println(dp_bottomUp(weights, values, capacity));
    }

    public static int recursive(int[] weights, int[] values, int capacity, int n) {
        if(n == 0 || capacity == 0)
            return 0;
        if(weights[n - 1] > capacity)
            // 若当前物品超重，直接跳过
            return recursive(weights, values, capacity, n - 1);
        else
            // 不选或选该物品
            return Math.max(recursive(weights, values, capacity, n - 1),
                    values[n - 1] + recursive(weights, values, capacity - weights[n - 1], n - 1));
    }

    public static int recur_memo(int[] weights, int[] values, int capacity, int n, Map<String, Integer> memo) {
        if(n == 0 || capacity == 0)
            return 0;

        String key = n + "#" + capacity; // 生成唯一键
        if(memo.containsKey(key))
            return memo.get(key);

        if(weights[n - 1] > capacity) {
            int val = recur_memo(weights, values, capacity, n - 1, memo);
            memo.put(key, val);
            return val;
        }
        int notTake = recur_memo(weights, values, capacity, n - 1, memo);
        int take = values[n - 1] + recur_memo(weights, values, capacity - weights[n - 1], n - 1, memo);
        int result = Math.max(notTake, take);
        memo.put(key, result);
        return result;
    }

    public static int dp_bottomUp(int[] weights, int[] values, int capacity) {
        int[][] dp = new int[weights.length + 1][capacity + 1];

        for(int i = 1; i <= weights.length; i++) {
            for(int j = 1; j <= capacity; j++) {
                if(weights[i - 1] > j) // 注意weights取值范围
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], values[i - 1] + dp[i - 1][j - weights[i - 1]]); // 同样注意values和weights范围
            }
        }
        return dp[weights.length][capacity];
    }

    public static int dp_oneDimArray(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];

        for(int i = 0; i < weights.length; i++) {
            for(int j = capacity; j >= 0; j--) { // 从后向前遍历
                dp[j] = Math.max(dp[j], values[i] + dp[j - weights[i]]);
            }
        }
        return dp[capacity];
    }
}
