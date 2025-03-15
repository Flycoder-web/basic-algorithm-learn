# 经典排序算法

## 冒泡排序
冒泡排序（Bubble Sort）是基于交换的排序，每次遍历需要排序的元素，依次比较相邻的两个元素的大小，如果前一个元素大于后一个元素则两者交换，保证最后一个数字一定是最大的（假设按照从小到大排序），即最后一个元素已经排好序，下一轮只需要保证前面 n-1 个元素的顺序即可。

```java
public static void bubbleSort(int[] num) {
    int size = num.length;
    for(int i = 0; i < size - 1; i++) {
        System.out.println("第" + (i + 1) + "轮交换");
        for(int j = 0; j < size - 1 - i; j++) {
            if(num[j] > num[j + 1]) {
                int temp = num[j + 1];
                num[j + 1] = num[j];
                num[j] = temp;
            }
            System.out.println(Arrays.toString(num));
        }
    }
}
```
时间复杂度是 $O(n^2)$。如果这个数组已经排好序了，我们在每一轮冒泡的时候，都增加一个标识，表示是否有交换，如果没有交换，说明数组顺序已经排好，排序提前结束。这样一来，时间复杂度就是 $O(n)$ 了，因为只遍历了第一轮的 n 个数。

## 选择排序
选择排序就是每次选择剩下的元素中最小的那个元素，与当前索引位置的元素交换，直到所有的索引位置都选择完成。即从第一个元素开始，遍历其后面的元素，找出其后面比它更小的且最小的元素，若有，则两者交换，保证第一个元素最小。然后对于第二个元素也是如此，依此类推，直到最后一个元素。

```java
public static void selectionSort(int[] nums) {
    int size = nums.length;
    int minIndex, temp;
    for(int i = 0; i < size - 1; i++) {
        System.out.println("第" + (i + 1) + "次交换");
        minIndex = i;
        for(int j = i + 1; j < size; j++) {
            if(nums[j] < nums[minIndex])
                minIndex = j;
        }
        System.out.println("交换" + nums[i] + "和" + nums[minIndex]);
        temp = nums[i];
        nums[i] = nums[minIndex];
        nums[minIndex] = temp;
        System.out.println(Arrays.toString(nums));
    }
}
```

由于选择排序只会选择最小的元素进行交换，如果可以保证我们每次选择到的最小元素是第一次出现的（就算后面出现大小相等的元素也不会选择后面的），那么就可以保证它的稳定性，所以选择排序是可以做到稳定的。

## 插入排序
插入排序是依次选择一个元素，插入到前面已经排好序的数组中间，确保它处于正确的位置，当然，这是需要已经排好的顺序数组不断移动。

```java
public static void insertionSort(int[] nums) {
    if(nums == null)
        return;
    int size = nums.length;
    int index, temp;
    for(int i = 1; i < size; i++) {
        // 当前选择插入的元素前面一个索引值
        index = i - 1;
        // 当前需要插入的元素
        temp = nums[i];
        while (index >= 0 && nums[index] > temp) {
            // 往后移，nums[i] 已保存为 temp，所以只需一次赋值
            nums[index + 1] = nums[index];
            index--;
        }
        nums[index + 1] = temp;
        System.out.print("第" + (i) + "轮插入结果：");
        System.out.println(Arrays.toString(nums));
    }
}
```
插入排序在元素基本有序时，由于比较次数会减小，所以较为优势。

由于插入排序只会选择元素插入到适合的位置，只要我们按照原来的顺序遍历，即使相等的两个元素最后排完顺序之后，也会保持原有的相对顺序，所以插入排序是稳定的。

## 希尔排序
希尔排序（Shell's Sort）又称“缩小增量排序”（Diminishing Increment Sort），是插入排序的一种更高效的改进版本，该算法是首次冲破 $O(n^2)$ 的算法之一。

插入排序的痛点在于不管是否是大部分有序，都会对元素进行比较，如果最小数在数组末尾，想要把它移动到数组的头部是较为费劲的。希尔排序是在数组中采用跳跃式分组，按照某个增量 gap 进行分组，分为若干组，每一组分别进行插入排序。再逐步将增量 gap 缩小，再每一组进行插入排序，循环这个过程，直到增量为 1。

