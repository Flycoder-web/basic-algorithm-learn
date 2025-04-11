# 位运算
## 基本概念
对于 8 位二进制，能表示的总数是 $2^8=256$，为了表示负数，将第一位作为符号位，并使用补码表示法：
- 正数：从 00000000 到 01111111，也就是从 0 到 127，一共 128 个值（包括 0）。
- 负数：从 10000000 到 11111111，代表 -128 到 -1，也是 128 个值。

由于 00000000 和 10000000 都能表示 0（一个正0，一个负0），但 0 只需一个编码，所以，负数可以“借”来这个位置，多表示一个数（就是 -128）。

`负数的补码表示 = 该数的正数的反码 + 1`，因为 `1 + (-1)` 要等于 0，而只改变符号的话，它们的二进制数相加并不等于 0，以 1 的二进制表示 `00000001` 为例，若使 `1 - 1 = 0` 成立，则 -1 应该为 `11111111`，而这正是 `00000001` 的反码 `11111110` 加 1 得到的。

对于 8 位二进制，只有 -128 不满足 `负数的补码表示 = 该数的正数的反码 + 1`，因为第一位已经被用来表示符号位，这时就不存在 +128，最大只能到 +127，而 0 只需一个表示就行，所以 `10000000` 就留给了 -128。

## 常见位运算
常见的位运算如下：
- `~`：按位取反，0 变成 1，1 变成 0，如 `~1010 = 0101`
- `&`：按为与运算， 1 与 1 方为 1
- `|`：按位或运算，只要有一个 为 1 ，则该位置 为 1
- `^`：按位异或，不相同的值则为 1，比如 0 异或 1，或者 1 异或 0
- `<<`：带符号左移，比如 35(00100011),左移一位为 70(01000110), -35(11011101)左移一位为 -70(10111010)
- `>>`：带符号右移，比如 35(00100011),右移一位为 17(00010001), -35(11011101)左移一位为 -18(11101110)
- `<<<`：无符号左移，比如 35(00100011),左移一位为 70(01000110)
- `>>>`：无符号右移，比如 -35(11011101),右移一位为 110(01101110)
- `x ^= y`; `y ^= x`; `x ^= y`;：x 与 y 交换
- `s &= ~(1 << k)`：第 k 位置为 0

## 位运算实现加法
如果给两个正数 a 和 b，怎么使用位运算求出它们的和。

a 与 b 的每一位相加，如果相加时没有产生进位，结果就是异或操作（`a ^ b`）的值，但是如果产生了进位，分为两部分，一部分是 a 和 b 不进位的和，另外一部分是 a 和 b 进位的结果，两部分相加即可。如果此时相加还是会有进位问题，那么就循环直到进位等于 0，即不产生进位为止：
- 当前位的和值等于 `a ^ b`
- 进位的结果为 `a & b`，但是进位是往前每次往前进 1 位，叠加在前面一位上，因此需要左移一位 `a & b << 1`。

将两者相加，在循环中直到进位为 0。代码如下：
```java
public static int add(int a, int b) {
    while (b != 0) {
        int sum = a ^ b;
        int carry = (a & b) << 1;
        a = sum;
        b = carry;
    }
    return a;
}
```

## 统计二进制整数中1的数量
给一个整数，计算出该整数在二进制表示中 1 的个数。可以直接使用标准库中的 `Integer.bitCount()` 方法，该方法使用了位运算优化策略。常见的方法是使用循环，逐位检查。

任何一位与 1 进行与运算，最终结果都是它本身，那么一个数与 1 进行与运算（`&`），如果结果是 1 ，则说明最后一位 是 1。知道了最后一位是否为 1，只要把数字不断向右移位和 1 进行与计算，结果为 1 则说明最后一位是 1，循环直到数字为 0 即可。注意这里需要使用无符号右移，即 `>>>`，代码如下：
```java
public static int countBits(int num) {
    int count = 0;
    while (num != 0) {
        count += num & 1; // 检查最低位是否为 1
        num >>>= 1; // 无符号右移一位
    }
    return count;
}
```

