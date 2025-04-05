package backtrack;

// TSP的变体：不回到原点
public class TSP_Try {
    int[][] distances;
    int cityNum;
    boolean[] visited;
    int minDis = Integer.MAX_VALUE;

    public TSP_Try(int num) {
        cityNum = num;
        distances = new int[cityNum][cityNum];
        visited = new boolean[cityNum];
    }

    public static void main(String[] args) {
        TSP_Try travel = new TSP_Try(4);
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
        travel.travelOnce(0, 0);
        System.out.println(travel.minDis);
    }

    public void travelOnce(int start, int totalDis) {
        // 只遍历完，假设不回到原点
        if(isAllVisited()) {
            if(totalDis < minDis)
                minDis = totalDis;
            return;
        }
        for(int i = 0; i < cityNum; i++) {
            if(distances[start][i] != -1 && !visited[i]) {
                int currentDis = totalDis + distances[start][i];
                if(currentDis < minDis) {
                    // 在回溯过程中，visited 仅在内部被修改，这样才能确保回溯逻辑正确
                    visited[i] = true;
                    travelOnce(i, currentDis);
                    visited[i] = false;
                }
            }
        }
    }

    public boolean isAllVisited() {
        for(boolean visit : visited)
            if(!visit)
                return false;
        return true;
    }
}