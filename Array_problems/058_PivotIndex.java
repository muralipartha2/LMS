//724. Find Pivot Index
//
class Solution {
    public int pivotIndex(int[] nums) {

        int total_sum=0;

        for(int num: nums)
        total_sum+= num;

        int leftsum=0;
        int rightsum=0;
        for(int i=0;i<nums.length;i++){
            rightsum= total_sum - leftsum - nums[i];
            if(rightsum == leftsum)
            return i;
            leftsum+=nums[i];
        }
        return -1;
    }
}
