package tree;

import java.util.ArrayDeque;
import java.util.Deque;

public class InOrder {
    public static void main(String[] args) {
        inOrder_recur(TreeNode.initTree());
        System.out.println();
        inOrder_stack(TreeNode.initTree());
    }

    public static void inOrder_recur(TreeNode node) {
        if(node != null) {
            inOrder_recur(node.left);
            System.out.print(node.val + " ");
            inOrder_recur(node.right);
        }
    }

    public static void inOrder_stack(TreeNode node) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        while(node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.offerFirst(node);
                node = node.left;
            }
            node = stack.pop();
            System.out.print(node.val + " ");
            node = node.right;
        }
    }
}
