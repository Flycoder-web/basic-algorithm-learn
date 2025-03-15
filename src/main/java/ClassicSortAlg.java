import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassicSortAlg {
    public static void main(String[] args) {
        int[] nums = new int[]{98,90,34,56,21};
        int[] nums2 = {11,21,34,43, 56, 61, 90, 98};
        //bubbleSort(nums);
        //selectionSort(nums);
        //insertionSort(nums);
        //shellSort(nums);
        //quickSort(nums);
        //mergeSort(nums);
        //nums = countSort(nums);
        //bucketSort(nums);
        //heapSort(nums2);
        radixSort(nums2);
        System.out.println(Arrays.toString(nums2));
    }

    public static void bubbleSort(int[] nums) {
        int size = nums.length;
        int temp;
        for(int i = 0; i < size - 1; i++) {
            System.out.println("第" + (i + 1) + "轮交换");
            for(int j = 0; j < size - 1 - i; j++) {
                if(nums[j] > nums[j + 1]) {
                    temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
            System.out.println(Arrays.toString(nums));
        }
    }

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

    public static void quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    // 挖坑填数法实现
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
        int[] L = new int[nL];
        int[] R = new int[nR];
        System.arraycopy(nums, left, L, 0, nL);
        System.arraycopy(nums, mid + 1, R, 0, nR);
        int i = 0, j = 0, k = left;
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
            int n = (num - min) / nums.length;
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
        if(left < length && nums[largest] < nums[left])
            largest = left;
        if(right < length && nums[largest] < nums[right])
            largest = right;
        if(largest != i) {
            int temp = nums[largest];
            nums[largest] = nums[i];
            nums[i] = temp;
            maxHeapify(nums, largest, length);
        }
    }

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
}
