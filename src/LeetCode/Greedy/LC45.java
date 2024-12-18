package LeetCode.Greedy;

public class LC45 {
    public static void main(String[] args) {
        System.out.println(new LC45().jump(new int[]{2, 3, 1, 1, 4}));
        System.out.println(new LC45().jump(new int[]{1, 1, 1, 1}));
    }

    public int jump(int[] nums) {
        int length = nums.length;
        if (length == 1) {
            return 0;
        }

        int[] maxInstance = new int[length];
        for (int i = 0; i < length; i++) {
            maxInstance[i] = Math.min(i + nums[i], length - 1);
        }

        int step = 0;
        for (int i = 0; i < length;) {
            if (i >= length - 1) {
                return step;
            }
            int currentStepMaxInstance = 0;
            int nextIndex = i;
            boolean isMoreFar = false;
            for (int j = i; j <= maxInstance[i] && j < length; j++) {
                if (maxInstance[j] >= currentStepMaxInstance) {
                    nextIndex = j;
                    currentStepMaxInstance = maxInstance[j];
                    isMoreFar = true;
                }
            }
            if (!isMoreFar) {
                return -1;
            }
            step++;
            i = nextIndex;
        }
        return step;
    }
}
