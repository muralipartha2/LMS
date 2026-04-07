//https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

// Naive Approach:
class Solution {
    public int maxProfit(int[] prices) {
        int maxProfit=0;
        for(int i=0;i< prices.length;i++){
            for(int j=i+1;j< prices.length;j++){
                maxProfit=Math.max(maxProfit,(prices[j] - prices[i]));
            }
        }
        return maxProfit;
    }
}



// Optimal Solution
class Solution {
    public int maxProfit(int[] prices) {
        int maxProfit=0;
        int currentPrice=prices[0];
        for(int i=1;i<prices.length;i++){
            if(prices[i] > currentPrice){
                maxProfit=Math.max(maxProfit,prices[i] - currentPrice);
            }else if(prices[i] < currentPrice){
                currentPrice=prices[i];
            }
        }

        return maxProfit;
    }
}
