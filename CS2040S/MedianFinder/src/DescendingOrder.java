import java.util.Arrays;
import java.util.concurrent.CompletionException;

public class DescendingOrder {
    public DescendingOrder() {
    }
    public final <T extends Comparable<T>> void descending(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[i].compareTo(arr[j]) > 0) {
                    swap(arr, i , j);

                }
            }
        }
    }

    public final void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1,2,6,3,2,7,4,5};
        new DescendingOrder().descending(arr);
        System.out.println(Arrays.toString(arr));
        new AscendingOrder().ascending(arr);
        System.out.println(Arrays.toString(arr));
    }
}
