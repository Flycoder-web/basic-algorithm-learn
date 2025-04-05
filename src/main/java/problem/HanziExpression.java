package problem;

import java.util.Arrays;

// 祥 瑞 生 辉 + 三 羊 献 瑞 = 三 羊 生 瑞 气
public class HanziExpression {
    public static void main(String[] args) {
        int[] hanzis = new int[8]; // 分别代表“三羊献瑞祥生辉气”
        find(0, 8, hanzis);
    }

    public static void find(int i, int n, int[] hanzis) {
        if(i == n) {
            if(check(hanzis))
                System.out.println(Arrays.toString(hanzis));
            return;
        }
        for(int num = 0; num < 10; num++) {
            // 对每一位尝试每一个数字
            hanzis[i] = num;
            if(isValid(hanzis, i)) {
                // 如果合法，就继续下一个
                find(i + 1, n, hanzis);
            }
        }
    }

    // 检查第i个字是否合法（数字首不为0，第i个数字在之前未被使用过）
    public static boolean isValid(int[] hanzis, int i) {
        if(i == 0 && hanzis[i] == 0) {
            return false;
        }
        for(int j = 0; j < i; j++) {
            if(hanzis[j] == hanzis[i])
                return false;
        }
        if(i == 4 && hanzis[i] == 0)
            return false;
        return true;
    }

    // 检查这8个子是否满足加法式
    public static boolean check(int[] hanzis) {
        int a = hanzis[4] * 1000 + hanzis[3] * 100 + hanzis[5] * 10 + hanzis[6];
        int b = hanzis[0] * 1000 + hanzis[1] * 100 + hanzis[2] * 10 + hanzis[3];
        int c = hanzis[0] * 10000 + hanzis[1] * 1000 + hanzis[5] * 100 + hanzis[3] * 10 + hanzis[7];
        return a + b == c;
    }
}