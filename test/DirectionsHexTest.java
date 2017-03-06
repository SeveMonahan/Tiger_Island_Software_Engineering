import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DirectionsHexTest {
    @Test
    public void getNextClockwise(){
        assertEquals(DirectionsHex.RIGHT, DirectionsHex.UPPERRIGHT.getNextClockwise());

        assertEquals(DirectionsHex.LEFT, DirectionsHex.LOWERLEFT.getNextClockwise());

        assertEquals(DirectionsHex.LOWERLEFT, DirectionsHex.LOWERRIGHT.getNextClockwise());
    }
}
