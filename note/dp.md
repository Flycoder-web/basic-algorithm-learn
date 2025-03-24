# 动态规划
动态规划就是把问题分成多个阶段，每个阶段都符合一个式子（状态转移），后面阶段的状态（结果）一般可以由前面阶段的状态（结果）转移而来。

使用动态规划求解时，最关键的是找出**状态转移方程**，想要找出状态转移方程，首先要对问题状态有清晰的定义。一般来说，动态规划求解主要包括以下步骤：
- 划分阶段：把问题划分为子问题，类似于分治法，每个阶段一般是有序的。
- 定义状态：每个阶段，都有属于自己的最优状态，每一个阶段的最优状态，可以从前面阶段的最优状态中转化获得。
- 状态转化：不同阶段之间的转化关系，就是状态转移方程，定义好它们之间的递推关系，就是极其重要的一步。
- 逆向求解：一般来说我们要求解一个状态，需要先把前面的状态先求解。那么逆向就是自底向上，也就是先挨个把前面的状态求解，再使用前面的状态求解后面的状态。（注意最初的状态定义必须准确，边界值需要处理好）

动态规划一般是用来求解最优解的，这类问题一般有很多种解，但是我们期望的是找出其中的一个最优解。动态规划的厉害之处，在于分解大的问题，并且找出了递推的式子，能够利用前面的状态不断求解出后面的状态。

动态规划问题的两大特征：重叠子问题和最优子结构。解题三步骤：状态定义，初始化状态以及状态转换。

## 斐波那契数列
斐波那契数列（ Fibonacci sequence ），又称黄金分割数列，以兔子繁殖为例子而引入，故又称为“兔子数列”，指的是这样一个数列：0、1、1、2、3、5、8、13、21、34...。

在数学上的定义为：F(0) = 0，F(1) = 1，F(n) = F(n - 1) + F(n - 2)（n ≥ 2，n ∈ N*）。

直接的方法就是使用递归：
```java
public static long fibonacci_recur(long n) {
    if(n == 0)
        return 0;
    if(n == 1)
        return 1;
    return fibonacci_recur(n - 1) + fibonacci_recur(n - 2);
}
```
但该方法容易造成栈溢出，可以用动态规划求解：
- 状态定义：Fibonacci(i) 表示第 i 个斐波那契数
- 初始化状态：定义 Fibonacci(0) = 0 和 Fibonacci(1)=1
- 状态递推式：Fibonacci(i) = Fibonacci(i-1) + Fibonacci(i-2) (i>=2)

可以用一个数组存储前面的状态，然后不断循环，递推出后面的状态：
```java
public static long bottomUp(int n) {
    int[] nums = new int[n + 2]; // 注意是 n+2
    nums[0] = 0;
    nums[1] = 1;
    for(int i = 2; i <= n; i++) { // 注意是 i<=n
        nums[i] = nums[i - 1] + nums[i - 2];
    }
    return nums[n];
}
```
由于当 i>=2 时，当前状态仅与前两个状态有关，更前的状态就没用了，所以这里没必要存储这么多状态，可以只用两个变量表示状态转移即可：
```java
public static long noArray(int n) {
    if(n == 0)
        return 0;
    long fib0 = 0;
    long fib1 = 1;
    long res = 1;
    for(int i = 2; i <= n; i++) { // 注意是 i<=n
        res = fib0 + fib1;
        fib0 = fib1;
        fib1 = res;
    }
    return res;
}
```
虽然时间复杂度仍是 $O(n)$，但空间复杂度变为 $O(1)$。

## 跳台阶问题
假设可以一次跳 1 级台阶，也可以跳上 2 级…… 甚至可以一次跳上n级，求跳上一个n级台阶共有多少种跳法？

由于一次可以跳 1, 2, 3... n 级，假设函数 f(n) 表示跳上n级台阶的跳法数量，则：
- 跳到第 1 级是 f(1)= 1，只有一种跳法；
- 跳到第 2 级，可以直接跳到第 2 级，也可以是从第 1 级直接跳，所以 f(2) = f(1)+1;
- 跳到第 3 级，可以从第 1 级跳，也可以从第 2 级跳，还可以直接跳到第 3 级，所以 f(3) = f(1)+f(2)+1;
- 以此类推，跳到第 n 级：f(n)=f(1)+f(2)+f(3)+...+f(n-1)+1。

