package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpandSettlementConstructionMoveTest {
    private Board startBoard(){
       Board board = new Board();
       board.placeStartingTile();
       return board;
    }

    @Test
    public void basic_test(){
        Board board = startBoard();

        Coordinate upperRight = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);

        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);

        // Place a meeple down on upperright
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(upperRight);

        assertEquals(true, move1.canPreformMove(player_1, board));

        move1.makePreverifiedMove(player_1, board);

        /// Expand a settlement to the adjacent Jungle hexagon
        ExpandSettlementConstructionMove move2 = new ExpandSettlementConstructionMove(upperRight, Terrain.JUNGLE);

        assertEquals(true, move2.canPreformMove(player_1, board));

        move2.makePreverifiedMove(player_1, board);

        Coordinate upperLeft = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);

        assertEquals(true, board.getHexagonAt(upperLeft).containsPieces());
        assertEquals(PieceStatusHexagon.MEEPLE , board.getHexagonAt(upperLeft).getPiecesStatus());
        assertEquals(Player_Color, board.getHexagonAt(upperLeft).getOccupationColor());
    }
}
