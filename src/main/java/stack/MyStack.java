package stack;

public class MyStack<T> {
    // 定义链表节点
    private static class Node<T> {
        T t;
        Node<T> next;
    }

    private Node<T> head;

    private int size;

    MyStack() {
        head = null;
        size = 0;
    }

    public void push(T t) {
        if(t == null)
            throw new RuntimeException("push item is null!");
        // 创建新节点，并直接指向现head
        Node<T> node = new Node<>();
        node.t = t;
        node.next = head;
        // 更新头节点
        head = node;
        size++;
    }

    public T pop() {
        if(head == null)
            throw new RuntimeException("can't pop from an empty stack!");
        Node<T> node = head;
        head = head.next;
        size--;
        return node.t;
    }

    public T peek() {
        if(head == null)
            throw new RuntimeException("can't peek from an empty stack!");
        return head.t;
    }

    public boolean isEmpty() {
        return head == null;
    }

    // 新增size方法
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        var top = head;
        StringBuilder sb = new StringBuilder();
        while(top != null) {
            sb.append(top.t);
            sb.append(" ");
            top = top.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MyStack<String> stack = new MyStack<>();
        stack.push("java");
        stack.push("lean");
        stack.push("I");
        System.out.println(stack);
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.size);
        System.out.println(stack.peek());
    }
}
