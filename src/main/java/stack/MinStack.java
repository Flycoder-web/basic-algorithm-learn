package stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack {
    Deque<Integer> dataStack = new ArrayDeque<>();
    Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int num) {
        // 将最小元素放到最小栈的栈顶
        if(minStack.isEmpty() || minStack.peek() >= num)
            minStack.push(num);
        dataStack.push(num);
    }

    public int pop() {
        if(dataStack.isEmpty())
            throw new RuntimeException("stack is empty!");
        int res = dataStack.pop();
        // 与最小堆栈栈顶元素相对则一样弹出
        if(res == minStack.peek())
            minStack.pop();
        return res;
    }

    public int min() {
        if(minStack.isEmpty())
            throw new RuntimeException("stack is empty!");
        return minStack.peek();
    }

    public int peek() {
        if(dataStack.isEmpty())
            throw new RuntimeException("stack is empty!");
        return dataStack.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(1);
        minStack.push(4);
        // 1
        System.out.println(minStack.min());
        minStack.push(3);
        minStack.push(5);
        minStack.push(0);
        // 0
        System.out.println(minStack.min());
        // 0
        System.out.println(minStack.pop());
        // 1
        System.out.println(minStack.min());
        minStack.push(5);
        // 5
        System.out.println(minStack.peek());
    }
}
