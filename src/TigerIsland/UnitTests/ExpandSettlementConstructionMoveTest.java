package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpandSettlementConstructionMoveTest {
    private Board startBoard() {
       Board board = new Board();
       board.placeStartingTile();
       return board;
    }

    @Test
    public void basic_test() {
        Board board = startBoard();

        Coordinate center = new Coordinate(100,100);
        Coordinate upperRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);

        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);

        // Place a meeple down on upperRight.
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(upperRight);
        assertEquals(true, move1.canPerformMove(player_1, board));
        move1.makePreverifiedMove(player_1, board);

        /// Expand the settlement to the adjacent Jungle hexagon.
        ExpandSettlementConstructionMove move2 = new ExpandSettlementConstructionMove(upperRight, Terrain.JUNGLE);
        assertEquals(true, move2.canPerformMove(player_1, board));
        move2.makePreverifiedMove(player_1, board);

        Coordinate upperLeft = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);

        assertEquals(true, board.getHexagonAt(upperLeft).containsPieces());
        assertEquals(PieceStatusHexagon.MEEPLE , board.getHexagonAt(upperLeft).getPiecesStatus());
        assertEquals(Player_Color, board.getHexagonAt(upperLeft).getOccupationColor());
    }

    @Test
    public void expandSettlementFailsDueToNumberOfMeeplesLeft() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        Coordinate coordinate = new Coordinate(99,100);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.LEFT, coordinate);
        board.placeTile(tileMove);

        Player player = new Player(Color.BLACK);
        Coordinate sourceCoordinate = new Coordinate(98,100);
        player.foundSettlement(sourceCoordinate, board);
        assertEquals(19, player.getMeeplesCount());

        player.subtractMeeples(18);

        boolean isSuccess = player.expandSettlement(sourceCoordinate, board, Terrain.JUNGLE);
        assertEquals(false, isSuccess);
        assertEquals(1, player.getMeeplesCount());
    }
    @Test
    public void expandSettlementFailsDueToMismatchTerrain() throws Exception {
        Board board = startBoard();

        Coordinate center = new Coordinate(100, 100);
        Coordinate LowerRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERRIGHT);

        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);

        // Place a meeple down on LowerRight.
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(LowerRight);
        assertEquals(true, move1.canPerformMove(player_1, board));
        move1.makePreverifiedMove(player_1, board);

        /// Expand the settlement to the adjacent Jungle hexagon.
        ExpandSettlementConstructionMove move2 = new ExpandSettlementConstructionMove(LowerRight, Terrain.JUNGLE);
        assertEquals(false, move2.canPerformMove(player_1, board));
        move2.makePreverifiedMove(player_1, board);
    }
    @Test
    public void twoMeeplesDepletedTest() throws Exception {
        Board board = startBoard();

        Coordinate center = new Coordinate(100, 100);
        Coordinate LowerRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERRIGHT);

        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);

        // Place a meeple down on LowerRight.
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(LowerRight);
        assertEquals(true, move1.canPerformMove(player_1, board));
        move1.makePreverifiedMove(player_1, board);

        Coordinate rockCoordinate = LowerRight.getNeighboringCoordinateAt(HexagonNeighborDirection.LEFT);
        Coordinate rockCoordinate2 = rockCoordinate.getNeighboringCoordinateAt(HexagonNeighborDirection.LEFT);
        Hexagon rockHex2 = board.getHexagonAt(rockCoordinate2);
        rockHex2.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        assertEquals(1,rockHex2.getLevel());

        ExpandSettlementConstructionMove move2 = new ExpandSettlementConstructionMove(LowerRight, Terrain.ROCK);
        assertEquals(true, move2.canPerformMove(player_1, board));
        move2.makePreverifiedMove(player_1, board);
        assertEquals(17,player_1.getMeeplesCount());

    }
}
