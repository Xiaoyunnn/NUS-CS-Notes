import java.util.HashMap;


public class Solution {
    // TODO: Implement your solution here
    public static int solve(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();

        int maxLength = -1;
        int distinctStartIdx = 0;

        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                distinctStartIdx = Math.max(map.get(arr[i]), distinctStartIdx);
                System.out.println("duplicate index = " + distinctStartIdx);
            }

            maxLength = Math.max(maxLength, i - distinctStartIdx + 1);
            System.out.println("max length = " + maxLength);

            // key = arr[i], value = 1 index after arr[i]
            map.put(arr[i], i + 1);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        int[] test = new int[] {1, 2, 20, 4, 3, 2, 7, 9}; // 6
        int[] test2 = new int[] {4,5,4,5,4,5}; // 2
        int[] test3 = new int[] {1,2,3,4,5,3,4,1,2,3}; // 5
        int[] test4 = new int[] {1,1,1}; // 1
        // index before 3rd duplicate or last index - index after 1st duplicate
        // 2 hash tables: 1 stores 1st duplicate, 1 stores 2nd duplicate
        // System.out.println(solve(test));
        // System.out.println(solve(test2));
        // System.out.println("##########");
        // System.out.println(solve(test4));
        System.out.println(solve(test3));

    }
}
/*
int maxLength = -1;
        int nonDuplicateIdx = 0;
        int duplicateIdx = 0;
        int firstDupIndex = -1;
        int thirdDupIndex = -1;

        for (int i = 0; i < arr.length; i++) {
            if (!map1.containsKey(arr[i])) {
                map1.put(arr[i], i);
            } else if (!map2.containsKey(arr[i])) {
                // 2nd duplicate encountered
                map2.put(arr[i], i);
                duplicateIdx = map1.get(arr[i]);
            } else {
                // 3rd duplicate encountered
                System.out.println("curr key: " + arr[i]);
                System.out.println("3rd duplicate index: " + i);
                System.out.println("first duplicate index: " + map1.get(arr[i]));
                firstDupIndex = map1.get(arr[i]);
                thirdDupIndex = i;
                maxLength = Math.max(i - map1.get(arr[i]) - 1, maxLength);
                System.out.println("max length: " + maxLength);
            }
        }

        if (firstDupIndex != -1 && thirdDupIndex != -1) {
            int[] subarr = Arrays.copyOfRange(arr, firstDupIndex, thirdDupIndex-1);
            System.out.println(Arrays.toString(subarr));
            return solve(subarr);
        }


//        if (maxLength == -1 && duplicateIdx != -1) {
//            maxLength = arr.length - duplicateIdx - 1;
//        }
        return maxLength == Integer.MAX_VALUE ? arr.length - duplicateIdx : maxLength;
//        for (int i = 0; i < arr.length; i++) {
//            Integer duplicateIdx = map1.put(arr[i], i);
//            if (duplicateIdx != null) {
//                // duplicate encountered
//                if (duplicateIdx > nonDuplicateIdx) {
//                    maxLength = Math.max(maxLength, i - nonDuplicateIdx);
//                    nonDuplicateIdx = duplicateIdx + 1;
//                    System.out.println(maxLength);
//                }
//            }
//        }
//        return Math.max(maxLength, arr.length - nonDuplicateIdx);
 */

