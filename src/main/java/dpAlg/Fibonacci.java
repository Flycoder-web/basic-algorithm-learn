package dpAlg;

public class Fibonacci {
    public static void main(String[] args) {
        for(int i = 0; i <= 20; i++)
            System.out.println(recursive(i) + "  " + i);
        for(int i = 0; i <= 20; i++)
            System.out.println(bottomUp(i) + "  " + i);
        for(int i = 0; i <= 20; i++)
            System.out.println(noArray(i) + "  " + i);
    }

    public static long recursive(int n) {
        if(n == 0)
            return 0;
        if(n == 1)
            return 1;
        return recursive(n - 1) + recursive(n - 2);
    }

    public static long bottomUp(int n) {
        int[] nums = new int[n + 2]; // 注意是 n+2
        nums[0] = 0;
        nums[1] = 1;
        for(int i = 2; i <= n; i++) { // 注意是 i<=n
            nums[i] = nums[i - 1] + nums[i - 2];
        }
        return nums[n];
    }

    public static long noArray(int n) {
        if(n == 0)
            return 0;
        long fib0 = 0;
        long fib1 = 1;
        long res = 1;
        for(int i = 2; i <= n; i++) { // 注意是 i<=n
            res = fib0 + fib1;
            fib0 = fib1;
            fib1 = res;
        }
        return res;
    }
}
