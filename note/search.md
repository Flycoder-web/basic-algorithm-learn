# 查找搜索
## 二分查找
给一个有序数组，给一个数字，需要从该数组中找出该数的索引值，如果数字不存在，则返回 -1。

可以使用二分查找，先初始化左边界 start 和右边界 end，当 `start <= end` 时，取中间值 `mid = (start + end) / 2`，使用该中间值索引对应数组中的值与目标值进行比较，当相同时，直接返回该索引；当大于目标值时，说明目标值在左边区间，右边界 `end = mid - 1`，继续循环；当小于目标值时，说明目标值在右边区间，左边界缩小 `start = mid + 1`，继续循环，直到找到目标值对应索引，或者 `start > end` 时，结束循环。

二分查找的递归实现和迭代实现如下：
```java
ublic class BinarySearch {
    public static void main(String[] args) {
        int[] sorted = {1, 2, 3, 4, 5};
        int[] datas = {1, 23, 43, 56, 67, 69, 75, 81, 94};
        System.out.println(binarySearch(sorted, 1));
        System.out.println(iterate(datas, 81));
    }

    public static int binarySearch(int[] sorted, int target) {
        return recursive(sorted, target, 0, sorted.length - 1);
    }

    public static int recursive(int[] sorted, int target, int start, int end) {
        if(start > end)
            return -1;
        
        int mid = (start + end) / 2;
        if(sorted[mid] > target)
            return recursive(sorted, target, start, mid - 1);
        else if(sorted[mid] < target)
            return recursive(sorted, target, mid + 1, end);
        else
            return mid;
    }

    public static int iterate(int[] sorted, int target) {
        int start = 0, end = sorted.length - 1;
        while(start <= end) {
            int mid = (start + end) / 2;
            if(sorted[mid] > target)
                end = mid - 1;
            else if(sorted[mid] < target)
                start = mid + 1;
            else
                return mid;
        }
        return -1;
    }
}
```
最差的情况每次能排除掉一半的数，查找次数最多是 $O(\log n)$，因此时间复杂度为 $O(\log n)$。

## 两数之和
如果有一个整数数组 nums 和一个目标值 target，如何在该数组中找出和为目标值的两个整数，并返回他们的数组下标。比如 `nums[] = {5, 12, 43, 34, 6, 23}` 和 `target = 35`，应当返回索引组合 `1 5`。

可以使用哈希表，key 为元素的值，value 为元素的下标，将数字不断放进 Hashmap，放之前判断里面是否存在 target-num。代码实现如下：
```java
public class TwoNumSum {
    public static void main(String[] args) {
        int[] nums = {5, 12, 43, 34, 6, 23};
        int target = 35;
        System.out.println(find(nums, target));
    }

    public static String find(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            if(map.containsKey(target - nums[i]))
                return map.get(target - nums[i]) + " " + i;
            map.put(nums[i], i); // 应该先判断，再存入，防止匹配自身
        }
        return "";
    }
}
```
时间复杂度是 $O(n)$，空间复杂度是 $O(n)$。

## 二维数组中的查找
在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

倘若直接暴力遍历，在最坏的情况是遍历完所有的元素才能获取结果。如果这个数组规模是 n * m，那么时间复杂度就是 $O(n*m)$，没有借助其他的空间，空间复杂度为 O(1)。

其实可以从左下角的元素作为起点，若目标值大于该值，就向右移动，如果目标值小于该值，就向上移动。最坏的结果是查找直到数组的右上角结束，这样一来，最坏情况就是 $O(n+m)$。

代码实现如下：
```java
public class TwoDimArray {
    public static void main(String[] args) {
        int[][] nums = {{ 1, 4, 6, 28 }, { 2, 7, 32, 34 }, { 10, 11, 67, 79 }};
        System.out.println(isExist(nums, 32));
    }

    public static boolean isExist(int[][] array, int target) {
        int i = array.length - 1, j = 0;
        while(i >= 0 && i < array.length && j >= 0 && j < array[0].length) {
            // 相等，直接返回
            if(array[i][j] == target)
                return true;
            // 比当前数小，往上边移动
            else if(array[i][j] > target)
                i--;
            // 比当前数大，往右边移动
            else
                j++;
        }
        return false;
    }
}
```

