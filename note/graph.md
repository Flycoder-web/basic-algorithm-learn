# 图的算法
## DFS 求联通块
输入一个 m 行 n 列的字符矩阵，统计字符 # 组成多少个连通块。如果两个字符 # 所在的格子相邻（横、纵），就说它们属于一个连通块，字符矩阵里面除了 # 就是 *。如下面的矩阵，有两个联通块：
```java
char[][] maps = {
    {'*', '*', '*', '#', '#'},
    {'*', '#', '#', '*', '#'},
    {'*', '#', '*', '*', '#'},
    {'#', '#', '#', '*', '#'},
    {'#', '#', '*', '*', '#'}
};
```
可以使用深度优先搜索，同时需要一个 `visited[][]` 数组记录字符是否已经被访问。

逐个检查矩阵中的每个元素，当遇到一个未被访问的 '#' 时，开始一次 DFS，递归遍历它周围的 ’#‘ 格子，每次访问一个格子时就给它标识已经被访问。代码如下：
```java
public class LinkBlock {
    static int count = 0;

    public static void main(String[] args) {
        char[][] maps = {
                {'*', '*', '*', '#', '#'},
                {'*', '#', '#', '*', '#'},
                {'*', '#', '*', '*', '#'},
                {'#', '#', '#', '*', '#'},
                {'#', '#', '*', '*', '#'}
        };
        int row = maps.length, col = maps[0].length;
        boolean[][] visited = new boolean[row][col];
        traverse(maps, visited);
        System.out.println(count);
    }

    public static void traverse(char[][] graph, boolean[][] visited) {
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[0].length; j++) {
                if(!visited[i][j] && graph[i][j] == '#') {
                    count++;
                    dfs(graph, visited, i, j);
                }
            }
        }
    }

    private static void dfs(char[][] graph, boolean[][] visited, int i, int j) {
        // 边界
        if(i < 0 || i >= graph.length || j < 0 || j >= graph[0].length)
            return;
        // 深度搜索
        if(!visited[i][j] && graph[i][j] == '#') {
            visited[i][j] = true;
            dfs(graph, visited, i + 1, j);
            dfs(graph, visited, i - 1, j);
            dfs(graph, visited, i, j + 1);
            dfs(graph, visited, i, j - 1);
        }
    }
}
```
空间复杂度是 $O(n^2)$，由于每个字符只会被访问一次，时间复杂度也是 $O(n^2)$。

## BFS 求最短路径
假设有一个迷宫，需要求解起点到终点的最短路径。即给一个 n 行 m 列的迷宫，S 表示迷宫的起点，T 表示迷宫的终点，* 表示不能通过的点，# 表示可以通过的点。现在要求从 S 出发走到 T，每次只能上下左右走动，并且只能进入能通过的点，每个点只能通过一次，问是否能够到达终点可以的话求出最短路程，如果不可以输出 -1。例如：
```
* S * #
# # # *
* * # *
* # # *
T # * *
```
为了记录每个位置是否被访问过，以及从起点到当前位置的路程，可以直接在原二维数组上修改，访问到非 `*` 元素就将其设置为路程数，例如 `S` 起点设置为 0，则其下一步的 `#` 设置为 1，以此类推，直到终点，这时终点处修改后的值就是最终的最短路程。当然，也可以额外用一个同样大小的数组来标识每一个位置是否被访问过，另外一个数组来记录从开始位置到当前位置的路程（步数）。

初始化时，需要将入口的节点加到队列中，然后循环判断队列是否为空，如果队列不为空，执行以下操作：

取出第一个元素，同时取出坐标，然后遍历其四个方向上的相邻位置，如果该位置的坐标合法并且该位置不是 *，并且没有被访问过，说明该位置可以走，更新路程和访问数组，再执行下面的判断：
- 如果该位置是 T，说明到出口了，直接返回刚更新的路程；
- 如果不是 T，说明是 `#`，将当前位置添加到队列中。

