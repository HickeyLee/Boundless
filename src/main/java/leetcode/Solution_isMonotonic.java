package leetcode;
import java.util.Arrays;
//判断是否单调数列

public class Solution_isMonotonic {
    public static boolean isMonotonic(int[] nums) {
        return isSorted(nums, true) || isSorted(nums, false);
    }

    public static boolean isSorted(int[] nums, boolean increasing) {
        int n = nums.length;
        if (increasing) {
            for (int i = 0; i < n - 1; ++i) {
                if (nums[i] > nums[i + 1]) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < n - 1; ++i) {
                if (nums[i] < nums[i + 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

        int[] ns = {28, 12, 89, 73, 65, 18, 96, 50, 8, 36, 88};
//        Arrays.sort(ns);
        System.out.println(isMonotonic(ns));

    }

}


