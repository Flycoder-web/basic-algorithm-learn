package search;

public class CircleNum {
    public static void main(String[] args) {
        int[][] friendships = {{0, 1}, {1, 2}, {3, 4}};
        int[][] nums2 = {{ 1, 2 },{ 1, 3 },{ 2, 5 },{ 3, 5 }, { 4, 6 },};
        System.out.println(find(friendships, 5));
        System.out.println(find(nums2, 7) - 1);
    }

    public static int find(int[][] friendships, int n) {
        int[] parent = new int[n];
        int[] rank = new int[n];
        int count = 0;
        for(int i = 0; i < n; i++)
            parent[i] = i;

        for(int[] pair : friendships) {
            union(pair[0], pair[1], parent, rank);
        }

        for(int i = 0; i < n; i++)
            if(parent[i] == i)
                count++;
        return count;
    }

    public static void union(int x, int y, int[] parent, int[] rank) {
        int rootX = getRoot(x, parent);
        int rootY = getRoot(y, parent);
        if(rootX != rootY) {
            // 按秩合并，且只在秩相同时增加
            if(rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            }
            else if(rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    public static int getRoot(int x, int[] parent) {
        if(parent[x] != x)
            parent[x] = getRoot(parent[x], parent);
        return parent[x];
    }
}
