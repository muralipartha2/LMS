//724. Find Pivot Index
//
Great! Let's solve **Find Pivot Index** (LeetCode 724).

---

## **Problem Statement:**

Given an array of integers `nums`, calculate the **pivot index** of this array.

The pivot index is the index where the sum of all the numbers **strictly to the left** of the index is equal to the sum of all the numbers **strictly to the right** of the index.

If the index is on the left edge (index 0), then the left sum is `0` because there are no elements to the left. This also applies to the right edge.

Return the **leftmost pivot index**. If no such index exists, return `-1`.

**Example 1:**
```
Input: nums = [1,7,3,6,5,6]
Output: 3
Explanation: 
Left sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11
Right sum = nums[4] + nums[5] = 5 + 6 = 11
```

**Example 2:**
```
Input: nums = [1,2,3]
Output: -1
Explanation: There is no index that satisfies the conditions.
```

**Example 3:**
```
Input: nums = [2,1,-1]
Output: 0
Explanation: 
Left sum = 0 (no elements to the left of index 0)
Right sum = nums[1] + nums[2] = 1 + (-1) = 0
```

---

## **Step 1: Clarify Constraints**

Before we discuss approaches, let's consider:

- What's the range of `n` (length of nums)?
- Can the array be empty?
- Can the array contain negative numbers?
- Can there be multiple pivot indices? (We return the leftmost)
- What if all elements are 0?
- Range of integer values in the array?

**Assumptions:**
- 1 ≤ n ≤ 10^4
- Array can contain negative numbers
- Return leftmost pivot index
- If no pivot exists, return -1

---

## **Step 2: Brute Force Approach**

**Idea:**
- For each index `i`, calculate:
  - Sum of all elements to the left of `i`
  - Sum of all elements to the right of `i`
- If both sums are equal, return `i`
- If no such index found, return `-1`

**Algorithm:**
```
1. For each index i from 0 to n-1:
   - Calculate leftSum = sum of nums[0...i-1]
   - Calculate rightSum = sum of nums[i+1...n-1]
   - If leftSum == rightSum, return i
2. If no pivot found, return -1
```

**Complexity:**
- **Time:** O(n²)
  - We check n indices
  - For each index, we calculate left and right sums: O(n)
  - Total: O(n × n) = O(n²)
- **Space:** O(1)

**Problem:**
We're recalculating sums from scratch for every index. Very inefficient!

---

## **Step 3: Optimized Approach - Prefix Sum**

**Key Insight:**
Instead of recalculating sums repeatedly, we can use the relationship:
```
If pivot is at index i:
leftSum = rightSum

Also, we know:
totalSum = leftSum + nums[i] + rightSum

Since leftSum = rightSum:
totalSum = leftSum + nums[i] + leftSum
totalSum = 2 × leftSum + nums[i]
leftSum = (totalSum - nums[i]) / 2

Or more simply:
leftSum = rightSum
leftSum + nums[i] + rightSum = totalSum
2 × leftSum + nums[i] = totalSum
leftSum = totalSum - nums[i] - rightSum
```

**Better approach:**
```
At any index i:
rightSum = totalSum - leftSum - nums[i]

For pivot: leftSum == rightSum
So: leftSum == totalSum - leftSum - nums[i]
    2 × leftSum == totalSum - nums[i]
    leftSum == (totalSum - nums[i]) / 2

Or simpler: leftSum == totalSum - leftSum - nums[i]
           2 × leftSum == totalSum - nums[i]
```

**Even simpler logic:**
```
We can iterate through array and maintain:
- leftSum (sum of elements to the left of current index)
- At each index i, rightSum = totalSum - leftSum - nums[i]
- If leftSum == rightSum, we found the pivot
- After checking, add nums[i] to leftSum for next iteration
```

**Algorithm:**
```
1. Calculate totalSum of all elements
2. Initialize leftSum = 0
3. For each index i from 0 to n-1:
   - Calculate rightSum = totalSum - leftSum - nums[i]
   - If leftSum == rightSum, return i
   - Add nums[i] to leftSum (for next iteration)
4. Return -1 if no pivot found
```

**Complexity:**
- **Time:** O(n)
  - One pass to calculate totalSum: O(n)
  - One pass to find pivot: O(n)
  - Total: O(n)
- **Space:** O(1)
  - Only using a few variables

**Trade-offs:**
- Much faster than brute force
- Still constant space
- Classic prefix sum application

---

## **Step 4: Detailed Dry Run**

Let's trace through: `nums = [1, 7, 3, 6, 5, 6]`

```
Step 1: Calculate totalSum
totalSum = 1 + 7 + 3 + 6 + 5 + 6 = 28

Step 2: Initialize
leftSum = 0

Step 3: Iterate through array

i=0, nums[0]=1:
  rightSum = 28 - 0 - 1 = 27
  leftSum (0) != rightSum (27) ✗
  leftSum = 0 + 1 = 1

i=1, nums[1]=7:
  rightSum = 28 - 1 - 7 = 20
  leftSum (1) != rightSum (20) ✗
  leftSum = 1 + 7 = 8

i=2, nums[2]=3:
  rightSum = 28 - 8 - 3 = 17
  leftSum (8) != rightSum (17) ✗
  leftSum = 8 + 3 = 11

i=3, nums[3]=6:
  rightSum = 28 - 11 - 6 = 11
  leftSum (11) == rightSum (11) ✓
  Return 3

Result: 3
```

