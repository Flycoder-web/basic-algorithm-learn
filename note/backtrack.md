# 回溯
回溯法，可理解为试探性搜索方法，在搜索到某一步的时候，如果发现不能满足条件，那么就退回到上一步，在上一步重新选择。它本质上是对暴力解法的一种优化，一种尝试失败则提前剪枝的算法。

回溯法是以深度优先方式来搜索问题的解，里面的每一步可以理解为一个结点，这些步骤串起来就是一棵树，也就是解空间树。

当搜索解空间树中的任何一个结点的时候，判断该结点是不是包含问题的解：
- 如果不包含，那么就把当前的结点以及剩下的节点步骤全部抛弃（也称为剪枝），然后往上一层的结点回溯，也就是退回上一步重新选择（之前的选择走不通）。
- 如果包含，说明当前结点是可能获取到解的，继续进入下一层子树进行深度优先搜索。

一般回溯法有两种做法：递归和递推，递归的设计思路简单但效率不高，递推的算法设计较复杂，但效率高。

## 八皇后问题
八皇后问题是要在8x8的国际象棋棋盘上放置八个皇后，使得它们互不攻击，即任何两个皇后都不能在同一行、同一列，或者同一斜线上，问一共有多少种摆法。

通常使用回溯法解决，步骤如下：
1. 按行放置，从第 0 行开始逐行放置皇后。
2. 尝试在该行的每一列放置皇后，检查是否符合规则：
   1. 是否与已有的皇后在同一列
   2. 是否在同一条对角线上
3. 如果满足要求，则递归处理下一行。
4. 如果当前行没有合法的放置方案，则回溯到上一行，尝试改变上一行皇后的位置。

代码实现如下：
```java
public class Nqueens {
    public static void main(String[] args) {
        int n = 4;
        int[][] maps = new int[n][n];
        find_origin(maps, 0);
        find(n);
    }

    // 未优化的二维数组版回溯
    public static void find_origin(int[][] maps, int row) {
        // 所有行都放置完毕，找到一个解
        if(row == maps.length) {
            printMaps(maps);
            return;
        }
        // 对当前行，尝试每一列
        for(int col = 0; col < maps.length; col++) {
            // 验证是否可以放置
            if(isValid(maps, row, col)) {
                maps[row][col] = 1; // 放置
                find_origin(maps, row + 1); // 下一行递归
                maps[row][col] = 0; // 回溯
            }
        }
    }

    public static void printMaps(int[][] maps) {
        System.out.println(Arrays.deepToString(maps));
    }

    private static boolean isValid(int[][] maps, int row, int col) {
        // 对前面行检查
        for(int i = 0; i < row; i++) {
            // 是否列冲突
            if(maps[i][col] == 1)
                return false;
            // 行差
            int diff = row - i;
            // 斜线上元素的行差等于列差绝对值
            if(col - diff >= 0 && maps[i][col - diff] == 1) // 左上角斜线
                return false;
            if(col + diff < maps.length && maps[i][col + diff] == 1) // 右上角斜线
                return false;
        }
        return true;
    }

    // 优化后的一维数组版
    public static void find(int n) {
        int[] board = new int[n];
        Arrays.fill(board, -1); // 初始填充为 -1
        backtrack(board, 0);
    }

    public static void backtrack(int[] board, int row) {
        if(row == board.length) {
            System.out.println(Arrays.toString(board));
            return;
        }
        for(int col = 0; col < board.length; col++) {
            if(isSafe(board, row, col)) {
                board[row] = col;
                backtrack(board, row + 1);
                board[row] = -1;
            }
        }
    }

    private static boolean isSafe(int[] board, int row, int col) {
        for(int i = 0; i < row; i++) {
            // 是否位于同一列
            if(board[i] == col)
                return false;
            // 是否位于同一斜线上，即行差绝对值等于列差绝对值
            if(Math.abs(board[i] - col) == row - i)
                return false;
        }
        return true;
    }
}
/* output
[[0, 1, 0, 0], [0, 0, 0, 1], [1, 0, 0, 0], [0, 0, 1, 0]]
[[0, 0, 1, 0], [1, 0, 0, 0], [0, 0, 0, 1], [0, 1, 0, 0]]
[1, 3, 0, 2]
[2, 0, 3, 1]  */
```
若是 n 皇后问题，空间复杂度为 $O(n)$，这里使用剪枝（`isSafe()` 返回 true 才进行递归）情况下，实际的搜索节点大约是指数级的，接近 $O(c^N)$，其中 c 介于 1.5 到 2 之间，无剪枝，最坏情况下为 $O(N!)$。

