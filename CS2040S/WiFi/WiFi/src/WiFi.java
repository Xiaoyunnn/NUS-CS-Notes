import java.util.Arrays;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        Arrays.sort(houses);
        int len = houses.length;
        double minRadius = houses[len - 1] - houses[0];

        double begin = 0;
        double end = minRadius/2;
        while (begin < end) {
            double mid = begin + (end - begin) / 2;
            // System.out.println("begin: " + begin + " end: " + end);
            //System.out.println(coverable(houses, numOfAccessPoints, mid));
            if (coverable(houses, numOfAccessPoints, mid)) {
                //System.out.println(mid);
                end = mid;
            } else {
                begin = mid + 0.5;
            }
        }
        minRadius = end;
        // System.out.println(minRadius);
        return minRadius;
    }

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        Arrays.sort(houses);
        int numLeft = numOfAccessPoints - 1;
        double maxCover = houses[0] + 2 * distance;
        // router 1: house 1 + distance
        // if router 1 + distance >= house 2 ==> select a new router to be placed at house 3 + dist

        for (int i = 1; i < houses.length; i++) {
            if (numLeft < 0) {
                break;
            } else if (houses[i] > maxCover) {
                numLeft--;
                maxCover = houses[i] + 2 * distance;
            } // else within range
        }
        return numLeft >= 0;
    }
}