**Verification:**
```
Index 3:
Left: [1, 7, 3] → sum = 11
Right: [5, 6] → sum = 11
Equal! ✓
```

---

## **Another Example:** `nums = [2, 1, -1]`

```
Step 1: Calculate totalSum
totalSum = 2 + 1 + (-1) = 2

Step 2: Initialize
leftSum = 0

Step 3: Iterate

i=0, nums[0]=2:
  rightSum = 2 - 0 - 2 = 0
  leftSum (0) == rightSum (0) ✓
  Return 0

Result: 0
```

**Verification:**
```
Index 0:
Left: [] → sum = 0
Right: [1, -1] → sum = 0
Equal! ✓
```

---
Perfect! Here's the complete Java code with both approaches:

---

## **Approach 1: Brute Force**

```java
class Solution {
    /**
     * APPROACH: BRUTE FORCE
     * 
     * Algorithm:
     * 1. For each index i (0 to n-1):
     *    - Calculate sum of all elements to the left (0 to i-1)
     *    - Calculate sum of all elements to the right (i+1 to n-1)
     *    - If leftSum == rightSum, return i
     * 2. If no pivot found after checking all indices, return -1
     * 
     * Key Idea:
     *   - Check every possible pivot index
     *   - Calculate left and right sums from scratch each time
     *   - First index where sums match is the answer
     * 
     * Time Complexity: O(n²)
     *   - Outer loop runs n times (checking each index)
     *   - Inner loops calculate sums: O(n) each
     *   - Total: O(n × n) = O(n²)
     * 
     * Space Complexity: O(1)
     *   - Only using a few variables
     * 
     * Problem:
     *   - Recalculating sums repeatedly is wasteful
     *   - Same elements are summed multiple times
     *   - Not efficient for large arrays
     */
    public int pivotIndex(int[] nums) {
        int n = nums.length;
        
        // Check each index as potential pivot
        for (int i = 0; i < n; i++) {
            // Calculate left sum (elements before index i)
            int leftSum = 0;
            for (int j = 0; j < i; j++) {
                leftSum += nums[j];
            }
            
            // Calculate right sum (elements after index i)
            int rightSum = 0;
            for (int j = i + 1; j < n; j++) {
                rightSum += nums[j];
            }
            
            // Check if this is a pivot index
            if (leftSum == rightSum) {
                return i;
            }
        }
        
        // No pivot index found
        return -1;
    }
}
```

---

## **Approach 2: Optimized - Prefix Sum (Single Pass)**

```java
class Solution {
    /**
     * APPROACH: PREFIX SUM (OPTIMAL)
     * 
     * Algorithm:
     * 1. Calculate totalSum of all elements in array
     * 2. Initialize leftSum = 0 (sum of elements to the left)
     * 3. Iterate through each index i:
     *    - Calculate rightSum = totalSum - leftSum - nums[i]
     *    - If leftSum == rightSum, we found the pivot
     *    - Otherwise, add nums[i] to leftSum and continue
     * 4. Return -1 if no pivot found
     * 
     * Key Insight:
     *   At any index i:
     *   - leftSum = sum of elements before i
     *   - rightSum = sum of elements after i
     *   - totalSum = leftSum + nums[i] + rightSum
     *   
     *   Therefore: rightSum = totalSum - leftSum - nums[i]
     *   
     *   For pivot: leftSum == rightSum
     *   We check this condition at each index efficiently
     * 
     * Why this works:
     *   - We maintain leftSum as we iterate
     *   - We calculate rightSum using the formula (no need to sum again)
     *   - This avoids redundant calculations
     * 
     * Time Complexity: O(n)
     *   - One pass to calculate totalSum: O(n)
     *   - One pass to find pivot: O(n)
     *   - Total: O(2n) = O(n)
     * 
     * Space Complexity: O(1)
     *   - Only using three variables (totalSum, leftSum, rightSum)
     * 
     * Trade-offs:
     *   - Significantly faster than brute force
     *   - Still uses constant space
     *   - Classic application of prefix sum technique
     */
    public int pivotIndex(int[] nums) {
        int n = nums.length;
        
        // Step 1: Calculate total sum of all elements
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        // Step 2: Initialize left sum (no elements to the left initially)
        int leftSum = 0;
        
        // Step 3: Iterate through array to find pivot
        for (int i = 0; i < n; i++) {
            // Calculate right sum using the formula
            // rightSum = totalSum - leftSum - current element
            int rightSum = totalSum - leftSum - nums[i];
            
            // Check if current index is pivot
            if (leftSum == rightSum) {
                return i;  // Found the leftmost pivot
            }
            
            // Update leftSum for next iteration
            // After this iteration, nums[i] becomes part of left side
            leftSum += nums[i];
        }
        
        // Step 4: No pivot index exists
        return -1;
    }
}
```

