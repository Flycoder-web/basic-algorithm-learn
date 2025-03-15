package tree;

import java.util.*;

// 层次遍历
public class LevelTree {
    public static void main(String[] args) {
        Map<Integer, List<Integer>> map = levelPrint_origin(TreeNode.initTree());
        System.out.println(map);
        Map<Integer, List<Integer>> map2 = levelPrint_dsOptimized(TreeNode.initTree());
        System.out.println(map2);
        List<Integer> list = directPrint(TreeNode.initTree());
        System.out.println(list);
    }

    public static Map<Integer, List<Integer>> levelPrint_origin(TreeNode root) {
        if(root == null) return null;
        Deque<TreeNode> deque = new LinkedList<>();
        Map<Integer, List<Integer>> res = new HashMap<>();
        deque.offer(root);
        int level = 0;
        while(!deque.isEmpty()) {
            List<TreeNode> nodes = new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            while(!deque.isEmpty()) {
                TreeNode node = deque.pop();
                nodes.addLast(node);
                values.addLast(node.val);
            }
            res.put(level++, values);
            nodes.forEach(node -> {
                if(node.left != null)
                    deque.offer(node.left);
                if(node.right != null)
                    deque.offer(node.right);
            });
        }
        return res;
    }

    public static Map<Integer, List<Integer>> levelPrint_dsOptimized(TreeNode root) {
        if(root == null) return new HashMap<>(); // 防止NPE
        // 使用 Queue，不需要双端队列 Deque
        Queue<TreeNode> queue = new LinkedList<>();
        Map<Integer, List<Integer>> res = new LinkedHashMap<>(); // 保持层级顺序
        int level = 0;
        queue.offer(root);
        while(!queue.isEmpty()) {
            int size = queue.size(); // 用size变量代替临时队列
            List<Integer> values = new ArrayList<>(size);
            for(int i = 0; i < size; i++) {
                TreeNode top = queue.poll();
                values.add(top.val);
                if(top.left != null) queue.offer(top.left);
                if(top.right != null) queue.offer(top.right);
            }
            res.put(level++, values);
        }
        return res;
    }

    // 直接返回List<Integer>，未隔开
    public static List<Integer> directPrint(TreeNode root) {
        if(root == null) return new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> res = new ArrayList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            TreeNode top = queue.poll();
            res.add(top.val);
            if(top.left != null) queue.offer(top.left);
            if(top.right != null) queue.offer(top.right);
        }
        return res;
    }
}
