//https://leetcode.com/problems/find-the-highest-altitude/
//
class Solution {
    public int largestAltitude(int[] gain) {
         int highest_Altitude=0;
         int current_Altitude=0;

         for(int i=0;i<gain.length;i++){
            current_Altitude+= gain[i];
            highest_Altitude=Math.max(highest_Altitude, current_Altitude);
         }

         return highest_Altitude;
    }
}
