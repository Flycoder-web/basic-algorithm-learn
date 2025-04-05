# 贪心算法
贪心算法（Greedy Algorithm）是一种在每一步选择中都采取当前状态下最优的局部决策，从而希望最终得到全局最优解的算法设计思想。注意，当前的状态不会受到以后的状态/阶段选择的影响，只和当前的状态有关。

贪心算法有效问题的两大特性：贪心选择性质和最优子结构。求解贪心算法的基本思路如下：
- 建立数学模型来描述问题。
- 将求解的问题分解成为若干个小问题。
- 每次对小问题求解，总是得到子问题的局部最优解。
- 将每一次局部最优解合并，就是整体最优解。

贪心算法的缺陷：不能保证最后求得的是最佳解法，不能用来求解最大值或者最小值。

## 找零问题
在给定面额的硬币集合下，如何用最少数量的硬币来找零特定金额。

假设现有 100, 50, 20, 10, 5, 2, 1 面额的硬币，思路是将金额与硬币面额按照从大到小对比，如果金额大于纸币面额，那么就计算该面额纸币的张数，然后剩下的用更小的面额对比即可：
```java
public class ChangeMaking {
    public static void main(String[] args) {
        int[] coins = {100, 50, 20, 10, 5, 2, 1};
        System.out.println(Arrays.toString(changeMaking(coins, 64)));
    }

    // 假设硬币体系满足规范，且逆序排列
    public static int[] changeMaking(int[] coins, int sum) {
        int[] res = new int[coins.length]; // 每个面额的硬币的数量
        for(int i = 0; i < coins.length; i++) {
            if(sum > 0) {
                res[i] = sum / coins[i];
                sum = sum - res[i] * coins[i];
            } else {
                break;
            }
        }
        return res;
    }
}
/* output
[0, 1, 0, 1, 0, 2, 0] */
```
时间复杂度其实只有 $O(k)$，k 表示面额的数量，因为每个面额其实只处理一次即可，对于该场景，每一步都是当前最优的选择，最终结果就是最优的。

## 活动选择问题
给定一组活动，每个活动都有一个开始时间和结束时间，该活动只能在这个时间段内执行。一个人一次只能执行一个活动，目标是选出最多的互不冲突的活动。使用贪心算法的思路是：
- 优先选择结束时间最早的活动，这样可以尽可能地留出更多时间给后续活动。
- 按照结束时间升序排序，然后逐个选择下一个与已选活动不重叠的活动（即其开始时间大于等于前一个已选活动的结束时间）。

实现如下：
```java
class Activity {
    final int start;
    final int end;
    Activity(int start, int end) {
        this.start = start;
        this.end = end;
    }
    @Override
    public String toString() {
        return "(" + start + ", " + end + ")";
    }
}

public class ActivitySelect {
    public static void main(String[] args) {
        List<Activity> list = new ArrayList<>();
        list.add(new Activity(3, 5));
        list.add(new Activity(1, 4));
        list.add(new Activity(5, 7));
        list.add(new Activity(0, 6));
        list.add(new Activity(6, 10));
        list.add(new Activity(3, 8));
        list.add(new Activity(5, 9));
        list.add(new Activity(8, 12));
        list.add(new Activity(8, 11));
        list.add(new Activity(2, 13));
        list.add(new Activity(12, 14));
        boolean[] selected = greedy(list);
        printSelected(selected, list);
    }

    public static boolean[] greedy(List<Activity> activities) {
        boolean[] res = new boolean[activities.size()];
        // 按结束时间排序
        activities.sort((a1, a2) -> a1.end - a2.end);
        // 已选择活动的最后结束时间
        int currentEnd = 0;
        for(int i = 0; i < activities.size(); i++) {
            Activity a = activities.get(i);
            // 当前活动开始时间大于已选择活动的结束时间
            if(a.start > currentEnd) {
                res[i] = true;
                currentEnd = a.end;
            }
        }
        return res;
    }

    public static void printSelected(boolean[] selected, List<Activity> activities) {
        for(int i = 0; i < activities.size(); i++) {
            if(selected[i])
                System.out.print(activities.get(i) + " ");
        }
        System.out.println();
    }
}
/* output
(1, 4) (5, 7) (8, 11) (12, 14)  */
```
排序时间复杂度是 $O(n\log n)$，遍历活动 $O(n)$，总时间复杂度是 $O(n\log n)$，申请了额外的数组空间存储状态，空间复杂度为 $O(n)$。

## 分数背包问题
分数背包问题是 0-1 背包问题 的一个变种，不同之处在于每件物品可以拆分，即可以取其中的一部分，而不仅仅是全拿或不拿。给定 n 个物品，每个物品都有一个 重量 w[i] 和 价值 v[i]，有一个容量为 W 的背包，可以装入部分物品（即可以取 w[i] 的一部分），目标是选择装入背包的物品，使总价值最大。

