// https://leetcode.com/problems/valid-anagram/?envType=problem-list-v2&envId=string
class Solution {
    public boolean isAnagram(String s, String t) {
		
		int[] check_array= new int[26];
		if (s.length() != t.length())
			return false;
        
		for(int i=0;i<s.length();i++){

            char c= s.charAt(i);
			if(c >= 'a' && c<= 'z'){
			check_array[c-'a']++;
			}else if(c >= 'A' && c<= 'Z'){
			check_array[c-'A']++;
			}
		}
		
		for(int i=0;i<t.length();i++){
            char c= t.charAt(i);
			if(c >= 'a' && c<= 'z'){
			    check_array[c-'a']--;
			}else if(c >= 'A' && c<= 'Z'){
				check_array[c-'A']--;
			}
		}

        for(int i=0;i<check_array.length;i++){
            if(check_array[i] > 0)
            return false;
        }
		return true;
    }
}
