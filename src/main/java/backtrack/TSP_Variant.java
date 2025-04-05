package backtrack;

import java.util.Arrays;

public class TSP_Variant {
    int count; // 城市数量
    int[] currRoutes; // 当前路径
    int[] minRoutes; // 最短路径
    int currDistance; // 当前路程
    int minDistance = Integer.MAX_VALUE; // 最小路程
    boolean[] visited; // 是否被访问过
    int[][] distances; // 城市间距离

    public TSP_Variant(int count) {
        this.count = count;
        currRoutes = new int[count];
        minRoutes = new int[count];
        visited = new boolean[count];
        distances = new int[count][count];
        currDistance = 0;
        Arrays.fill(currRoutes, -1);
    }

    public static void main(String[] args) {
        TSP_Variant travel = new TSP_Variant(4);
        travel.distances[0][0] = -1;
        travel.distances[0][1] = 30;
        travel.distances[0][2] = 6;
        travel.distances[0][3] = 4;

        travel.distances[1][0] = 30;
        travel.distances[1][1] = -1;
        travel.distances[1][2] = 5;
        travel.distances[1][3] = 10;

        travel.distances[2][0] = 6;
        travel.distances[2][1] = 5;
        travel.distances[2][2] = -1;
        travel.distances[2][3] = 20;

        travel.distances[3][0] = 4;
        travel.distances[3][1] = 10;
        travel.distances[3][2] = 20;
        travel.distances[3][3] = -1;

        // 从城市0出发
        travel.currRoutes[0] = 0;
        travel.visited[0] = true;
        travel.travel(1);

        System.out.println(Arrays.toString(travel.minRoutes));
    }

    public void travel(int step) {
        if(step >= count) { // 是否遍历完所有城市，不回到原点
            if(currDistance < minDistance) {
                // 更新最短路程
                minRoutes = Arrays.copyOf(currRoutes, count);
                minDistance = currDistance;
                System.out.println("min distance: " + minDistance);
            }
            // 若想回到原点，只需加上回程即可，使用temp不要直接加到 currDistance 全局变量上
//            int temp = currDistance + distances[currRoutes[step - 1]][currRoutes[0]];
//            if(temp < minDistance) {
//                minRoutes = Arrays.copyOf(currRoutes, count);
//                minDistance = temp;
//                System.out.println("min distance: " + minDistance);
//            }
            return;
        }
        // 继续走下一个城市
        for(int i = 0; i < count; i++) {
            // 下一步没有走过，并且可达，则选中，currRoutes[step-1]是当前城市的索引
            if(!visited[i] && distances[currRoutes[step - 1]][i] != -1) {
                int ifgo = currDistance + distances[currRoutes[step - 1]][i];
                if(ifgo < minDistance) { // 剪枝
                    currRoutes[step] = i;
                    visited[i] = true;
                    currDistance += distances[currRoutes[step - 1]][i];
                    travel(step + 1);
                    // 回溯到上一步
                    currRoutes[step] = -1;
                    visited[i] = false;
                    currDistance -= distances[currRoutes[step - 1]][i];
                }
            }
        }
    }
}
