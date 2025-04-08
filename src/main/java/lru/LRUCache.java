package lru;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    int capacity;
    int size;
    Node head;
    Node tail;
    Map<K, Node> datas;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        size = 0;
        head = new Node(null, null, null, null);
        tail = head;
        datas = new HashMap<>();
    }

    public V get(K key) {
        Node node = datas.get(key);
        if(node == null)
            return null;
        else if(node == tail)
            return tail.value;

        Node prev = node.prev;
        Node next = node.next;
        if(node == head) {
            next.prev = null;
            head = next;
        } else {
            prev.next = next;
            next.prev = prev;
        }

        node.prev = tail;
        tail.next = node;
        tail = node;
        tail.next = null;
        return node.value;
    }

    public void put(K key, V value) {
        if(datas.containsKey(key))
            return;
        Node node = new Node(tail, null, key, value);
        datas.put(key, node);
        tail.next = node;
        tail = node;

        if(size == capacity) {
            datas.remove(head.key);
            head = head.next;
            head.prev = null;
        } else if(size < capacity) {
            if(size == 0)
                head = node;
            size++;
        }
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2));
        System.out.println(cache.get(1));
    }

    class Node {
        K key;
        V value;
        Node prev;
        Node next;

        public Node(Node prev, Node next, K key, V value) {
            this.prev = prev;
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }
}
