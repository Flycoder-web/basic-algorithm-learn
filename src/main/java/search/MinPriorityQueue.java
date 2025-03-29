package search;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MinPriorityQueue<T extends Comparable<T>> {
    private T[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public MinPriorityQueue(Object[] items) {
        heap = (T[])items;
        size = items.length;
        buildMinHeap();
    }

    @SuppressWarnings("unchecked")
    public MinPriorityQueue(Class<T> type, int n) {
        if(n < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        heap = (T[]) Array.newInstance(type, n);
        size = 0;
    }

    public T getMin() {
        if(isEmpty())
            throw new RuntimeException("heap is empty!");
        return heap[0];
    }

    public T extractMin() {
        if(isEmpty())
            throw new RuntimeException("heap is empty!");
        T res = heap[0];
        heap[0] = heap[--size];
        heapify(0);
        return res;
    }

    public void decreaseKey(int index, T key) {
        if(index >= size || heap[index].compareTo(key) < 0)
            throw new RuntimeException("index is illegal or key is larger than current!");
        heap[index] = key;
        // 注意 index 不要越界，且只需将当前元素与父节点交换
        while(index > 0 && heap[index].compareTo(heap[parent(index)]) < 0) {
            T temp = heap[index];
            heap[index] = heap[parent(index)];
            heap[parent(index)] = temp;
            index = parent(index);
        }
    }

    public void insert(T item, T max) {
        if(size >= heap.length)
            throw new RuntimeException("heap is full!");
        heap[size++] = max;
        decreaseKey(size - 1, item);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T[] toArray() {
        return heap;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private void buildMinHeap() {
        for(int i = size / 2 - 1; i >= 0; i--)
            heapify(i);
    }

    private void heapify(int n) {
        T temp = heap[n];
        for(int i = 2 * n + 1; i < size; i = 2 * n + 1) {
            if(i + 1 < size && heap[i + 1].compareTo(heap[i]) < 0)
                 i = i + 1;
            if(heap[i].compareTo(temp) < 0) {
                heap[n] = heap[i];
                n = i;
            } else {
                break;
            }
        }
        heap[n] = temp;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.stream(heap).limit(size).toArray());
    }

    public static void main(String[] args) {
        Integer[] nums = {2, 1, 3, 4, 6, 5};
        MinPriorityQueue<Integer> mpq = new MinPriorityQueue<>(nums);
        System.out.println(mpq);
        System.out.println(mpq.extractMin());
        System.out.println(mpq);
        mpq.decreaseKey(3, 1);
        System.out.println(mpq);
        mpq.extractMin();
        System.out.println(mpq);
        mpq.insert(1, Integer.MAX_VALUE);
        System.out.println(mpq);
    }
}
