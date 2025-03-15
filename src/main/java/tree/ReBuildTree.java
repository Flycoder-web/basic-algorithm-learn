package tree;

import java.util.HashMap;
import java.util.Map;

public class ReBuildTree {
    public static void main(String[] args) {
        int[] preOrder = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] inOrder = {4, 7, 2, 1, 5, 3, 8, 6};
        TreeNode root = buildBinaryTree(preOrder, inOrder);
        PreOrder.preOrder_stack(root);
    }

    // 根据前序遍历结果和中序遍历结果重建二叉树
    public static TreeNode buildBinaryTree(int[] preOrder, int[] inOrder) {
        if(preOrder == null || preOrder.length == 0 || inOrder == null || inOrder.length == 0)
            return null;
        Map<Integer, Integer> inMap = new HashMap<>();
        for(int i = 0; i < inOrder.length; i++) {
            inMap.put(inOrder[i], i);
        }
        return buildBinaryTree(preOrder, 0, preOrder.length - 1,
                inOrder, 0, inOrder.length - 1, inMap);
    }

    // inOrder参数实际多余
    private static TreeNode buildBinaryTree(int[] preOrder, int preStart, int preEnd,
                                            int[] inOrder, int inStart, int inEnd,
                                            Map<Integer, Integer> inMap) {
        if(preStart > preEnd || inStart > inEnd) return null;
        int rootVal = preOrder[preStart];
        TreeNode root = new TreeNode(rootVal);

        int rootIndex = inMap.get(rootVal);
        // 左子树结点数量
        int leftSize = rootIndex - inStart;

        root.left = buildBinaryTree(preOrder, preStart + 1,
                preStart + leftSize, inOrder, inStart, rootIndex - 1, inMap);
        root.right = buildBinaryTree(preOrder, preStart + leftSize + 1,
                preEnd, inOrder, rootIndex + 1, inEnd, inMap);
        return root;
    }
}