## 寻找最大的 k 个数
输入 n 个整数，找出其中最大的 k 个数。例如输入 `4, 5, 1, 6, 2, 7, 3, 8` 这 8 个数字，则最大的 4 个数字是 `5, 6, 7, 8`。

这个问题可以使用二叉堆数据结构，它是一个顺序存储的完全二叉树，且分为两种堆：最小堆：树的每一个节点都不大于其孩子节点；最大堆：树的每一个节点都不小于其孩子节点。

堆排序的思路是：
- 用无序的数组构建出一个最大堆，即上面的元素比下面的元素大。
- 将堆顶的元素和堆最末尾的元素交换，将最大元素下沉到数组的最后（末端）。
- 重新调整前面的顺序，继续交换堆顶的元素和当前末尾的元素，直到所有元素全部下沉。

这个问题要使用最小堆，即维护一个大小为 k 的最小堆，当输入超过 k 个时，优先将最小的弹出，这样剩下的就是最大的 k 个数。实现如下：
```java
public static int[] topK(int[] nums, int k) {
    if(nums == null || nums.length <= k)
        return nums;
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    for(int num : nums) {
        if(minHeap.size() < k)
            minHeap.offer(num);
        else {
            if(minHeap.peek() < num) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }
    }
    return minHeap.stream().mapToInt(Integer::intValue).toArray();
}
```
使用了优先队列，空间复杂度为 $O(N)$，时间复杂度，加上了内部的排序时间，为 $O(nlogk)$。

## 朋友圈
现在假设有 n 个人，并且有 m 对好友关系，如果甲和乙是朋友，并且甲和丙也是朋友，那么我们可以理解为乙和丙的朋友圈可以合并，也就是属于一个圈子（朋友圈）的人。然后我们需要判断里面到底有多少个朋友圈？一个人自己，不与其他人产生任何联系，也算是一个朋友圈。

可以用并查集解决，使用并查集来合并好友关系，并最终统计不同的朋友圈数量。并查集的核心操作包括：
- `getRoot(x)`：查找 x 的根节点（带路径压缩）。
- `union(x, y)`：合并两个集合（按秩合并）。
- 遍历统计朋友圈数量。

首先初始化并查集，将每个人的父节点初始为自己，即 `parent[x] = x`，在查找时，将 `parent[x]` 直接指向其根节点（路径压缩），优化后续查询；按秩合并，根据秩 rank 选择小树合并到大树，减少树的高度，提高效率。最后统计父节点仍然指向自己的节点的数量，则为朋友圈的数量。代码如下：
```java
public class CircleNum {
    public static void main(String[] args) {
        int[][] friendships = {{0, 1}, {1, 2}, {3, 4}};
        System.out.println(find(friendships, 5));
    }

    public static int find(int[][] friendships, int n) {
        int[] parent = new int[n];
        int[] rank = new int[n];
        int count = 0;
        // 初始化
        for(int i = 0; i < n; i++)
            parent[i] = i;

        // 合并
        for(int[] pair : friendships) {
            union(pair[0], pair[1], parent, rank);
        }

        // 统计
        for(int i = 0; i < n; i++)
            if(parent[i] == i)
                count++;
        return count;
    }

    // 按秩合并
    public static void union(int x, int y, int[] parent, int[] rank) {
        int rootX = getRoot(x, parent);
        int rootY = getRoot(y, parent);
        if(rootX != rootY) {
            // 按秩合并，且只在秩相同时增加
            if(rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            }
            else if(rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    // 查找 x 的根节点，带路径压缩
    public static int getRoot(int x, int[] parent) {
        if(parent[x] != x)
            parent[x] = getRoot(parent[x], parent); // 递归将 x 的 parent 设置为某根节点
        return parent[x];
    }
}
```
在 union() 步骤中，除了按秩合并，还可以按大小合并，即将小树挂到大树上，每次都增加根节点的 size：
```java
public static void union(int p1, int p2, int[] parent, int[] size) {
    int root1 = find(p1, parent);
    int root2 = find(p2, parent);
    if (root1 != root2) {
        // 让小树挂到大树上
        if (size[root1] > size[root2]) {
            parent[root2] = root1;
            size[root1] += size[root2];
        } else {
            parent[root1] = root2;
            size[root2] += size[root1];
        }
    }
}
```

