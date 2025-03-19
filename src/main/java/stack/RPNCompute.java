package stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

public class RPNCompute {
    public static void main(String[] args) {
        String RPN = InfixToRPN.infixToRPN("19 - 2 * ( 7 - 3 ) + 2 ^ 3 ^ 2");
        System.out.println(RPN + " --> " + compute(RPN));
    }

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
}
