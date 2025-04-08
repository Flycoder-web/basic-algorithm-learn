# LRU 算法
LRU（Least Recently Used）即最近最久未使用，是一种常见的缓存淘汰策略，用于在缓存空间不足时，决定哪些数据应该被移除，从而为新数据腾出空间。其核心思想是：优先淘汰最久未被访问的数据。

## 缓存淘汰算法
为了能高效的利用缓存，我们需要淘汰一些数据，再新加入一些数据，这种动态调整的过程，我们称之为缓存淘汰策略。常见的缓存淘汰算法有：
- FIFO 先进先出置换算法。
- LFU 最少使用置换算法。
- LRU 最近最少使用算法。

### FIFO
FIFO 的核心思想是，如果一个数据最先进入缓存，那么在缓存空间不足时，它应该最早被淘汰。一般实现方式是：使用双向链表 + HashMap 保存数据，使用 HashMap 是因为在读取数据时，时间复杂度为 O(1)，主要流程如下：
- 写入数据：新数据加入 HashMap，若链表空间已满，删除链表头部的数据（同时删除 HashMap 中的数据），再加入新的数据在链表尾部；若链表空间足够，直接将新数据添加在链表尾部。老数据在 HashMap 中以存在，不需操作。
- 读取数据：看 HashMap 中是否存在。

实现如下：
```java
class Node<K, V> {
    K key;
    V value;
    Node<K, V> prev;
    Node<K, V> next;

    public Node(Node<K, V> prev, Node<K, V> next, K key, V value) {
        this.key = key;
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}

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
/* output
1
null
3   */
```
其实 Java 标准库中已有相关实现，即 LinkedHashMap，其内部实现实际上与该思路大致相同，只需要设置负载因子以及重写移除的逻辑即可：
```java
import java.util.LinkedHashMap;
import java.util.Map;

public class FIFOCache<K, V> extends LinkedHashMap<K, V> {
  // 最大容量
  private final int maximumSize;

  public FIFOCache(final int maximumSize) {
    // true 代表按访问顺序排序，false 代表按插入顺序排序
    super(maximumSize, 0.75f, false);
    this.maximumSize = maximumSize;
  }

  // 当元素大于最大容量时，就删除最旧的元素
  @Override
  protected boolean removeEldestEntry(final Map.Entry eldest) {
    return size() > maximumSize;
  }
}
```

### LFU（Least frequently used）
LFU 意为最近最不常用，根据数据的历史访问频率来淘汰旧数据，其核心思想为：如果数据过去被访问多次，那么将来被访问的频率也更高，因此我们在空间不足的情况下需要淘汰使用频率最低的数据，可以使用 哈希表+双向链表/最小堆 保存数据。

LFU 相比 FIFO 多了操作频次步骤，不仅需要保存，更新频次，而且还需要不断根据频次来排序。因此可以使用一个自维护排序的集合，所以双向链表可以使用最小堆（优先队列）或 TreeSet 代替，只要我们重写其排序规则，按照频次排序即可，而频次一样的情况下，按照使用时间排序，越近使用，越优先，即越久越少使用，越可能被淘汰。

操作逻辑：
1. 访问元素：更新频率和时间戳，从 TreeSet 删除旧节点，插入新节点。
2. 插入元素：若键已存在，更新值并更新频率；若容量已满，从 TreeSet 中淘汰最小节点（`TreeSet.first()`）。

代码实现如下：
```java
public class LFUCache<K, V> {
    final int capacity;
    Map<K, Node> map; // 存储键值对，并记录键的访问频率和时间戳
    TreeSet<Node> freqTree; // 按频率和时间戳排序，实现淘汰逻辑。

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
```
访问元素的时间复杂度是 O(log n)，淘汰元素也是一样，每次更新频率需两次 O(log n) 操作。

### LRU（Least recently used）
LRU 意为最近最少使用，算法的核心思想：如果一个数据在最近一段时间没有被访问到，那么在将来它被访问的可能性也很小。也就是说，当限定的空间已存满数据时，应当把最久没有被访问到的数据淘汰。如果利用一个链表来存储，每次插入新数据，将新数据插入到链表尾部，如果空间已经满了，需要将链表头部的数据丢弃，但是这样读取数据的时候需要 O(n) 的时间复杂度，因此需要以空间换取时间的方式，借助额外的空间 HashMap。如果一个缓存被访问使用，LRU 将它放置到缓存的尾部，也就是不容易被淘汰的位置。逻辑如下：
1. 写入数据：对于老数据，不需操作，对于新数据，先添加到 HashMap，如果空间不够，删除链表头部元素（同时删除 HashMap 中的元素），再加入新的数据在链表尾部；如果空间足够，直接插入新数据在链表尾部。
2. 读取数据：若 HashMap 直接获取到 value，将数据移动到链表的尾部并返回；若 HashMap 中不存在，则返回 -1。

LRU-K 中的 K 代表最近使用的次数，LRU-K 的主要是为了解决 LRU 算法“缓存污染”的问题，其核心思想是将“最近使用过 1 次”的判断标准扩展为“最近使用过 K 次”。

相比 LRU，LRU-K 需要多维护一个队列，用于记录所有缓存数据被访问的历史。只有当数据的访问次数达到 K 次的时候，才将数据放入缓存。当需要淘汰数据时，LRU-K 会淘汰第 K 次访问时间距当前时间最大的数据。

如果有热点数据的时候，LRU 的命中率会比较高，但是如果数据分布比较零散，偶发性比较强，很可能导致 LRU 的命中率下降，可能出现不断淘汰，加入，再淘汰。

## LRU 缓存简单实现
现设计和实现一个 LRU 缓存，目标实现获取数据 get 和 写入数据 put，并且我们要求在 O(1) 的时间复杂度内执行完成。

对于读取数据操作，若 HashMap 中不存在，返回 null，若存在，判断它在链表中的位置，如果在尾部，直接返回；如果在头部或中间，需要将它移动到链表尾部，再返回。

写入数据时，若已存在，无操作，不存在则新建一个节点，拼接到链表尾部，再判断空间是否超出，若超出，删除头节点。

实现如下：
```java
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
```
该实现思路在 Java 标准库中同样可以用 LinkedHashMap 实现：
```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
  // 最大容量
  private final int maximumSize;

  public LRUCache(final int maximumSize) {
    // true 代表按访问顺序排序，false 代表按插入顺序排序
    super(maximumSize, 0.75f, true);
    this.maximumSize = maximumSize;
  }

  // 当元素大于最大容量时，就删除最旧的元素
  @Override
  protected boolean removeEldestEntry(final Map.Entry eldest) {
    return size() > this.maximumSize;
  }
}
```
