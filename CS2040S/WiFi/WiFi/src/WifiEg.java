import java.util.Arrays;

public class WifiEg {
    /**

     * Implement your solution here

     */

    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        Arrays.sort(houses);
        double begin = 0;
        double end = houses[houses.length-1] - houses[0];
        double mid;

        while (begin < end){
            mid = (begin + end) / 2;
            if (coverable(houses, numOfAccessPoints, mid)){
                end = mid - 0.1;
            } else {
                begin = mid + 0.1; // add 0.1 to avoid infinite loop
            }
        }
        return begin;
    }

    /**
     * Implement your solution here
     */

    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        Arrays.sort(houses);
        int index = 0;
        int currNo = 1;
// init front boundary
        double front = houses[index];
        while (index < houses.length - 1 && front < houses[houses.length-1] ){
            if ((houses[index+1] - front) > distance * 2){ // if one access point cannot cover two
                currNo += 1;
                front = houses[index+1]; // change front boundary to second
                index += 1;
            } else { // if one access point cover 2, change front boundary to exceeding part.
                front = front + distance * 2;
                while (houses[index] < front && index < houses.length - 1){
                    index += 1;
                }
                index -= 1;
            }
        }
        return currNo <= numOfAccessPoints;
    }
}
