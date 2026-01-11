//Link https://leetcode.com/problems/contains-duplicate/ 

class Solution {
    public boolean containsDuplicate(int[] nums) {

        int max=0;
        for(int n:nums){
            max= Math.max(max,n);
        }
        int[] arr_with_max=new int[max+1];

        for(int i: nums){

            if(arr_with_max[i] == 0){
                arr_with_max[i] = 1;
            }else{
                return true;
            }
        }
        return false;
    }
}
