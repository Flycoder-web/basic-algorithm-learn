package problem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class LuckyNums {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int min = scanner.nextInt();
//        int max = scanner.nextInt();
        //System.out.println(countLuckyNum(1, 20));
        System.out.println(countLuckyNum2(1, 20));
//        System.out.println(countLuckyNum(30, 69));
//        System.out.println(countLuckyNum2(30, 69));
    }

    public static int countLuckyNum(int min, int max) {
        LinkedList<Integer> list = new LinkedList<>();
        // 先把能被2整除的去除
        for(int i = 1; i < max; i = i + 2) {
            list.add(i);
        }
        int index = 1;
        while(index < list.size()) {
            int num = list.get(index);
            int numOfRemove = 0; // 偏移量
            int size = list.size();
            for(int i = 0; i < size; i++) {
                // 序号能否被当前数整除
                if((i + 1) % num == 0) {
                    list.remove(i - numOfRemove);
                    numOfRemove++;
                }
            }
            index++;
        }
        int count = 0;
        while(!list.isEmpty()) {
            int x = list.pop();
            if(x < max && x > min)
                count++;
        }
        return count;
    }

    public static int countLuckyNum2(int min, int max) {
        // 处理特殊情况
        if (max <= 1 || min >= max) return 0;

        // 创建布尔数组，true 表示保留，false 表示删除
        boolean[] isKept = new boolean[max]; // 索引 0 到 max-1 表示数字 1 到 max
        // 初始时所有数字都保留
        Arrays.fill(isKept, true);

        // 第一步：移除所有偶数位置（序号从 1 开始，第 2、4、6...个）
        // 注意数组索引与序号没对齐
        for (int i = 1; i < max; i += 2) { // 注意：i 是索引，从 0 开始
            isKept[i] = false; // 标记偶数位置为删除
        }

        // 后续筛法
        int step = 1; // 对应 index，从第 2 个数字开始
        while (true) {
            // 找到第 step 个未删除的数字
            int count = 0;
            int num = -1;
            for (int i = 0; i < max; i++) {
                if (isKept[i]) {
                    count++;
                    if (count == step + 1) { // 第 step+1 个数字（从 0 开始计数）
                        num = i + 1; // num 是实际数字（索引 + 1）
                        break;
                    }
                }
            }
            if (num == -1 || num >= max) break; // 没有足够的数字继续筛

            // 用 num 标记删除
            int pos = 0; // 当前未删除数字的序号
            for (int i = 0; i < max; i++) {
                if (isKept[i]) {
                    pos++;
                    if (pos % num == 0) { // 序号是 num 的倍数
                        isKept[i] = false;
                    }
                }
            }
            step++;
        }

        // 统计 (min, max) 内的幸运数字
        int count = 0;
        for (int i = 0; i < max; i++) {
            int num = i + 1; // 实际数字
            if (isKept[i] && num > min && num < max) {
                count++;
            }
        }
        return count;
    }
}