---

## **Approach 2 (Alternative): Two Pass with Explicit Right Sum**

```java
class Solution {
    /**
     * APPROACH: PREFIX SUM (ALTERNATIVE IMPLEMENTATION)
     * 
     * This variation explicitly maintains both leftSum and rightSum.
     * Functionally equivalent but shows different coding style.
     * 
     * Algorithm:
     * 1. Calculate totalSum
     * 2. Initialize leftSum = 0, rightSum = totalSum
     * 3. For each index:
     *    - Subtract current element from rightSum
     *    - Check if leftSum == rightSum
     *    - Add current element to leftSum
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int pivotIndex(int[] nums) {
        int n = nums.length;
        
        // Calculate total sum
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        // Initialize sums
        int leftSum = 0;
        int rightSum = totalSum;
        
        // Find pivot
        for (int i = 0; i < n; i++) {
            // Remove current element from right sum
            rightSum -= nums[i];
            
            // Check if pivot
            if (leftSum == rightSum) {
                return i;
            }
            
            // Add current element to left sum
            leftSum += nums[i];
        }
        
        return -1;
    }
}
```

---

## **Detailed Dry Run with Code Trace**

```java
/**
 * DRY RUN EXAMPLE 1
 * 
 * Input: nums = [1, 7, 3, 6, 5, 6]
 * 
 * Step-by-step execution of Optimal approach:
 * 
 * Step 1: Calculate totalSum
 * totalSum = 1 + 7 + 3 + 6 + 5 + 6 = 28
 * 
 * Step 2: Initialize
 * leftSum = 0
 * 
 * Step 3: Iterate through array
 * 
 * i=0, nums[0]=1:
 *   rightSum = 28 - 0 - 1 = 27
 *   leftSum (0) == rightSum (27)? NO
 *   leftSum = 0 + 1 = 1
 * 
 * i=1, nums[1]=7:
 *   rightSum = 28 - 1 - 7 = 20
 *   leftSum (1) == rightSum (20)? NO
 *   leftSum = 1 + 7 = 8
 * 
 * i=2, nums[2]=3:
 *   rightSum = 28 - 8 - 3 = 17
 *   leftSum (8) == rightSum (17)? NO
 *   leftSum = 8 + 3 = 11
 * 
 * i=3, nums[3]=6:
 *   rightSum = 28 - 11 - 6 = 11
 *   leftSum (11) == rightSum (11)? YES ✓
 *   return 3
 * 
 * Verification:
 * Left side: [1, 7, 3] → sum = 11
 * Pivot: 6
 * Right side: [5, 6] → sum = 11
 * Equal! ✓
 */

/**
 * DRY RUN EXAMPLE 2
 * 
 * Input: nums = [2, 1, -1]
 * 
 * Step 1: Calculate totalSum
 * totalSum = 2 + 1 + (-1) = 2
 * 
 * Step 2: Initialize
 * leftSum = 0
 * 
 * Step 3: Iterate
 * 
 * i=0, nums[0]=2:
 *   rightSum = 2 - 0 - 2 = 0
 *   leftSum (0) == rightSum (0)? YES ✓
 *   return 0
 * 
 * Verification:
 * Left side: [] → sum = 0
 * Pivot: 2
 * Right side: [1, -1] → sum = 0
 * Equal! ✓
 */

/**
 * DRY RUN EXAMPLE 3
 * 
 * Input: nums = [1, 2, 3]
 * 
 * Step 1: Calculate totalSum
 * totalSum = 1 + 2 + 3 = 6
 * 
 * Step 2: Initialize
 * leftSum = 0
 * 
 * Step 3: Iterate
 * 
 * i=0, nums[0]=1:
 *   rightSum = 6 - 0 - 1 = 5
 *   leftSum (0) == rightSum (5)? NO
 *   leftSum = 0 + 1 = 1
 * 
 * i=1, nums[1]=2:
 *   rightSum = 6 - 1 - 2 = 3
 *   leftSum (1) == rightSum (3)? NO
 *   leftSum = 1 + 2 = 3
 * 
 * i=2, nums[2]=3:
 *   rightSum = 6 - 3 - 3 = 0
 *   leftSum (3) == rightSum (0)? NO
 *   leftSum = 3 + 3 = 6
 * 
 * Step 4: Loop ended, no pivot found
 * return -1
 */
```
 * 
 * KEY PATTERNS:
 *   - This is a classic PREFIX SUM problem
 *   - Use relationship: totalSum = leftSum + nums[i] + rightSum
 *   - No need to recalculate sums - use arithmetic
 * 
 * INTERVIEW RECOMMENDATION:
 *   - Start with Approach 1 (Brute Force) to show understanding
 *   - Optimize to Approach 2 (Prefix Sum)
 *   - Explain the mathematical relationship clearly
 */
```






