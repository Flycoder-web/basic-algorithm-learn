package graph;

import java.util.Arrays;

class Edge {
    int form, to, weight;
    public Edge(int form, int to, int weight) {
        this.form = form;
        this.to = to;
        this.weight = weight;
    }
}

public class BellmanFord {
    public static void main(String[] args) {
        int V = 4;
        int E = 7;
        Edge[] edges = new Edge[E];

        edges[0] = new Edge(0, 1, 2);
        edges[1] = new Edge(0, 2, 5);
        edges[2] = new Edge(3, 0, 9);
        edges[3] = new Edge(1, 3, 5);
        edges[4] = new Edge(3, 1, 4);
        edges[5] = new Edge(2, 3, 1);
        edges[6] = new Edge(1, 2, 2);

        System.out.println(Arrays.toString(find(0, V, edges)));
    }

    public static int[] find(int source, int V, Edge[] edges) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // 对所有边进行 V-1 次松弛
        for(int i = 0; i < V; i++) {
            for(Edge edge : edges) {
                int u = edge.form;
                int v = edge.to;
                int w = edge.weight;
                if(dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        // 检测负权环
        for(Edge edge : edges) {
            int u = edge.form;
            int v = edge.to;
            int w = edge.weight;
            if(dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                System.out.println("存在负权环！");
                return null;
            }
        }

        return dist;
    }
}
