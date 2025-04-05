package backtrack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MColors_opt {
    int m; // m种颜色
    int n; // n个节点
    int[][] graph; // 邻接矩阵
    int[] colored; // 指示节点涂的颜色
    Set<String> results = new HashSet<>();

    public MColors_opt(int m, int n) {
        this.m = m;
        this.n = n;
        graph = new int[n][n];
        colored = new int[n];
        Arrays.fill(colored, -1);
    }

    public static void main(String[] args) {
        MColors_opt mc = new MColors_opt(4, 5);
        mc.graph = new int[][] {
                {0, 1, 1, 1, 0},
                {1, 0, 1, 1, 0},
                {1, 1, 0, 1, 1},
                {1, 1, 1, 0, 0},
                {0, 0, 1, 0, 0}
        };
        mc.backtrack(0);
        System.out.println(mc.results.size());
    }

    public void backtrack(int i) {
        if(i == n) {
            results.add(Arrays.toString(colored));
            return;
        }
        // 对每一种颜色
        for(int color = 0; color < m; color++) {
            if(isValid(i, color)) {
                colored[i] = color; // 染色
                backtrack(i + 1); // 递归下一个节点
                colored[i] = -1; // 回溯
            }
        }
    }

    private boolean isValid(int node, int color) {
        for(int j = 0; j < node; j++) {
            if(graph[node][j] == 1 && colored[j] == color)
                return false;
        }
        return true;
    }
}
