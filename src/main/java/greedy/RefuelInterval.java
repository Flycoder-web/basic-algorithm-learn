package greedy;

public class RefuelInterval {
    public static void main(String[] args) {
        int[] dis = {30,40,80,12,78};
        System.out.println(greedy(dis, 90));
    }

    public static int greedy(int[] stationDis, int origin) {
        // 每相邻两个加油站的距离不超过 n，否则无解
        for(int station : stationDis)
            if(origin < station)
                return -1;
        // 出发加油第一次
        int count = 1;
        // 剩余可行驶里程
        int remain = origin;
        for(int station : stationDis) {
            if(remain > station)
                // 剩余里程够
                remain -= station;
            else {
                // 不够，则加油
                remain = origin;
                count++;
            }
        }
        return count;
    }
}