两种方式的代码如下：
```java
public class BFSShortedPath {
    public static void main(String[] args) {
        char[][] graph = {
                { '*', 'S', '*', '#' },
                { '#', '#', '#', '*' },
                { '*', '*', '#', '*' },
                { '*', '#', '#', '*' },
                { 'T', '#', '*', '*' }
        };
        System.out.println(bfs_ref(graph, 0, 1));
        find(graph);
        System.out.println(Arrays.deepToString(graph));
    }

    public static void find(char[][] graph) {
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[0].length; j++) {
                // 找到起点
                if(graph[i][j] == 'S') {
                    bfs(graph, i, j);
                }
            }
        }
    }

    public static void bfs(char[][] graph, int i, int j) {
        Queue<List<Integer>> queue = new ArrayDeque<>();
        queue.offer(Arrays.asList(i, j));
        graph[i][j] = '0';
        int x = i, y = j;
        while(!queue.isEmpty()) {
            List<Integer> current = queue.poll();
            x = current.getFirst();
            y = current.getLast();
            if(x + 1 < graph.length && (graph[x + 1][y] == '#' || graph[x + 1][y] == 'T')) {
                graph[x + 1][y] = (char) (1 + graph[x][y]);
                queue.offer(Arrays.asList(x + 1, y));
            }
            if(x - 1 >= 0 && (graph[x - 1][y] == '#' || graph[x - 1][y] == 'T')) {
                graph[x - 1][y] = (char) (1 + graph[x][y]);
                queue.offer(Arrays.asList(x - 1, y));
            }
            if(y + 1 < graph[0].length && (graph[x][y + 1] == '#' || graph[x][y + 1] == 'T')) {
                graph[x][y + 1] = (char) (1 + graph[x][y]);
                queue.offer(Arrays.asList(x, y + 1));
            }
            if(y - 1 >= 0 && (graph[x][y - 1] == '#' || graph[x][y - 1] == 'T')) {
                graph[x][y - 1] = (char) (1 + graph[x][y]);
                queue.offer(Arrays.asList(x, y - 1));
            }
        }
        // 此时 graph[x][y] 处的值就是答案
        //System.out.println(graph[x][y]);
    }

    public static int bfs_ref(char[][] graph, int i, int j) {
        boolean[][] visited = new boolean[graph.length][graph[0].length];
        // 四个方向
        int[][] directors = {{ -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 }};
        // S 到可达点的路程
        int[][] result = new int[graph.length][graph[0].length];

        Queue<List<Integer>> queue = new ArrayDeque<>();
        queue.offer(Arrays.asList(i , j));
        result[i][j] = 0;
        visited[i][j] = true;
        while(!queue.isEmpty()) {
            List<Integer> current = queue.poll();
            int x = current.getFirst();
            int y = current.getLast();
            // 遍历四个方向
            for(int d = 0; d < 4; d++) {
                int nextX = x + directors[d][0];
                int nextY = y + directors[d][1];
                // 判断是否合法
                if(nextX >= 0 && nextX < graph.length && nextY >= 0 && nextY < graph[0].length
                        && graph[nextX][nextY] != '*' && !visited[nextX][nextY]) {
                    result[nextX][nextY] = result[x][y] + 1;
                    visited[nextX][nextY] = true;
                    // 若是出口，直接返回，若不是，
                    if(graph[nextX][nextY] == 'T') {
                        return result[nextX][nextY];
                    } else {
                        queue.offer(Arrays.asList(nextX, nextY));
                    }
                }
            }
        }
        return -1;
    }
}
/* output
7
[[*, 0, *, #], [2, 1, 2, *], [*, *, 3, *], [*, 5, 4, *], [7, 6, *, *]]  */
```
时间复杂度和空间复杂度都是 $O(n^2)$。

## Dijkstra 算法
Dijkstra 算法，又叫狄克斯特拉算法，是从一个顶点到其余各顶点的最短路径算法，解决的是有向图中单源最短路径问题。迪杰斯特拉算法主要特点是以起始点为中心向外层层扩展，直到扩展到终点为止。其实也是类似广度优先搜索算法。

该算法的核心思想是贪心策略，步骤如下：
- 初始状态：设置一个集合 S，用于存放已经找到最短路径的点（初始只有起点）。设定起点到各个点的最短距离 `dist[]`，起点到自己的距离为 0，其它点为无穷大。
- 贪心选择：每次从未处理的点中，选择一个当前距离最小的点 u（也就是 `dist[u]` 最小），加入集合 S。
- 更新路径：对于每个从 u 出发能到达的相邻点 v，如果经过 u 到 v 的路径比原来记录的 `dist[v]` 更短，就更新 `dist[v]`。
- 重复步骤 2 和 3，直到所有点都被处理过，或者找不到更短路径为止。

