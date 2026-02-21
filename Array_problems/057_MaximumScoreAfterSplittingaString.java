//https://leetcode.com/problems/maximum-score-after-splitting-a-string/description/
//
class Solution {
    public int maxScore(String s) {
        int rightScore=0;
        int leftScore=0;
        int maxScore=0;

        for(int i=0; i<s.length();i++){
            if(s.charAt(i) == '1')
            rightScore+= 1;
        }

        for(int i=0; i<s.length()-1;i++){
            if(s.charAt(i) == '0'){
            leftScore++;
            }else{
            rightScore--;
            }
            maxScore=Math.max(maxScore, leftScore + rightScore);
        }
        return maxScore;
    }
}