可以用双层循环完成：
```java
public static int jumpSum(int n) {
    if (n <= 0)
        return 0;
    int[] nums = new int[n];
    nums[0] = 1;
    for(int i = 1; i < n; i++) {
        int sum = 1;
        for(int j = 0; j < i; j++) {
            sum += nums[j];
        }
        nums[i] = sum;
    }
    return nums[n - 1];
}
```
可以用动态规划的方式：
- 定义状态：f(n)表示跳上 n 级台阶的跳法数量；
- 初始化状态：跳上第一级只有一种方法，f(1) = 1。

对于状态转换，将 f(n) 的表达式减去 f(n-1) 的表达式可以得到：f(n)=2*f(n-1)。

实现如下：
```java
public static int jumpSum_dp(int n) {
    if(n <= 0)
        return 0;
    int res = 1;
    // 相当于计算 2^(n-1)
    for(int i = 1; i < n; i++) {
        res = 2 * res;
    }
    return res;
}
```
再由 f(n-1)=2f(n-2) 可以递推出 f(n)=2^(n-1)，所以还可以直接计算得出。

## 正则表达式
正则表达式是用一个式子来表示一种规则，来匹配出符合该规则的所有字符串。现需要实现一个方法来匹配包括 `.` 和 `*` 的正则表达式，模式中的字符 `.` 表示任意一个字符，而 `*` 表示它前面的字符可以出现任意次（包含 0 次）。例如，字符串 `aaa` 与模式 `a.a` 和 `a*ac*a` 匹配，但是与 `aa.a` 和 `ab*a` 均不匹配。

首先是递归实现，核心逻辑：
1. 先检查 pattern 是否为空，若为空则 str 也必须为空才返回 True;
2. 检查 str 当前字符是否和 pattern 当前字符匹配（或者 p 当前字符是 `.`），即一次匹配;
3. 处理 `*`：
   1. 跳过 pattern 的前两个字符（相当于 `*` 代表 0 次匹配）;
   2. 若当前字符匹配，则尝试匹配 str 的下一个字符（即 `*` 代表多个字符匹配）;
4. 若 * 不存在，则直接匹配 str 和 pattern 的下一个字符。

实现如下：
```java
public static boolean recursive(char[] str, char[] pattern, int strIndex, int pIndex) {
    if(pattern.length == pIndex) {
        return str.length == strIndex;
    }

    // 当前字符匹配或者模式中为.
    boolean firstMatch = strIndex < str.length && str[strIndex] == pattern[pIndex] || pattern[pIndex] == '.';

    if(pIndex + 1 < pattern.length && pattern[pIndex + 1] == '*')
        // 匹配0次或多次
        return recursive(str, pattern, strIndex, pIndex + 2) || (firstMatch && recursive(str, pattern, strIndex + 1, pIndex));
    else
        // 正常匹配
        return firstMatch && recursive(str, pattern, strIndex + 1, pIndex + 1);
}
```
该方法直观但会有重复计算，可以使用记忆化进行优化，即将计算出的组合 `(strIndex, pINdex)` 的结果存储起来，这里使用 HashMap 实现：
```java
public static boolean recur_memo(char[] str, char[] pattern, int strIndex, int pIndex, Map<String, Boolean> memo) {
    String key = strIndex + "#" + pIndex;
    if(memo.containsKey(key))
        return memo.get(key);

    if(pattern.length == pIndex) {
        return str.length == strIndex;
    }

    boolean firstMatch = strIndex < str.length && str[strIndex] == pattern[pIndex] || pattern[pIndex] == '.';

    boolean result;
    if(pIndex + 1 < pattern.length && pattern[pIndex + 1] == '*')
        result = recur_memo(str, pattern, strIndex, pIndex + 2, memo) ||
                (firstMatch && recur_memo(str, pattern, strIndex + 1, pIndex, memo));
    else
        result = firstMatch && recur_memo(str, pattern, strIndex + 1, pIndex + 1, memo);

    memo.put(key, result);
    return result;
}
```
当然也可以使用动态规划，状态定义为二维数组 `dp[i][j]`，表示字符串 s 的前 i 个字符和模式 p 的前 j 个字符是否匹配。

状态转移：
- 初始化：`dp[0][0]=true`，表示两个空字符串匹配。
- 处理空字符串：当模式 p 中存在 `*` 时，可以跳过该字符及其前一个字符，从而可能匹配空字符串。
- 字符匹配：
  - 当前字符匹配（包括 `.`）时，`dp[i][j]` 取决于前一个状态 `dp[i-1][j-1]`。
  - 遇到 `*` 时，分两种情况处理：
    - 匹配零次：跳过 `*` 及其前一个字符，即 `dp[i][j] = dp[i][j-2]`。
    - 匹配多次：当前字符匹配时，`dp[i][j]` 取决于 `dp[i-1][j]`。

