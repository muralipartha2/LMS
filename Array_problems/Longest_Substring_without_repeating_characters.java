//3. Longest Substring Without Repeating Characters
//
class Solution {
    public int lengthOfLongestSubstring(String s) {

        int[] count_Array= new int[127];
        int start=0;
        int longest_Substring= 0;
        for(int i=0;i<s.length();i++){
            int ascii=s.charAt(i);
            while(count_Array[ascii] > 0){
                count_Array[s.charAt(start)]--;
                start++;
            }
                count_Array[ascii]++;
                longest_Substring=Math.max(longest_Substring, i - start +1);
        }

        return longest_Substring;
    }
}
