//169. Majority Element
//

//BruteForce Solution: using hashmap. TC: O(n), SC: O(n)
class Solution {
    public int majorityElement(int[] nums) {

    HashMap<Integer,Integer> hm=new HashMap<Integer,Integer>();
    for(int num: nums){
        hm.put(num,hm.getOrDefault(num,0)+1);
    }

    for(Map.Entry<Integer,Integer> entry: hm.entrySet()){
        if(entry.getValue() > nums.length/2){
            return entry.getKey();
        }
    }

    return 0;    
    }
}

//Optimal Solution: using Boyre-Moore Voting Algorithm. TC:O(n), SC:O(1)
//candidate becomes negligible at point of time if the candidate is not the majority so we can pick up the next candidate.
// finally the candidate who will survive with the count will become majority_element.    
class Solution {
    public int majorityElement(int[] nums) {
     int majority_element=nums[0];
     int count=1;
     for(int i=1;i<nums.length;i++){
        if(majority_element == nums[i]){
            count++;
        }else{
            count--;
            if(count == 0){
                majority_element =nums[i];
                count=1;
            }
        }
     }
     return majority_element;       
    }
}
