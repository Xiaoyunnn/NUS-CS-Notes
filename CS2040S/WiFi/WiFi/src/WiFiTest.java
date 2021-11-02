import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WiFiTest {

    @Test
    public void computeDistance() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 2;
        assertEquals(1.0, WiFi.computeDistance(houses, numAccessPoints), 0.5);
    }

    @Test
    public void computeDistance2() {
        int[] houses = {1, 3, 10, 20};
        int numAccessPoints = 2;
        assertEquals(4.5, WiFi.computeDistance(houses, numAccessPoints), 0.5);
    }

    @Test
    public void computeDistance3() {
        int[] houses = {6, 13, 11, 12};
        int numAccessPoints = 2;
        assertEquals(1.0, WiFi.computeDistance(houses, numAccessPoints), 0.5);
    }

    @Test
    public void coverable1() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 2;
        assertTrue(WiFi.coverable(houses, numAccessPoints, 1.0));
    }

//    @org.junit.Test
//    public void coverable6() {
//        int[] houses = {};
//        int numAccessPoints = 1;
//        assertTrue(WiFi.coverable(houses, numAccessPoints, 7.0));
//    }

    @Test
    public void coverable2() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 0;
        assertFalse(WiFi.coverable(houses, numAccessPoints, 0.5));
    }

    @Test
    public void coverable3() {
        int[] houses = {1, 3, 10, 12};
        int numAccessPoints = 2;
        assertTrue(WiFi.coverable(houses, numAccessPoints, 1));
    }

    @Test
    public void coverable4() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 3;
        assertTrue(WiFi.coverable(houses, numAccessPoints, 0.5));
    }

    @Test
    public void coverable5() {
        int[] houses = {1, 2, 3, 12, 14, 15, 18, 20, 25};
        int numAccessPoints = 4;
        assertTrue(WifiEg.coverable(houses, numAccessPoints, 1));
    }
}