以数组 `[98，90，34，56，21，11，43，61]` 为例，元素个数为 8，首次 gap 一般为数组个数的一半，为 4，将元素分为 4 组（跳跃式分组），每组进行插入排序，这样保证了大致位置上大的元素在后面，小的元素在前面：
```
21 11 34 56 98 90 43 61
```
第二轮，gap=4/2=2，将元素分为两组，对每组进行插入排序：
```
21 11 34 56 43 61 98 90
```
最后一轮，gap=2/2=1，即将元素视为一组，相当于对所有元素进行插入排序，这时候元素已经基本有序，只需要做小范围的调整即可：
```
11 21 34 43 56 61 90 98
```
希尔排序是不稳定排序算法，每一组的排序，都确保了这一组的数据基本有序，整体上也是基本有序。

代码实现如下：
```java
public static void shellSort(int[] nums) {
    int times = 1;
    for(int gap = nums.length / 2; gap > 0; gap /= 2) {
        System.out.print("第" + (times++) + "轮希尔排序，gap= " + gap + ", 结果：");
        for(int i = gap; i < nums.length; i++) {
            int j = i;
            int temp = nums[j];
            while (j - gap >= 0 && temp < nums[j - gap]) {
                // 往后移
                nums[j] = nums[j - gap];
                j -= gap;
            }
            nums[j] = temp;
        }
        System.out.println(Arrays.toString(nums));
    }
}
```
希尔排序的算法复杂度受增量序列的选择的影响，这里使用的是建议的增量函数，每次取数组的一半，这时最坏情况下时间复杂度是 $O(n^2)$，最好情况下数组基本有序时是 $O(n)$，平均时间复杂度是 $O(n^{3/2})$。Hibbard 提出了另一个增量序列 $\{1, 3, 7, ..., 2^k-1\}$，这种序列的时间复杂度最坏为 $O(n^{3/2})$。

## 快速排序
快速排序是选择数组中一个数为基准，一次排序，将数组分割为两部分，一部分均小于/等于基准数，另外一部分大于/等于基准数。然后分别对基准数的左右两部分继续排序，直到数组有序。这里体现了分治的思想，还使用到挖坑填数的策略。

假设对数组中索引从 i 到 j 的元素排序，步骤如下：
1. 首先挑选基准数，一般选择第一个数 nums[i]，保存为 standardNum，即 nums[i] 坑位的数被拎出来，留下空坑位。
2. 取数组的左边界索引指针 i，右边界索引指针 j，j 从右往左，寻找到比 standardNum 小的数，停下，写到 nums[i] 的坑位，nums[j] 的坑位空出。索引指针 i 从左往右，寻找比 standardNum 大的数，停下，写到 nums[j] 的坑位，这时 nums[i] 的坑位空出。
3. i 和 j 循环步骤2，直到两个索引指针 i 和 j 相撞，将基准值 standardNum 写到坑位 nums[i] 中，这时 standardNum 左边的数都小于等于它本身，右边的数都大于等于它本身。
4. 分别对 standardNum 左边的子数组和右边的子数组，循环执行前面的 1，2，3，直到不可再分，并且有序。

代码如下：
```java
public static void quickSort(int[] nums) {
    quickSort(nums, 0, nums.length - 1);
}

// 挖坑填数法 Hoare分区
public static void quickSort(int[] nums, int left, int right) {
    System.out.println("[" + left + ", " + right + "]");
    if(left < right) {
        // 左边界，右边界，基准值
        int i = left, j = right, standardNum = nums[left];
        while(i < j) {
            // 从右向左找第一个小于基准值的索引
            while (i < j && nums[j] >= standardNum)
                j--;
            System.out.print("standardNum: " + standardNum + ", 第1个小于基准值的数: " + nums[j]);
            if(i < j) {
                // nums[i]已经被保存到standardNum，将nums[j]写到左边
                nums[i] = nums[j];
                i++;
            }
            // 从左向右找第一个大于等于基准值的索引
            while (i < j && nums[i] < standardNum)
                i++;
            System.out.println("，第一个大于等于基准值的数: " + nums[i]);
            if(i < j) {
                nums[j] = nums[i];
                j--;
            }
        }
        // 将基准值写入中间坑位
        nums[i] = standardNum;
        System.out.println(Arrays.toString(nums));
        quickSort(nums, left, i -1);
        System.out.println(Arrays.toString(nums));
        quickSort(nums, i + 1, right);
    }
}
```
最好情况是每次划分都是五五分，此时递归深度为 $\log_2 n$，每一层都要遍历完 n 个数，所以粗略计算时间复杂度为 $n\log_2 n$。最坏情况是每次划分都是 1 和 n-1 两部分，这时时间复杂度变成 $O(n^2)$。

