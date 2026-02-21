//209. Minimum Size Subarray Sum
//
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        
        int start=0;
        int currentSum=0;
        int minwindow= Integer.MAX_VALUE;

        for(int end=0;end< nums.length;end++){
            currentSum+= nums[end];
            while(currentSum >= target){
                currentSum-= nums[start];
                minwindow=Math.min(minwindow, end- start + 1);
                start++;
            }
        }

        return (minwindow == Integer.MAX_VALUE)? 0:minwindow;
    }
}