这比之前的 0-1 背包更加简单，要使总的价值最大，那么我们就需要单位重量价值尽可能的大，也就是每一个包裹的价值/重量的比值要尽可能大。到最后放不进去时，可以选择只放进去一部分。这就是贪心的思想，每一次捡包裹，都是当前最优选择，也就是单位重量价值最高的包裹。代码实现如下：
```java
class Item {
    final int weight;
    final int value;
    final double ratio;
    Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
        ratio = (double) value / weight;
    }
    @Override
    public String toString() {
        return "(" + weight + ", " + value + ", " + ratio + ")";
    }
}

public class KnapsackFraction {
    public static void main(String[] args) {
        int[] weights = {10, 8, 5, 3, 4, 7, 3};
        int[] values = {2, 4, 12, 32, 33, 54, 6};
        int total = 15;
        double maxValue = greedy(weights, values, total);
        System.out.println(maxValue);
    }

    public static double greedy(int[] weights, int[] values, int W) {
        List<Item> items = new ArrayList<>();
        for(int i = 0; i < weights.length; i++) {
            items.add(new Item(weights[i], values[i]));
        }
        items.sort((i1, i2) -> (int)((i2.ratio - i1.ratio) * 100));
        double maxValue = 0;
        for (Item current : items) {
            if (W >= current.weight) {
                // 如果当前物品能完整放入
                W -= current.weight;
                maxValue += current.value;
            } else {
                // 放入部分物品，背包已满，直接退出
                maxValue += W * current.ratio;
                break;
            }
        }
        return maxValue;
    }
}
/* output
121.4 */
```
排序的时间复杂度为 O(nlogn)，遍历时间复杂度为 O(n)，空间复杂度为 O(n)。

## 途中的加油区间
现在假设我们开的车加满油可以行驶 90 千米，旅途中有若干个加油站，给出每个加油站到上一个加油点的距离，在起点时，汽车的油是满的（算第一次加油），每次加满油，可以开 90 千米，那么至少要加多少次油？（如果汽车不能跑完全程，那么返回 -1）。测试数据：
```
加油站距离上一个的距离（最后一个加油站是终点）：{30,40,80,12,78}
加油站个数：5
每次加满油可以跑：90km
```
若想加油次数最少，那么每一次汽车加满油之后，都要尽可能跑到最远的地方再加油（但是车的油量必须大于 0 的时候才能往前面跑）。即加完油之后，以当前的加油站为起点，继续往前面开，开到第一个距离油耗尽最近的加油站。依次循环，直到开完全程。代码如下：
```java
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
/* output
3   */
```
贪心策略，体现在每一次尽量开到最远的加油站，油量支撑不到下一个加油站时，才加油。每一次局部选择贪心，总体就能达到最优的策略。

## Huffman 编码
Huffman 编码，本质上使用了贪心的策略，它要处理的事，将字符编码成二进制存储起来以及将二进制解码成字符串，可以理解为压缩和解压。它之所以拥有这个功能，是因为不同的字母使用的频率不一样，如果都使用相同的位数来存储，是不划算的，所以就使用可变长度的编码，出现频率高的使用较短的位数编码，频率低的使用较长位数编码。

比如小写 a，ASCII 编码中，十进制为 97 ，二进制为 01100001。ASCII 的每一个字符都用 8 个 Bit（1 Byte）编码，假如有 1000 个字符要传输，那么就要传输 8000 个 Bit。如果我们可以让高频的字符用更少的 bit 来表示，那肯定可以减少很多存储空间。

首先需要统计字符串里不同的字符出现的次数，获取它们的频率，使用 `HashMap<Character,Integer>` 表示。

其次，需要定义数据结构，使用二叉树来存储数据，首先定义根节点，紧接着构建树，主要的思想是把所有的字符挂到二叉树的叶子节点上，保证任何一个非叶子节点的左节点出现的频率不大于右节点。

先将所有的字符和频率构建成为节点，放到优先队列里面，保证低频率的节点在前面。每次从队列中弹出两个频率最小的节点，构建成为一个新的父节点，开始出来的作为左子节点，后面的作为右子节点。父节点的字符内容是刚刚弹出来的两个节点的字符相加，频率也是两者相加。然后把父节点加到队列中去，重复以上过程（如果队列里面只剩下一个节点，弹出作为树的根节点）。

得到树结构表示后，可以使用深度优先搜索遍历所有叶节点，从根节点开始，根据遍历路径，向左为0，向右为1，得到每个字符的二进制表示，存储在 `HashMap<Character, String>` 中。进一步就可以得到字符串的二进制表示，即编码。

解码时不仅要有二进制表示，还要有各字符的二叉树结构表示，遍历二进制表示，从根节点开始，直到叶节点，这样就得到了原字符，然后回到根节点，重复该过程得到解码的字符串。