## 装载问题
假设现在有一批共 n 个集装箱要装上一艘载重量为 c 的轮船，其中集装箱 i 的重量为 Wi，找出一种最优装载方案，将轮船尽可能装满，即在装载体积不受限制的情况下，将尽可能重的集装箱装上轮船。

这个问题是 0-1 背包问题的一个变种，目标是使得装上轮船的总重量尽可能接近但不超过载重量 c，可以使用动态规划或回溯法，这里先实现回溯法。

需要定义一个 `boolean[] visited` 数组表示货物是否被访问过，sum 表示当前装载的货物总的重量，result 为最终的最大总重量。

遍历每一件货物，尝试将当前货物加上总重量，如果不大于最大的重量，并且加上之后的总重量大于保存的最大实际载重，那么就更新最大实际载重，否则直接返回。接着，将当前的货物的状态置为已经访问过，执行对下一个货物的判断，执行完之后，将当前货物置为不被访问过，相当于回溯，退回上一步。实现代码如下：
```java
public class ShipLoad {
    private static int result = 0;

    public static void main(String[] args) {
        int[] weights = {18, 7, 25, 36};
        int c = 80;
        System.out.println(find(weights, c));
    }

    public static int find(int[] weights, int c) {
        result = 0;
        backtrack(weights, c, 0, 0, new boolean[weights.length]);
        return result;
    }

    public static void backtrack(int[] weights, int c, int sum, int i, boolean[] visited) {
        for(; i < weights.length; i++) {
            if(!visited[i]) {
                // 尝试加上当前货物
                int load = sum + weights[i];
                if(load <= c) {
                    // 与保存的最大装载量对比更新
                    if(load > result)
                        result = load;
                } else continue; // 超过了就跳过当前货物
                visited[i] = true; // 当前货物已装过
                backtrack(weights, c, load, i + 1, visited); // 递归后面的货物
                visited[i] = false; // 去掉当前货物，递归其他的货物
            }
        }
    }
}
/* output
79  */
```
空间复杂度是 O(n)，时间复杂度是指数级别 O(n!)。

该回溯法的思路是通过 for 循环遍历所有可能的组合，也可以使用 0-1 背包中的朴素回溯方法，即“选或不选”递归。思路是先排序（大数优先），减少不必要搜索，然后按固定的“二叉决策树”深度优先搜索：
```java
public class ShipLoad {
    private static int best = 0;

    public static void main(String[] args) {
        int[] weights = {18, 7, 25, 36};
        int c = 80;
        System.out.println(find(weights, c));
    }

    public static int find(int[] weights, int c) {
        best = 0;
        // 先对weights降序排序，加快搜索
        weights = IntStream.of(weights)
                .boxed()
                .sorted((w1, w2) -> w2 - w1)
                .mapToInt(Integer::intValue)
                .toArray();
        dfs(weights, c, 0, 0);
        return best;
    }
    // 0-1背包的朴素回溯方法（“选或不选“递归）
    public static void dfs(int[] weights, int c, int current, int i) {
        if(current > c)
            return; // 若当前总重过载，剪枝
        if(current > best)
            best = current; // 若当前总重大于历史最佳，则更新
        if(i == weights.length)
            return; // 所有集装箱已考虑
        // 选择装载当前集装箱
        dfs(weights, c, current + weights[i], i + 1);
        // 选择不装载当前集装箱
        dfs(weights, c, current, i + 1);
    }
}
```
在 `dfs()` 中，先剪枝，避免后续无意义的计算，再更新 best，确保 current 未超载的情况下才更新，最后检查是否遍历完，如果所有集装箱都考虑过了，就返回。这样的顺序保证了正确性和效率的平衡。

