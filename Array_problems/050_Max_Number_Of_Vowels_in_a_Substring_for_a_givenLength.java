//https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/description/
//
class Solution {
    public int maxVowels(String s, int k) {

        int count=0;
        int max=0;

        for(int i=0;i<k;i++){
            if(isVowel(s.charAt(i)))
            count++;
        }
        
        max=count;
        for(int i=k;i<s.length();i++){
            if(isVowel(s.charAt(i)))
            count++;
            if(isVowel(s.charAt(i-k)))
            count--;

            max=Math.max(count,max);
        }

        return max;

    }

    public boolean isVowel(char a){
        return a == 'a' ||a == 'e' ||a == 'i' ||a == 'o' ||a == 'u';
    }
}