动态匹配过程如下：

![正则表达式动态规划解法](./assets/regexMatch.gif)

实现如下：
```java
public static boolean match_dp(String str, String pattern) {
    int sLen = str.length();
    int pLen = pattern.length();
    // 表示字符串前i个字符和模式串前j个字符是否匹配
    boolean[][] dp = new boolean[sLen + 1][pLen + 1];

    dp[0][0] = true;

    // 模式串中的 * 可匹配空字符串
    for(int j = 2; j <= pLen; j++)
        if(pattern.charAt(j - 1) == '*')
            dp[0][j] = dp[0][j - 2];

    for(int i = 1; i <= sLen; i++) {
        for(int j = 1; j <= pLen; j++) {
            // i-1表示当前字符
            if(str.charAt(i - 1) == pattern.charAt(j - 1) || pattern.charAt(j - 1) == '.')
                dp[i][j] = dp[i - 1][j - 1];
            else if(j > 1 && pattern.charAt(j - 1) == '*')
                // 匹配0个或者多个
                dp[i][j] = dp[i][j - 2] || (dp[i - 1][j] &&
                        (str.charAt(i - 1) == pattern.charAt(j - 2) || pattern.charAt(j - 2) == '.'));
        }
    }
    return dp[sLen][pLen];
}
```
时间复杂度 $O(mn)$，其中 m、n 分别为 str 和 pattern 的长度，状态转移需遍历整个 dp 矩阵。空间复杂度 $O(mn)$： 状态矩阵 dp 使用 O(mn) 的额外空间。

## 动态规划求解最长回文串
之前已经介绍了中心扩展法，通过遍历每个可能的回文中心，并向两边扩展来寻找最长回文，时间复杂度为 O(n²)，空间复杂度为 O(1)。

也可以使用动态规划方法，定义状态：`dp[i][j]` 表示子串 `s[i:j+1]` 是否是回文串。初始化：长度为 1 的子串一定是回文串，即 `dp[i][i] = true`。状态转移：当满足 `s[i] == s[j]` 时：
- 若 j - i <= 1（即子串长度为 1 或 2），那么 `dp[i][j] = true`。
- 若 j - i > 1，则 `dp[i][j] = dp[i+1][j-1]`（即内部子串也必须是回文串）。

在填充 dp 数组的过程中，记录最长回文的起始位置和长度。动态过程如下：

![动态规划求最长回文串](./assets/LongestPalindromeDP.gif)

实现如下：
```java
public static String find(String s) {
    if(s == null || s.length() < 2)
        return s;

    int n = s.length();
    boolean[][] dp = new boolean[n][n];
    int start = 0, maxLen = 1;

    // 单个字符的子串都是回文串
    for(int i = 0; i < n; i++)
        dp[i][i] = true;

    // 递推填表
    for(int len = 2; len <= n; len++) { // 子串长度
        for(int i = 0; i < n - len; i++) { // 子串起点
            int j = i + len - 1; // 子串终点
            if(s.charAt(i) == s.charAt(j)) {
                if(len == 2)
                    dp[i][j] = true;
                else
                    dp[i][j] = dp[i + 1][j - 1];
            }

            if(dp[i][j] && maxLen < len) {
                start = i;
                maxLen = len;
            }
        }
    }

    return s.substring(start, start + maxLen);
}
```
空间复杂度为 $O(n^2)$，时间复杂度为 $O(n^2)$。

## 连续子数组的最大和
假设有一个整型数组，数组里有正数也有负数，数组中的一个或连续多个整数组成一个子数组，现在要求所有子数组的和的最大值。比如数组 `[1, -2, 3, 10, -4, 7, 2, -5]`，和最大的连续子数组为 `{3, 10, -4, 7, 2}`，和为 18。

暴力解法就是两层循环，求每一个区间的子数组的和，不断和最大值比较，最后得到最大值：
```java
public static int simple(int[] array) {
    if(array == null || array.length == 0)
        return 0;

    int max = Integer.MIN_VALUE;
    // 从每一个字符开始
    for(int i = 0; i < array.length; i++) {
        int tempSum = 0;
        // 每一种长度的子串（合法的索引范围）
        for(int j = i; j < array.length; j++) {
            tempSum += array[j];
            // 当前最大值和保存的最大值相比较
            if(max < tempSum)
                max = tempSum;
        }
    }

    return max;
}
```
尝试使用动态规划方法求解，介绍 Kadane 算法的思路：遍历数组的时候，维护当前的最大子数组和。对于每个元素，有两种选择：要么把当前元素加入到之前的子数组中，要么重新开始一个子数组（以当前元素为起点）。然后取这两种情况的较大值，作为当前位置的最大和。最后，整个过程中的最大值就是答案。

