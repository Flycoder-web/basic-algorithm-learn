package tree;

import java.util.*;

public class FindPathForTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.initTree();
        root.right.right = new TreeNode(3);
        System.out.println(findPath(root, 7));
    }

    public static List<List<Integer>> findPath(TreeNode root, int target) {
        List<List<Integer>> paths = new ArrayList<>();
        if(root == null) return paths;
        Deque<TreeNode> queue = new LinkedList<>();
        int sum = 0;
        findPath(root, target, sum, queue, paths);
        return paths;
    }

    private static void findPath(TreeNode root, int target, int sum,
                                                Deque<TreeNode> queue, List<List<Integer>> paths) {
        if(root != null) {
            sum += root.val; // 累加路径
            queue.push(root);
            // 若和满足且为叶子结点
            if(sum == target && root.left == null && root.right == null) {
                // 添加路径到结果集合
                paths.add(new ArrayList<>(queue.stream()
                        .map(n -> n.val)
                        .toList()
                        .reversed()));
            } else {
                findPath(root.left, target, sum, queue, paths);
                findPath(root.right, target, sum, queue, paths);
            }
            // 处理完一条路径，回溯到上一结点
            queue.pop();
            sum -= root.val;
        }
    }
}