最后，还可以使用 0-1 背包问题的动态规划算法，这里使用优化后的一维数组，定义 `dp[j]` 表示载重量不超过 j 时的最大装载重量：
- 初始化：`dp[j] = 0`；
- 遍历每个集装箱重量 Wi，逆序更新 `dp[j]`：`dp[j] = max(dp[j], dp[j - Wi] + Wi)` （如果 `j >= Wi`）；
- 结果是 dp[c]，即不超过 c 的最大装载重量。

实现如下：
```java
public static int dpOneDim(int[] weights, int c) {
    int[] dp = new int[c + 1];
    for(int weight : weights) {
        for(int j = c; j >= weight; j--) // 逆序遍历
            dp[j] = Math.max(dp[j], dp[j - weight] + weight);
    }
    return dp[c];
}
```
时间复杂度 O(nc)，适合 c 不太大的情况。

## 0-1 背包
前面已经介绍了 0-1 背包问题的动态规划以及递归+备忘录的解法，现在介绍回溯法。

对每一件物品都尝试性加入，在加入之前，我们需要判断是否被添加过，加入后的总重量会不会超重，如果符合条件，则判断加入的总价值是否为当前最大，符合则保存最大的价值，反之，跳过当前物品（相当于减枝）。然后尝试对后面的物品进行相同的判断，处理完成之后，需要将该物品拿出来（相当于不放该物品，回溯的做法），接着判断后续的物品。实现如下：
```java
class Item {
    int weight;
    int value;

    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}

public class Knapsack01 {
    private static int maxValue = 0;

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(10, 32));
        items.add(new Item(5, 12));
        items.add(new Item(8, 4));
        items.add(new Item(3, 32));
        int capacity = 13;
        System.out.println(findMaxValue(items, capacity));
    }

    public static int findMaxValue(List<Item> items, int c) {
        maxValue = 0;
        backtrack_origin(items, c, 0, 0, 0, new boolean[items.size()]);
        return maxValue;
    }

    public static void backtrack_origin(List<Item> items, int c, int sumValue, int sumWeight, int i, boolean[] visited) {
        for(; i < items.size(); i++) {
            Item item = items.get(i);
            if(!visited[i]) {
                int currentWeight = sumWeight + item.weight;
                int currentValue = sumValue + item.value;
                if(currentWeight <= c) {
                    if(currentValue > maxValue)
                        maxValue = currentValue;
                } else continue; // 注意，必须得continue跳过当前，不能直接return跳过所有
                visited[i] = true;
                backtrack_origin(items, c, currentValue, currentWeight, i + 1, visited);
                visited[i] = false;
            }
        }
    }
}
/* output
64  */
```
时间复杂度是 $O(n^n)$。

其实可以不用 visited 数组，优化后的回溯法如下：
```java
public static void backtrack(List<Item> items, int c, int sumValue, int sumWeight, int i) {
    if(sumWeight > c) return; // 剪枝：重量超过背包容量，立即返回
    // 更新最大价值
    if(sumValue > maxValue)
        maxValue = sumValue;

    // 从当前索引 i 开始尝试选取物品
    for(int j = i; j < items.size(); j++) {
        Item item = items.get(j);
        backtrack(items, c, sumValue + item.value, sumWeight + item.weight, j + 1);
    }
}
```
for 循环用于枚举物品，但递归时 j + 1，避免重复选择。每次递归考虑两种情况：
- 选当前物品：递归调用 backtrack，更新 sumValue 和 sumWeight。
- 不选当前物品：递归自然跳过当前 j，因为 j + 1 传入。

剪枝优化，如果 sumWeight > c，直接 return，避免无效计算。

