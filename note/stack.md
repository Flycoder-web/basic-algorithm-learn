# 堆栈
## 栈的手动实现
数据结构里面的栈是限定仅在表尾进行插入或者删除的线性表，所谓的表尾，就是我们所称的栈顶，相应的，我们可以称表头为栈底。栈的最重要的特性，是后进先出（Last in first out），也称为 LIFO 结构

类比场景：往一个箱子里面放入一些书籍，取书时，肯定是最后放进去的书，最先被取出来。

前面介绍非递归时，总是优先考虑使用栈，因为方法递归本身就是天然的栈。

Java 标准库中已有栈结构，而且最佳选择是使用 Deque 接口及其实现类（如 ArrayDeque 或 LinkedList），不推荐使用 Stack 类，因为 Stack 继承自 Vector，而 Vector 是早期线程安全的动态数组实现，其同步开销可能降低性能，且 Stack 的方法（如 push、pop）与 Vector 的继承关系导致设计不够清晰，且无法选择其他底层数据结构。

Deque 提供了 `push()`（压栈）、`pop()`（弹栈）和 `peek()`（查看栈顶）等适合栈操作的方法。对于它的两个实现，ArrayDeque：基于数组实现，内存连续，访问效率高，适合大多数场景；LinkedList：基于链表实现，适合频繁插入/删除但随机访问较少的场景。若需要线程安全，可使用 ConcurrentLinkedDeque 或用 `Collections.synchronizedDeque(new ArrayDeque<>())` 包装。

如果要手动实现简单的堆栈，先定义一个链表节点，节点里面包含数据，以及下一个节点的引用，定义方法如下：
- 初始化：头结点置为 null。
- `push()`：压栈操作，创建新节点，并直接将其连接到当前 head 节点，再更新 head 节点为新节点。
- `pop()`：出栈操作，若头节点为空，说明栈里没有元素，否则取出第一个元素，更新头结点指针到下一个节点的位置。
- `peek()`：获取栈顶元素，但不弹出。
- `isEmpty()`：判断栈是否为空。

实现如下：
```java
public class MyStack<T> {
    // 定义链表节点
    private static class Node<T> {
        T t;
        Node<T> next;
    }

    private Node<T> head;

    private int size; // 新增 size 字段

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
    }
}
```
这样 `push()`、`pop()`、`peek()` 都是 $O(1)$ 的时间复杂度。

## 两个栈实现队列
栈结构的特性是先进后出，而队列的特性是先进先出，就像水管一样，前面的水先流出来。

如果不直接使用队列，而用栈实现队列，则需要两个栈才能实现一个队列。因为栈本身是先进后出的，假设我们需要先进先出，那么势必需要另外一个堆栈保存数据。数据进入一个堆栈，出来时是逆序的，但是数据依次进入两个堆栈，出来是正序的。

有两个栈 stack1 和 stack2，如果有新的数据进入，那么可以直接 push 到 stack1 中。如果要取出数据，那么我们优先取出 stack2 的数据，如果 stack2 里面的数据是空的，那么我们需要把 stack1 全部的数据倒入 stack2，再从 stack2 取数据。即放入数据永远都是放在 stack1，取出数据必须从 stack2 取。实现如下：
```java
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
```
这样加入队列的时间复杂度是 $O(1)$，而出队列的时间复杂度是 $O(n)$，借助了两个栈结构，空间复杂度为 $O(n)$。

## 实现快速获取最小数的栈
如果把很多数值放入栈中，希望能获取到该栈中最小的元素。直接的方法是将栈中的所有元素取出，不断对比得到最小元素，然后又全部放回去栈中，时间复杂度是 $O(n)$。

能否可以在保持栈特性下，提供一个方法 `min()`，将获取最小元素的时间复杂度保持在 $O(n)$。

可以使用空间换时间的做法，使用两个栈，一个存储所有元素的 datas stack，另一个存储最小值 d 的 mins stack。

push 一个元素时，dataStack 正常操作，对于 minStack，当最小栈为空，或者栈顶元素大于等于待 push 的数时，将该数 push 进栈顶，保持栈顶元素最小。

pop 一个元素时，先 pop 出 dataStack 的元素，然后查看 minStack 的栈顶元素是否等于这个元素，相等时也要一起 pop 出去。

具体实现如下：
```java
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
        // 与最小堆栈栈顶元素相同则一样弹出
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
```
这样，最小值栈的栈顶元素就是最小的元素，那么可以做到 $O(1)$ 的时间复杂度取出最小值。同时，也保留了栈结构先进后出的特性。

