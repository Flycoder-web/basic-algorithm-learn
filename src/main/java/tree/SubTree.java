package tree;

public class SubTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.initTree();
        TreeNode root2 = TreeNode.initTree();
        System.out.println(IfSubTree_orig(root.left, root2.left.right));
        System.out.println(isSubTree_ds(root.left, root2.left.right));
    }

    public static boolean IfSubTree_orig(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null)
            return true;
        if(root1 == null || root2 == null)
            return false;
        if(root1.val == root2.val) {
            return IfSubTree_orig(root1.left, root2.left) && IfSubTree_orig(root1.right, root2.right);
        } else {
            boolean res = false;
            if(root1.left != null)
                res = IfSubTree_orig(root1.left, root2);
            if(!res && root1.right != null)
                res = IfSubTree_orig(root1.right, root2);
            return res;
        }
    }

    public static boolean isSubTree_ds(TreeNode root1, TreeNode root2) {
        if(root1 == null || root2 == null)
            return false;
        if(isSameTree_ds(root1, root2))
            return true;
        else
            return isSubTree_ds(root1.left, root2) || isSubTree_ds(root1.right, root2);
    }

    private static boolean isSameTree_ds(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null) // 遍历结束
            return true;
        if(root1 == null || root2 == null)
            return false;
        return root1.val == root2.val && isSameTree_ds(root1.left, root2.left)
                && isSameTree_ds(root1.right, root2.right);
    }
}
