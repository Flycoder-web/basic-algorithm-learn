package problem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class NumElf {
    public static void main(String[] args) throws Exception {
        long num = 4042021211L;
        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        long chunkSize = num / threads;

        // 第一步：先计算每个线程的起点（start）、终点（end）
        long[] starts = new long[threads];
        long[] ends = new long[threads];
        for (int t = 0; t < threads; t++) {
            starts[t] = (t == 0) ? 1 : t * chunkSize + 1;
            ends[t] = (t == threads - 1) ? num : (t + 1) * chunkSize;
        }

        // 第二步：主线程滚动计算每段的起始 sum 和 multi 值
        long[] startSums = new long[threads];
        long[] startMultis = new long[threads];
        long sum = 0;
        long multi = 1;
        int tid = 0;
        for (long i = 1; i <= num; i++) {
            if (i == starts[tid]) {
                startSums[tid] = sum;
                startMultis[tid] = multi;
                tid++;
                if (tid >= threads) break;
            }
            sum = (sum + i) % 100;
            multi = (multi * i) % 100;
        }

        // 第三步：提交并发任务，每个线程从自己的 startSum 和 startMulti 开始
        List<Future<Long>> futures = new ArrayList<>();
        for (int t = 0; t < threads; t++) {
            final long start = starts[t];
            final long end = ends[t];
            final long initSum = startSums[t];
            final long initMulti = startMultis[t];
            futures.add(executor.submit(() -> {
                long localSum = initSum;
                long localMulti = initMulti;
                long count = 0;
                for (long i = start; i <= end; i++) {
                    localSum = (localSum + i) % 100;
                    localMulti = (localMulti * i) % 100;
                    if ((localSum - localMulti + 100) % 100 == 0) {
                        count++;
                    }
                }
                return count;
            }));
        }

        // 第四步：汇总结果
        long total = 0;
        for (Future<Long> f : futures) {
            total += f.get();
        }
        executor.shutdown();
        System.out.println("Total: " + total);
    }
}