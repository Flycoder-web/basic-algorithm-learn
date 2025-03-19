package stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyQueue<T> {
    Deque<T> stack1 = new ArrayDeque<>();
    Deque<T> stack2 = new ArrayDeque<>();

    public void offer(T t) {
        stack1.push(t);
    }

    public T poll() {
        if(stack2.isEmpty()) {
            if(stack1.isEmpty())
                throw new RuntimeException("stack is empty!");
            else {
                while(!stack1.isEmpty())
                    stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    public static void main(String[] args) {
        MyQueue<String> queue = new MyQueue<>();
        queue.offer("hello");
        queue.offer("my");
        System.out.println(queue.poll());
        queue.offer("old");
        queue.offer("friends");
        while(!queue.isEmpty())
            System.out.println(queue.poll());
    }
}
