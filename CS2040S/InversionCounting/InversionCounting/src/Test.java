import java.util.Arrays;

public class Test {
    public static void merge_sort(int[] A) {
        merge_sort_helper(A, 0, A.length -1);
    }

    public static void merge_sort_helper(int[] A, int low, int high) {
        if (low < high) {
        int mid = (low + high) / 2;
            merge_sort_helper(A, low, mid);
            merge_sort_helper(A, mid + 1, high);
            merge(A, low, mid, high);
        } else{ }
    }

    public static void merge(int[] A, int low, int mid, int high) {
        int[] B = new int[A.length];
        int left = low;
        int right = mid + 1;
        int Bidx = 0;

        while (left <= mid && right <= high) {
            if (A[left] <= A[right]) {
                B[Bidx] = A[left];
                left = left + 1;
            } else{
                B[Bidx] = A[right];
                right = right + 1;
            }
            Bidx = Bidx + 1;
        }

        while (left <= mid) {
            B[Bidx] = A[left];
            Bidx= Bidx+ 1;
            left = left + 1;
        }
        // right half is exhausted
        // no more elements in the right half
        // put the remaining elements in the left list into B directly

        while (right <= high) {
            B[Bidx] = A[right];
            Bidx = Bidx + 1;
            right = right + 1;
        }
        // left half is exhausted
        // only one of the two while loops will be evaluated
        // cannot have both halves being exhausted simultaneously

        for (int k = 0; k < high -low + 1; k = k + 1) {
            A[low + k] = B[k];
        }
        // [low + k] may not be [0]
        // depends on where the half is split & merged
    }

    public static void main(String[] args) {
        int[] arr = {1,3,6,7,2,4,5};
        merge_sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}