虽然快排本身没有申请额外的空间，但是递归需要使用栈空间，空间复杂度取决于递归深度，所以空间复杂度是 $\log_2 n$。

由于快速排序会将一个数大间隔的移动到一边，大的数放在右边，小的数放在左边，所以会破坏两个相等的元素的相对顺序，所以它是不稳定的排序算法。

## 归并排序
归并排序体现了非常典型的分治策略。归并的总体思想是先将数组分割，再分割 ... 分割到只有一个元素，然后再两两归并排序，做到局部有序，不断地归并，直到数组又被全部合起来。

代码如下：
```java
public static void mergeSort(int[] nums) {
    mergeSort(nums, 0, nums.length - 1);
}

public static void mergeSort(int[] nums, int left, int right) {
    if(left >= right) return;
    int mid = (left + right) / 2;
    mergeSort(nums, left, mid);
    mergeSort(nums, mid + 1, right);
    merge(nums, left, mid, right);
}

public static void merge(int[] nums, int left, int mid, int right) {
    int nL = mid - left + 1;
    int nR = right - mid;
    // 分别创建左右子数组的临时副本 L 和 R
    int[] L = new int[nL];
    int[] R = new int[nR];
    System.arraycopy(nums, left, L, 0, nL);
    System.arraycopy(nums, mid + 1, R, 0, nR);
    int i = 0, j = 0, k = left;
    // 比较归并到原数组
    while(i < nL && j < nR) {
        if(L[i] < R[j])
            nums[k++] = L[i++];
        else
            nums[k++] = R[j++];
    }
    while(i < nL)
        nums[k++] = L[i++];
    while(j < nR)
        nums[k++] = R[j++];
}
```
归并排序的时间复杂度与快速排序一样，但归并不存在好坏情况，代价是空间复杂度为 $O(n)$。快速排序的平均性能优于归并，因为其常数因子更小。归并排序适合稳定性要求高、数据量大到需要外部排序、或者数据结构是链表的情况。而快速排序适合内存受限、对速度要求高、数据是数组结构的情况。

由于归并排序只会在相邻的子数组做合并操作，而且是严格按照从左到右的顺序，不会出现跳跃交换的情况，所以归并算法是稳定的排序算法。

## 计数排序
计数排序实现了线性时间复杂度的排序，它不是基于比较的，而且该排序仅适合元素值取值范围不大且是整数的情况。

计数排序的步骤：
- 遍历数组，找出最大值和最小值。
- 根据最大值和最小值，初始化对应的统计元素数量的数组。
- 遍历元素，统计元素个数到新的数组。
- 遍历统计的数组，按照顺序输出排序的数组元素。

代码逻辑如下：
```java
public static int[] countSort(int[] nums) {
    int max = nums[0];
    int min = nums[0];
    for (int num : nums) {
        max = Math.max(num, max);
        min = Math.min(num, min);
    }
    int count = max - min;
    int[] values = new int[count + 1];
    // values先存储了各元素的个数
    for(int num : nums)
        values[num - min]++;
    // values再存储小于等于各元素的个数
    for(int i = 1; i < count + 1; i++)
        values[i] = values[i] + values[i - 1];
    int[] result = new int[nums.length];
    // 从原数组的尾部开始复制到result数组
    for(int i = nums.length - 1; i >= 0; i--) {
        result[values[nums[i] - min] - 1] = nums[i];
        values[nums[i] - min]--; // 处理重复元素
    }
    return result;
}
```
计数排序假设元素在 0~k 之间，一共 n 个数，那么只需要遍历 n 个数，就可以统计，统计的时候，只需要遍历 k 个数，就可以将排序的元素移动到数组中。时间复杂度为 O(n+k)，申请了一个统计数组和一个新数组，空间复杂度为 O(n+k)。

