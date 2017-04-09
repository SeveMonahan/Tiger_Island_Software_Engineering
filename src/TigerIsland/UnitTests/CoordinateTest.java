package TigerIsland.UnitTests;

import TigerIsland.Coordinate;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CoordinateTest {
    @Test
    public void convertToSquare() throws Exception {
        Coordinate testCoordinate = new Coordinate(1,-1,0);
        assertEquals(101, testCoordinate.getX());
        assertEquals(100, testCoordinate.getY());

        Coordinate testCoordinate2 = new Coordinate(2,-2,0);
        assertEquals(102, testCoordinate2.getX());
        assertEquals(100, testCoordinate2.getY());

        Coordinate testCoordinate3 = new Coordinate(-1,0,1);
        assertEquals(99, testCoordinate3.getX());
        assertEquals(99, testCoordinate3.getY());

        Coordinate testCoordinate4 = new Coordinate(-1,1,0);
        assertEquals(99, testCoordinate4.getX());
        assertEquals(100, testCoordinate4.getY());

        Coordinate testCoordinate5 = new Coordinate(0,1,-1);
        assertEquals(99, testCoordinate5.getX());
        assertEquals(101, testCoordinate5.getY());

        Coordinate testCoordinate6 = new Coordinate(2,-1,-1);
        assertEquals(101, testCoordinate6.getX());
        assertEquals(101, testCoordinate6.getY());

        Coordinate testCoordinate7 = new Coordinate(1,0,-1);
        assertEquals(100, testCoordinate7.getX());
        assertEquals(101, testCoordinate7.getY());

        Coordinate testCoordinate8 = new Coordinate(0,-1,1);
        assertEquals(100, testCoordinate8.getX());
        assertEquals(99, testCoordinate8.getY());

        Coordinate testCoordinate9 = new Coordinate(-2,1,1);
        assertEquals(98, testCoordinate9.getX());
        assertEquals(99, testCoordinate9.getY());
    }

    @Test
    public void convertToCubeCordinates() throws Exception {
        Coordinate testCoordinate = new Coordinate(101,100);
        int result[] = testCoordinate.ConvertToCube();
        int testResult[] = {1,-1,0};
        assertArrayEquals(testResult,result);

        Coordinate testCoordinate1 = new Coordinate(102,100);
        int result1[] = testCoordinate1.ConvertToCube();
        int testResult1[] = {2,-2,0};
        assertArrayEquals(testResult1,result1);

        Coordinate testCoordinate2 = new Coordinate(99,100);
        int result2[] = testCoordinate2.ConvertToCube();
        int testResult2[] = {-1,1,0};
        assertArrayEquals(testResult2, result2);

        Coordinate testCoordinate3 = new Coordinate(100,101);
        int result3[] = testCoordinate3.ConvertToCube();
        int testResult3[] = {1,0,-1};
        assertArrayEquals(testResult3, result3);

        Coordinate testCoordinate4 = new Coordinate(99,99);
        int result4[] = testCoordinate4.ConvertToCube();
        int testResult4[] = {-1,0,1};
        assertArrayEquals(testResult4, result4);

        Coordinate testCoordinate6 = new Coordinate(99,101);
        int result6[] = testCoordinate6.ConvertToCube();
        int testResult6[] = {0,1,-1};
        assertArrayEquals(testResult6, result6);

        Coordinate testCoordinate7 = new Coordinate(101,101);
        int result7[] = testCoordinate7.ConvertToCube();
        int testResult7[] = {2,-1,-1};
        assertArrayEquals(testResult7, result7);

        Coordinate testCoordinate9 = new Coordinate(101,99);
        int result9[] = testCoordinate9.ConvertToCube();
        int testResult9[] = {1,-2,1};
        assertArrayEquals(testResult9, result9);
    }
}