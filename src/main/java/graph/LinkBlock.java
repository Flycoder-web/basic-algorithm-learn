package graph;

public class LinkBlock {
    static int count = 0;

    public static void main(String[] args) {
        char[][] maps = {
                {'*', '*', '*', '#', '#'},
                {'*', '#', '#', '*', '#'},
                {'*', '#', '*', '*', '#'},
                {'#', '#', '#', '*', '#'},
                {'#', '#', '*', '*', '#'}
        };
        int row = maps.length, col = maps[0].length;
        boolean[][] visited = new boolean[row][col];
        traverse(maps, visited);
        System.out.println(count);
    }

    public static void traverse(char[][] graph, boolean[][] visited) {
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[0].length; j++) {
                if(!visited[i][j] && graph[i][j] == '#') {
                    count++;
                    dfs(graph, visited, i, j);
                }
            }
        }
    }

    private static void dfs(char[][] graph, boolean[][] visited, int i, int j) {
        // 边界
        if(i < 0 || i >= graph.length || j < 0 || j >= graph[0].length)
            return;
        // 深度搜索
        if(!visited[i][j] && graph[i][j] == '#') {
            visited[i][j] = true;
            dfs(graph, visited, i + 1, j);
            dfs(graph, visited, i - 1, j);
            dfs(graph, visited, i, j + 1);
            dfs(graph, visited, i, j - 1);
        }
    }
}