使用双层循环的版本如下：
```java
public static int[] dijkstra(int start, int[][] graph) {
    int len = graph.length;
    if(start < 0 || start >= len)
        return null;
    boolean[] visited = new boolean[len];
    // 存储最短距离
    int[] result = new int[len];
    // 初始化边，若为0，设置为无穷大
    for(int i = 0; i < len; i++) {
        for(int j = i + 1; j < len; j++) {
            if(graph[i][j] == 0) {
                graph[i][j] = Integer.MAX_VALUE;
                graph[j][i] = Integer.MAX_VALUE;
            }
        }
    }
    // 初始化result
    System.arraycopy(graph[start], 0, result, 0, result.length);
    visited[start] = true;
    // 遍历其余节点
    for(int i = 0; i < len; i++) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        // 找起点到其他节点的最短距离的节点
        for(int j = 0; j < len; j++) {
            if(!visited[j]) { // 从未被访问的节点中找最短距离的节点
                if(result[j] < min) {
                    min = result[j];
                    minIndex = j;
                }
            }
        }
        if(minIndex != -1)
            visited[minIndex] = true;
        // 更新最短距离
        for(int x = 0; x < len; x++) {
            if(!visited[x]) {
                if(graph[minIndex][x] != Integer.MAX_VALUE && graph[minIndex][x] + result[minIndex] < result[x]) {
                    result[x] = graph[minIndex][x] + result[minIndex];
                }
            }
        }
    }
    return result;
}
```
由于每次都需要遍历查找到最短距离的节点，所以可以使用优先级队列进一步优化，如下：
```java
public static int[] dijkstraWithPq(int start, int[][] graph) {
    int n = graph.length;
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start] = 0;

    // 优先队列，按照距离从小到大排列
    PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
    queue.offer(new int[]{start, 0});
    boolean[] visited = new boolean[n];

    while(!queue.isEmpty()) {
        int[] curr = queue.poll();
        int u = curr[0];
        if(visited[u]) continue;
        visited[u] = true;

        for(int i = 0; i < n; i++) {
            if(!visited[i] && graph[u][i] > 0) {
                int newDist = dist[u] + graph[u][i];
                if(newDist < dist[i]) {
                    dist[i] = newDist;
                    queue.offer(new int[] {i, newDist});
                }
            }
        }
    }

    return dist;
}
```

## Floyd 算法
Floyd 算法也是一种用于寻找给定的加权图中顶点间最短路径的算法。核心思想是以每一个点作为中转点，尝试优化任意两点之间的路径。（可以有负权边，但不能有负权回路。而 Dijkstra 算法只能处理正权图）

该算法主要做三重循环，最外一层表示枚举中转点，里面的两层循环其实意味着任何两个节点 A 和 B，随着这个中转点 C 的加入，「A 到 C 的距离 + C 到 B 的距离」 可能更小，如果更小就更新：「A 到 B 的距离 = A 到 C 的距离 + C 到 B 的距离」，这种做法挺暴力的，但是简单易懂，而且求出来的是所有节点之间的最短路径。

实现如下：
```java
public class Floyd {
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[][] graph = {
                { 0, 11, 4, INF },
                { 1, 0, 23, 6 },
                { 56, 5, 0, 7 },
                { INF, 34, 11, 0 },
        };
        int[][] dist = floyd(graph);
        for (int[] ints : dist) {
            System.out.println(Arrays.toString(ints));
        }
    }

    public static int[][] floyd(int[][] graph) {
        int n = graph.length;
        // 对于多维基本类型数组不能直接Array.copyOf()
        int[][] dist = new int[n][];
        for(int i = 0; i < n; i++) {
            dist[i] = Arrays.copyOf(graph[i], graph[i].length);
        }
        for(int k = 0; k < n; k++) { // 遍历中转点
            for(int i = 0; i < n; i++) { // 起点
                for(int j = 0; j < n; j++) { // 终点
                    // 只要有一段不可达，就是不可达，否则计算途径新加入的节点的路径
                    int newDist = (dist[i][k] == INF || dist[k][j] == INF) ? INF : dist[i][k] + dist[k][j];
                    if(newDist < dist[i][j]) {
                        dist[i][j] = newDist;
                    }
                }
            }
        }
        return dist;
    }
}
/* output
[0, 9, 4, 11]
[1, 0, 5, 6]
[6, 5, 0, 7]
[17, 16, 11, 0]  */
```
该算法简单，但时间复杂度为 $O(n^3)$，求出来的是所有节点之间的最短距离。