## 位运算找字符串的差异
若有两个字符串 a 和 b，都是大小写字母，b 是由 a 字符串打乱字符顺序之后，再随机位置加上 1 个字符，转换而成，该如何找出这个字符。比如 a = "lasda" , b = "daldas" ,不同的字符是 "d"。

最简单的方法就是统计两个字符串中每个字符出现的次数，对比一下就可以找出多出来的字符到底是哪一个。

每个字符对应一个 ASCII 码，ASCII 码使用指定的 7 位或 8 位二进制数组合来表示 128 或 256 种可能的字符。标准 ASCII 码也叫基础 ASCII 码，使用 7 位二进制数（剩下的 1 位二进制为 0）来表示所有的大写和小写字母，数字 0 到 9、标点符号，以及在美式英语中使用的特殊控制字符。另一种方式就是我们将每个字符的 ASCII 全部加起来，差值就是多出来字符的 ASCII 码，ASCII 码的数值是 int ，可以转换成为字符。

也可以使用位运算解决，首先，可以注意到 `a^b^a = b`，即任何一个字符，与偶数个其他字符进行异或运算，最终结果等于本身。所以两个字符串的所有字符都进行异或，多出来的字符数量必定是奇数，其他的字符数量是偶数。因此，只需要对所有字符进行异或计算，就可以得到最终结果。代码如下：
```java
public static char findDifferentChar(String a, String b) {
    int ret = 0;
    for(char c : a.toCharArray())
        ret ^= c;
    for(char c : b.toCharArray())
        ret ^= c;
    return (char) ret;
}
```

## 二进制中的汉明距离
信息论中，Hamming Distance(汉明距离)表示两个等长字符串在对应位置上不同字符的数目，从另外一个方面看，汉明距离度量了通过替换字符的方式将字符串 x 变成 y 所需要的最小的替换次数。比如：
```
"KathSam" 和 "kothran" 的汉明距离是 3
1111101 和 1001001 的汉明距离是 3
```
这里的问题是计算两个整数 a 和 b ，他们的二进制表示中不同的位置的数量（汉明距离）。

可以分为两步：
- 先异或计算，获取到数值不同的位置
- 计算数值不同的位的数量，正好使用之前统计二进制整数中1的数量的代码。

代码如下：
```java
public static int hammingDist(int a, int b) {
    int s = a ^ b, count = 0;
    while(s != 0) {
        count += s & 1;
        s >>>= 1;
    }
    return count;
}
```
对于该循环计算1数量的代码，即使是 0 位置我们也会循环，每次都要循环完 32 位。有一个Brian Kernighan优化算法，该算法使用 x 与 x-1 做与运算，得到的结果恰好为 x 删去其二进制表示中最右侧的 1 的结果。即循环中每次 `x &= x-1` 操作都会删去最右侧的一个 1，直到等于 0，循环的次数就是 1 的个数：
```java
public static int hammingDist_opt(int a, int b) {
    int s = a ^ b, count = 0;
    while(s != 0) {
        s &= s - 1;
        count++;
    }
    return count;
}
```

## 布隆过滤器
布隆过滤器的误判率是可以预测的，与位数组的大小，以及 hash 函数的个数等相关。

假设位数组的大小是 m，一共有 k 个 hash 函数，那么每一个 hash 函数，只能 hash 到 m 位中的一个位置，所以某一位没有被 hash 到的概率是 $1-1/m$，k 个 hash 函数都 hash 之后，该位还是没有被 hash 到的概率为 $(1-1/m)^k$。

如果此时插入了 n 个元素，即位数组已经被 hash 了 n*k 次，该位还是没有被 hash 到的概率是 $(1-1/m)^{kn}$，那么该位已被 hash 变成 1 的概率为 $1-(1-1/m)^{kn}$。

如果需要检测某一个元素是不是在集合中，也就是该元素对应的 k 个位 hash 出来的值都为 1，即元素本不存在，但该元素对应的所有位都被 hash 变成 1 的概率是 $(1-(1-1/m)^{kn})^k$，当 m 很大时可近似为 $(1-e^{-kn/m})^k$。

