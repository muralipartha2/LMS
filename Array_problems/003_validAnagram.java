// https://leetcode.com/problems/valid-anagram/?envType=problem-list-v2&envId=string

//Naive Approach: O(n logn) SC O(n)
// sort both the strings and check whether they are equal.for that we
// have to convert strings to char Array.use Arrays.sort on them and then
// Arrays.equal(s,t)
class Solution {
    public boolean isAnagram(String s, String t) {

        if(s.length() != t.length())
            return false;

        //Sort strings first
        char[] str1=s.toCharArray();
        char[] str2=t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);

        // now comparing whether they are equal
        return Arrays.equals(str1,str2);
	}
}

//First pass intermediate approach using hashmap: O(n) and SC O(n)
class Solution {
    public boolean isAnagram(String s, String t) {

        if(s.length() != t.length())
            return false;

        //create a hashmap to store counts of the char.
        Map<Character,Integer> hm=new HashMap<>();
        for(char c: s.toCharArray()){
            hm.put(c,hm.getOrDefault(c,0)+1);
        }

        // lets try to nullify the counts which are stored
        for(char c: t.toCharArray()){
            if(hm.getOrDefault(c,0) == 0){
                return false;
            }
            hm.put(c,hm.get(c)-1);
            
        }

        return true;
    }
}


//Optimised approach: O(n) and SC O(1)

class Solution {
    public boolean isAnagram(String s, String t) {

        if(s.length() != t.length())
            return false;

        // since anagram has only characters, only length of 26 is there.
        int[] countCheck=new int[26];

        //now add count for first array
        for(int i=0;i<s.length();i++){
            countCheck[s.charAt(i) - 'a']++;
        }
        // nullify the counts for second array
        for(int i=0;i<t.length();i++){
            countCheck[t.charAt(i) - 'a']--;
        }

        //Iterate through the countCheck array to see if there
        // is a value > 0 anywhere in the array.
        for(int i=0;i<countCheck.length;i++){
            if(countCheck[i] != 0)
                return false;
        }

        return true;
    }
}
