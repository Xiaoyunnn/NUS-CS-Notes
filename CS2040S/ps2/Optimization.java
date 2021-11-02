/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4}, // 11
            {1, 9, 11, 10, 8, 6, 4}, // 11
            {67, 65, 43, 42, 23, 17, 9, 100}, // 100
            {4, -100, -80, 15, 20, 25, 30}, // 30
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}, // 100
            {4, 5, 6, 7, 3, 2}, // 7
            {100, 90, 20, 30, 40},
            {98,231,2432,324,123,12},
            {0}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        // TODO: Implement this
        int max = dataArray[0]; // empty array throw array out of bound exception
        int len = dataArray.length;

        if (len > 1) {
            if ((dataArray[0] > dataArray[1]) && (dataArray[0] < dataArray[len - 1])) {
                // decreasing dir first: only need to compare the first element with the last element
                // last element is the max
                    max = dataArray[len - 1];
            } else if (dataArray[0] < dataArray[1]) {
                // increasing dir first: find the turning point (max) using binary search
                int start = 0;
                int end = len - 1;
                while (start < end) {
                    int mid = start + (end - start) / 2;
                    max = dataArray[mid];
                    if (max <= dataArray[mid]) {
                        if (dataArray[mid + 1] > dataArray [mid]) {
                            // increasing dir: recurse on the right
                            start = mid + 1;
                        } else {
                            //decreasing dir: recurse on the left
                            end = mid;
                        }
                    } else {
                        start = mid + 1;
                    }
                }
                max = dataArray[start];
            } // else: decreasing direction first && first element is the max; no need update max
        }
        return max;
    }

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
