package tree;

import java.util.*;

public class NodeTrace {
    public static void main(String[] args) {
        TreeNode root = TreeNode.initTree();
        System.out.println(trace(root, root.left.left));
        System.out.println(trace_buildParentMap(root, root.left.left));
    }

    // 递归方法
    public static List<TreeNode> trace(TreeNode root, TreeNode node) {
        List<TreeNode> res = new LinkedList<>();
        if(root == null || node == null) return res;
        findPath(root, node, res);
        return res;
    }

    private static boolean findPath(TreeNode current, TreeNode target, List<TreeNode> path) {
        if(current == null) return false;
        path.add(current);
        if(current == target) return true;
        if(findPath(current.left, target, path) || findPath(current.right, target, path))
            return true;
        // 若该节点的左右子树都没找到，就回溯
        path.removeLast();
        return false;
    }

    // 通过构建父节点映射重构路径
    public static List<TreeNode> trace_buildParentMap(TreeNode root, TreeNode node) {
        List<TreeNode> res = new ArrayList<>();
        if(root == null || node == null) return res;

        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        boolean find = buildParentMap(root, node, parentMap);

        if(find) {
            TreeNode current = node;
            while(current != null) {
                res.add(current);
                current = parentMap.get(current);
            }
            Collections.reverse(res);
        }
        return res;
    }

    // 借助栈进行深度优先搜索，也可将栈换成队列就变成了层级搜索
    private static boolean buildParentMap(TreeNode root, TreeNode target, Map<TreeNode, TreeNode> map) {
        Deque<TreeNode> stack = new LinkedList<>();
        if(root == null) return false;
        stack.push(root);
        map.put(root, null);
        while(!stack.isEmpty()) {
            TreeNode top = stack.pop();
            if(top == target)
                return true;
            if(top.left != null) {
                stack.push(top.left);
                map.put(top.left, top);
            }
            if(top.right != null) {
                stack.push(top.right);
                map.put(top.right, top);
            }
        }
        return false;
    }
}