## 二叉搜索树的查找
二叉搜索树的任意节点，其左子树中的所有节点的值都严格小于该节点的值，而右子树中的所有节点的值都严格大于该节点的值。而且左子树和右子树各自也必须是二叉搜索树。

给定一个二叉搜索树，里面存的是数字，通过根节点引用，判断该数字是否存在。可以使用递归的方式，判断根节点和目标值的大小：
- 如果两者相等，则直接返回当前节点；
- 如果比目标值小，则应该往右子树继续搜索；
- 如果比目标值大，则应该往左子树继续搜索。

实现代码如下：
```java
public static TreeNode search(TreeNode root, int value) {
    if(root == null || root.val == value)
        return root;
    if(root.val > value)
        return search(root.left, value);
    else
        return search(root.right, value);
}
```
现改一下需求，在二叉搜索树中，查找出第 k 小的节点。

由于其中每个节点都比它的左节点大，比右节点小，因此可以让 k 不断减小，直到 1 时就是第 k 小的元素。具体的，当 root 为空时，直接返回，否则用 k 减去左子树的节点数：
- 如果结果为 1，说明当前的 root 节点就是第 k 个节点（左子树有 k-1 个节点）。
- 如果结果小于 1，那么说明左子树的节点大于 k-1 个，第 k 个元素在左子树中，使用左子树进行递归。
- 如果结果大于 1，说明左子树的节点不够 k-1 个，那么第 k 个节点肯定是在右子树中。

代码如下：
```java
public static TreeNode kNode(TreeNode root, int k) {
    if(root == null)
        return root;
    else {
        int left = getNum(root.left, k);
        if(left == 1)
            // 左子树恰好有 k-1 个节点
            return root;
        else if(left < 1)
            // 左子树的节点数小于k
            return kNode(root.left, k);
        else
            // 左子树的节点数大于k
            return kNode(root.right, left - 1);
    }
}

// k减去node的子树节点
private static int getNum(TreeNode node, int k) {
    if(node == null)
        return k;
    int left = getNum(node.left, k);
    left--;
    return getNum(node.right, left);
}
```
其实该方法有很多冗余操作，更优雅的方法是中序遍历，因为对二叉搜索树进行中序遍历就是排序过程，下面是使用中序遍历得到第 k 小元素的方法：
```java
// 中序遍历的找第k小的节点
public static TreeNode kNode_inOrder(TreeNode node, int k) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    while(node != null || !stack.isEmpty()) {
        while(node != null) {
            stack.push(node);
            node = node.left;
        }
        node = stack.pop();
        if(--k == 0)
            return node;
        node = node.right;
    }
    return null;
}
```

## 关于海量数据查询
有 100 台机器，每个机器的磁盘特别大，磁盘大小为 1T，但是内存大小只有 4G，现在每台机器上都产生了很多 url 的日志文件，每个文件假设有 50G，那么如何计算出这 100 台机器上访问量最多的 100 个 url 呢？也就是 Top100。

