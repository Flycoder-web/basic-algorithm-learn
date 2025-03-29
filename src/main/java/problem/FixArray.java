package problem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FixArray {
    public static void main(String[] args) {
        int n = 5;
        int[] nums = {2, 1, 1, 3, 4};
        union(nums, n);
        union_changes(nums, n);
    }

    // 暴力解法，最坏O(n^2)
    public static void origin(int[] nums, int n) {
        Set<Integer> set = new HashSet<>(n);
        for(int i = 0; i < n; i++) {
            while(set.contains(nums[i]))
                nums[i] += 1;
            set.add(nums[i]);
        }
        System.out.println(Arrays.toString(nums));
    }

    // 并查集
    public static void union(int[] nums, int n) {
        int[] changes = new int[1_000_010];
        for(int i = 0; i < 1_000_010; i++)
            changes[i] = i;
        for(int i = 0; i < n; i++) {
            nums[i] = unionFind(nums[i], changes);
            changes[nums[i]] = unionFind(nums[i] + 1, changes);
        }
        System.out.println(Arrays.toString(nums));
    }
    // 并查集，changes 数组初始值优化
    public static void union_changes(int[] nums, int n) {
        int max = Arrays.stream(nums).max().getAsInt();
        int[] changes = new int[max + n + 2];

        for(int i = 0; i < changes.length; i++)
            changes[i] = i;
        for(int i = 0; i < n; i++) {
            nums[i] = unionFind(nums[i], changes);
            changes[nums[i]] = unionFind(nums[i] + 1, changes);
        }
        System.out.println(Arrays.toString(nums));
    }

    // 未实现路径压缩
    public static int unionFind_NoPathCompress(int index, int[] changes) {
        while(changes[index] != index)
            index = changes[index]; // 只是单纯向上查找，未路径压缩
        return changes[index];
    }
    // 带路径压缩的实现
    public static int unionFind(int index, int[] changes) {
        if(changes[index] != index) // 注意是判断，不是循环
            changes[index] = unionFind(changes[index], changes);
        return changes[index];
    }
}
