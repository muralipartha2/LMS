//https://leetcode.com/problems/longest-continuous-increasing-subsequence/submissions/1861640530/
//
class Solution {
    public int findLengthOfLCIS(int[] nums) {

        int currentLen=1;
        int maxLen=1;

        for(int i=1;i< nums.length;i++){
            if(nums[i]>nums[i-1]){
                currentLen++;
            }else{
                currentLen=1;
            }
            maxLen=Math.max(currentLen,maxLen);
        }
        return maxLen;
    }
}
