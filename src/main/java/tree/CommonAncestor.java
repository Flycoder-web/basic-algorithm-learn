package tree;

import java.util.*;

public class CommonAncestor {
    public static void main(String[] args) {
        TreeNode root = TreeNode.initTree();
        TreeNode common = findUseCommonTrace(root, root.left, root.left.left);
        System.out.println(common);
        TreeNode common2 = lowestCommonAncestor(root, root.left, root.left.left);
        System.out.println(common2);
        TreeNode common3 = findUseParentHash(root, root.left, root.left.left);
        System.out.println(common3);
    }

    // 递归法
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 若当前节点为空，或者等于p或q，直接返回当前节点
        if(root == null || p == root || q == root)
            return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if(left != null && right != null)
            return root;
        return left == null ? right : left;
    }

    // 路径记录法
    public static TreeNode findUseCommonTrace(TreeNode root, TreeNode first, TreeNode second) {
        List<TreeNode> firstTrace = NodeTrace.trace(root, first);
        List<TreeNode> secondTrace = NodeTrace.trace(root, second);
        int min = Math.min(firstTrace.size(), secondTrace.size());
        TreeNode common = null;
        for(int i = 0; i < min; i++) {
            if(firstTrace.get(i).val == secondTrace.get(i).val)
                common = firstTrace.get(i);
        }
        return common;
    }

    // 父节点哈希法
    public static TreeNode findUseParentHash(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        parentMap.put(root, null);
        while(!parentMap.containsKey(p) || !parentMap.containsKey(q)) {
            TreeNode top = stack.pop();
            if(top.left != null) {
                stack.push(top.left);
                parentMap.put(top.left, top);
            }if(top.right != null) {
                stack.push(top.right);
                parentMap.put(top.right, top);
            }
        }
        Set<TreeNode> ancestors = new HashSet<>();
        while (p != null) {
            ancestors.add(p);
            p = parentMap.get(p);
        }
        while(!ancestors.contains(q))
            q = parentMap.get(q);
        return q;
    }
}
