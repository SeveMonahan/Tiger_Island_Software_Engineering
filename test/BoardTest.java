import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void createBoard() throws Exception{
        Board TestBoard = new Board();
    }

    @Test
    public void getHexagon() throws Exception{
        Board TestBoard = new Board();

        assert(TestBoard.getHexagonUsingPrivateIndexingForTest(0,0)
                instanceof Hexagon);
    }

    @Test
    public void setHexagon() throws Exception{
        Board TestBoard = new Board();

        Hexagon TestHexagon = new Hexagon();

        TestHexagon.incrementlevel();

        TestBoard.setHexagonUsingPrivateIndexingForTest(0,0,TestHexagon);

        Hexagon ReturnedHexagon = TestBoard.getHexagonUsingPrivateIndexingForTest(0,0);

        assertEquals(1, ReturnedHexagon.getlevel());

    }
}