所以，随着位数组大小 m 和 hash 函数个数的增加，误判率会下降，随着插入的元素 n 的增加，概率会上升。

最后还可以根据自己期待的误判率 P 和期待添加的个数 n，来大致计算出布隆过滤器的位数组的长度：$m=-\frac{n\ln P}{(\ln 2)^2}$。

下面实现一个简单的布隆过滤器，需要考虑的点：
- 位数组空间大小，其他不变，位空间越大，hash 冲突的可能性越小。
- hash 函数的实现，为避免冲突，应使用多个不同的质数来当种子。
- 实现两个方法：添加元素和判断某元素是否存在。

下面用了简单的 hash 函数，主要是使用 hash 值得高低位进行异或，然后乘以种子，再对位数组大小进行取余数：
```java
public class MyBloomFilter {
    private static final int DEFAULT_SIZE = Integer.MAX_VALUE; // 默认大小

    private static final int MIN_SIZE = 1000;

    private int SIZE = DEFAULT_SIZE;

    // hash函数的种子因子
    private static final int[] HASH_SEEDS = new int[]{3, 5, 7, 11, 13, 17, 19, 23, 29, 31};

    // 位数组，表示特征
    private BitSet bitSet = null;

    // hash 函数
    private HashFunction[] hashFunctions = new HashFunction[HASH_SEEDS.length];

    public MyBloomFilter() {
        init();
    }

    public MyBloomFilter(int size) {
        if(size > MIN_SIZE) {
            SIZE = size;
        }
        init();
    }

    private void init() {
        bitSet = new BitSet(SIZE);
        for(int i = 0; i < HASH_SEEDS.length; i++) {
            hashFunctions[i] = new HashFunction(SIZE, HASH_SEEDS[i]);
        }
    }

    public void add(Object value) {
        for(HashFunction hf : hashFunctions)
            bitSet.set(hf.hash(value)); // 将hash计算出来的位置为true
    }

    public boolean contains(Object value) {
        boolean result = true;
        for(HashFunction hf : hashFunctions) {
            result = bitSet.get(hf.hash(value));
            // hash函数只要有一个计算出为false，则直接返回
            if(!result)
                return result;
        }
        return result;
    }

    public static class HashFunction {
        int size;
        int seed;

        public HashFunction(int size, int seed) {
            this.size = size;
            this.seed = seed;
        }

        public int hash(Object value) {
            if(value == null)
                return 0;
            else {
                int hash1 = value.hashCode();
                int hash2 = hash1 >>> 16; // 高位的hash值
                int combine = hash1 ^ hash2; // 合并hash值(相当于把高低位的特征结合)
                return Math.abs(combine * seed) % size; // 相乘再求余
            }
        }
    }

    public static void main(String[] args) {
        Integer num1 = 12321;
        Integer num2 = 12345;
        MyBloomFilter myBloomFilter =new MyBloomFilter();
        System.out.println(myBloomFilter.contains(num1));
        System.out.println(myBloomFilter.contains(num2));

        myBloomFilter.add(num1);
        myBloomFilter.add(num2);

        System.out.println(myBloomFilter.contains(num1));
        System.out.println(myBloomFilter.contains(num2));
    }

    // BitSet 应用于找素数的例子
    private static List<Integer> primes(int n) {
        BitSet isPrime = new BitSet(n + 1);
        isPrime.set(2, n + 1); // 默认全部设为true
        // 埃拉托色尼筛法
        for(int i = 2; i * i <= n; i++) {
            if(isPrime.get(i)) {
                for(int j = i * i; j <= n; j += i)
                    isPrime.clear(j);
            }
        }
        List<Integer> res = new ArrayList<>();
        for(int i = 2; i <= n; i++) {
            if(isPrime.get(i))
                res.add(i);
        }
        return res;
    }
}
/* output
false
false
true
true  */
```
