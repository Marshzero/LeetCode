package LeetCode.Stack;

import java.util.Deque;
import java.util.LinkedList;

public class LC84 {
    public static void main(String[] args) {
        System.out.println(new LC84().largestRectangleArea2(new int[]{2, 1, 5, 6, 2, 3}));
        System.out.println(new LC84().largestRectangleArea2(new int[]{2, 4}));
        System.out.println(new LC84().largestRectangleArea2(new int[]{4, 2}));
        System.out.println(new LC84().largestRectangleArea2(new int[]{1, 1}));
    }

    /**
     * 从暴力解法，引入单调栈后的优化解法
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        int left;
        int right;
        int area = 0;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < heights.length; i++) {
            while (!deque.isEmpty() && heights[deque.peekLast()] > heights[i]) {
                int index = deque.pollLast();
                left = deque.isEmpty() ? -1 : deque.peekLast();
                right = i;
                area = Math.max(area, heights[index] * (right - left - 1));
            }
            deque.addLast(i);
        }
        while (!deque.isEmpty()) {
            int index = deque.pollLast();
            left = deque.isEmpty() ? -1 : deque.peekLast();
            right = heights.length;
            area = Math.max(area, heights[index] * (right - left - 1));
        }
        return area;
    }

    public int largestRectangleArea2(int[] heights) {
        int length = heights.length;
        int[] left = new int[length]; // heights每个元素左侧第一个比该元素小的元素的下标
        int[] right = new int[length]; // heights每个元素右侧第一个比该元素小的元素的下标
        Deque<Integer> deque = new LinkedList<>(); // 单调栈计算第一个小的元素

        // 计算左侧第一个小的元素下标
        for (int i = 0; i < length; i++) {
            while (!deque.isEmpty() && heights[deque.peekLast()] >= heights[i]) {
                deque.pollLast();
            }
            // 单调递增的栈，当栈无元素时即左侧没有比当前元素还小的
            left[i] = deque.isEmpty() ? -1 : deque.peekLast();
            deque.addLast(i);
        }

        deque.clear();
        // 计算右侧第一个小的元素下标
        for (int i = length - 1; i >= 0; i--) {
            while (!deque.isEmpty() && heights[i] <= heights[deque.peekLast()]) {
                deque.pollLast();
            }
            // 单调递减的栈，当栈无元素时即右侧没有比当前元素还小的
            right[i] = deque.isEmpty() ? length : deque.peekLast();
            deque.add(i);
        }

        int area = 0;
        for (int i = 0; i < length; i++) {
            area = Math.max(area, heights[i] * (right[i] - left[i] - 1));
        }
        return area;
    }
}
