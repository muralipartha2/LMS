Perfect! Here's the complete Java code with both approaches:

---

## **Approach 1: Brute Force**

```java
class Solution {
    /**
     * APPROACH: BRUTE FORCE
     * 
     * Algorithm:
     * 1. For each possible starting position (0 to n-k)
     * 2. Calculate the sum of k consecutive elements
     * 3. Calculate average and track the maximum
     * 4. Return the maximum average found
     * 
     * Key Idea:
     *   - Check every subarray of size k
     *   - Calculate sum from scratch for each window
     *   - Simple but inefficient due to redundant calculations
     * 
     * Time Complexity: O(n × k)
     *   - n-k+1 windows to check ≈ O(n)
     *   - Each window requires summing k elements: O(k)
     *   - Total: O(n × k)
     * 
     * Space Complexity: O(1)
     *   - Only using a few variables
     * 
     * Problem:
     *   - Recalculating sum for overlapping windows is wasteful
     *   - Windows share k-1 common elements
     */
    public double findMaxAverage(int[] nums, int k) {
        int n = nums.length;
        
        // Initialize with smallest possible value
        double maxAverage = Double.NEGATIVE_INFINITY;
        
        // Check each window of size k
        for (int i = 0; i <= n - k; i++) {
            // Calculate sum of current window
            int sum = 0;
            for (int j = i; j < i + k; j++) {
                sum += nums[j];
            }
            
            // Calculate average and update maximum
            double average = (double) sum / k;
            maxAverage = Math.max(maxAverage, average);
        }
        
        return maxAverage;
    }
}
```

---

## **Approach 2: Optimized - Sliding Window**

```java
class Solution {
    /**
     * APPROACH: SLIDING WINDOW (Fixed Size)
     * 
     * Algorithm:
     * 1. Calculate sum of first k elements (first window)
     * 2. Initialize maxSum with this first window sum
     * 3. Slide the window one position at a time:
     *    - Remove the element leaving the window (leftmost)
     *    - Add the element entering the window (rightmost)
     *    - Update maxSum if current sum is greater
     * 4. Return maxSum / k to get the maximum average
     * 
     * Key Insight:
     *   - Consecutive windows overlap by k-1 elements
     *   - Instead of recalculating entire sum, just update:
     *     newSum = oldSum - elementLeaving + elementEntering
     *   - This reduces each window update to O(1)
     * 
     * Why we track sum instead of average:
     *   - Comparing sums is equivalent to comparing averages
     *   - maxSum/k == maxAverage
     *   - Avoids repeated division operations
     *   - Only divide once at the end
     * 
     * Time Complexity: O(n)
     *   - Calculate first window: O(k)
     *   - Slide through remaining n-k elements: O(n-k)
     *   - Total: O(k + n - k) = O(n)
     * 
     * Space Complexity: O(1)
     *   - Only using a few variables
     * 
     * Trade-offs:
     *   - Much faster than brute force
     *   - Still uses constant space
     *   - Classic sliding window pattern for fixed-size windows
     */
    public double findMaxAverage(int[] nums, int k) {
        int n = nums.length;
        
        // Step 1: Calculate sum of first window (indices 0 to k-1)
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        // Step 2: Initialize maxSum with first window's sum
        int maxSum = windowSum;
        
        // Step 3: Slide the window through the rest of the array
        // Starting from index k, we look at windows [1...k], [2...k+1], etc.
        for (int i = k; i < n; i++) {
            // Remove element leaving the window (left side)
            // Add element entering the window (right side)
            windowSum = windowSum - nums[i - k] + nums[i];
            
            // Update maxSum if current window has larger sum
            maxSum = Math.max(maxSum, windowSum);
        }
        
        // Step 4: Convert maximum sum to average
        // We only divide once at the end for efficiency
        return (double) maxSum / k;
    }
}
```

---

## **Approach 2 (Alternative): Tracking Average Directly**

