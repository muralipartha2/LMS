//169. Majority Element
//
class Solution {
    public int majorityElement(int[] nums) {
        
        int majority_element= nums[0];
        int count= 1;
        for(int i=1;i<nums.length;i++){
            if(majority_element == nums[i]){
                count++;
            }else{
                count--;
                if (count == 0){
                majority_element = nums[i];
                count =1;
            }
            }
        }
        return majority_element;
    }
}
