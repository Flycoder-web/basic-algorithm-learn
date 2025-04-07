package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Kruskal {
    public static void main(String[] args) {
        int n = 4;
        List<int[]> graph = new ArrayList<>();
        graph.add(new int[]{0, 1, 2});
        graph.add(new int[]{0, 2, 2});
        graph.add(new int[]{0, 3, 9});
        graph.add(new int[]{1, 2, 2});
        graph.add(new int[]{1, 3, 4});
        graph.add(new int[]{2, 3, 1});
        graph.sort(Comparator.comparingInt(a -> a[2]));

        kruskal_ref(graph, n);
        System.out.println(Arrays.toString(kruskal(graph, n)));
    }

    public static void kruskal_ref(List<int[]> graph, int n) {
        int sum = 0;
        int[] parent = new int[n]; // 这里的parent并代表生成树结构
        Arrays.fill(parent, -1);

        for (int[] curr : graph) {
            int rootStart = findRoot(parent, curr[0]);
            int rootEnd = findRoot(parent, curr[1]);

            if (rootStart != rootEnd) {
                parent[rootStart] = rootEnd; // 两个根节点合并
                sum += curr[2];
                System.out.println(curr[0] + " <- " + curr[1]);
            }
        }
        System.out.println(sum);
    }

    // 并查集找根节点，没有路径压缩
    private static int findRoot(int[] parent, int index) {
        while (parent[index] >= 0)
            index = parent[index];
        return index;
    }

    public static int[] kruskal(List<int[]> graph, int n) {
        int sum = 0;
        int[] parents = new int[n]; // 这里的parent代表生成树结构
        Arrays.fill(parents, -1);
        UnionFind uf = new UnionFind(n);

        for(int[] edge : graph) {
            int from = edge[0], to = edge[1];
            int weight = edge[2];
            if(uf.find(from) != uf.find(to)) {
                uf.union(from, to);
                parents[to] = from;
                sum += weight;
            }
        }
        return parents;
    }
}

// 并查集，带路径压缩，按秩合并
class UnionFind {
    int[] parent;
    int[] rank;

    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for(int i = 0; i < n; i++)
            parent[i] = i;
    }

    int find(int index) {
        if(parent[index] != index)
            index = find(parent[index]);
        return index;
    }

    void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if(rootX == rootY) return;

        if(rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if(rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}