```java
class Solution {
    /**
     * APPROACH: SLIDING WINDOW (Tracking Average Directly)
     * 
     * This is a variation where we track averages instead of sums.
     * Functionally equivalent but demonstrates alternative thinking.
     * 
     * Note: This approach does more divisions (every iteration)
     *       Previous approach is slightly more efficient.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public double findMaxAverage(int[] nums, int k) {
        int n = nums.length;
        
        // Calculate sum of first window
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        // Calculate first window's average
        double maxAverage = (double) windowSum / k;
        
        // Slide the window
        for (int i = k; i < n; i++) {
            // Update window sum
            windowSum = windowSum - nums[i - k] + nums[i];
            
            // Calculate current average
            double currentAverage = (double) windowSum / k;
            
            // Update maximum
            maxAverage = Math.max(maxAverage, currentAverage);
        }
        
        return maxAverage;
    }
}
```

---

## **Detailed Dry Run with Code Trace**

```java
/**
 * DRY RUN EXAMPLE
 * 
 * Input: nums = [1, 12, -5, -6, 50, 3], k = 4
 * 
 * Step-by-step execution of Sliding Window approach:
 * 
 * Initial Setup:
 * n = 6, k = 4
 * 
 * Calculate First Window (i = 0 to 3):
 * windowSum = 0
 * i=0: windowSum = 0 + 1 = 1
 * i=1: windowSum = 1 + 12 = 13
 * i=2: windowSum = 13 + (-5) = 8
 * i=3: windowSum = 8 + (-6) = 2
 * 
 * maxSum = 2
 * 
 * Sliding Window Loop (i = 4 to 5):
 * 
 * i=4:
 *   Window: [12, -5, -6, 50]
 *   Remove: nums[4-4] = nums[0] = 1
 *   Add: nums[4] = 50
 *   windowSum = 2 - 1 + 50 = 51
 *   maxSum = max(2, 51) = 51 ✓ (updated)
 * 
 * i=5:
 *   Window: [-5, -6, 50, 3]
 *   Remove: nums[5-4] = nums[1] = 12
 *   Add: nums[5] = 3
 *   windowSum = 51 - 12 + 3 = 42
 *   maxSum = max(51, 42) = 51 (no change)
 * 
 * Final Result:
 * return (double) 51 / 4 = 12.75
 */
```

---

## **Summary Table**

```java
/**
 * COMPARISON OF APPROACHES
 * 
 * ┌─────────────────────┬──────────────────┬────────────┬─────────────────────────────┐
 * │ Approach            │ Time Complexity  │ Space      │ Notes                       │
 * ├─────────────────────┼──────────────────┼────────────┼─────────────────────────────┤
 * │ 1. Brute Force      │ O(n × k)         │ O(1)       │ Recalculates sum each time  │
 * │                     │                  │            │ Simple but inefficient      │
 * ├─────────────────────┼──────────────────┼────────────┼─────────────────────────────┤
 * │ 2. Sliding Window   │ O(n)             │ O(1)       │ Updates sum incrementally   │
 * │    (Track Sum)      │                  │            │ Most efficient ✓            │
 * │                     │                  │            │ Recommended for interview   │
 * ├─────────────────────┼──────────────────┼────────────┼─────────────────────────────┤
 * │ 3. Sliding Window   │ O(n)             │ O(1)       │ Tracks average directly     │
 * │    (Track Average)  │                  │            │ More divisions (slower)     │
 * └─────────────────────┴──────────────────┴────────────┴─────────────────────────────┘
 * 
 * INTERVIEW RECOMMENDATION:
 *   - Explain Approach 1 (Brute Force) first
 *   - Optimize to Approach 2 (Sliding Window - Track Sum)
 *   - This is a classic fixed-size sliding window problem
 */
```

---

Perfect! This problem is a great example of the **fixed-size sliding window** pattern. Ready for the next problem?
