package backtrack;

import java.util.Arrays;

public class Nqueens {
    public static void main(String[] args) {
        int n = 4;
        int[][] maps = new int[n][n];
        find_origin(maps, 0);
        find(n);
    }

    // 未优化的二维数组版回溯
    public static void find_origin(int[][] maps, int row) {
        // 所有行都放置完毕，找到一个解
        if(row == maps.length) {
            printMaps(maps);
            return;
        }
        // 对当前行，尝试每一列
        for(int col = 0; col < maps.length; col++) {
            // 验证是否可以放置
            if(isValid(maps, row, col)) {
                maps[row][col] = 1; // 放置
                find_origin(maps, row + 1); // 下一行递归
                maps[row][col] = 0; // 回溯
            }
        }
    }

    public static void printMaps(int[][] maps) {
        System.out.println(Arrays.deepToString(maps));
    }

    private static boolean isValid(int[][] maps, int row, int col) {
        // 对前面行检查
        for(int i = 0; i < row; i++) {
            // 是否列冲突
            if(maps[i][col] == 1)
                return false;
            // 行差
            int diff = row - i;
            // 斜线上元素的行差等于列差绝对值
            if(col - diff >= 0 && maps[i][col - diff] == 1) // 左上角斜线
                return false;
            if(col + diff < maps.length && maps[i][col + diff] == 1) // 右上角斜线
                return false;
        }
        return true;
    }

    // 优化后的一维数组版
    public static void find(int n) {
        int[] board = new int[n];
        Arrays.fill(board, -1); // 初始填充为 -1
        backtrack(board, 0);
    }

    public static void backtrack(int[] board, int row) {
        if(row == board.length) {
            System.out.println(Arrays.toString(board));
            return;
        }
        for(int col = 0; col < board.length; col++) {
            if(isSafe(board, row, col)) {
                board[row] = col;
                backtrack(board, row + 1);
                board[row] = -1;
            }
        }
    }

    private static boolean isSafe(int[] board, int row, int col) {
        for(int i = 0; i < row; i++) {
            // 是否位于同一列
            if(board[i] == col)
                return false;
            // 是否位于同一斜线上，即行差绝对值等于列差绝对值
            if(Math.abs(board[i] - col) == row - i)
                return false;
        }
        return true;
    }
}