## 旅行售货员问题
某售货员要到若干城市去推销商品，已知各城市之间的路程（或旅费）。他要选定一条从驻地出发，经过每个城市一次，使总的路程（或总旅费）最小。（假设不回到驻地）

使用使用二维数据存储每两个节点之间的路程，比如 `distances[0][2]` 和 `distances[2][0]` 都表示节点 1 和节点 3 之间的距离。除此外，还要存储城市的数量、路程最小值、当前路程值、被访问的城市。

从第 0 个城市开始，判断步数是否走完所有地方，如果满足并且路径路程比当前最小路程还小，那么就保存当前的路径。接着执行下一步，遍历后续的地方，如果没有被访问过，并且可达，那么就尝试走到该节点，递归遍历，递归完成后，需要回退上一步，接着执行下一个节点。代码如下：
```java
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
        if(step >= count) { // 是否遍历完所有城市，假设不回到原点
            if(currDistance < minDistance) {
                // 更新最短路程
                minRoutes = Arrays.copyOf(currRoutes, count);
                minDistance = currDistance;
                System.out.println("min distance: " + minDistance);
            }
            // 若想回到原点，只需加上回程即可，使用temp不要直接加到currDistance全局变量上
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
/* output
min distance: 55
min distance: 21
min distance: 19
[0, 3, 1, 2]
```
为了不必要的多余遍历，当当前路程值加上到另一节点的路程值大于最小路程值时，直接跳过，进行剪枝操作。注意，这里使用了 step 变量指示走了多少个城市，当 step 等于城市数时，说明遍历结束。

另外，如果不需要回到起始点，也可以直接使用 `visited[]` 数组判断是否还有没访问的城市，如下：
```java
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
        // ... distances 初始化

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
```

## 自动走迷宫
输入一个迷宫的地图，用程序判断迷宫是否有解，如果有解的话，需要输出解的路径图。

给定如下迷宫，1 表示是墙，不可以走，0 表示可以走：
```
0 0 0 0 1
1 0 1 0 1
1 1 0 0 0
1 0 0 1 0
1 0 1 1 0
```
在上下左右都可以走的情况下，我们会选择一个方向来走，每次抉择都是如此，遇到前面走不通，我们就退回到上一步尝试其他方向。如果一个点的四个方向都尝试过了，都不行，还不能到达终点，那么说明这条路是行不通的。

需要用一个同样大小的数组来保存每一个点是否被访问过，用栈来保存访问路径。

首先尝试将 (0, 0) 位置加入堆栈，然后循环判断栈是不是不为空，且没有到达终点：分别尝试上下左右四个方向上的元素是否符合要求（该元素存在、没有被访问过、不是墙），若符合要求，就放入堆栈，并标识已访问过。若都不符合，那么就把堆栈第一个元素弹出，也就是相当于回溯到上一步，此时如果堆栈不为空，那么更新当前位置为栈顶元素的坐标，继续循环。

最后判断堆栈是否为空，如果为空，说明没有解，否则把堆栈反转，从头部取出元素，打印路径。

