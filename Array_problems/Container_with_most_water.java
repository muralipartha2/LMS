//11. Container With Most Water
//
class Solution {
    public int maxArea(int[] height) {
        int maxArea= 0;
        int i=0;
        int j=height.length - 1;
        while(i<j){
            int calculatedArea = (j-i) * Math.min(height[i],height[j]);
            maxArea = Math.max(calculatedArea,maxArea);
            if(height[i] <= height[j]){
                i++;
            }else{
                j--;
            }
        }
        return maxArea;
    }
}
