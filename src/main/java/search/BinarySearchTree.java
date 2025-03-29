package search;

import tree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class BinarySearchTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.initBinarySearchTree();
        System.out.println(search(root, 5));
        System.out.println(kNode(root, 6));
        System.out.println(kNode_inOrder(root, 6));
    }

    public static TreeNode search(TreeNode root, int value) {
        if(root == null || root.val == value)
            return root;
        if(root.val > value)
            return search(root.left, value);
        else
            return search(root.right, value);
    }

    public static TreeNode kNode(TreeNode root, int k) {
        if(root == null)
            return root;
        else {
            int left = getNum(root.left, k);
            if(left == 1)
                // 左子树恰好有 k-1 个节点
                return root;
            else if(left < 1)
                // 左子树的节点数小于k
                return kNode(root.left, k);
            else
                // 左子树的节点数大于k
                return kNode(root.right, left - 1);
        }
    }

    // k减去node的子树节点
    private static int getNum(TreeNode node, int k) {
        if(node == null)
            return k;
        int left = getNum(node.left, k);
        left--;
        return getNum(node.right, left);
    }

    // 中序遍历的找第k小的节点
    public static TreeNode kNode_inOrder(TreeNode node, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        while(node != null || !stack.isEmpty()) {
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            if(--k == 0)
                return node;
            node = node.right;
        }
        return null;
    }
}