## Bellman-Ford 算法
Bellman-Ford 与 Dijkstra 一样是解决单源最短路径问题，比 Dijkstra 慢，但是它能处理负权边。Bellman-Ford 算法通过反复对所有边进行“松弛”操作，在 V-1 次迭代中逐步逼近最短路径（V 是节点数，最短路径最多包含 V-1 条边），最终得出从源点到其他所有点的最短路径距离。步骤如下：
1. 初始化，设源点为 S，从 S 到自己的距离设为 0，其余所有点的距离设为无穷大。
2. 松弛操作，对每一条边 `(u, v, w)`，如果 `dist[u] + w < dist[v]`，就更新 `dist[v] = dist[u] + w`。即如果找到更短的路径就更新。
3. 重复松弛操作 V-1 次，V 是图中顶点的个数。因为最短路径最多包含 V-1 条边，执行 V-1 次松弛就能保证找到所有从源点出发的最短路径。
4. 检测负权环，再进行一次全图松弛操作，如果还能更新某个点的距离，说明存在负权环。

代码实现如下：
```java
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
```
时间复杂度：O(V × E)。

## 最小生成树 Prim
一个无向连通图的生成树是包含图中所有顶点的树（无环连通子图），需使用恰好 n-1 条边。最小生成树（MST）是所有生成树中，边的权重之和最小的那个。

若图中所有边权互异，则 MST 唯一；否则可能存在多个 MST，但是他们的权值之和是一样的。

通常生成最小生成树常用的算法有两种，一种是 Kruskal 算法，另一种是 Prim 算法。

Prim 算法是以图的顶点为基础，从一个初始顶点开始，寻找触达其他顶点权值最小的边，并把该顶点加入到已触达顶点的集合中。当全部顶点都加入到集合时，算法结束。它的本质是贪心算法，总是优先加入最短的可达的点。

实现如下：
```java
public static void main(String[] args) {
    int[][] matrix = new int[][] {
            { 0, 2, 2, 9 },
            { 2, 0, 2, 4 },
            { 2, 2, 0, 1 },
            { 9, 4, 1, 0 },
    };
    System.out.println(Arrays.toString(prim(matrix)));
}
public static int[] prim(int[][] graph) {
    int n = graph.length;
    List<Integer> visited = new ArrayList<>();
    // 任选一个点当根节点
    visited.add(0);
    int[] parent = new int[n];
    parent[0] = -1; // 首节点无父节点
    int start = 0, end = 0, val;
    // 直到所有节点都加入
    while(visited.size() < n) {
        val = Integer.MAX_VALUE;
        // 遍历已经加入的节点
        for(Integer node : visited) {
            for(int i = 0; i < n; i++) {
                // 从未访问过的节点中找路径最短的
                if(!visited.contains(i)) {
                    if(graph[node][i] < val) {
                        start = node;
                        end = i;
                        val = graph[node][i];
                    }
                }
            }
        }
        // 加入该点，并保存它的父节点
        visited.add(end);
        parent[end] = start;
    }
    return parent;
}
```
这里每次都遍历查找最小的边，因此理论时间复杂度 `O(n^3)`。其中 ArrayList 的 contains 操作时间复杂度是 O(n)，可以使用 boolean 数组代替该操作，还可以通过另一 key 数组记录每个节点到已访问集合的最小边权，避免每次遍历所有已访问节点。这样每次循环直接选择未访问节点中 key 值最小的节点，减少不必要的遍历。

如果使用邻接表表示图，且是稀疏图时，使用优先级队列实现的 Prim 算法更清晰和高效。定义的邻接表 `List<List<int[]>> graph` 中每个 `graph.get(u)` 是一个列表，里面是形如 `new int[]{v, weight}` 的数组，表示从 u 到 v 有一条权重为 weight 的边。执行步骤如下：
1. 维护一个 `visited[]` 标记哪些节点已加入 MST
2. 使用 `PriorityQueue<int[]> pq` 按照边权升序存储候选边，每个元素是 `new int[]{from, to, weight}`
3. 每次从优先队列中取最小边，如果目标节点未访问，则加入 MST：
   1. 标记目标节点 `visited[to] = true`
   2. 更新 `parent[to] = from`
   3. 把新节点的邻接边加入优先队列

