package LeetCode.Stack;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class LC394 {
    public String decodeString(String s) {
        Deque<Character> charDeque = new LinkedList<>();
        Deque<Character> numDeque = new LinkedList<>();
        // 3[a2[c]]
        // 3[2[
        // [a[c
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                numDeque.addLast(c);
            }
            if (c >= 'a' && c <= 'z') {
                charDeque.addLast(c);
            }
            if (c == '[') {
                charDeque.addLast(c);
                numDeque.addLast(c);
            }
            if (c == ']') {
                calc(charDeque, numDeque);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Character c : charDeque) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private void calc(Deque<Character> charDeque, Deque<Character> numDeque) {
        List<Character> list = new ArrayList<>();

        int num = 0;
        int offset = 1;
        numDeque.pollLast(); // '['出栈
        while (!numDeque.isEmpty() && numDeque.peekLast() != '[') {
            num = num + Integer.parseInt(numDeque.pollLast().toString()) * offset;
            offset = offset * 10;
        }

        while (!charDeque.isEmpty() && charDeque.peekLast() != '[') {
            list.add(charDeque.pollLast());
        }
        charDeque.pollLast(); // '['出栈

        for (int i = 0; i < num; i++) {
            for (int j = list.size() - 1; j >= 0; j--) {
                charDeque.addLast(list.get(j));
            }
        }
    }
}
