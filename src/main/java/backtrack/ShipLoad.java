package backtrack;

import java.util.stream.IntStream;

public class ShipLoad {
    private static int result = 0; // 记录最优值（backtrack()方法中使用）
    private static int best = 0;

    public static void main(String[] args) {
//        int[] weights = {18, 7, 25, 36};
        int[] weights = {50, 40, 35, 20, 10};
        int c = 60;
        System.out.println(find(weights, c));
        System.out.println(find2(weights, c));
        System.out.println(dpOneDim(weights, c));
    }

    public static int find(int[] weights, int c) {
        result = 0;
        backtrack(weights, c, 0, 0, new boolean[weights.length]);
        return result;
    }

    // 遍历所有可能组合
    public static void backtrack(int[] weights, int c, int sum, int i, boolean[] visited) {
        for(; i < weights.length; i++) {
            if(!visited[i]) {
                // 尝试加上当前货物
                int load = sum + weights[i];
                if(load <= c) {
                    // 与保存的最大装载量对比更新
                    if(load > result)
                        result = load;
                } else continue; // 超过了就跳过当前货物
                visited[i] = true; // 当前货物已装过
                backtrack(weights, c, load, i + 1, visited); // 递归后面的货物
                visited[i] = false; // 去掉当前货物，递归其他的货物
            }
        }
    }

    public static int find2(int[] weights, int c) {
        best = 0;
        // 先对weights降序排序，加快搜索
        weights = IntStream.of(weights)
                .boxed()
                .sorted((w1, w2) -> w2 - w1)
                .mapToInt(Integer::intValue)
                .toArray();
        dfs(weights, c, 0, 0);
        return best;
    }
    // 0-1背包的朴素回溯方法（“选或不选“递归）
    public static void dfs(int[] weights, int c, int current, int i) {
        if(current > c)
            return; // 若当前总重过载，剪枝
        if(current > best)
            best = current; // 若当前总重大于历史最佳，则更新
        if(i == weights.length)
            return; // 所有集装箱已考虑
        // 选择装载当前集装箱
        dfs(weights, c, current + weights[i], i + 1);
        // 选择不装载当前集装箱
        dfs(weights, c, current, i + 1);
    }

    // 0-1背包的dp
    public static int dpOneDim(int[] weights, int c) {
        int[] dp = new int[c + 1];
        for(int weight : weights) {
            for(int j = c; j >= weight; j--) // 逆序遍历
                dp[j] = Math.max(dp[j], dp[j - weight] + weight);
        }
        return dp[c];
    }
}