海量的数据一般不可能一次性加载进内存，所以就需要分片，但要如何拆分才容易统计？如果是 ip 地址，可以直接统计，因为它本身是有限的，对于 url，需要哈希分流再统计，要保证一个 url 一定存在于同一个机器的同一个文件。即每台机器先将本地文件中每个 url 进行哈希，将其分配到 100 * n 个分片文件中，这样相同的 url 总被分配到同一分片。然后每台机器将分片文件发送到对应编号的机器。例如，分片k的数据发送到第k台机器。如果每台机器的文件还是很大，则还需要继续使用其他 hash 函数进行拆分，最后统计每个小文件中 url 的次数，使用堆排序得到每台机器的 top100，再将不同的结果合并，得到最终的结果。

换一种场景，如果数据库有一百万的数据，有一些请求过来，我们要快速的判断数据库里面有没有这个数据，需要怎么处理呢？前提是允许一定的误差存在，这时我们应该首先考虑布隆过滤器。

布隆过滤器（Bloom Filter）实际上是由一个很长的二进制向量和一系列随机 hash 映射函数组成（就是用二进制数组存储数据的特征）。它可以检查值是“**可能在集合中**”还是“**绝对不在集合中**”。“可能”表示有一定的概率，即可能存在误判。它的优点就是查询效率高，空间小，缺点是存在一定的误差，以及如果想要剔除元素时，可能会相互影响。

假设该二进制向量的长度为 m，最初所有的值均设置为 0，当一个元素被加入集合的时候，通过多个 hash 函数，将元素映射到位数组中的 k 个点（单个哈希函数只能输出单个索引值，而布隆过滤器使用多个哈希函数，这将会产生多个索引值），置为 1。

当对值进行搜索时，与哈希表类似，使用这几个哈希函数对该值进行哈希运算，并查看其生成的索引值。如果计算出的索引值中有已被置为 1 的，那么该值就**可能在集合中**，如果计算出的索引值对应全为 0，那么该值**一定不在集合中**。而判定“可能在集合中”，就有可能是误报，产生的原因是哈希碰撞导致的巧合而将不同的元素存储在相同的比特位上。

极端情况下，当布隆过滤器没有空闲空间时（满），每一次查询都会返回 true 。这就意味着布隆过滤器的长度要远大于预计添加元素的数量。

## 修改数组
给定一个长度为 N 的数组 $A=[A_1,A_2,...,A_N]$，数组中可能有重复出现的整数。现在小明要按以下方法将其修改为没有重复整数的数组。小明会依次修改 $A_2，A_3，...，A_N$。当修改 $A_i$ 时，小明会检查 $A_i$ 是否在 $A_1~A_{i-1}$ 中出现过。如果出现过，则小明会给 $A_i$ 加上 1；如果新的 $A_i$ 仍在之前出现过，小明会持续给 $A_i$ 加 1，直到 $A_i$ 没有在 $A_1~A_{i-1}$ 中出现过。当 $A_N$ 也经过上述修改之后，显然 A 数组中就没有重复的整数了。现在给定初始的 A 数组，请你计算出最终的 A 数组。

输入格式：第一行包含一个整数 N，第二行包含 N 个整数 $A_1,A_2,...,A_N$。输出格式：输出 N 个整数，依次是最终的 $A_1,A_2,...,A_N$。数据规模：$1<=N<=100000, 1<=A_i<=1000000$。

如果直接用暴力解法，复杂度很高，每一个数字的排重，都需要循环已有的数字，最差情况的复杂度将是 $O(n^2)$，如下：
```java
// 暴力解法，最坏O(n^2)
public static void origin(int[] nums, int n) {
    Set<Integer> set = new HashSet<>(n);
    for(int i = 0; i < n; i++) {
        while(set.contains(nums[i]))
            nums[i] += 1;
        set.add(nums[i]);
    }
    System.out.println(Arrays.toString(nums));
}
```
更好的方法是使用并查集（Union-Find），并查集包括查找（Find）和合并（Union）两个操作，这里的合并是指将所有出现过的数字，都指向下一个没有出现过的数字，下次再次出现，就知道应该增加到什么数字，才不会重复。

需要一个数组 `change[]` 来存储元素相同时，应该增加到哪一个数值。初始时数组中的元素存储的是自身索引，查找时如果发现存储的值不等于自身索引，那么说明存储的是索引。

