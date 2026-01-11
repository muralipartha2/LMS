//Link https://leetcode.com/problems/concatenation-of-array/

class Solution {
    public int[] getConcatenation(int[] nums) {

        int mid= nums.length;
        int[] output= new int[2*mid];
        for(int i=0;i<=nums.length-1;i++){
            output[i]=nums[i];
            output[mid+i]=nums[i];
        }
        return output;
    }
}
