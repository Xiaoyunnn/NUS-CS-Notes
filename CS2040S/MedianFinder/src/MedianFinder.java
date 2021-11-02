import java.util.Objects;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

public class MedianFinder {
    // TODO: Include your data structures here
    PriorityQueue<IntMax> smaller; // max heap
    PriorityQueue<Integer> larger; // min heap
    int smallerSize;
    int largerSize;

    public class IntMax implements Comparable<IntMax>{
        public int num;

        public IntMax(int num) {
            this.num = num;
        }

        @Override
        public int compareTo(IntMax o) {
            return o.num - this.num;
        }

        @Override
        public String toString() {
            return String.valueOf(num);
        }
    }

    public MedianFinder() {
        // TODO: Construct/Initialise your data structures here
        this.smaller = new PriorityQueue<>();
        this.larger = new PriorityQueue<>();
        this.smallerSize = 0;
        this.largerSize = 0;
    }

    public void insert(int x) {
        // TODO: Implement your insertion operation here
        if (smaller.isEmpty()) {
            smaller.add(new IntMax(x));
            smallerSize++;
        } else {
            if (x <= smaller.peek().num) {
                smaller.add(new IntMax(x));
                smallerSize++;
            } else {
                larger.add(x);
                largerSize++;
            }

            if (smallerSize > largerSize + 1) {
                // move one elem from smaller to larger
                larger.add(Objects.requireNonNull(smaller.poll()).num);
                smallerSize--;
                largerSize++;
            } else if (largerSize > smallerSize + 1) {
                // move one elem from larger to smaller
                smaller.add(new IntMax(larger.poll()));
                smallerSize++;
                largerSize--;
            }
        }
    }

    public int getMedian() {
        // TODO: Implement your getMedian operation here
//        System.out.println(smaller.toString());
//        System.out.println(larger.toString());
        if (smallerSize + largerSize == 0) {
            return -1;
        }

        boolean isEven = (smallerSize + largerSize) % 2 == 0;
        if (isEven) {
            if (larger.isEmpty()) {
                int temp = smaller.poll().num;
                smallerSize--;
                return temp;
            } else {
                largerSize--;
                return larger.poll();
            }
            // return larger.isEmpty() ? smaller.poll().num : larger.poll();
        } else {
            int n = (smallerSize + largerSize + 1) / 2;
            if (n - smallerSize > 0) {
                largerSize--;
                return larger.poll();
            } else {
                smallerSize--;
                return smaller.poll().num;
            }
            // return n - smallerSize > 0 ? larger.poll() : smaller.poll().num;
        }
    }
}
