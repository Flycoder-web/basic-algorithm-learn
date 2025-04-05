package backtrack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MColors {
    int m; // m种颜色
    int n; // n个节点
    int[][] graph; // 邻接矩阵
    int[] colored; // 指示节点涂的颜色
    boolean[] selected; // 指示颜色是否已使用
    Set<String> results = new HashSet<>();

    public MColors(int m, int n) {
        this.m = m;
        this.n = n;
        graph = new int[n][n];
        colored = new int[n];
        selected = new boolean[m];
        Arrays.fill(colored, -1);
    }

    public static void main(String[] args) {
        MColors mc = new MColors(4, 5);
        mc.graph = new int[][] {
                {0, 1, 1, 1, 0},
                {1, 0, 1, 1, 0},
                {1, 1, 0, 1, 1},
                {1, 1, 1, 0, 0},
                {0, 0, 1, 0, 0}
        };
        mc.find(0, "");
        System.out.println(mc.results.size());
        System.out.println(mc.results);
    }

    public void find(int i, String s) {
        if(i == n) {
            results.add(s);
            return;
        }
        for(int color = 0; color < m; color++) {
            boolean isValid = true;
            for(int j = 0; j < n; j++) {
                if(graph[i][j] == 1 && colored[j] == color) {
                    isValid = false;
                    break;
                }
            }
            if(isValid) {
                // 染色
                colored[i] = color;
                // 该颜色已用
                selected[color] = true;
                // 递归下一节点
                find(i + 1, s + color);
                // 回溯
                colored[i] = -1;
                selected[color] = false;
            }
        }
    }
}
