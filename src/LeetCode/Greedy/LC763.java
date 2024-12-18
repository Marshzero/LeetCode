package LeetCode.Greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LC763 {
    public static void main(String[] args) {
        System.out.println(new LC763().partitionLabels("ababcbacadefegdehijhklij"));
    }

    public List<Integer> partitionLabels(String s) {
        Map<Character, Integer> map = new HashMap<>(); // 字母最后出现的位置
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), i);
        }

        List<Integer> ans = new ArrayList<>();
        int lastIndex = -1;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!map.containsKey(c)) {
                continue;
            }

            end = Math.max(end, map.get(c));
            if (end == i) {
                ans.add(i - lastIndex);
                lastIndex = i;
            }
        }
        return ans;
    }
}
