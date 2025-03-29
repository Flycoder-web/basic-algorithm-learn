package search;

import utils.ConvertToUtil;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LargestKNums {
    public static void main(String[] args) {
        int[] nums = {4, 5, 1, 6, 2, 7, 3, 8};
        System.out.println(Arrays.toString(topK(nums, 4)));
        System.out.println(Arrays.toString(topK2(nums, 4)));
    }

    public static int[] topK(int[] nums, int k) {
        if(nums == null || nums.length <= k)
            return nums;
        MinPriorityQueue<Integer> minHeap = new MinPriorityQueue<>(Integer.class, k);
        for(int num : nums) {
            if(minHeap.size() < k)
                minHeap.insert(num, Integer.MAX_VALUE);
            else {
                if(minHeap.getMin() < num) {
                    minHeap.extractMin();
                    minHeap.insert(num, Integer.MAX_VALUE);
                }
            }
        }
        return ConvertToUtil.primitive(minHeap.toArray());
    }

    public static int[] topK2(int[] nums, int k) {
        if(nums == null || nums.length <= k)
            return nums;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int num : nums) {
            if(minHeap.size() < k)
                minHeap.offer(num);
            else {
                if(minHeap.peek() < num) {
                    minHeap.poll();
                    minHeap.offer(num);
                }
            }
        }
        return minHeap.stream().mapToInt(Integer::intValue).toArray();
    }
}
