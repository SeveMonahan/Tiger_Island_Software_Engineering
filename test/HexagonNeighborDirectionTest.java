package test;

import TigerIsland.HexagonNeighborDirection;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class HexagonNeighborDirectionTest {
    @Test
    public void getNextClockwise(){
        assertEquals(HexagonNeighborDirection.RIGHT, HexagonNeighborDirection.UPPERRIGHT.getNextClockwise());

        assertEquals(HexagonNeighborDirection.LEFT, HexagonNeighborDirection.LOWERLEFT.getNextClockwise());

        assertEquals(HexagonNeighborDirection.LOWERLEFT, HexagonNeighborDirection.LOWERRIGHT.getNextClockwise());
    }
}
