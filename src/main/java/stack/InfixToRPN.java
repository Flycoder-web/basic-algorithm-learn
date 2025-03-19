package stack;

import java.util.*;

public class InfixToRPN {
    public static void main(String[] args) {
        String infix = infixToRPN("19 - 2 * ( 7 - 3 ) + 2 ^ 3 ^ 2");
        System.out.println(infix);
    }

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
}