实现如下：
```java
public static void primWithAdjList() {
    int n = 4;
    List<List<int[]>> graph = new ArrayList<>();
    for(int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
    }

    // 添加边：双向图
    addEdge(graph, 0, 1, 2);
    addEdge(graph, 0, 2, 2);
    addEdge(graph, 0, 3, 9);
    addEdge(graph, 1, 2, 2);
    addEdge(graph, 1, 3, 4);
    addEdge(graph, 2, 3, 1);

    System.out.println(Arrays.toString(find(graph, n)));
}

private static void addEdge(List<List<int[]>> graph, int u, int v, int weight) {
    graph.get(u).add(new int[]{v, weight});
    graph.get(v).add(new int[]{u, weight});
}

public static int[] find(List<List<int[]>> graph, int n) {
    boolean[] visited = new boolean[n];
    int[] parent = new int[n];
    // {from, to, weight} 其中 weight 只用于排序
    PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
    // 任选根节点
    parent[0] = -1;
    for(int[] neighbor : graph.get(0)) {
        queue.offer(new int[]{0, neighbor[0], neighbor[1]});
    }
    visited[0] = true;

    while(!queue.isEmpty()) {
        int[] curr = queue.poll();
        int from = curr[0], to = curr[1];

        if(visited[to]) continue;

        visited[to] = true;
        parent[to] = from;
        for(int[] neighbor : graph.get(to)) {
            if(!visited[neighbor[0]])
                queue.offer(new int[]{to, neighbor[0], neighbor[1]});
        }
    }

    return parent;
}
```
这时优化的 Prim 算法时间复杂度 O(VlogE)。

## 最小生成树 Kruskal
Kruskal 算法的基本思想是以边为主导地位，始终选择当前可用的最小边权的边（可以直接使用快排）。每次选择边权最小的边连接两个节点是 kruskal 的规则，并实时判断两个点之间有没有间接联通：
1. 取出所有的边，对边进行排序（从小到大升序）。
2. 判断边是否符合要求，判断边上的两个点是否来自于同一个集合（需要用到并查集），如果属于同一个集合说明已经联通，不用添加。
3. 将需要添加的边加到最小生成树边的集合中。
4. 输出这个集合。

这里的并查集，指同一棵树上的节点，势必都会有相同的根节点，所以只要查找到根节点是同一个节点，那么就是同一个集合的节点。说明已经联通，不必再添加该条边。

带与不带路径压缩的两个实现如下：
```java
public class Kruskal {
    public static void main(String[] args) {
        int n = 4;
        List<int[]> graph = new ArrayList<>();
        graph.add(new int[]{0, 1, 2});
        graph.add(new int[]{0, 2, 2});
        graph.add(new int[]{0, 3, 9});
        graph.add(new int[]{1, 2, 2});
        graph.add(new int[]{1, 3, 4});
        graph.add(new int[]{2, 3, 1});
        graph.sort(Comparator.comparingInt(a -> a[2]));

        kruskal_ref(graph, n);
        System.out.println(Arrays.toString(kruskal(graph, n)));
    }

    public static void kruskal_ref(List<int[]> graph, int n) {
        int sum = 0;
        int[] parent = new int[n]; // 这里的parent并代表生成树结构
        Arrays.fill(parent, -1);

        for (int[] curr : graph) {
            int rootStart = findRoot(parent, curr[0]);
            int rootEnd = findRoot(parent, curr[1]);

            if (rootStart != rootEnd) {
                parent[rootStart] = rootEnd; // 两个根节点合并
                sum += curr[2];
                System.out.println(curr[0] + " <- " + curr[1]);
            }
        }
        System.out.println(sum);
    }

    // 并查集找根节点，没有路径压缩
    private static int findRoot(int[] parent, int index) {
        while (parent[index] >= 0)
            index = parent[index];
        return index;
    }

    public static int[] kruskal(List<int[]> graph, int n) {
        int sum = 0;
        int[] parents = new int[n]; // 这里的parent代表生成树结构
        Arrays.fill(parents, -1);
        UnionFind uf = new UnionFind(n);

        for(int[] edge : graph) {
            int from = edge[0], to = edge[1];
            int weight = edge[2];
            if(uf.find(from) != uf.find(to)) {
                uf.union(from, to);
                parents[to] = from;
                sum += weight;
            }
        }
        return parents;
    }
}

// 并查集，带路径压缩，按秩合并
class UnionFind {
    int[] parent;
    int[] rank;

    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for(int i = 0; i < n; i++)
            parent[i] = i;
    }

    int find(int index) {
        if(parent[index] != index)
            index = find(parent[index]);
        return index;
    }

    void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if(rootX == rootY) return;

        if(rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if(rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}
/* output
2 <- 3
0 <- 1
0 <- 2
5
[-1, 0, 0, 2]  */
```
将边按权值进行快速排序：O(ElogE)，选择边，最多选择 E 次，每次需要并查集操作，所以总体时间复杂度是 O(ElogE)。


