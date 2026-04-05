//169. Majority Element
//
//Brute Force — O(n²)
class Solution {
    public int majorityElement(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (nums[i] == nums[j]) {
                    count++;
                }
            }
            if (count > n / 2) {
                return nums[i];
            }
        }
        return -1;
    }
}
//Logic: For every element count how many times it appears. If count > n/2 return it.

//Sorting — O(n log n)
class Solution {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
}
//Logic: After sorting, majority element always occupies middle index since it appears more than n/2 times.

//hashmap Solution: using hashmap. TC: O(n), SC: O(n)
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
