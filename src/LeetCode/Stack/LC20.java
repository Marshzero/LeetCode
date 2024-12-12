package LeetCode.Stack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LC20 {
    public static Map<String, String> map = new HashMap<String, String>(){{
        put(")", "(");
        put("]", "[");
        put("}", "{");
    }};

    public boolean isValid(String s) {
        if (s.length() % 2 == 1) {
            return false;
        }
        Deque<String> deque = new LinkedList<>();
        for (String c : s.split("")) {
            if (map.containsKey(c)) {
                if (deque.isEmpty() || !map.get(c).equals(deque.peekLast())) {
                    return false;
                }
                deque.pollLast();
            } else {
                deque.addLast(c);
            }
        }
        return deque.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(new LC20().isValid("()[]{}"));
    }
}
