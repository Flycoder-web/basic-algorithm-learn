package search;

import java.util.HashMap;
import java.util.Map;

public class TwoNumSum {
    public static void main(String[] args) {
        int[] nums = {5, 12, 43, 34, 6, 23};
        int target = 35;
        System.out.println(find(nums, target));
    }

    public static String find(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            if(map.containsKey(target - nums[i]))
                return map.get(target - nums[i]) + " " + i;
            map.put(nums[i], i); // 应该先判断，再存入，防止匹配自身
        }
        return "";
    }
}
