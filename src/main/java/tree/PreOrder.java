package tree;

import java.util.ArrayDeque;
import java.util.Deque;

public class PreOrder {
    public static void main(String[] args) {
        preOrder_recur(TreeNode.initTree());
        System.out.println();
        preOrder_stack(TreeNode.initTree());
    }

    public static void preOrder_recur(TreeNode node) {
        if(node != null) {
            System.out.print(node.val + " ");
            // 访问左子树
            preOrder_recur(node.left);
            // 访问右子树
            preOrder_recur(node.right);
        }
    }

    public static void preOrder_stack(TreeNode node) {
        Deque<TreeNode> deque = new ArrayDeque<>();
        if(node != null) {
            deque.addFirst(node);
        }
        while(!deque.isEmpty()) {
            TreeNode top = deque.pop();
            System.out.print(top.val + " ");
            if(top.right != null) {
                deque.addFirst(top.right);
            }
            if(top.left != null) {
                deque.addFirst(top.left);
            }
        }
    }
}
