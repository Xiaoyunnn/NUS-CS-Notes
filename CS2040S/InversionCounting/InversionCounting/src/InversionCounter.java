import java.util.Arrays;

class InversionCounter {

    public static long countSwaps(int[] arr) {
        int[] temp = new int[arr.length];
        return mergeSort(arr, temp, 0, arr.length-1);
    }

    public static long mergeSort(int[] arr, int[] temp, int begin, int end) {
        int mid = 0;
        long count = 0L;
        if (end > begin) {
            // end > begin
            mid = begin + (end - begin) / 2;
            count += mergeSort(arr, temp, begin, mid);
            count += mergeSort(arr, temp, mid + 1, end);
            count += mergeAndCount(arr, begin, mid, mid + 1, end);
        }
        return count;
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    // e.g. arr = [1, 3, 5, 2, 4, 6] -> [1, 3, 2, 4, 5, 6] (2) -> [1, 2, 3, 4, 5, 6] (1) // 0-2, 3-5
    // [1,2,3,7, 4,5,6]
    // [3, 1, 2] 0,0,1,2 => 2 => max swap for one round = right2 - right1
    // {2, 3, 4, 1}; 0,2,3,3 => 3 => max no. of rounds = left2 - left1
    // count = max round * max swap

    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        int[] temp = new int[right2 - left1+1];
        long count = 0L;
        int left = left1; // index of left arr
        int right = left2; // index of right arr
        int tempIdx = 0; // index of the resulted sorted arr

        while ((left <= right1) && (right <= right2)) {
            if (arr[left] <= arr[right]) {
                temp[tempIdx] = arr[left];
                left++;
            } else {
                temp[tempIdx] = arr[right];
                right++;
                count += left2 - left;
            }
            tempIdx++;
        }

        while (left <= right1) {
            temp[tempIdx] = arr[left];
            tempIdx++;
            left++;
        }

        while (right <= right2) {
            temp[tempIdx] = arr[right];
            tempIdx++;
            right++;
        }

        if (right2 - left1 + 1 >= 0)
            System.arraycopy(temp, 0, arr, left1, right2 - left1 + 1);
        //System.out.println(Arrays.toString(temp));

        return count;
    }
}
