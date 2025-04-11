package bit;

public class Operations {
    public static void main(String[] args) {
        System.out.println(add(4, 12));
        int a = -1;
        System.out.println(Integer.bitCount(a));
        System.out.println(countBits(a));
        System.out.println(findDifferentChar("bac", "abcd"));
        System.out.println(hammingDist(83, 53));
        System.out.println(hammingDist_opt(83, 53));
    }

    public static int add(int a, int b) {
        while (b != 0) {
            int sum = a ^ b;
            int carry = (a & b) << 1; // 进位信息
            a = sum;
            b = carry;
        }
        return a;
    }

    public static int countBits(int num) {
        int count = 0;
        while (num != 0) {
            count += num & 1; // 检查最低位是否为 1
            num >>>= 1; // 无符号右移一位
        }
        return count;
    }

    public static char findDifferentChar(String a, String b) {
        int ret = 0;
        for(char c : a.toCharArray())
            ret ^= c;
        for(char c : b.toCharArray())
            ret ^= c;
        return (char) ret;
    }

    public static int hammingDist(int a, int b) {
        int s = a ^ b, count = 0;
        while(s != 0) {
            count += s & 1;
            s >>>= 1;
        }
        return count;
    }

    public static int hammingDist_opt(int a, int b) {
        int s = a ^ b, count = 0;
        while(s != 0) {
            s &= s - 1;
            count++;
        }
        return count;
    }
}
