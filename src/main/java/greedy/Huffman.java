package greedy;

import java.util.*;

class Node {
    Character val = null;
    Node left;
    Node right;
    int freq;
    Node() {}
    Node(Character val, int freq) {
        this.val = val;
        this.freq = freq;
    }
    @Override
    public String toString() {
        return Character.toString(val);
    }
}

public class Huffman {
    public static void main(String[] args) {
        String s = "hello";
        //String s = "aaaa";
        Map<Character, Integer> cMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            if(cMap.containsKey(c))
                cMap.put(c, cMap.get(c) + 1);
            else
                cMap.put(c, 1);
        }
        Node root = buildTree(cMap);
        List<List<Node>> traces = traverse(root);
        Map<Character, String> binaryMap = binaryMap(traces);
        String encodes = encode(s, binaryMap);
        System.out.println(encodes);
        System.out.println(decode(encodes, root));
    }

    public static String encode(String s, Map<Character, String> binaryMap) {
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()) {
            sb.append(binaryMap.get(c));
        }
        return sb.toString();
    }

    public static String decode(String encodes, Node root) {
        StringBuilder sb = new StringBuilder();
        Node current = root;
        // 从根节点开始，0往左走，1往右走，直到叶节点
        for(char c : encodes.toCharArray()) {
            if(c == '0')
                current = current.left;
            else
                current = current.right;
            if(current.left == null && current.right == null) {
                // 到达叶节点，将指针重新指向根节点
                sb.append(current.val);
                current = root;
            }
        }
        return sb.toString();
    }

    public static Node buildTree(Map<Character, Integer> cMap) {
        int n = cMap.size();
        // 最小优先级队列，优先级为频率
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n2 -> n2.freq));
        cMap.forEach((key, value) -> queue.add(new Node(key, value)));
        // 特殊情况，字符串中只出现了一种字符
        if (queue.size() == 1) {
            Node node = queue.poll();
            Node parent = new Node();
            parent.left = node;
            parent.freq = node.freq;
            return parent;
        }
        // 自底向上构建树
        while(queue.size() > 1) {
            Node node = new Node();
            Node x = queue.poll();
            Node y = queue.poll();
            node.left = x;
            node.right = y;
            node.freq = x.freq + y.freq;
            queue.offer(node);
        }
        return queue.poll();
    }

    public static Map<Character, String> binaryMap(List<List<Node>> traces) {
        Map<Character, String> encodeMap = new HashMap<>();
        for(List<Node> path : traces) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < path.size() - 1; i++) {
                if(path.get(i).left == path.get(i + 1))
                    sb.append("0");
                else
                    sb.append("1");
            }
            encodeMap.put(path.getLast().val, sb.toString()); // List.getLast()在 JDK21 加入
        }
        return encodeMap;
    }

    public static List<List<Node>> traverse(Node root) {
        List<List<Node>> traces = new ArrayList<>();
        dfs(root, new ArrayList<>(), traces);
        return traces;
    }

    // 深度优先搜索遍历所有到叶节点的路径
    private static void dfs(Node node, List<Node> path, List<List<Node>> traces) {
        if(node == null)
            return;
        path.add(node);
        if(node.left == null && node.right == null) {
            traces.add(new ArrayList<>(path));
        }
        dfs(node.left, path, traces);
        dfs(node.right, path, traces);
        path.removeLast(); // List.removeLast()在 JDK21 加入
    }
    // TODO 用栈模拟DFS获取路径
}
