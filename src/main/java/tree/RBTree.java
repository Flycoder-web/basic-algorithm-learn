package tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class RBTree<T extends Comparable<T>> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private Node<T> root;

    public RBTree() {
        root = null;
    }

    static final class Node<T extends Comparable<T>> {
        T key;
        Node<T> left;
        Node<T> right;
        Node<T> parent;
        boolean color = BLACK;

        public Node(T key, Node<T> parent) {
            this.key = key;
            this.parent = parent;
        }

        public Node(T key, boolean color, Node<T> parent, Node<T> left, Node<T> right) {
            this.key = key;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public T getKey() {
            return key;
        }

        @Override
        public String toString() {
            return key + (color == RED ? "(R)" : "(B)");
        }
    }

    private Node<T> parentOf(Node<T> node) {
        return node != null ? node.parent : null;
    }

    private Node<T> leftOf(Node<T> node) {
        return node != null ? node.left : null;
    }

    private Node<T> rightOf(Node<T> node) {
        return node != null ? node.right : null;
    }

    private boolean colorOf(Node<T> node) {
        return node == null ? BLACK : node.color;
    }

    private void setColor(Node<T> n, boolean color) {
        if(n != null)
            n.color = color;
    }

    public Node<T> search(T key) {
        return search(root, key);
    }

    private Node<T> search(Node<T> n, T key) {
        if(n == null) {
            return n;
        }

        int cmp = key.compareTo(n.key);
        if(cmp < 0) {
            return search(n.left, key);
        } else if(cmp > 0) {
            return search(n.right, key);
        }
        return n;
    }

    private Node<T> minimum(Node<T> tree) {
        if(tree == null) {
            return tree;
        }
        while(tree.left != null) {
            tree = tree.left;
        }
        return tree;
    }

    private T minimum() {
        Node<T> minNode = minimum(root);

        if(minNode != null) {
            return minNode.key;
        }
        return null;
    }

    private T maximum() {
        Node<T> maxNode = maximum(root);

        if(maxNode != null) {
            return maxNode.key;
        }
        return null;
    }

    private Node<T> maximum(Node<T> tree) {
        if(tree == null) {
            return tree;
        }
        while(tree.right != null) {
            tree = tree.right;
        }
        return tree;
    }

    public void inOrder() {
        System.out.print("in order: ");
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node<T> node) {
        if(node != null) {
            inOrder(node.left);
            System.out.print(node + " ");
            inOrder(node.right);
        }
    }

    public void levelPrint() {
        System.out.println("level print: ");
        levelPrint(root);
        System.out.println();
    }

    public void levelPrint(Node<T> node) {
        if(node == null) return;
        Queue<Node<T>> queue = new ArrayDeque<>();
        queue.offer(node);
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                Node<T> curr = queue.poll();
                System.out.print(curr + "(" + (curr.parent == null ? "null" : curr.parent.key)  + ") ");
                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }
            System.out.println();
        }
    }

    public void insert(T key) {
        Node<T> t = root;
        if(t == null) {
            root = new Node<>(key, null);
            return;
        }

        if(key == null)
            return;

        int cmp = 0;
        Node<T> parent = t.parent;
        while(t != null) {
            parent = t;
            cmp = key.compareTo(t.key);
            if(cmp > 0) {
                t = t.right;
            } else if(cmp < 0) {
                t = t.left;
            } else {
                t.key = key;
                return;
            }
        }

        insertNode(key, parent, cmp < 0);
    }

    public void remove(T key) {
        Node<T> node = search(key);
        if(node != null) {
            deleteNode(node);
        }
    }

    private void insertNode(T key, Node<T> parent, boolean isLeft) {
        Node<T> node = new Node<>(key, parent);
        if(isLeft) {
            parent.left = node;
        } else {
            parent.right = node;
        }
        fixAfterInsertion(node);
    }

    private Node<T> successor(Node<T> node) {
        if(node == null) {
            return null;
        } else if(node.right != null) { // 存在右子节点，则为右子树的最左节点
            Node<T> r = node.right;
            while(r.left != null) {
                r = r.left;
            }
            return r;
        } else { // 没有右子树，则向上递归，找第一个左子树父节点
            Node<T> p = node.parent;
            Node<T> t = node;
            while(p != null && t == p.right) {
                t = p;
                p = p.parent;
            }
            return p;
        }
    }

    private void rotateRight(Node<T> p) {
        if(p != null) {
            // 取 p 的左子节点
            Node<T> l = p.left;
            // 将 l 的右子节点转移到 p 的左子节点
            p.left = l.right;
            if(l.right != null) l.right.parent = p;
            // 将 p 的父节点变为 l 的父节点
            l.parent = p.parent;
            if(p.parent == null) {
                root = l;
            } else if (p.parent.right == p) {
                p.parent.right = l;
            } else {
                p.parent.left = l;
            }
            // p 和 l 的父子关系转换
            l.right = p;
            p.parent = l;
        }
    }

    private void rotateLeft(Node<T> p) {
        if(p != null) {
            // 取 p 的右子节点
            Node<T> r = p.right;
            // 将 r 的左子节点转移给 p 的右子节点
            p.right = r.left;
            if(r.left != null) r.left.parent = p;
            // 将 p 的父节点变为 l 的父节点
            r.parent = p.parent;
            if(p.parent == null) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            // p 和 l 的父子关系转换
            r.left = p;
            p.parent = r;
        }
    }

    private void fixAfterInsertion(Node<T> x) {
        setColor(x, RED);

        while(x != null && x != root && x.parent.color == RED) {
            if(parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                Node<T> u = rightOf(parentOf(parentOf(x)));
                if(colorOf(u) == RED) {
                    // case 1
                    setColor(parentOf(x), BLACK);
                    setColor(u, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if(x == rightOf(parentOf(x))) {
                        // case 2
                        rotateLeft(parentOf(x));
                        x = leftOf(x);
                    }
                    // case 3
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                Node<T> u = leftOf(parentOf(parentOf(x)));
                if(colorOf(u) == RED) {
                    // case 1
                    setColor(parentOf(x), BLACK);
                    setColor(u, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if(x == leftOf(parentOf(x))) {
                        // case 2
                        rotateRight(parentOf(x));
                        x = rightOf(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        setColor(root, BLACK);
    }

    private void deleteNode(Node<T> p) {
        if(p == null) return;
        // 两个子树都存在的情况
        if(p.left != null && p.right != null) {
            Node<T> s = successor(p);
            p.key = s.key; // 用 s 的值覆盖掉 p
            p = s; // 将 p 指向后继节点
        }
        // 这时的 p 最多只有一个子树
        Node<T> replacement = p.left != null ? p.left : p.right;

        if(replacement != null) {
            // 只有一个子树的情况
            replacement.parent = p.parent;
            if(p.parent != null) {
                if(p == p.parent.left) {
                    p.parent.left = replacement;
                } else {
                    p.parent.right = replacement;
                }
            }
            p.parent = p.left = p.right = null;
            if(colorOf(p) == BLACK) {
                fixAfterDeletion(replacement);
            }
        } else if(p.parent == null) {
            // p 没有子节点，且为根节点
            root = null;
        } else {
            // p 没有子节点的一般情况
            if(colorOf(p) == BLACK) {
                fixAfterDeletion(p);
            }

            // 直接删除
            if(p == p.parent.left) {
                p.parent.left = null;
            } else {
                p.parent.right = null;
            }
            p.parent = null;
        }
    }

    private void fixAfterDeletion(Node<T> x) {
        while(x != root && colorOf(x) == BLACK) {
            if(x == leftOf(parentOf(x))) {
                Node<T> sib = rightOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    // case 1
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
                    // case 2
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        // case 3
                        setColor(sib, RED);
                        setColor(leftOf(sib), BLACK);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }

                    // case 4
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else {
                // symmetric
                Node<T> sib = leftOf(parentOf(x));
                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(sib, RED);
                        setColor(rightOf(sib), BLACK);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }

                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }

        setColor(x, BLACK);
    }


    public static void main(String[] args) {
        int[] nums = {20, 50, 35, 72, 91, 67, 10, 56, 83};
        RBTree<Integer> tree = new RBTree<Integer>();
        for (int num : nums) {
            tree.insert(num);
            tree.inOrder();
        }
        System.out.printf("最小值: %s\n", tree.minimum());
        System.out.printf("最大值: %s\n", tree.maximum());
        for (int num : nums) {
            tree.remove(num);
            System.out.printf("删除节点: %d\n", num);
            tree.inOrder();
        }
    }
}
