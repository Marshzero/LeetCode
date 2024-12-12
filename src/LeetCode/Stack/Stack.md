## 栈

##### [20. 有效的括号](https://leetcode.cn/problems/valid-parentheses/)

**算法思路**

括号的匹配，假定字符串是这样"((()))"，这时发现第三个字符和第四个字符会先匹配，而第一个字符最先遍历到，但是后处理，符合先进后出的思想，可以采用栈进行匹配。

1. 遍历字符串每个字符
2. 左括号就放入栈，右括号就和栈顶元素匹配，若不匹配则字符串不合法

**代码**

```java
class Solution {
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
}
```

##### [155. 最小栈](https://leetcode.cn/problems/min-stack/)

**算法思路**

题目要求实现一个栈，需要在常数时间内检索到最小的元素，其他的要求和常规的栈一样。



暴力思路

每次检索时，遍历栈，找到最小的元素，时间复杂度O(n)，空间复杂度O(1)，n为栈的长度



优化思路

空间换时间，用一个变量x记录当前最小的元素，每次入栈出栈要更新x。这时问题来了，入栈时好更新x，但是出栈时，如果栈顶元素就是最小值，出栈后，新的最小值怎么求呢，如果再次遍历一遍栈的话，复杂度仍然不满足题目要求。



再次优化

如果在每个元素入栈时把当前最小的值都记录下来，并且和当前元素对应起来。那么当出栈元素时，就可以直接找到最小值。

入栈元素时，记录当前的最小值。

出栈元素时，从记录好的最小值列表中找对应的最小值。

因为入栈出栈是栈的特性，最小值列表要和栈对应，因此这部分空间也用栈。

1. 两个栈：一个记录元素的栈stack，一个记录当前元素入栈时的最小值的栈minStack
2. 入栈数字x：stack入栈x，计算x与minStack栈顶元素的最小值，入栈minStack
3. 出栈：stack出栈元素，minStack出栈元素
4. 查找栈顶元素：stack栈顶元素
5. 查找最小值：minStack栈顶元素

**代码**

```java
class MinStack {
    Deque<Integer> deque = new LinkedList<>();

    Deque<Integer> minDeque = new LinkedList<>();

    public MinStack() {
        // 首次入栈时，栈为空，因此提前放入最大值供比较
        minDeque.addLast(Integer.MAX_VALUE);
    }

    public void push(int val) {
        deque.addLast(val);
        minDeque.addLast(Math.min(minDeque.peekLast(), val));
    }

    public void pop() {
        deque.pollLast();
        minDeque.pollLast();
    }

    public int top() {
        return deque.peekLast();
    }

    public int getMin() {
        return minDeque.peekLast();
    }
}
```

##### [394. 字符串解码](https://leetcode.cn/problems/decode-string/)

**思路**

模拟字符串的重复和拼接，用两个栈，一个存储数字，一个存储字符

**代码**

```java
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
```

**官方题解**

官方题解写的比较简洁

- `Character.isDigit(ch)`: 使用工具类判断是否为数字字符
- `currentString`: 使用该变量存储需要重复的字符串，而不是栈，比较简洁

```java
class Solution {
   public String decodeString(String s) {
       Deque<Integer> countStack = new ArrayDeque<>(); // 存储数字
       Deque<String> stringStack = new ArrayDeque<>(); // 存储字符串
       String currentString = ""; // 当前解码字符串
       int k = 0; // 当前的倍数

       for (char ch : s.toCharArray()) {
           if (Character.isDigit(ch)) {
               k = k * 10 + (ch - '0'); // 处理多位数
           } else if (ch == '[') {
               // 遇到 '['，将当前的字符串和数字推入各自的栈
               countStack.push(k);
               stringStack.push(currentString);
               currentString = ""; // 重置当前字符串
               k = 0; // 重置倍数
           } else if (ch == ']') {
               // 遇到 ']'，解码
               StringBuilder temp = new StringBuilder(stringStack.pop());
               int repeatTimes = countStack.pop();
               for (int i = 0; i < repeatTimes; i++) {
                   temp.append(currentString); // 重复当前字符串
               }
               currentString = temp.toString(); // 更新当前字符串
           } else {
               // 如果是字母，直接加到当前字符串
               currentString += ch;
           }
       }

       return currentString;
    }
}
```

##### [739. 每日温度](https://leetcode.cn/problems/daily-temperatures/)

单调栈

##### [84. 柱状图中最大的矩形](https://leetcode.cn/problems/largest-rectangle-in-histogram/)

**题解视频讲的思路挺好的，先想到暴力解法，然后再有优化思路，暴力解法会提供一些思路的**

单调栈

题解链接：https://leetcode.cn/problems/largest-rectangle-in-histogram/solutions/266844/zhu-zhuang-tu-zhong-zui-da-de-ju-xing-by-leetcode-/?envType=study-plan-v2&envId=top-100-liked