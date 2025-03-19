package stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class EveryDayTemperature {
    public static void main(String[] args) {
        int[] temperatures = {63,54,76,56,37,89,23,74};
        int[] awaitDays = awaitDays_MonoStack(temperatures);
        System.out.println(Arrays.toString(awaitDays));
    }

    public static int[] awaitDays_intuit(int[] temperatures) {
        int[] res = new int[temperatures.length];
        for(int i = 0; i < temperatures.length; i++) {
            for(int j = i + 1; j < temperatures.length; j++) {
                // 找到第一个比它温度大的，计算距离
                if(temperatures[j] > temperatures[i]) {
                    res[i] = j - i;
                    break;
                }
            }
        }
        return res;
    }

    public static int[] awaitDays_MonoStack(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] res = new int[temperatures.length];
        for(int i = 0; i < temperatures.length; i++) {
            // 只要遇到比栈顶温度高的索引，就计算栈顶索引对应的天数
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int index = stack.pop();
                res[index] = i - index;
            }
            stack.push(i);
        }
        return res;
    }
}
