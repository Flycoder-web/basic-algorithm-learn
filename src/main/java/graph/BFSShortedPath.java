package graph;

import java.util.*;

public class BFSShortedPath {
    public static void main(String[] args) {
        char[][] graph = {
                { '*', 'S', '*', '#' },
                { '#', '#', '#', '*' },
                { '*', '*', '#', '*' },
                { '*', '#', '#', '*' },
                { 'T', '#', '*', '*' }
        };
        System.out.println(bfs_ref(graph, 0, 1));
        find(graph);
        System.out.println(Arrays.deepToString(graph));
    }

    public static void find(char[][] graph) {
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[0].length; j++) {
                // 找到起点
                if(graph[i][j] == 'S') {
                    bfs(graph, i, j);
                }
            }
        }
    }

    public static void bfs(char[][] graph, int i, int j) {
        Queue<List<Integer>> queue = new ArrayDeque<>();
        queue.offer(Arrays.asList(i, j));
        graph[i][j] = '0';
        int x = i, y = j;
        while(!queue.isEmpty()) {
            List<Integer> current = queue.poll();
            x = current.getFirst();
            y = current.getLast();
            if(x + 1 < graph.length && (graph[x + 1][y] == '#' || graph[x + 1][y] == 'T')) {
                graph[x + 1][y] = (char) (1 + graph[x][y]);
                queue.offer(Arrays.asList(x + 1, y));
            }
            if(x - 1 >= 0 && (graph[x - 1][y] == '#' || graph[x - 1][y] == 'T')) {
                graph[x - 1][y] = (char) (1 + graph[x][y]);
                queue.offer(Arrays.asList(x - 1, y));
            }
            if(y + 1 < graph[0].length && (graph[x][y + 1] == '#' || graph[x][y + 1] == 'T')) {
                graph[x][y + 1] = (char) (1 + graph[x][y]);
                queue.offer(Arrays.asList(x, y + 1));
            }
            if(y - 1 >= 0 && (graph[x][y - 1] == '#' || graph[x][y - 1] == 'T')) {
                graph[x][y - 1] = (char) (1 + graph[x][y]);
                queue.offer(Arrays.asList(x, y - 1));
            }
        }
        //System.out.println(graph[x][y]);
    }

    public static int bfs_ref(char[][] graph, int i, int j) {
        boolean[][] visited = new boolean[graph.length][graph[0].length];
        // 四个方向
        int[][] directors = {{ -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 }};
        // S 到可达点的路程
        int[][] result = new int[graph.length][graph[0].length];

        Queue<List<Integer>> queue = new ArrayDeque<>();
        queue.offer(Arrays.asList(i , j));
        result[i][j] = 0;
        visited[i][j] = true;
        while(!queue.isEmpty()) {
            List<Integer> current = queue.poll();
            int x = current.getFirst();
            int y = current.getLast();
            // 遍历四个方向
            for(int d = 0; d < 4; d++) {
                int nextX = x + directors[d][0];
                int nextY = y + directors[d][1];
                // 判断是否合法
                if(nextX >= 0 && nextX < graph.length && nextY >= 0 && nextY < graph[0].length
                        && graph[nextX][nextY] != '*' && !visited[nextX][nextY]) {
                    result[nextX][nextY] = result[x][y] + 1;
                    visited[nextX][nextY] = true;
                    // 若是出口，直接返回，若不是，
                    if(graph[nextX][nextY] == 'T') {
                        return result[nextX][nextY];
                    } else {
                        queue.offer(Arrays.asList(nextX, nextY));
                    }
                }
            }
        }
        return -1;
    }
}