实现如下：
```java
class Position {
    int row;
    int col;
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    @Override public String toString() {
        return "(" + row + "," + col + ")";
    }
}

public class Maze {
    int[][] maze;
    Deque<Position> stack;
    boolean[][] visited;
    int row;
    int col;

    public Maze(int row, int col) {
        this.row = row;
        this.col = col;
        maze = new int[row][col];
        stack = new ArrayDeque<>();
        visited = new boolean[row][col];
    }

    public static void main(String[] args) {
        Maze m = new Maze(5, 5);
        m.maze = new int[][]{
                {0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 1, 0, 0, 0},
                {1, 0, 0, 1, 0},
                {1, 0, 1, 1, 0}
        };
        System.out.println(m.hasPath());
        m.visited = new boolean[5][5];
        m.findAllPath();
    }

    public boolean hasPath() {
        int i = 0;
        int j = 0;
        stack.push(new Position(i, j));
        visited[i][j] = true;

        while (!stack.isEmpty() && !((i == row - 1) && (j == col - 1))) {
            if(j + 1 < col && !visited[i][j + 1] && maze[i][j + 1] == 0) {
                stack.push(new Position(i, j + 1));
                visited[i][j + 1] = true;
            } else if(i + 1 < row && !visited[i + 1][j] && maze[i + 1][j] == 0) {
                stack.push(new Position(i + 1, j));
                visited[i + 1][j] = true;
            } else if(j - 1 >= 0 && !visited[i][j - 1] && maze[i][j - 1] == 0) {
                stack.push(new Position(i, j - 1));
                visited[i][j - 1] = true;
            } else if(i - 1 >= 0 && !visited[i - 1][j] && maze[i - 1][j] == 0) {
                stack.push(new Position(i - 1, j));
                visited[i - 1][j] = true;
            }
            Position top = stack.peek();
            if(top != null && top.row == i && top.col == j) {
                stack.pop();
            }
            if(!stack.isEmpty()) {
                i = stack.peek().row;
                j = stack.peek().col;
            } else break;
        }
        if(stack.isEmpty())
            return false;
        else {
            stack.reversed().forEach(p -> {
                System.out.print("(" + p.row + "," + p.col + ")" + "->");
            });
        }
        return true;
    }
}
/* output
(0,0)->(0,1)->(0,2)->(0,3)->(1,3)->(2,3)->(2,4)->(3,4)->(4,4)->true
```
这里借助了双向链表以及二维数组，空间复杂度为 $O(N^2)$，时间复杂度是 $O(n^2)$，之前都是使用递归来实现回溯，这里用堆栈来替换了递归，时间复杂度有所减低。

如果想要打印出所有的迷宫出口路径，需要先定义出口的条件，比如“出口在最后一行”，然后使用深度优先搜索：
```java
// 深度优先搜索找所有可到达“出口”的路径
public void findAllPath() {
    List<Position> path = new ArrayList<>();
    dfs(0, 0, path);
}

private void dfs(int i, int j, List<Position> path) {
    if(i < 0 || i > row - 1 || j < 0 || j > col - 1 || visited[i][j] || maze[i][j] == 1)
        return;

    path.add(new Position(i, j));
    visited[i][j] = true;

    if(isExit(i, j)) { // 使用给定的出口规则
        System.out.println(path);
    } else {
        dfs(i - 1, j, path);
        dfs(i + 1, j, path);
        dfs(i, j - 1, path);
        dfs(i, j + 1, path);
    }

    // 回溯
    path.removeLast();
    visited[i][j] = false;
}

// 假设出口在最后一行
boolean isExit(int i, int j) {
    return (i == row - 1) && !(i == 0 && j == 0);
}
```

## 图的 m 着色
给定无向连通图 G 和 m 种不同的颜色。用这些颜色为图 G 的各顶点着色，每个顶点着一种颜色。如果有一种着色法使 G 中每条边的 2 个顶点着不同颜色，则称这个图是 m 可着色的。图的 m 着色问题是对于给定图 G 和 m 种颜色，找出所有不同的着色法。

需要找到所有可能的颜色分配方案，使得相邻的两个顶点颜色不同。可以使用回溯算法来尝试所有可能的颜色组合，并通过剪枝来排除无效的路径，从而高效地找到所有合法的着色方案。

这里使用邻接矩阵表示图，使用一个数组 colored 来记录每个顶点的颜色，初始时所有顶点颜色为 -1，表示未着色。

