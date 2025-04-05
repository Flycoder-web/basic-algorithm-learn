package greedy;

import java.util.Arrays;

public class WeightMatch {
    public static void main(String[] args) {
        int[] weights = {1, 9, 7, 3, 5, 5};
        System.out.println(match(weights, weights.length));
    }

    public static int match(int[] weights, int n) {
        Arrays.sort(weights);
        int j = n / 2;
        int res = n;
        for(int i = 0; i < n / 2; i++) {
            // 不满足，则说明后面一个人不够胖，往后面找
            while(j < n && weights[i] * 2 > weights[j]) {
                j++;
            }
            if(j < n) {
                res--;
                j++;
            } else break;
        }
        return res;
    }
}
