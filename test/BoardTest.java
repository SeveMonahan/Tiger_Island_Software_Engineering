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

    @Test
    public void getNeighborsOddX() throws Exception{
        Board TestBoard = new Board();

        Hexagon TestHexagon = new Hexagon();

        TestHexagon.incrementlevel();

        TestBoard.setHexagonUsingPrivateIndexingForTest(102, 101, TestHexagon);
        TestBoard.setHexagonUsingPrivateIndexingForTest(100, 101, TestHexagon);

        TestBoard.setHexagonUsingPrivateIndexingForTest(101, 100, TestHexagon);
        TestBoard.setHexagonUsingPrivateIndexingForTest(101, 102, TestHexagon);

        TestBoard.setHexagonUsingPrivateIndexingForTest(102, 100, TestHexagon);
        TestBoard.setHexagonUsingPrivateIndexingForTest(102, 102, TestHexagon);

        Hexagon[] neighbors = TestBoard.getNeighbors(101,101);

        for(int i=0; i<6; i++){
            assertEquals(1, neighbors[i].getlevel());
        }
    }

    @Test
    public void getNeighborsEvenX() throws Exception{
        Board TestBoard = new Board();

        Hexagon TestHexagon = new Hexagon();

        TestHexagon.incrementlevel();

        TestBoard.setHexagonUsingPrivateIndexingForTest(53, 52, TestHexagon);
        TestBoard.setHexagonUsingPrivateIndexingForTest(51, 52, TestHexagon);

        TestBoard.setHexagonUsingPrivateIndexingForTest(52, 51, TestHexagon);
        TestBoard.setHexagonUsingPrivateIndexingForTest(52, 53, TestHexagon);

        TestBoard.setHexagonUsingPrivateIndexingForTest(51, 51, TestHexagon);
        TestBoard.setHexagonUsingPrivateIndexingForTest(51, 53, TestHexagon);

        Hexagon[] neighbors = TestBoard.getNeighbors(52,52);

        for(int i=0; i<6; i++){
            assertEquals(1, neighbors[i].getlevel());
        }
    }
}
