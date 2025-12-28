//https://leetcode.com/problems/maximum-average-subarray-i/description/
//
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        
        double maxSum=0;
        double calcSum=0;
        for(int i=0;i<k;i++){
                calcSum+=nums[i];
        }
        maxSum=calcSum;
        for(int i=k;i<nums.length;i++){
            calcSum+=nums[i];
            calcSum-=nums[i-k];
            maxSum=Math.max(maxSum,(calcSum));
        }
        return maxSum/k;
    }
}
