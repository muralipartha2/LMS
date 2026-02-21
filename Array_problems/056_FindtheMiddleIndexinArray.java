
//https://leetcode.com/problems/find-the-middle-index-in-array/description/
//Find the Middle Index in Array
class Solution {
    public int findMiddleIndex(int[] nums) {

        int totalSum=0;
        int leftSum=0;
        int rightSum=0;

        for(int i: nums)
        totalSum+=i;
        
        for(int i=0;i< nums.length; i++){
            rightSum= totalSum - leftSum - nums[i];
            if(leftSum == rightSum)
            return i;
            leftSum+= nums[i];
        }

        return -1;
    }
}
