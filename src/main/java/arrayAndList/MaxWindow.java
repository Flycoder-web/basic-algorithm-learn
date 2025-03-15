package arrayAndList;

import java.util.Arrays;
import java.util.LinkedList;

public class MaxWindow {
    public static void main(String[] args) {
        int[] nums = { 3, 5, -1, 3, 2, 5, 1, 6};
        System.out.println(Arrays.toString(maxSlidingWindow_Queue(nums, 3)));
    }

    public static int[] maxSlidingWindow_Naive(int[] nums, int k) {
        if(nums.length == 0 || k == 0)
            return new int[0];
        int[] result = new int[nums.length - k + 1];
        for(int i = 0; i < nums.length - k + 1; i++) {
            int max = nums[i];
            for(int j = i; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            result[i] = max;
        }
        return result;
    }

    public static int[] maxSlidingWindow_Queue(int[] nums, int k) {
        int n = nums.length;
        if(nums.length == 0 || k == 0)
            return new int[0];
        int[] result = new int[n - k + 1];
        // 双向队列
        LinkedList<Integer> queue = new LinkedList<>();
        for(int i = 0; i < n; i++) {
            // 移除队列中较小元素
            while(!queue.isEmpty() && nums[i] > nums[queue.getFirst()])
                queue.pollFirst();
            // 添加当前元素
            queue.offerFirst(i);
            // 移除不在窗口内的元素
            if(queue.getLast() <= i - k)
                queue.pollLast();
            // 当前索引大于等于窗口大小时，记录最大元素
            if(i - k >= -1)
                result[i - k + 1] = nums[queue.getLast()];
        }
        return result;
    }
}
