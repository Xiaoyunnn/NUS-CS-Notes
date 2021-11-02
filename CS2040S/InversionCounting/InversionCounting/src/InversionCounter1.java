import java.util.Arrays;

public class InversionCounter1 {
    public static long countSwaps(int[] arr) {
        int[] temp = new int[arr.length];
        return mergeSort(arr, temp, 0, arr.length-1);
    }

    public static long mergeSort(int[] arr, int[] temp, int begin, int end) {
        int mid = 0;
        int count = 0;
        if (begin == end) {
            return count;
        } else {
            // end > begin
            mid = begin + (end - begin)/2;
            count += mergeSort(arr, temp, begin, mid);
            count += mergeSort(arr, temp, mid + 1, end);
            count += merge(arr, temp, begin, mid+1, end);
            return count;
        }
    }

    public static long merge(int[] arr, int[] temp, int begin, int mid, int end){
            long count = 0;
            int left = begin;
            int right = mid;
            int tempIdx = begin; // index of the resulted sorted arr

            while ((left <= mid-1) && (right <= end)) {
                if (arr[left] <= arr[right]) {
                    temp[tempIdx] = arr[left];
                    left++;
                } else {
                    temp[tempIdx] = arr[right];
                    right++;
                    count += mid - left;
                }
                tempIdx++;
            }

            while (left <= mid - 1) {
                temp[tempIdx] = arr[left];
                tempIdx++;
                left++;
            }

            while (right <= end) {
                temp[tempIdx] = arr[right];
                tempIdx++;
                right++;
            }

            for (int k = begin; k <= end; k++) {
                arr[k] = temp[k];
            }

            return count;
    }
    public static int FindPeak(int[] arr, int n) {
        if (n == 1 || arr[0] > arr[1]) {
            return 0;
        } else if (arr[n - 1] > arr[n-2]) {
            return n;
        } else if (arr[n/2] > arr[n/2-1]) {
            return FindPeak(Arrays.copyOfRange(arr,n/2, n), n/2);
        } else if (arr[n/2-1] > arr[n/2]) {
            return FindPeak(Arrays.copyOfRange(arr,0,n/2),n/2);
        } else {
            return n;
        }
    }

    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        long count = 0;
        for (int j = left2; j < right2 + 1; j++) {
            int key = arr[j];
            int i = j - 1;
            while (i >= 0 && arr[i] > key) {
                count += 1;
                arr[i+1] = arr[i];
                i --;
            }
            arr[i+1] = key;
        }
        // System.out.println(Arrays.toString(arr));
        return count;

        //        for (int i = right1; (i < left2 - left1 && i >= 0); i--) {
//            for (int j = i; j < right2; j++) {
//                if (arr[j] > arr[j+1]) {
//                    count += 1;
//                    int temp = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = temp;
//                }
//            }
//        }
    }
}

/*
long count = 0;
        for (int j = 1; j < arr.length; j++) {
            int key = arr[j];
            int i = j - 1;
            while (i >= 0 && arr[i] > key) {
                count += 1;
                arr[i+1] = arr[i];
                i --;
            }
            arr[i+1] = key;
        }
        // System.out.println(Arrays.toString(arr));
        return count;

//        int left2 = FindPeak(arr, arr.length) + 1;
//        int right2 = arr.length - 1;
//        for (int i = 0; i < right2; i++) {
//            if (arr[i] > arr[i+1]) {
//                left2 = i + 1;
//            }
//        }
//        //System.out.println(left2);
//        long count = 0;
//        for (int j = left2; j < right2 + 1; j++) {
//            int key = arr[j];
//            int i = j - 1;
//            while (i >= 0 && arr[i] > key) {
//                count += 1;
//                arr[i+1] = arr[i];
//                i --;
//            }
//            arr[i+1] = key;
//        }
//        return count;
 */