由于只做计数，不会大部分交换，根据统计数组来回复排序数组的时候是可以保持元素的相对位置的，所以是稳定排序。

## 桶排序
桶排序，是指用多个桶存储元素，每个桶有一个存储范围，先将元素按照范围放到各个桶中，每个桶中是一个子数组，然后再对每个子数组进行排序，最后合并子数组，成为最终有序的数组。这与计数排序类似，只不过计数排序每个桶只有一个元素，而且桶存储的值为该元素出现的次数。

具体步骤：
- 遍历数组，查找数组的最大最小值，设置桶的区间（非必需），初始化一定数量的桶，每个桶对应一定的数值区间。
- 遍历数组，将每一个数，放到对应的桶中。
- 对每一个非空的桶进行分别排序（桶内部的排序可以选择 JDK 自带排序）。
- 将桶中的子数组拼接成为最终的排序数组。

实现逻辑如下：
```java
public static void bucketSort(int[] nums) {
    int max = nums[0];
    int min = nums[0];
    for(int num : nums) {
        max = Math.max(num, max);
        min = Math.min(num, min);
    }
    int number = (max - min) / nums.length + 1; // 桶的个数算法选择要根据数据分布和个数来确定
    List<List<Integer>> buckets = new ArrayList<>(number);
    for(int i = 0; i < number; i++)
        buckets.add(new ArrayList<>());
    // 将每个元素放入桶
    for(int num : nums) {
        int n = (num - min) / nums.length; // 定位放入的桶
        buckets.get(n).add(num);
    }
    // 对每个桶内部进行排序
    buckets.forEach(Collections::sort);
    // 将桶的元素复制到数组中
    int[] index = {0};
    buckets.forEach(bucket -> {
        bucket.forEach(n -> nums[index[0]++] = n);
    });
}
```
假设需要排序数组的大小为 n，分为 m 个桶。将 n 个数分别放进桶中需要 n 次操作，然后，对每个桶中的数分别进行排序，假设平均每个桶有 n/m 个数，若桶内部排序使用快排，则时间复杂度为 $\frac{n}{m}\log(\frac{n}{m})$，m 个桶就是 $n\log(\frac{n}{m})$，总时间复杂度为：

$$O(n) + n\log(\frac{n}{m}) = O(n(\log(\frac{n}{m})+1))$$

当 n = m 时，时间复杂度演变成为 O(n)，可以看出，其实将元素分到各个桶的时间是固定的 O(n)，总的时间复杂度其实很大程度决定于桶内部的排序算法以及桶的数量。桶的范围越小，桶的数量越多，各个桶里面的元素数量越少，那么排序时间复杂度越小。最差情况是 m = 1，时间复杂度可以是 $O(n^2)$，平均的时间复杂度为 O(n+k)。

由于在中间过程中会申请桶的数量 m，所以空间复杂度为 O(n+m)。

桶排序的稳定性取决于桶内部的排序，如果桶内使用快速排序则整体桶排序表现为不稳定排序。

## 堆排序
堆排序是利用最大堆或最小堆的性质设计的排序算法，是一种选择排序。

以最大堆为例，假设数组为 nums[]，则第 i 个数满足：num[i] >= nums[2i+1] 且 num[i] >= nums[2i+2]，第 i 个数在堆上的左节点就是数组中下标索引 2i+1 的元素，其右节点就是数组中下标索引 2i+2 的元素。

具体步骤：
- 将无序的数组构造成最大堆。
- 将堆顶元素和堆尾元素交换，将最大元素下沉到数组的最后面（末端）。
- 从堆顶重新调整堆，堆大小减一。
- 重复2，3步，直至所有元素下沉。