代码实现如下：
```java
class Node {
    Character val = null;
    Node left;
    Node right;
    int freq;
    Node() {}
    Node(Character val, int freq) {
        this.val = val;
        this.freq = freq;
    }
    @Override
    public String toString() {
        return Character.toString(val);
    }
}

public class Huffman {
    public static void main(String[] args) {
        String s = "hello";
        //String s = "aaaa";
        Map<Character, Integer> cMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            if(cMap.containsKey(c))
                cMap.put(c, cMap.get(c) + 1);
            else
                cMap.put(c, 1);
        }
        Node root = buildTree(cMap);
        List<List<Node>> traces = traverse(root);
        Map<Character, String> binaryMap = binaryMap(traces);
        String encodes = encode(s, binaryMap);
        System.out.println(encodes);
        System.out.println(decode(encodes, root));
    }

    public static String encode(String s, Map<Character, String> binaryMap) {
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()) {
            sb.append(binaryMap.get(c));
        }
        return sb.toString();
    }

    public static String decode(String encodes, Node root) {
        StringBuilder sb = new StringBuilder();
        Node current = root;
        // 从根节点开始，0往左走，1往右走，直到叶节点
        for(char c : encodes.toCharArray()) {
            if(c == '0')
                current = current.left;
            else
                current = current.right;
            if(current.left == null && current.right == null) {
                // 到达叶节点，将指针重新指向根节点
                sb.append(current.val);
                current = root;
            }
        }
        return sb.toString();
    }

    public static Node buildTree(Map<Character, Integer> cMap) {
        int n = cMap.size();
        // 最小优先级队列，优先级为频率
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n2 -> n2.freq));
        cMap.forEach((key, value) -> queue.add(new Node(key, value)));
        // 特殊情况，字符串中只出现了一种字符
        if (queue.size() == 1) {
            Node node = queue.poll();
            Node parent = new Node();
            parent.left = node;
            parent.freq = node.freq;
            return parent;
        }
        // 自底向上构建树
        while(queue.size() > 1) {
            Node node = new Node();
            Node x = queue.poll();
            Node y = queue.poll();
            node.left = x;
            node.right = y;
            node.freq = x.freq + y.freq;
            queue.offer(node);
        }
        return queue.poll();
    }

    public static Map<Character, String> binaryMap(List<List<Node>> traces) {
        Map<Character, String> encodeMap = new HashMap<>();
        for(List<Node> path : traces) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < path.size() - 1; i++) {
                if(path.get(i).left == path.get(i + 1))
                    sb.append("0");
                else
                    sb.append("1");
            }
            encodeMap.put(path.getLast().val, sb.toString()); // List.getLast()在 JDK21 加入
        }
        return encodeMap;
    }

    public static List<List<Node>> traverse(Node root) {
        List<List<Node>> traces = new ArrayList<>();
        dfs(root, new ArrayList<>(), traces);
        return traces;
    }

    // 深度优先搜索遍历所有到叶节点的路径
    private static void dfs(Node node, List<Node> path, List<List<Node>> traces) {
        if(node == null)
            return;
        path.add(node);
        if(node.left == null && node.right == null) {
            traces.add(new ArrayList<>(path));
        }
        dfs(node.left, path, traces);
        dfs(node.right, path, traces);
        path.removeLast(); // List.removeLast()在 JDK21 加入
    }
}
```
其实本质上，Huffman 算法利用的是贪心策略，出现频率最高的字符使用最短的编码，这样可以节省存储的空间。在上例子中，01 的字符串实际上是可以使用二进制来表示的，所以计算时，按照二进制计算压缩大小。

## 轻重搭配
n 个同学去动物园参观，原本每人都需要买一张门票，但售票处推出了一个优惠活动，一个体重为 x 的人可以和体重至少为 2x 配对，这样两人只需买一张票。现在给出了 n 个人的体重，请你计算他们最少需要买几张门票？

输入：第一行一个整数 n，表示人数；第二行 n 个整数，每个整数表示不同人的体重：
```
6
1 9 7 3 5 5
```
输出为 4.

使用贪心算法，先将所有人的体重排序，如果让最瘦的和最胖的成为一组，那么这样就浪费了，会导致一些没有特别胖的不能匹配比较瘦的。我们对每一个人，找一个可以匹配最瘦的人，即按照体重排序之后的每个人，分成两半，前半段最瘦的找后半段最瘦的里面第一个可以匹配的。

具体做法是使用两个指针，一个指向数组的前半部分（较小体重的人），另一个指向数组的后半部分（较大体重的人）。通过遍历，尝试将较小体重的人与较大体重的人配对，满足条件（较大体重至少是较小体重的两倍）。每次成功配对，两个指针同时后移，继续寻找下一个可能的配对。最终，配对的数量即为能减少的门票数量。代码实现如下：
```java
public class WeightMatch {
    public static void main(String[] args) {
        int[] weights = {1, 9, 7, 3, 5, 5};
        System.out.println(match(weights, weights.length));
    }

    public static int match(int[] weights, int n) {
        Arrays.sort(weights);
        int j = n / 2;
        int res = n;
        for(int i = 0; i < n / 2; i++) {
            // 不满足，则说明后面一个人不够胖，往后面找
            while(j < n && weights[i] * 2 > weights[j]) {
                j++;
            }
            if(j < n) {
                res--;
                j++;
            } else
                // 后半部分已无可匹配的人
                break;
        }
        return res;
    }
}
```
排序的时间复杂度为 O(nlogn)，后面匹配的时间复杂度为 O(n) ，总体的时间复杂度可以取最大的 O(nlogn)，空间复杂度为 O(1)。