该算法的核心思想是遍历数组时维护当前的最大子数组和，并更新全局最大值。步骤如下：
1. 初始化：将当前最大和（current_max）和全局最大和（global_max）设为数组的第一个元素。
2. 遍历数组：从第二个元素开始，对于每个元素：
   1. 计算当前最大和，选择将当前元素加入之前的子数组或作为新子数组的起点，取较大者。
   2. 更新全局最大和。
3. 返回结果：全局最大和。

代码实现如下：
```java
public static int kadane(int[] array) {
    int currentMax = array[0];
    int globalMax = array[0];
    for(int i = 1; i < array.length; i++) {
        // 对于每个元素，要么把当前元素加入到之前的子数组中，要么重新开始一个子数组
        currentMax = Math.max(currentMax + array[i], array[i]);
        globalMax = Math.max(currentMax, globalMax);
    }
    return globalMax;
}
```
此算法时间复杂度为 O(n)，空间复杂度为 O(1)。

## 0-1背包问题
0-1背包问题是指，给定一组物品，每个物品都有一个重量和一个价值，然后有一个容量固定的背包。我们的目标是在不超过背包容量的前提下，选择一些物品装入背包，使得总价值最大。这里的“0-1”指的是每个物品要么选要么不选，不能分割。

首先暴力解法就是使用递归，枚举所有可能的选择：
- 如果不选当前物品，则问题变成对剩余物品继续求解；
- 如果选当前物品，则减少背包容量，并计算剩余物品的最优解。

代码实现如下：
```java
public static int recursive(int[] weights, int[] values, int capacity, int n) {
    if(n == 0 || capacity == 0)
        return 0;
    if(weights[n - 1] > capacity)
        // 若当前物品超重，直接跳过
        return recursive(weights, values, capacity, n - 1);
    else
        // 不选或选该物品
        return Math.max(recursive(weights, values, capacity, n - 1),
                values[n - 1] + recursive(weights, values, capacity - weights[n - 1], n - 1));
}
```
由于存在大量重复子问题，该方法的时间复杂度是 $O(2^n)$，效率非常低。可以使用记忆化优化，即在计算过程中保存子问题的解，避免重复计算：
```java
public static int recur_memo(int[] weights, int[] values, int capacity, int n, Map<String, Integer> memo) {
    if(n == 0 || capacity == 0)
        return 0;

    String key = n + "#" + capacity; // 生成唯一键
    if(memo.containsKey(key))
        return memo.get(key);

    if(weights[n - 1] > capacity) {
        int val = recur_memo(weights, values, capacity, n - 1, memo);
        memo.put(key, val);
        return val;
    }
    int notTake = recur_memo(weights, values, capacity, n - 1, memo);
    int take = values[n - 1] + recur_memo(weights, values, capacity - weights[n - 1], n - 1, memo);
    int result = Math.max(notTake, take);
    memo.put(key, result);
    return result;
}
```
也可以使用动态规划，状态定义：`dp[i][w]` 表示前 i 个物品中，能够放入容量 w 的背包中的最大价值。转移方程定义：

$$dp[i][w]=\max(dp[i-1][w],v_i+dp[i-1][w-w_i])$$

其中 `dp[i-1][w]` 表示不选第 i 个物品的情况，`v_i + dp[i-1][w-w_i]` 表示选第 i 个物品的情况。

采用自底向上遍历：
```java
public static int dp_bottomUp(int[] weights, int[] values, int capacity) {
    int[][] dp = new int[weights.length + 1][capacity + 1];

    for(int i = 1; i <= weights.length; i++) {
        for(int j = 1; j <= capacity; j++) {
            if(weights[i - 1] > j) // 注意weights取值范围
                dp[i][j] = dp[i - 1][j];
            else
                dp[i][j] = Math.max(dp[i - 1][j], values[i - 1] + dp[i - 1][j - weights[i - 1]]); // 同样注意values和weights范围
        }
    }
    return dp[weights.length][capacity];
}
```
注意到 `dp[i][w]` 只依赖于 `dp[i-1][w]`，所以可以用一维数组代替二维数组，并从右向左更新，避免数据覆盖问题：
```java
public static int dp_oneDimArray(int[] weights, int[] values, int capacity) {
    int[] dp = new int[capacity + 1];

    for(int i = 0; i < weights.length; i++) {
        for(int j = capacity; j >= 0; j--) { // 从后向前遍历
            dp[j] = Math.max(dp[j], values[i] + dp[j - weights[i]]);
        }
    }
    return dp[capacity];
}
```
时间复杂度是 $O(mn)$，空间复杂度为 $O(m)$。

