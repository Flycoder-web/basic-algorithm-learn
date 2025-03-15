package arrayAndList;

public class Josephus {
    public static void main(String[] args) {
        System.out.println(josephus(5, 3));
    }

    public static int josephusCircle(int n, int m) {
        int[] peoples = new int[n];
        // 环中剩余的人
        int remain = n;
        int index = -1; // 开始的索引
        // 循环数1-m
        int step = 1;
        while(remain > 0) {
            // 索引循环移动
            if(index < n - 1)
                index++;
            else
                index = 0;
            // 跳过已淘汰的人
            if(peoples[index] == -1)
                continue;
            // 计数
            if(step < m) {
                step++;
            } else {
                // 若计数到m，则淘汰这个人
                peoples[index] = -1;
                remain--;
                step = 1;
            }
        }
        return index;
    }

    public static int josephus(int n, int m) {
        int[] peoples = new int[n];
        int index = -1; // 开始索引
        int remain = n; // 环中剩余的人
        int count = 0; // 循环数0-m
        while(remain > 0) {
            index++;
            if(index == n)
                index = 0;
            // 跳过淘汰的人
            if(peoples[index] == -1)
                continue;
            else {
                count++;
                if(count == m) {
                    peoples[index] = -1;
                    remain--;
                    count = 0;
                }
            }
        }
        return index;
    }
}
