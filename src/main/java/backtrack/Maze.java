package backtrack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Position {
    int row;
    int col;
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    @Override public String toString() {
        return "(" + row + "," + col + ")";
    }
}

public class Maze {
    int[][] maze;
    Deque<Position> stack;
    boolean[][] visited;
    int row;
    int col;

    public Maze(int row, int col) {
        this.row = row;
        this.col = col;
        maze = new int[row][col];
        stack = new ArrayDeque<>();
        visited = new boolean[row][col];
    }

    public static void main(String[] args) {
        Maze m = new Maze(5, 5);
        m.maze = new int[][]{
                {0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 1, 0, 0, 0},
                {1, 0, 0, 1, 0},
                {1, 0, 1, 1, 0}
        };
        System.out.println(m.hasPath());
        m.visited = new boolean[5][5];
        m.findAllPath();
    }

    public boolean hasPath() {
        int i = 0;
        int j = 0;
        stack.push(new Position(i, j));
        visited[i][j] = true;

        while (!stack.isEmpty() && !((i == row - 1) && (j == col - 1))) {
            if(j + 1 < col && !visited[i][j + 1] && maze[i][j + 1] == 0) {
                stack.push(new Position(i, j + 1));
                visited[i][j + 1] = true;
            } else if(i + 1 < row && !visited[i + 1][j] && maze[i + 1][j] == 0) {
                stack.push(new Position(i + 1, j));
                visited[i + 1][j] = true;
            } else if(j - 1 >= 0 && !visited[i][j - 1] && maze[i][j - 1] == 0) {
                stack.push(new Position(i, j - 1));
                visited[i][j - 1] = true;
            } else if(i - 1 >= 0 && !visited[i - 1][j] && maze[i - 1][j] == 0) {
                stack.push(new Position(i - 1, j));
                visited[i - 1][j] = true;
            }
            Position top = stack.peek();
            if(top != null && top.row == i && top.col == j) {
                stack.pop();
            }
            if(!stack.isEmpty()) {
                i = stack.peek().row;
                j = stack.peek().col;
            } else break;
        }
        if(stack.isEmpty())
            return false;
        else {
            stack.reversed().forEach(p -> {
                System.out.print("(" + p.row + "," + p.col + ")" + "->");
            });
        }
        return true;
    }

    // 深度优先搜索找所有可到达“出口”的路径
    public void findAllPath() {
        List<Position> path = new ArrayList<>();
        dfs(0, 0, path);
    }

    private void dfs(int i, int j, List<Position> path) {
        if(i < 0 || i > row - 1 || j < 0 || j > col - 1 || visited[i][j] || maze[i][j] == 1)
            return;

        path.add(new Position(i, j));
        visited[i][j] = true;

        if(isExit(i, j)) { // 使用给定的出口规则
            System.out.println(path);
        } else {
            dfs(i - 1, j, path);
            dfs(i + 1, j, path);
            dfs(i, j - 1, path);
            dfs(i, j + 1, path);
        }

        path.removeLast();
        visited[i][j] = false;
    }

    // 假设出口在最后一行
    boolean isExit(int i, int j) {
        return (i == row - 1) && !(i == 0 && j == 0);
    }
}
