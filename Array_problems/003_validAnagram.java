// https://leetcode.com/problems/valid-anagram/?envType=problem-list-v2&envId=string
class Solution {
    public boolean isAnagram(String s, String t) {
		
		//check if the length of arr1 and arr2 are the same
		if (s.length() != t.length())
			return false;

		//create an array with english alphabets as length
        int[] check_array= new int[26];
		//iterate through the first array
		for(int i=0;i<s.length();i++){
            char c= s.charAt(i);
			if(c >= 'a' && c<= 'z'){
			check_array[c-'a']++;
			}else if(c >= 'A' && c<= 'Z'){
			check_array[c-'A']++;
			}
		}
		// iterate through the second array
		for(int i=0;i<t.length();i++){
            char c= t.charAt(i);
			if(c >= 'a' && c<= 'z'){
			    check_array[c-'a']--;
			}else if(c >= 'A' && c<= 'Z'){
				check_array[c-'A']--;
			}
		}

		//check now whether the alphabet array is nuetralized to 0
        for(int i=0;i<check_array.length;i++){
            if(check_array[i] > 0)
            return false;
        }
		return true;
    }
}
