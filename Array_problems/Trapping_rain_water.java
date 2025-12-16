//42. Trapping Rain Water
//
class Solution {
    public int trap(int[] height) {
        int i=0,watercount=0,leftMax=0,rightMax=0;
        int j=height.length-1;

        while(i<j){
            if(height[i] <= height[j]){
                if(height[i] > leftMax){
                    leftMax=height[i];
                }else{
                    watercount+= leftMax-height[i];
                }
            i++;
            }else{
                if(height[j] > rightMax){
                    rightMax=height[j];
                }else{
                    watercount+=rightMax-height[j];
                }
            j--;
            }
        }
        return watercount;
    }
}