从第一个节点开始进行尝试，对它进行所有可能颜色的选择。在选择某种颜色时，需要确保与该节点相邻的所有节点中没有已经使用这种颜色的。如果满足这个条件，就可以继续对下一个节点进行相同的操作：为其选择一个在相邻节点中未被使用的颜色。如此递归进行，直到所有节点都被成功着色，每完成一次这样的过程，就是一种有效的着色方案。实现如下：
```java
public class MColors_opt {
    int m; // m种颜色
    int n; // n个节点
    int[][] graph; // 邻接矩阵
    int[] colored; // 指示节点涂的颜色
    Set<String> results = new HashSet<>();

    public MColors_opt(int m, int n) {
        this.m = m;
        this.n = n;
        graph = new int[n][n];
        colored = new int[n];
        Arrays.fill(colored, -1);
    }

    public static void main(String[] args) {
        MColors_opt mc = new MColors_opt(4, 5);
        mc.graph = new int[][] {
                {0, 1, 1, 1, 0},
                {1, 0, 1, 1, 0},
                {1, 1, 0, 1, 1},
                {1, 1, 1, 0, 0},
                {0, 0, 1, 0, 0}
        };
        mc.backtrack(0);
        System.out.println(mc.results.size());
    }

    public void backtrack(int i) {
        if(i == n) {
            results.add(Arrays.toString(colored));
            return;
        }
        // 对每一种颜色
        for(int color = 0; color < m; color++) {
            if(isValid(i, color)) {
                colored[i] = color; // 染色
                backtrack(i + 1); // 递归下一个节点
                colored[i] = -1; // 回溯
            }
        }
    }

    private boolean isValid(int node, int color) {
        for(int j = 0; j < node; j++) {
            if(graph[node][j] == 1 && colored[j] == color)
                return false;
        }
        return true;
    }
}
```
backtrack 方法中递归处理每个顶点。当处理到最后一个顶点时，保存当前颜色方案。对于每个顶点，尝试所有可能的颜色，合法时继续递归处理下一个顶点，否则回溯。使用 isValid 方法检查当前顶点 node 是否可以分配颜色 color，遍历所有已处理的相邻顶点（索引小于 node），如果存在相邻顶点颜色相同，则返回 False，否则返回 True。

空间复杂度是 $O(n^2)$，时间复杂度是 $O(m^n)$。

## 猜汉字代表的数字
观察下面的加法算式：
```
      祥 瑞 生 辉
   +  三 羊 献 瑞
----------------------
=  三 羊 生 瑞 气
```
其中相同的汉字代表相同的数字，不同的汉字代表不同的数字。请你写出这8个字各自代表的数字（答案唯一）。

这是经典的回溯法问题，可以给“三羊献瑞祥生辉气”编号 01234567，然后每一个都需要去尝试从 0 到 9，当尝试到最后一个编号的数字时，需要判断等式是否成立。

实现如下：
```java
public class HanziExpression {
    public static void main(String[] args) {
        int[] hanzis = new int[8]; // 分别代表“三羊献瑞祥生辉气”
        find(0, 8, hanzis);
    }

    public static void find(int i, int n, int[] hanzis) {
        if(i == n) {
            if(check(hanzis))
                System.out.println(Arrays.toString(hanzis));
            return;
        }
        for(int num = 0; num < 10; num++) {
            // 对每一位尝试每一个数字
            hanzis[i] = num;
            if(isValid(hanzis, i)) {
                // 如果合法，就继续下一个
                find(i + 1, n, hanzis);
            }
        }
    }

    // 检查第i个字是否合法（数字首不为0，第i个数字在之前未被使用过）
    public static boolean isValid(int[] hanzis, int i) {
        if(i == 0 && hanzis[i] == 0) {
            return false;
        }
        for(int j = 0; j < i; j++) {
            if(hanzis[j] == hanzis[i])
                return false;
        }
        if(i == 4 && hanzis[i] == 0)
            return false;
        return true;
    }

    // 检查这8个子是否满足加法式
    public static boolean check(int[] hanzis) {
        int a = hanzis[4] * 1000 + hanzis[3] * 100 + hanzis[5] * 10 + hanzis[6];
        int b = hanzis[0] * 1000 + hanzis[1] * 100 + hanzis[2] * 10 + hanzis[3];
        int c = hanzis[0] * 10000 + hanzis[1] * 1000 + hanzis[5] * 100 + hanzis[3] * 10 + hanzis[7];
        return a + b == c;
    }
}
/* output
[1, 0, 8, 5, 9, 6, 7, 2]  */
```