实现代码如下：
```java
public static void heapSort(int[] nums) {
    // 构造最大堆
    for(int i = nums.length / 2 - 1; i >= 0; i--)
        adjustMaxHeap(nums, i, nums.length);
    int temp;
    // 将堆尾元素放到堆顶，调整堆
    for(int i = nums.length - 1; i >= 0; i--) {
        temp = nums[i];
        nums[i] = nums[0];
        nums[0] = temp;
        adjustMaxHeap(nums, 0, i);
    }
}

// 调整最大堆的迭代实现
public static void adjustMaxHeap(int[] nums, int i, int length) {
    int temp = nums[i];
    for(int j = i * 2 + 1; j < length; j = j * 2 + 1) {
        // 选择左右子节点中较大的那个
        if(j + 1 < length && nums[j + 1] > nums[j])
            j++;
        // 与temp比较，因为迭代中的值向上传递，temp永远是当前的父节点值
        if(nums[j] > temp) {
            nums[i] = nums[j];
            i = j;
        } else {
            break;
        }
    }
    nums[i] = temp;
}
// 调整最大堆的递归实现
public static void maxHeapify(int[] nums, int i, int length) {
    int left = i * 2 + 1;
    int right = left + 1;
    int largest = i;
    // 找出父节点与两个子节点最大的那个
    if(left < length && nums[largest] < nums[left])
        largest = left;
    if(right < length && nums[largest] < nums[right])
        largest = right;
    // 若较大的是子节点，则交换并递归
    if(largest != i) {
        int temp = nums[largest];
        nums[largest] = nums[i];
        nums[i] = temp;
        maxHeapify(nums, largest, length);
    }
}
```
调整最大堆的递归实现比迭代实现更直观，但是其所需栈深度较大，而且递归的函数调用开销略高。

堆实际上是由 n 个数构成的完全二叉树，其深度为 $\log(n+1)$。堆排序的主要时间复杂度由两部分组成，即构建初始化堆和交换堆顶元素以及末尾元素之后的重建堆两个部分。

初始化堆的过程时间复杂度为 O(n)，排序重建堆的时间复杂度为 O(nlogn)，故堆排序的总时间复杂度为 O(nlogn)。

由于在调整堆的时候，会修改相等元素的相对位置，所以堆排序是不稳定的排序算法。

## 基数排序
基数排序的核心思想是通过逐位或逐部分的稳定排序实现整体有序，也不是基于比较的，适用于可以分解为可独立排序的“键”的数据。对于整数数据，就是将其按照位分成不同的数字，按照每个数各位值逐步排序。并且还分为从低位开始（LSD）和从高位开始（MSD）两种方式，这里介绍 LSD。

具体步骤：
- 找到最大值: 确定数组中最大的数，以计算需要处理多少位。
- 逐位排序: 对每一位（个位、十位、百位等）使用稳定的计数排序。

代码实现如下：
```java
public static void radixSort(int[] nums) {
    // 先找到最大值
    int max = nums[0];
    for(int num : nums)
        max = Math.max(num, max);
    // 然后定义指数，从个位到十位到百位...遍历
    for(int exp = 1; max / exp > 0; exp *= 10) {
        int[] tempArray = new int[nums.length];
        int[] count = new int[10]; // 计数数组，0~9
        // 计数排序的步骤
        for(int num : nums)
            count[(num / exp) % 10]++;
        for(int i = 1; i < 10; i++)
            count[i] = count[i] + count[i - 1];
        for(int i = nums.length - 1; i >= 0; i--) {
            tempArray[count[(nums[i] / exp) % 10] - 1] = nums[i];
            count[(nums[i] / exp) % 10]--;
        }
        // 将一轮排序的结果复制回原数组
        System.arraycopy(tempArray, 0, nums, 0, nums.length);
    }
}
```
由于是分别排序，分别收集，所以基数排序是稳定的。假设数组元素 n 个，位数为 d ，每一位的范围是 0 - 9，每一次桶分配需要 O(n) 的时间复杂度，分配之后需要收集，又是 O(n) 时间复杂符，一共进行 d 次分配和收集。所以时间复杂度为 O(d(2n))。一般只使用于整数排序，不适合小数或者文字排序。



