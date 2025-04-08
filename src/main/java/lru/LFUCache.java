package lru;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class LFUCache<K, V> {
    final int capacity;
    Map<K, Node> map;
    TreeSet<Node> freqTree;

    long timeCounter = 0; // 全局时间戳计数器

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqTree = new TreeSet<>();
    }

    public V get(K key) {
        if(!map.containsKey(key))
            return null;
        // 更新频率和时间戳
        Node node = map.get(key);
        freqTree.remove(node);
        node.freq++;
        node.timestamp = ++timeCounter;
        freqTree.add(node);
        return node.value;
    }

    public void put(K key, V value) {
        if(capacity == 0) return;
        if(!map.containsKey(key)) {
            if(capacity == map.size()) {
                // 淘汰最小频率节点
                Node minNode = freqTree.first();
                freqTree.remove(minNode);
                map.remove(minNode.key);
            }
            // 插入新键
            Node node = new Node(1, ++timeCounter, key, value);
            freqTree.add(node);
            map.put(key, node);
        } else {
            // 更新键
            Node node = map.get(key);
            freqTree.remove(node);
            node.freq++;
            node.timestamp = ++timeCounter;
            freqTree.add(node);
        }
    }

    public static void main(String[] args) {
        LFUCache<Integer, Integer> cache = new LFUCache<>(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2)); // 频率低的已被删除
        System.out.println(cache.get(1));
    }

    // 节点类需实现 Comparable
    class Node implements Comparable<Node> {
        K key;
        V value;
        int freq; // 访问频率
        long timestamp; // 最后一次访问时间戳

        public Node(int freq, long timestamp, K key, V value) {
            this.freq = freq;
            this.timestamp = timestamp;
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Node node) {
            // 优先按频率排序，频率相同则按时间戳排序（淘汰更早的）
            return this.freq == node.freq ? Long.compare(this.timestamp, node.timestamp)
                    : Integer.compare(this.freq, node.freq);
        }
    }
}
