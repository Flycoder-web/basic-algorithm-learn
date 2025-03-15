package stringAlg;

import java.util.Scanner;

public class CountOfNum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        long num = 0;
        num = str.chars().filter(c -> c >= '0' && c <= '9').count();
        System.out.println(num);
    }
}
