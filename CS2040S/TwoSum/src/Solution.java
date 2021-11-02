import java.util.*;

public class Solution {

    public static int solve(int[] arr, int target) {
        // TODO: Implement your solution here
        HashMap<Integer, Integer> map = new HashMap<>();
        // key is arr[i], value is the no. of target-arr[i] added to the hash map
        int pairCount = 0;

        for (int j : arr) {
            int other = target - j;

            if (map.containsKey(j)) {
                int count = map.get(j);

                if (count > 0) {
                    // j is the other int for some prev int to sum to target
                    pairCount++;
                    map.put(j, count - 1);
                } else if (map.containsKey(other)) {
                    count = map.get(other);
                    map.put(other, count + 1);
                } else {
                    map.put(other, 1);
                }
            } else if (map.containsKey(other)) {
                // increment the no. of other int needed to sum to target
                map.put(other, map.get(other) + 1);
            } else {
                map.put(other, 1);
            }
            // System.out.println(map);
        }
        return pairCount;
    }

//    public static void main(String[] args) {
//        int[] arr = new int[] {4, 5, 4, 5, 4, 5, 4};
//        System.out.println(solve(arr, 9)); // 3
//
//        int[] arr2 = new int[] {1,1,3,7};
//        System.out.println(solve(arr2, 4)); // 1
//    }
}
