public class AscendingOrder extends DescendingOrder {
    public final <T extends Comparable<T>> void ascending (T[] arr) {
        descending(arr);
        for (int i = 0; i < arr.length/2; i++) {
            swap(arr, i, arr.length - 1 - i);
        }
    }
}
