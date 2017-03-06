import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HexagonTest {
    @Test
    public void getlevel() throws Exception{
        Hexagon TestHexagon = new Hexagon();
        assertEquals(0, TestHexagon.getlevel());
    }

    @Test
    public void incrementlevel() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.incrementlevel();
        assertEquals(1, TestHexagon.getlevel());
    }
}