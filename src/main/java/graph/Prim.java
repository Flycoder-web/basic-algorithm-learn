package graph;

import java.util.*;

public class Prim {
    public static void main(String[] args) {
        int[][] matrix = new int[][] {
                { 0, 2, 2, 9 },
                { 2, 0, 2, 4 },
                { 2, 2, 0, 1 },
                { 9, 4, 1, 0 },
        };
        System.out.println(Arrays.toString(find_wrong(matrix)));
        System.out.println(Arrays.toString(prim(matrix)));
        primWithAdjList();
    }

    // 会生成错误的树结构
    private static int[] find_wrong(int[][] graph) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        for(int i = 0; i < graph.length; i++) {
            for(int j = i + 1; j < graph.length; j++) {
                queue.offer(new int[]{i, j, graph[i][j]});
            }
        }

        int[] parent = new int[graph.length];
        Set<Integer> set = new HashSet<>();
        int[] first = queue.poll();
        set.add(first[0]);
        set.add(first[1]);
        parent[first[0]] = -1;
        parent[first[1]] = first[0];
        while(set.size() < graph.length && !queue.isEmpty()) {
            int[] curr = queue.poll();
            int u = curr[0];
            int v = curr[1];
            if(!set.contains(u) && set.contains(v)) {
                set.add(u);
                set.add(v);
                parent[u] = v;
            } else if(set.contains(u) && !set.contains(v)) {
                set.add(u);
                set.add(v);
                parent[v] = u;
            }
        }
        return parent;
    }

    public static int[] prim(int[][] graph) {
        int n = graph.length;
        List<Integer> visited = new ArrayList<>();
        // 任选一个点当根节点
        visited.add(0);
        int[] parent = new int[n];
        parent[0] = -1; // 首节点无父节点
        int start = 0, end = 0, val;
        // 直到所有节点都加入
        while(visited.size() < n) {
            val = Integer.MAX_VALUE;
            // 遍历已经加入的节点
            for(Integer node : visited) {
                for(int i = 0; i < n; i++) {
                    // 从未访问过的节点中找路径最短的
                    if(!visited.contains(i)) {
                        if(graph[node][i] < val) {
                            start = node;
                            end = i;
                            val = graph[node][i];
                        }
                    }
                }
            }
            // 加入该点，并保存它的父节点
            visited.add(end);
            parent[end] = start;
        }
        return parent;
    }

    public static void primWithAdjList() {
        int n = 4;
        List<List<int[]>> graph = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // 添加边：双向图
        addEdge(graph, 0, 1, 2);
        addEdge(graph, 0, 2, 2);
        addEdge(graph, 0, 3, 9);
        addEdge(graph, 1, 2, 2);
        addEdge(graph, 1, 3, 4);
        addEdge(graph, 2, 3, 1);

        System.out.println(Arrays.toString(find(graph, n)));
    }

    private static void addEdge(List<List<int[]>> graph, int u, int v, int weight) {
        graph.get(u).add(new int[]{v, weight});
        graph.get(v).add(new int[]{u, weight});
    }

    public static int[] find(List<List<int[]>> graph, int n) {
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        // {from, to, weight} 其中 weight 只用于排序
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        // 任选根节点
        parent[0] = -1;
        for(int[] neighbor : graph.get(0)) {
            queue.offer(new int[]{0, neighbor[0], neighbor[1]});
        }
        visited[0] = true;

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int from = curr[0], to = curr[1];

            if(visited[to]) continue;

            visited[to] = true;
            parent[to] = from;
            for(int[] neighbor : graph.get(to)) {
                if(!visited[neighbor[0]])
                    queue.offer(new int[]{to, neighbor[0], neighbor[1]});
            }
        }

        return parent;
    }
}
