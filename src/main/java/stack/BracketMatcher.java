package stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class BracketMatcher {
    public static void main(String[] args) {
        System.out.println(isValidBrackets("({[]})"));  // true
        System.out.println(isValidBrackets("{[()]}"));  // true
        System.out.println(isValidBrackets("({[)]}"));  // false
        System.out.println(isValidBrackets("((("));     // false
        System.out.println(isValidBrackets("()[]{"));   // false
    }

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
}
