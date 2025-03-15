package problem;

import java.util.Arrays;
import java.util.Scanner;

public class ArraySort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for(int i = 0; i < n; i++)
            nums[i] = scanner.nextInt();
        bubbleSort(nums);
        //insertionSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void bubbleSort(int[] nums) {
        int temp;
        for(int i = 0; i < nums.length - 1; i++) {
            for(int j = 0; j < nums.length - 1 - i; j++) {
                if(nums[j] > nums[j + 1]) {
                    temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

    public static void insertionSort(int[] nums) {
        for(int i = 1; i < nums.length; i++) {
            int j = i - 1;
            int temp = nums[i];
            while(j >= 0 && temp < nums[j]) {
                    nums[j + 1] = nums[j];
                    j--;
            }
            nums[j + 1] = temp;
        }
    }
}
