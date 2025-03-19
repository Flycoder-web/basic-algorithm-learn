package stack;

import java.util.ArrayDeque;
import java.util.Deque;

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

        StringBuilder sb = new StringBuilder();
        for(Character c : stack)
            sb.append(c);
        return sb.reverse().toString();
    }
}
