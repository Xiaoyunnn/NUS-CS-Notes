/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean feasibleLoad(int[] jobSize, int queryLoad, int p) {
        // TODO: Implement this
        int len = jobSize.length;
        int numLeft = p - 1;
        int currLoad = 0;
        for (int i = 0; i < len; i++) {
            if (currLoad + jobSize[i] <= queryLoad) {
                currLoad += jobSize[i];
            } else {
                // allocate to a new processor
                numLeft--;
                currLoad = jobSize[i];
                if (currLoad > queryLoad) {
                    return false;
                }
            }
        }
        return numLeft >= 0;
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSize, int p) {
        // TODO: Implement this
        int maxLoad = 0;
        int len = jobSize.length;
        for (int i = 0; i < len; i++) {
            maxLoad += jobSize[i];
        }

        int start = 0;
        int end = maxLoad;
        while (start < end) {
            int mid = start + (end - start) / 2;

            if (feasibleLoad(jobSize, mid, p)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        maxLoad = end;
        return maxLoad;
    }

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4}, // 1357/9/11/10/864
            {67, 65, 43, 42, 23, 17, 9, 100}, // 67 65/43 43 23 / 17 9 100
            {4, 100, 80, 15, 20, 25, 30}, // 4 100 / 80 15 20 25 30
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            // 12345678 100 99 / 98 97 / 96 95 / 94 93 / 92 91 / 90 89 / 88 87 86/ 85 84 83
            {2,3},
            {7},
            {}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
