package tree;

public class BalanceTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.initTree();
        root.right.left.left = new TreeNode(7);
        System.out.println(isBalanced(root));
        System.out.println(isBalanced_ref(root));
    }

    public static boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    // 通过一次后序遍历，在计算高度的同时检查平衡性
    private static int checkHeight(TreeNode root) {
        if(root == null)
            return 0;
        int left = checkHeight(root.left);
        if(left == -1)
            return -1;
        int right = checkHeight(root.right);
        if(right == -1)
            return -1;
        if(Math.abs(left - right) > 1)
            return -1;
        return Math.max(left, right) + 1;
    }

    // 先计算高度再递归判断
    public static boolean isBalanced_ref(TreeNode root) {
        if(root != null) {
            int left = deep(root.left);
            int right = deep(root.right);
            if(Math.abs(left - right) > 1)
                return false;
            else {
                return isBalanced_ref(root.left) && isBalanced_ref(root.right);
            }
        }
        return true;
    }

    private static int deep(TreeNode root) {
        if(root == null)
            return 0;
        return Math.max(deep(root.left), deep(root.right)) + 1;
    }
}
