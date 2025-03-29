package search;

public class BinarySearch {
    public static void main(String[] args) {
        int[] sorted = {1, 2, 3, 4, 5};
        int[] datas = {1, 23, 43, 56, 67, 69, 75, 81, 94};
        System.out.println(binarySearch(sorted, 1));
        System.out.println(iterate(datas, 81));
    }

    public static int binarySearch(int[] sorted, int target) {
        return recursive(sorted, target, 0, sorted.length - 1);
    }

    private static int recursive(int[] sorted, int target, int start, int end) {
        if(start > end)
            return -1;
        int mid = (start + end) / 2;
        if(sorted[mid] == target)
            return mid;
        else if(sorted[mid] < target)
            return recursive(sorted, target, mid + 1, end);
        else
            return recursive(sorted, target, start, mid - 1);
    }

    public static int iterate(int[] sorted, int target) {
        int start = 0, end = sorted.length - 1;
        while(start <= end) {
            int mid = (start + end) / 2;
            if(sorted[mid] == target)
                return mid;
            else if(sorted[mid] < target)
                start = mid + 1;
            else
                end = mid - 1;
        }
        return -1;
    }
}
