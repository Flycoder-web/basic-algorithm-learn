package tree;

import java.util.LinkedList;
import java.util.Queue;

public class MirrorTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.initTree();
        System.out.println(LevelTree.levelPrint_dsOptimized(root));
        mirror(root);
        System.out.println(LevelTree.levelPrint_dsOptimized(root));
        mirror_queue(root);
        System.out.println(LevelTree.levelPrint_dsOptimized(root));
    }

    public static void mirror(TreeNode root) {
        if(root != null && (root.left != null || root.right != null)) {
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            mirror(root.left);
            mirror(root.right);
        }
    }

    // 这里用队列或栈都行，只是一个是按层遍历，一个是深度优先
    public static void mirror_queue(TreeNode root) {
        if(root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if(node.left != null) queue.offer(node.left);
            if(node.right != null) queue.offer(node.right);
        }
    }
}
