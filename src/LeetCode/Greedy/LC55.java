package LeetCode.Greedy;

public class LC55 {
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }

        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            // 判断当前位置是否，超过最远可达距离
            if (i > max) {
                return false;
            }
            // 最远可达距离大于等于目的地，即可以走到目的地
            if (max >= (nums.length - 1)) {
                return true;
            }
            // 维护最远可达距离，不管之前怎么跳到的，反正能跳到
            max = Math.max(max, i + nums[i]);
        }

        return false;
    }
}
