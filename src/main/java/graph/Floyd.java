package graph;

import java.util.Arrays;

public class Floyd {
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[][] graph = {
                { 0, 11, 4, INF },
                { 1, 0, 23, 6 },
                { 56, 5, 0, 7 },
                { INF, 34, 11, 0 },
        };
        int[][] dist = floyd(graph);
        for (int[] ints : dist) {
            System.out.println(Arrays.toString(ints));
        }
    }

    public static int[][] floyd(int[][] graph) {
        int n = graph.length;
        // 对于多维基本类型数组不能直接Array.copyOf()
        int[][] dist = new int[n][];
        for(int i = 0; i < n; i++) {
            dist[i] = Arrays.copyOf(graph[i], graph[i].length);
        }
        for(int k = 0; k < n; k++) { // 遍历中转点
            for(int i = 0; i < n; i++) { // 起点
                for(int j = 0; j < n; j++) { // 终点
                    // 只要有一段不可达，就是不可达，否则计算途径新加入的节点的路径
                    int newDist = (dist[i][k] == INF || dist[k][j] == INF) ? INF : dist[i][k] + dist[k][j];
                    if(newDist < dist[i][j]) {
                        dist[i][j] = newDist;
                    }
                }
            }
        }
        return dist;
    }
}
