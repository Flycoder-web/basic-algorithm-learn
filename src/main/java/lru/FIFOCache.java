package lru;

import java.util.HashMap;
import java.util.Map;

public class FIFOCache<K, V> {
    Node<K, V> head;
    Node<K, V> tail;
    Map<K, Node<K, V>> datas;
    int capacity;
    int size;

    public FIFOCache(int capacity) {
        this.capacity = capacity;
        size = 0;
        datas = new HashMap<>();
        head = new Node<>(null, null, null, null);
        tail = head;
    }

    public V get(K key) {
        Node<K, V> node = datas.get(key);
        if(node == null)
            return null;
        else return node.value;
    }

    public void put(K key, V value) {
        if(datas.containsKey(key))
            return;

        Node<K, V> node = new Node<>(tail, null, key, value);
        tail.next = node;
        datas.put(key, node);
        tail = node;

        if(size == capacity) {
            // 先从map中删除，再删链表中元素
            datas.remove(head.key);
            head = head.next;
            head.prev = null;
        } else if(size < capacity) {
            if(size == 0)
                head = node;
            size++;
        }
    }

    static class Node<T, U> {
        T key;
        U value;
        Node<T, U> prev;
        Node<T, U> next;

        public Node(Node<T, U> prev, Node<T, U> next, T key, U value) {
            this.key = key;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        FIFOCache<Integer, Integer> cache = new FIFOCache<>(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
    }
}
