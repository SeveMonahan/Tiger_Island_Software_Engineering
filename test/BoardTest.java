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

}
