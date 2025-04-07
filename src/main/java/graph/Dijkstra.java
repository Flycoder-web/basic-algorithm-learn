package graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;

public class Dijkstra {
    public static void main(String[] args) {
        int[][] edges = new int[][] {
                { 0, 0, 10, 0, 30, 100 },
                { 0, 0, 5, 0, 0, 0 },
                { 10, 5, 0, 50, 0, 0 },
                { 0, 0, 50, 0, 20, 10 },
                { 30, 0, 0, 20, 0, 60 },
                { 100, 0, 0, 10, 60, 0 },
        };
        System.out.println(Arrays.toString(dijkstraWithPq(0, edges)));
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        int[] dis = new int[edges.length];
        Arrays.fill(dis, Integer.MAX_VALUE);
        set.add(0);
        dis[0] = 0;
        find(edges, set, dis);
        System.out.println(Arrays.toString(dis));
        System.out.println(Arrays.toString(dijkstra(0, edges)));
    }

    public static void find(int[][] graph, LinkedHashSet<Integer> visited, int[] distances) {
        // 初始化边，若为0，设置为无穷大
        for(int i = 0; i < graph.length; i++) {
            for(int j = i + 1; j < graph.length; j++) {
                if(graph[i][j] == 0) {
                    graph[i][j] = Integer.MAX_VALUE;
                    graph[j][i] = Integer.MAX_VALUE;
                }
            }
        }
        int minIndex = 0, min;
        do {
            int current = visited.getLast();
            min = Integer.MAX_VALUE;
            for(int i = 0; i < graph.length; i++) {
                // 更新 distances，判断时防止溢出
                if(graph[current][i] != Integer.MAX_VALUE && graph[current][i] + distances[current] < distances[i]) {
                    distances[i] = graph[current][i] + distances[current];
                }
                // 找未被访问的点中的最短距离点
                if(!visited.contains(i) && distances[i] < min) {
                    min = distances[i];
                    minIndex = i;
                }
            }
        } while(visited.add(minIndex));
    }

    public static int[] dijkstra(int start, int[][] graph) {
        int len = graph.length;
        if(start < 0 || start >= len)
            return null;
        boolean[] visited = new boolean[len];
        // 存储最短距离
        int[] result = new int[len];
        // 初始化边，若为0，设置为无穷大
        for(int i = 0; i < len; i++) {
            for(int j = i + 1; j < len; j++) {
                if(graph[i][j] == 0) {
                    graph[i][j] = Integer.MAX_VALUE;
                    graph[j][i] = Integer.MAX_VALUE;
                }
            }
        }
        // 初始化result
        System.arraycopy(graph[start], 0, result, 0, result.length);
        visited[start] = true;
        // 遍历其余节点
        for(int i = 0; i < len; i++) {
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            // 找起点到其他节点的最短距离的节点
            for(int j = 0; j < len; j++) {
                if(!visited[j]) { // 从未被访问的节点中找最短距离的节点
                    if(result[j] < min) {
                        min = result[j];
                        minIndex = j;
                    }
                }
            }
            if(minIndex != -1)
                visited[minIndex] = true;
            // 更新最短距离
            for(int x = 0; x < len; x++) {
                if(!visited[x]) {
                    if(graph[minIndex][x] != Integer.MAX_VALUE && graph[minIndex][x] + result[minIndex] < result[x]) {
                        result[x] = graph[minIndex][x] + result[minIndex];
                    }
                }
            }
        }
        return result;
    }

    // 使用优先级队列
    public static int[] dijkstraWithPq(int start, int[][] graph) {
        int n = graph.length;
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // 按距离从大到小排列
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        queue.offer(new int[]{start, 0});
        boolean[] visited = new boolean[n];

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int u = curr[0];
            if(visited[u]) continue;
            visited[u] = true;

            for(int i = 0; i < n; i++) {
                if(!visited[i] && graph[u][i] > 0) {
                    int newDist = dist[u] + graph[u][i];
                    if(newDist < dist[i]) {
                        dist[i] = newDist;
                        queue.offer(new int[] {i, newDist});
                    }
                }
            }
        }

        return dist;
    }
}
