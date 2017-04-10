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
    public void oneMeepleDepletedTest() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate center = new Coordinate(100, 100);
        Coordinate SourceCoordinate = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERRIGHT);

        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);

        //found a settlement on lower right hex (uses 1 meeple)
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(SourceCoordinate);
        assertEquals(true, move1.canPerformMove(player_1, board));

        move1.makePreverifiedMove(player_1, board);
        assertEquals(19, player_1.getMeeplesCount());
        assertEquals(PieceStatusHexagon.MEEPLE, board.getHexagonAt(SourceCoordinate).getPiecesStatus());

        //rockCoordinate is a rock hex that is to the left of lower right (use 1 meeple)
        Coordinate rockCoordinate = new Coordinate(99,99);
        Hexagon lowerLeft = board.getHexagonAt(rockCoordinate);

        //create a grass hex that is of level one (should use 0 meeples)
        Coordinate rockCoordinate2 = new Coordinate(98,99);
        Hexagon grassHex = board.getHexagonAt(rockCoordinate2);
        grassHex.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        assertEquals(1, board.getHexagonAt(SourceCoordinate).getLevel());
        assertEquals(1, lowerLeft.getLevel());
        assertEquals(1, grassHex.getLevel());

        //when settlement is expanded it should use a total of 2 meelpes leaving a count of 16
        ExpandSettlementConstructionMove move2 = new ExpandSettlementConstructionMove(SourceCoordinate, Terrain.ROCK);
        assertEquals(true, move2.canPerformMove(player_1, board));

        move2.makePreverifiedMove(player_1, board);
        Settlement settlement = board.getSettlement(SourceCoordinate);
        assertEquals(2, settlement.getSettlementSize());
        assertEquals(PieceStatusHexagon.EMPTY, grassHex.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, lowerLeft.getPiecesStatus());
    }
    @Test
    public void twoMeeplesDepletedTest() throws Exception {
        Board board = startBoard();

        Coordinate center = new Coordinate(100, 100);
        Coordinate LowerRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERRIGHT);

        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);

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
        assertEquals(17, player_1.getMeeplesCount());
    }

    @Test
    public void expandOnMultipleLevelsTest() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate center = new Coordinate(100, 100);
        Coordinate lowerRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERRIGHT);

        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);
        //found a settlement on lower right hex (uses 1 meeple)
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(lowerRight);
        assertEquals(true, move1.canPerformMove(player_1, board));
        move1.makePreverifiedMove(player_1, board);

        assertEquals(19, player_1.getMeeplesCount());
        assertEquals(PieceStatusHexagon.MEEPLE, board.getHexagonAt(lowerRight).getPiecesStatus());

        //rockCoordinate is a rock hex that is to the left of lower right (use 1 meeple)
        Coordinate rockCoordinate = new Coordinate(99,99);
        Hexagon lowerLeft = board.getHexagonAt(rockCoordinate);
        //create a rock hex that is of level two (should use 2 meeples)
        Coordinate rockCoordinate2 = new Coordinate(98,99);
        Hexagon rockHex2 = board.getHexagonAt(rockCoordinate2);
        rockHex2.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        rockHex2.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        rockHex2.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        assertEquals(1, board.getHexagonAt(lowerRight).getLevel());
        assertEquals(1, lowerLeft.getLevel());
        assertEquals(3, rockHex2.getLevel());
        //when settlement is expanded it should use a total of 4 meelpes leaving a count of 16
        ExpandSettlementConstructionMove move2 = new ExpandSettlementConstructionMove(lowerRight, Terrain.ROCK);
        assertEquals(true, move2.canPerformMove(player_1, board));
        move2.makePreverifiedMove(player_1, board);

        assertEquals(15, player_1.getMeeplesCount());
        assertEquals(PieceStatusHexagon.MEEPLE, rockHex2.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, lowerLeft.getPiecesStatus());
    }

    @Test
    public void expandSettlementOnMultipleLevelsTest2() throws Exception {
        Board TestBoard = new Board();
        Player player = new Player(Color.BLACK);

        Tile tile_01 = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Tile tile_02 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Tile tile_03 = new Tile(Terrain.GRASS, Terrain.ROCK);
        Tile tile_04 = new Tile(Terrain.GRASS, Terrain.GRASS);

        TileMove tileMove_01 = new TileMove(tile_01, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 99));
        TileMove tileMove_02 = new TileMove(tile_02, HexagonNeighborDirection.LOWERLEFT, new Coordinate(100, 102));
        TileMove tileMove_03 = new TileMove(tile_03, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(100, 100));
        TileMove tileMove_04 = new TileMove(tile_04, HexagonNeighborDirection.LEFT, new Coordinate(100,100));

        TestBoard.placeTile(tileMove_01);
        TestBoard.placeTile(tileMove_02);
        TestBoard.placeTile(tileMove_03);
        TestBoard.placeTile(tileMove_04);

        Coordinate sourceCoordinate = new Coordinate(99,99);

        player.foundSettlement(sourceCoordinate, TestBoard);

        assertEquals(19, player.getMeeplesCount());
        assertEquals(PieceStatusHexagon.MEEPLE, TestBoard.getHexagonAt(sourceCoordinate).getPiecesStatus());

        boolean isSuccess = player.expandSettlement(sourceCoordinate, TestBoard, Terrain.GRASS);

        assertEquals(true, isSuccess);

        Hexagon TestHexagon1 = TestBoard.getHexagonAt(new Coordinate(100,101));
        Hexagon TestHexagon2 = TestBoard.getHexagonAt(new Coordinate(99,100));
        Hexagon TestHexagon3 = TestBoard.getHexagonAt(new Coordinate(99,101));

        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon1.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon2.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon3.getPiecesStatus());

        assertEquals(Color.BLACK, TestHexagon1.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon2.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon3.getOccupationColor());

        assertEquals(14, player.getMeeplesCount());
        assertEquals(10, player.getScore());

        Settlement settlement = TestBoard.getSettlement(sourceCoordinate);
        assertEquals(4, settlement.getSettlementSize());
    }

    @Test
    public void mergeTwoSettlementsWithTigerTest(){
        Board board = new Board();
        board.placeStartingTile();
        Player player = new Player(Color.WHITE);

        Tile tile = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.RIGHT, new Coordinate(97, 101));
        boolean isSuccess = board.placeTile(tileMove);
        assertEquals(true, isSuccess);

        // Make sure that the hexagons where the tile would've been placed where not modified.
        Coordinate TestCoordinate1 = new Coordinate(97,101);
        Coordinate TestCoordinate2 = new Coordinate(98,101);
        Coordinate TestCoordinate3 = new Coordinate(98,100);

        // Make the tile a level three tile
        Hexagon grassHex = board.getHexagonAt(TestCoordinate1);
        Hexagon NeighborOneHexagon = board.getHexagonAt(TestCoordinate2);
        Hexagon NeighborTwoHexagon = board.getHexagonAt(TestCoordinate3);

        grassHex.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        grassHex.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        NeighborOneHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        NeighborOneHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);

        assertEquals(Terrain.GRASS, grassHex.getTerrain());
        assertEquals(Terrain.GRASS, NeighborOneHexagon.getTerrain());
        assertEquals(Terrain.JUNGLE, NeighborTwoHexagon.getTerrain());
        assertEquals(3, grassHex.getLevel());
        assertEquals(3, NeighborOneHexagon.getLevel());
        assertEquals(1, NeighborTwoHexagon.getLevel());

        //find settlement at 100,101 tile and tile 98,100
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(new Coordinate(100,101));
        assertEquals(true, move1.canPerformMove(player, board));
        move1.makePreverifiedMove(player, board);
        Hexagon settlementHex = board.getHexagonAt(new Coordinate(98,100));
        assertEquals(1, settlementHex.getLevel());
        FoundSettlementConstructionMove move2 = new FoundSettlementConstructionMove(new Coordinate(98,100));
        assertEquals(true, move2.canPerformMove(player, board));
        move2.makePreverifiedMove(player, board);

        //expand settlement at 98,101
        ExpandSettlementConstructionMove move3 = new ExpandSettlementConstructionMove(new Coordinate(98,100), Terrain.GRASS);
        assertEquals(true, move3.canPerformMove(player, board));
        move3.makePreverifiedMove(player, board);

        //merge two settlements with a tiger
        Hexagon tigerHex = board.getHexagonAt(new Coordinate(99,101));
        tigerHex.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        tigerHex.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        boolean isValidMove = player.buildTigerPlayground(new Coordinate(99,101),board);
        assertEquals(3, tigerHex.getLevel());
        assertEquals(true, isValidMove);

        //check that the settlements are merged
        Settlement settlement = board.getSettlement(new Coordinate(98,101));
        assertEquals(5, settlement.getSettlementSize());
        assertEquals(12,player.getMeeplesCount());
        assertEquals(1,player.getTigerCount());

        Settlement settlement2 = board.getSettlement(new Coordinate(100,101));
        assertEquals(5, settlement.getSettlementSize());
        assertEquals(12,player.getMeeplesCount());
        assertEquals(1,player.getTigerCount());
    }

}