## 括号匹配
现在要实现字符串中的括号匹配，包括圆括号、方括号和大括号。括号匹配的常见方法是使用栈结构，思路如下：
1. 遍历字符串，遇到左括号 "(", "[", "{" 就入栈。
2. 遇到右括号 ")", "]", "}" 时：若栈为空，说明右括号没有对应的左括号，匹配失败；否则，弹出栈顶元素，并检查是否是对应的左括号，如果不是，匹配失败。
3. 遍历结束后，如果栈不为空，说明有未匹配的左括号，匹配失败。

实现如下：
```java
public static boolean isValidBrackets(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    Map<Character, Character> mapping = Map.of(')', '(', ']', '[', '}', '{');
    for(Character c : s.toCharArray()) {
        if(mapping.containsValue(c))
            stack.push(c);
        else if (mapping.containsKey(c)) {
            if(stack.isEmpty() || stack.pop() != mapping.get(c))
                return false;
        }
    }
    return stack.isEmpty();
}
```
时间复杂度 $O(n)$，空间复杂度 $O(n)$。

## 中序表达式转为逆波兰表达式
逆波兰表达式又叫做后缀表达式，是波兰逻辑学家 J・卢卡西维兹（J・ Lukasiewicz）于 1929 年首先提出的一种表达式的表示方法，这种表达式把运算量写在前面，把算符写在后面，且不需要括号来表示运算的优先级，可以简化计算机的计算过程。

中序表达式就是我们平时用的表达式，比如 `a + b * c`，运算符在中间，转换为后缀表达式是 `a b c * +`。

常见的算法是使用一个操作符栈和一个输出列表，遍历中序表达式的每个元素，如果是操作数就直接添加到输出；如果是操作符，则与栈顶的操作符比较优先级，如果当前操作符的优先级小于等于栈顶的，就弹出栈顶到输出，直到栈顶的优先级更低或者栈空，然后将当前操作符压栈。遇到左括号直接压栈，遇到右括号则弹出栈顶到输出，直到遇到左括号，左括号弹出但不输出。

对于运算符，如果考虑结合性，`+`、`-`、`*`、`/`都是左结合，而 `^` 是右结合。对于左结合的运算符，遇到相同优先级的运算符，先弹出栈顶的运算符，再入栈新的运算符（即按自然顺序）。对于右结合的运算符，直接入栈，不弹出栈顶的相同优先级运算符（即同一右结合运算符，先计算右面的）。

实现如下：
```java
public static String infixToRPN(String expression) {
    Map<String, Integer> precedence = Map.of("+", 1, "-", 1, "*", 2, "/", 2, "^", 3);
    Map<String, Character> associativity = Map.of("+", 'L', "-", 'L', "*", 'L', "/", 'L', "^", 'R');
    List<String> outputs = new ArrayList<>();
    Deque<String> operators = new ArrayDeque<>();

    // 假设输入已分隔，例如 "3 + 5 * ( 2 - 8 )"
    String[] tokens = expression.split(" ");

    for(String token : tokens) {
        if(token.matches("\\d+")) // 匹配数字
            outputs.add(token);
        else if(precedence.containsKey(token)) { // 运算符
            while (!operators.isEmpty() && !operators.peek().equals("(") &&
                    ((associativity.get(token) == 'L' && precedence.get(token) <= precedence.get(operators.peek()))
                            || (associativity.get(token) == 'R' && precedence.get(token) < precedence.get(operators.peek())))) {
                outputs.add(operators.pop());
            }
            operators.push(token);
        } else if(token.equals("(")) {
            operators.push(token);
        } else if(token.equals(")")) {
            while(!operators.isEmpty() && !operators.peek().equals("("))
                outputs.add(operators.pop());
            operators.pop(); // 弹出左括号 "("
        }
    }

    // 处理剩余运算符
    while(!operators.isEmpty())
        outputs.add(operators.pop());

    return String.join(" ", outputs);
}
```

## 逆波兰表达式的求解
从逆波兰表达式中可以看出，运算符总是出现在两个数值后面，当我们发现操作符号时，前面肯定有需要的两个数值。计算出来的数值同样作为操作数，与前面的数值进行计算。所以可以借助栈：
- 如果是操作数，则将操作数压入栈中。
- 如果是运算符，则将两个操作数出栈，其中先出栈的是右操作数，后出栈的是左操作数，使用运算符对两个操作数进行运算，将运算得到的新操作数入栈。

