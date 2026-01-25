//1768. Merge Strings Alternately

class Solution {
    public String mergeAlternately(String word1, String word2) {


        int i=0;
        int j=0;
        String resultant="";
        while(i<word1.length() || j<word2.length()){

            if(!(i>=word1.length()))
            resultant+= word1.charAt(i);
            if(!(j>=word2.length()))
            resultant+= word2.charAt(j);

            i++;
            j++;
        }
        return resultant;
    }
}