## 最长公共子序列
字符串一般会涉及子序列和子串两个概念，子串要求是连续的，而子序列只要求顺序并不要求连续。

最长公共子序列（Longest Common Subsequence，LCS）问题指的是给定两个序列，找出它们最长的公共子序列的长度。例如两个序列 `str1 = “abdbcabd"`，`str2 = "abbcdd"`，两者最长的公共子序列为 ”abbcd“，长度为 5。

该问题可以分解为更小的问题，即长度为 n 的字符串 str1 和长度为 m 的字符串 str2，两者的最长公共子序列，可以从str1 前长度为 n-1 的子串与 str2 前长度为 m-1 的子串的最长公共子序列中得知。思路如下：

- 首先定义状态：使用二维数组 dp，其中 `dp[i][j]` 表示序列 X 前 i 个元素和序列 Y 前 j 个元素的 LCS 长度。
- 初始化：当任一序列长度为 0 时，LCS 长度为 0，即 `dp[0][j] = 0` 和 `dp[i][0] = 0`。
- 状态转移：
  - 当 `X[i-1] == Y[j-1]` 时，`dp[i][j] = dp[i-1][j-1] + 1`；
  - 否则，`dp[i][j] = max(dp[i-1][j], dp[i][j-1])`；
- 按行或列顺序填充，确保计算每个状态时依赖的子问题已被解决。

动态过程如下：

![LCS动态规划解法](./assets/LCS_DP.gif)

代码实现如下：
```java
public static int find_dp(String s1, String s2) {
    int m = s1.length(), n = s2.length();
    int[][] dp = new int[m + 1][n + 1];

    for(int i = 1; i <= m; i++) {
        for(int j = 1; j <= n; j++) {
            // 注意这里的索引与字符串中索引错开
            if(s1.charAt(i - 1) == s2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }
    return dp[m][n];
}
```
借助了 n * m 的二维数组，时间和空间复杂度均为 $O(n*m)$。

## 数字塔的最长路径
数字塔（或称数字三角形）最长路径问题是一个经典的动态规划问题，要求从塔的顶端开始，沿着路径向下走到塔底，每次只能向左下或右下移动，求路径上的数字之和的最大值。

假设要求到第 i 层第 j 个位置的最大路径，到达该位置只能从上面一层到达，并且是上层左右的位置，即要求第 i 层第 j 个位置的最大路径，必须先求第 i-1 层第 j 个位置和第 i-1 层第 j-1 个位置的最大路径和，这就是典型的动态规划问题。步骤如下：
- 定义状态：`dp[i][j]` 表示走到第 i+1 层，第 j+1 个位置的最大路径值。
- 初始化状态：第一层的第一个位置的最大路径只有一种，就是塔的顶点位置的值，`dp[0][0]=data[0][0]`。
- 状态转换：每一个位置索引合法的情况下，只能通过自己的左上角或者右上角的位置走下来。即 `dp[i][j] = Math.max(dp[i-1][j-1], dp[i-1][j]) + data[i][j]`，如果在左边界，那么只能是右上角走下来，也就是 `dp[i][j] = dp[i-1][j] + data[i][j]`。

实现如下：
```java
public class NumTower {
    public static void main(String[] args) {
        int[][] data = {{1}, {3, 5}, {7, 5, 2}, {9, 3, 4, 11}, {11, 1, 2, 5, 7}};
        System.out.println(maxDis_dp(data));
    }

    public static int maxDis_dp(int[][] tower) {
        int rows = tower.length;
        int[][] dp = new int[rows][rows];
        dp[0][0] = tower[0][0];

        int max = 0;
        for(int i = 1; i < rows; i++) {
            for(int j = 0; j <= i; j++) {
                if(j == 0)
                    dp[i][j] = dp[i - 1][j] + tower[i][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i - 1][j]) + tower[i][j];
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }
}
```
时间复杂度和空间复杂度均为 $O(n^2)$。
