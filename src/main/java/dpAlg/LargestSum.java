package dpAlg;

public class LargestSum {
    public static void main(String[] args) {
        int[] nums = {1, -2, 3, 10, -4, 7, 2, -5};
        System.out.println(simple(nums));
    }

    public static int simple(int[] array) {
        if(array == null || array.length == 0)
            return 0;

        int max = Integer.MIN_VALUE;
        // 从每一个字符开始
        for(int i = 0; i < array.length; i++) {
            int tempSum = 0;
            // 每一种长度的子串（合法的索引范围）
            for(int j = i; j < array.length; j++) {
                tempSum += array[j];
                // 当前最大值和保存的最大值相比较
                if(max < tempSum)
                    max = tempSum;
            }
        }

        return max;
    }

    public static int kadane(int[] array) {
        int currentMax = array[0];
        int globalMax = array[0];
        for(int i = 1; i < array.length; i++) {
            // 对于每个元素，要么把当前元素加入到之前的子数组中，要么重新开始一个子数组
            currentMax = Math.max(currentMax + array[i], array[i]);
            globalMax = Math.max(currentMax, globalMax);
        }
        return globalMax;
    }
}
