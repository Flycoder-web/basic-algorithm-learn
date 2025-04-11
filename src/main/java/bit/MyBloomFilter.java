package bit;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

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
