package search;

public class TwoDimArray {
    public static void main(String[] args) {
        int[][] nums = {{ 1, 4, 6, 28 }, { 2, 7, 32, 34 }, { 10, 11, 67, 79 }};
        System.out.println(isExist(nums, 32));
    }

    public static boolean isExist(int[][] array, int target) {
        int i = array.length - 1, j = 0;
        while(i >= 0 && i < array.length && j >= 0 && j < array[0].length) {
            // 相等，直接返回
            if(array[i][j] == target)
                return true;
            // 比当前数小，往上边移动
            else if(array[i][j] > target)
                i--;
            // 比当前数大，往右边移动
            else
                j++;
        }
        return false;
    }
}