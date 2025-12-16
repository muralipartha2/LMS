//238. Product of Array Except Self
//

class Solution {
    public int[] productExceptSelf(int[] nums) {

        int[] leftSum= new int[nums.length];
        int[] rightSum= new int[nums.length];

        leftSum[0]=1;
        for(int i=1;i<nums.length; i++){
            leftSum[i]=leftSum[i-1] * nums[i-1];
        }
        rightSum[nums.length-1]=1;
        for(int i=(nums.length-2);i>=0;i--){
            rightSum[i]=rightSum[i+1] * nums[i+1];
        }

        for(int i=0;i<nums.length;i++){
            nums[i]= leftSum[i] * rightSum[i];
        }
        return nums;
    }
}
