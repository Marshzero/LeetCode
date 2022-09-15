package LeetCode;

import java.util.*;

public class LC_76_minWindow {
    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        LC_76_minWindow lc76 = new LC_76_minWindow();
        System.out.println(lc76.minWindowLabuladong(s, t));
    }

    /**
     * LeetCode 76 最小覆盖子串
     * <a href="https://leetcode.cn/problems/minimum-window-substring/solution/zui-xiao-fu-gai-zi-chuan-by-leetcode-solution/1407931">官方题解评论</a>
     *
     * @param s 大字符串
     * @param t 小字符串
     * @return s 中涵盖 t 所有字符的最小子串
     */
    public String minWindowLabuladong(String s, String t) {
        // 维护两个Map记录窗口中符合条件的字符及个数
        Map<Character, Integer> need = new HashMap<>(); // need存储所需要的字符及个数
        Map<Character, Integer> window = new HashMap<>();

        for (Character c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0; // 双指针
        int count = 0; // count记录窗口中满足need字符的数量，当count==need.size()时就可以shrink窗口

        int start = 0; //start表示符合最优解的substring的起始位序
        int len = Integer.MAX_VALUE; // 最优解的长度

        //一次遍历找“可行解”
        while (right < s.length()) {
            //更新窗口
            char c = s.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (need.get(c).equals(window.get(c))) {
                    count++;
                }
            }

            //shrink左边界，找符合条件的最优解
            while (count == need.size()) {
                if (right - left < len) {
                    //不断“打擂”找满足条件的len最短值，并记录最短的子串的起始位序start
                    len = right - left;
                    start = left;
                }

                //更新窗口——这段代码逻辑几乎完全同上面的更新窗口
                char d = s.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    // window.put(d,window.get(d)-1)
                    // bug：若一进去就将window对应的键值缩小，就永远不会满足下面的if，while也会一直执行，直到left越界
                    // 因此，尽管和上面对窗口的处理几乎一样，但是这个处理的顺序还是很关键的！要细心！
                    if (need.get(d).equals(window.get(d))) {
                        count--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }
}