直到整个逆波兰表达式遍历完成，堆栈里面只有一个元素，该元素就是为逆波兰表达式的值。实现如下：
```java
public static String compute(String expression) {
    Set<String> operators = Set.of("+", "-", "*", "/", "^");
    Deque<String> stack = new ArrayDeque<>();

    String[] tokens = expression.split(" ");
    for(String token : tokens) {
        // 操作数直接入栈
        if(token.matches("\\d+"))
            stack.push(token);
        // 操作符则取出两个数值并计算再入栈
        else if (operators.contains(token)) {
            double num1 = Double.parseDouble(stack.pop());
            double num2 = Double.parseDouble(stack.pop());
            switch (token) {
                case "+" -> stack.push(String.valueOf(num2 + num1));
                case "-" -> stack.push(String.valueOf(num2 - num1));
                case "*" -> stack.push(String.valueOf(num2 * num1));
                case "/" -> stack.push(String.valueOf(num2 / num1));
                case "^" -> stack.push(String.valueOf(Math.pow(num2, num1)));
                default -> throw new RuntimeException("No this operator");
            }
        }
    }
    return stack.pop();
}
```
时间和空间复杂度都是 $O(n)$。

## 每日温度问题
给定一个气温列表，比如 temperatures = [63,54,76,56,37,89,23,74]，要输出每个位置之后需要等多少天气温才会比今天高，如果之后都没有更高的温度，就输出 0，即输出 [2,1,3,2,1,0,1,0]。

比如第一个温度是 63，之后第二天是 54，比它低，第三天 76 比 63 高，所以需要等 2 天。那示例中的第一个元素输出是 2。那我们的任务是，对于每个元素，找到下一个比它大的温度的距离。

首先是暴力解法，对于每个元素 i，遍历 i+1 到末尾，找到第一个比 `temperatures[i]` 大的，记录天数差，这样时间复杂度是 $O(n^2)$：
```java
public static int[] awaitDays_intuit(int[] temperatures) {
    int[] res = new int[temperatures.length];
    for(int i = 0; i < temperatures.length; i++) {
        for(int j = i + 1; j < temperatures.length; j++) {
            // 找到第一个比它温度大的，计算距离
            if(temperatures[j] > temperatures[i]) {
                res[i] = j - i;
                break;
            }
        }
    }
    return res;
}
```
更高效的方法是使用单调栈，单调栈通常用于这类寻找下一个更大元素的问题。栈里保存的是索引，而栈中的元素对应的温度是单调递减的。当遇到一个温度比栈顶元素对应的温度高时，就弹出栈顶，并计算两者的索引差，也就是天数。这样就可以在一次遍历中得到结果。

初始化一个结果数组，初始值全为 0。然后维护一个栈，栈中保存的是索引。栈中的索引对应的温度是单调递减的。遍历每个温度，当当前温度比栈顶的温度高时，弹出栈顶元素，并计算当前索引和栈顶索引的差，作为结果中该栈顶元素位置的值。然后将当前索引入栈：
```java
public static int[] awaitDays_MonoStack(int[] temperatures) {
    Deque<Integer> stack = new ArrayDeque<>();
    int[] res = new int[temperatures.length];
    for(int i = 0; i < temperatures.length; i++) {
        // 只要遇到比栈顶温度高的索引，就计算栈顶索引对应的天数
        // 保证栈顶对应温度最低
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int index = stack.pop();
            res[index] = i - index;
        }
        stack.push(i);
    }
    return res;
}
```
时间复杂度为 $O(n)$。

## 退格字符处理
假设现在输入字符，要删除时只能使用 `#` 来代替退格，那么输入两个包含 `#` 的字符串，如何判断两个字符串是不是一样呢？

要得出字符串的退格后的字符串，比如 `AB###CAD#`，最终字符应该是 `CA`，只要借助堆栈结构，读取每一个字符。如果不是 #，就选择压入栈中，如果是 #，就看堆栈是不是为空，如果不为空，就弹出一个元素，相当于删除。处理完之后，堆栈里面剩下的元素，就是处理完退格键之后的元素。

那么，只要两个字符串处理之后的 stack 转成字符串之后相等，就是一样的结果，代码如下：
```java
public class BackDeleteCharacter {
    public static void main(String[] args) {
        String s1 = "AB###CAD#";
        String s2 = "ABC###CA";
        System.out.println(deleteResult(s1));
        System.out.println(isEqual(s1, s2));
    }

    public static boolean isEqual(String s1, String s2) {
        return deleteResult(s1).equals(deleteResult(s2));
    }

    public static String deleteResult(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for(Character c : s.toCharArray()) {
            if(!c.equals('#')) {
                stack.push(c);
            } else {
                if(!stack.isEmpty())
                    stack.pop();
            }
        }

        // 如果只是判断两个退格后的结果是否相等，可直接返回 stack.toString()
        StringBuilder sb = new StringBuilder();
        for(Character c : stack)
            sb.append(c);
        return sb.reverse().toString();
    }
}
```
时间复杂度和空间复杂度都是 $O(n)$。