如果索引为 1 的位置 `changes[1] = 1`，说明里面存储的是数值，而不是索引，也就是不需要继续查找了；如果再次出现 1，那么我们此时应该填入的是 1，也就是不需递增，没有重复元素。

如果索引为 2 的位置是 `changes[2] = 3`，说明里面存储的是索引，如果再次出现 2，我们应该先去找 `changes[3]`，此时 `changes[3] = 4`，说明里面存储的还是索引。继续查找 changes[4]，此时 `changes[4] = 4`，说明里面存储的是数值，不是索引，也就是 4 还没有出现过，如果再次出现 2，应该把 2 变成 4，就可以避免重复。这是不带路径压缩时的情况，如果带路径压缩，那么此时 `change[2]=4`，直接指向索引为 4 的元素。

代码实现如下：
```java
public static void union(int[] nums, int n) {
    int[] changes = new int[1_000_010];
    for(int i = 0; i < 1_000_010; i++)
        changes[i] = i;
    for(int i = 0; i < n; i++) {
        nums[i] = unionFind(nums[i], changes);
        changes[nums[i]] = unionFind(nums[i] + 1, changes);
    }
    System.out.println(Arrays.toString(nums));
}
// 未实现路径压缩
public static int unionFind_NoPathCompress(int index, int[] changes) {
    while(changes[index] != index)
        index = changes[index]; // 只是单纯向上查找，未路径压缩
    return changes[index];
}
// 带路径压缩的实现
public static int unionFind(int index, int[] changes) {
    if(changes[index] != index) // 注意是判断，不是循环
        changes[index] = unionFind(changes[index], changes);
    return changes[index];
}
```
另外，这里的 `union()` 方法初始化可以优化，找到 nums 数组中的最大元素 maxVal，将 changes[] 数组的大小动态设置为 `maxVal+n+2`，保证可处理所有可能的递增情况。例如，原数组中最大的数是 maxVal，每个数最多可能被递增 n 次，因此最大的数可能是 maxVal+n，加上可能的后续处理，因此数组的大小设为 maxVal+n+2是足够的：
```java
// 并查集，changes 数组初始值优化
public static void union_changes(int[] nums, int n) {
    int max = Arrays.stream(nums).max().getAsInt();
    int[] changes = new int[max + n + 2];

    for(int i = 0; i < changes.length; i++)
        changes[i] = i;
    for(int i = 0; i < n; i++) {
        nums[i] = unionFind(nums[i], changes);
        changes[nums[i]] = unionFind(nums[i] + 1, changes);
    }
    System.out.println(Arrays.toString(nums));
}
```

## Redis 中的跳表
跳跃表可以做到快速查询插入以及删除数据，平均的查找和插入的时间复杂度都是 O(logn)。为什么需要跳跃表？

如果使用链表，即使数据排好序了，查询也是需要全部遍历的，时间复杂度为 O(N)。要是在原链表基础上，每两个节点提取一个节点出来当成索引，就多出了一条更短的链表，可以成为索引链表，个数只有 n/2。这样查询的时候，可以先查询索引层，再往下查询的速度明显加快了。

要是我们再不断地每两个取一个，加索引链表呢？最后，就可以做到最上层只有两个节点，每一层最多遍历两个节点。所以跳表查询任意数据的时间复杂度为 O(2*logn)，也就是 O(logn)。

这就是一种空间换时间的做法，其实是上面所有的索引层次都加起来 `n/2+n/4+n/8...+8+4+2 = n-2`，因此空间复杂度还是 O(N)。要想空间小一点，可以抽取的间隔大一点，比如每 3 个或者 4 个就抽取出一个索引。

跳表插入或者删除数据，我们首先需要找到插入或者删除的位置，然后执行插入或删除操作，找到位置之后插入和删除的时间复杂度为 O(1)，所以最终插入和删除的时间复杂度也为 O(logn)。
