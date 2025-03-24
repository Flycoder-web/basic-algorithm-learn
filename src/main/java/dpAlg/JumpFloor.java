package dpAlg;

public class JumpFloor {
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++)
            System.out.println(i + "层：" + jumpSum(i));
        for(int i = 0; i < 10; i++)
            System.out.println(i + "层：" + jumpSum_dp(i));
        for(int i = 0; i < 10; i++)
            System.out.println(i + "层：" + jumpSum_intuit(i));
    }

    public static int jumpSum(int n) {
        if (n <= 0)
            return 0;
        int[] nums = new int[n];
        nums[0] = 1;
        for(int i = 1; i < n; i++) {
            int sum = 1;
            for(int j = 0; j < i; j++) {
                sum += nums[j];
            }
            nums[i] = sum;
        }
        return nums[n - 1];
    }

    public static int jumpSum_dp(int n) {
        if(n <= 0)
            return 0;
        int res = 1;
        // 相当于计算 2^(n-1)
        for(int i = 1; i < n; i++) {
            res = 2 * res;
        }
        return res;
    }
    public static int jumpSum_intuit(int n) {
        if(n <= 0)
            return 0;
        return (int)Math.pow(2, n - 1);
    }
}
