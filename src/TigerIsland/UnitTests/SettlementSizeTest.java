package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SettlementSizeTest {
    @Test
    public void settlementOfSizeOne() {
        Player playerOne = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board();
        board.placeStartingTile();

        Coordinate centerCoordinate = new Coordinate(100,100);
        Coordinate targetOne = centerCoordinate.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);
        Coordinate targetTwo = centerCoordinate.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);

        playerOne.foundSettlement(targetOne, board);
        playerTwo.foundSettlement(targetTwo, board);

        Settlement settlement = board.getSettlement(targetOne);
        assertEquals(1, settlement.getSettlementSize());
    }

    @Test
    public void settlementOfSizeTwo() {
        Player playerOne = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board();
        board.placeStartingTile();

        Coordinate playerOneTargetOne = new Coordinate(99,101);
        Coordinate playerOneTargetTwo = new Coordinate (100,101);

        Coordinate playerTwoTargetOne = new Coordinate(99,99);
        Coordinate playerTwoTargetTwo = new Coordinate (100,99);

        playerOne.foundSettlement(playerOneTargetOne, board);
        playerOne.foundSettlement(playerOneTargetTwo, board);

        playerTwo.foundSettlement(playerTwoTargetOne, board);
        playerTwo.foundSettlement(playerTwoTargetTwo, board);

        Settlement settlementOne = board.getSettlement(playerOneTargetOne);
        Settlement settlementTwo = board.getSettlement(playerTwoTargetOne);

        assertEquals(2, settlementOne.getSettlementSize());
        assertEquals(2, settlementTwo.getSettlementSize());
    }
}