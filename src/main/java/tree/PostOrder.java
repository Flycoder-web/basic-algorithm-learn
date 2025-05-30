package tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class PostOrder {
    public static void main(String[] args) {
        postOrder_recur(TreeNode.initTree());
        System.out.println();
        postOrder_deque(TreeNode.initTree());
    }

    public static void postOrder_recur(TreeNode node) {
        if(node != null) {
            postOrder_recur(node.left);
            postOrder_recur(node.right);
            System.out.print(node.val + " ");
        }
    }

    public static void postOrder_deque(TreeNode node) {
        if(node == null) return;
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> deque = new ArrayDeque<>();
        // 先加入根节点
        stack.push(node);
        while(!stack.isEmpty()) {
            TreeNode top = stack.pop();
            deque.push(top.val);
            // 先压入左边，再压入右边，出栈的时候，就会先右边再左边（调整前序遍历的顺序，然后反转结果）
            if(top.left != null)
                stack.push(top.left);
            if(top.right != null)
                stack.push(top.right);
        }
        deque.forEach(item -> System.out.print(item + " "));
    }


}
