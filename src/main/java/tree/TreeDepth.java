package tree;

import java.util.LinkedList;
import java.util.Queue;

public class TreeDepth {
    public static void main(String[] args) {
        TreeNode root = TreeNode.initTree();
        root.left.right.left = new TreeNode(7);
//        int depth = treeDepth_recur(root);
        int depth = treeDepth_queue(root);
        System.out.println(depth);
    }

    public static int treeDepth_recur(TreeNode root) {
        if(root == null) return 0;
        return Math.max(treeDepth_recur(root.left), treeDepth_recur(root.right)) + 1;
    }

    public static int treeDepth_queue(TreeNode root) {
        if(root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0; // 初始为0，循环中就不需要进行判断
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
            depth++;
        }
        return depth;
    }
}
