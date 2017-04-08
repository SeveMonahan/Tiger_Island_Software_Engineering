package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TigerConstructionMoveTest {
    @Test
    public void canPerformMoveTest() {
        Board board = new Board();
        board.placeStartingTile();
        Coordinate upperRight = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);
        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);
        Coordinate right = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.RIGHT);
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(right);
        move1.makePreverifiedMove(player_1, board);

        TigerConstructionMove move2= new TigerConstructionMove(upperRight);
        assertEquals(upperRight, move2.getCoordinate());
        assertEquals(false, move2.canPerformMove(player_1, board));

        Hexagon levelThreeHexagon = new Hexagon();
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        assertEquals(3, levelThreeHexagon.getLevel());
        board.setHexagonAt(upperRight, levelThreeHexagon);

        assertEquals(2, player_1.getTigerCount());
        assertEquals(true, move2.canPerformMove(player_1, board));
    }
    @Test
    public void makePreverifiedMoveTest() {
        Board board = new Board();
        board.placeStartingTile();
        Coordinate upperRight = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);
        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);
        Coordinate right = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.RIGHT);
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(right);
        move1.makePreverifiedMove(player_1, board);
        assertEquals(1,player_1.getScore());

        TigerConstructionMove move2 = new TigerConstructionMove(upperRight);
        assertEquals(false, move2.canPerformMove(player_1, board));
        Hexagon levelThreeHexagon = new Hexagon();
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        assertEquals(3, levelThreeHexagon.getLevel());
        board.setHexagonAt(upperRight, levelThreeHexagon);
        assertEquals(2, player_1.getTigerCount());
        assertEquals(true, move2.canPerformMove(player_1, board));

        TigerConstructionMove move3 = new TigerConstructionMove(upperRight);
        move3.makePreverifiedMove(player_1, board);

        assertEquals(1, player_1.getTigerCount());
        assertEquals(76, player_1.getScore());
    }
    @Test
    public void tigerPlacedOnVolcano() {
        Board board = new Board();
        board.placeStartingTile();
        Coordinate upperRight = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);
        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);
        Coordinate right = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.RIGHT);
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(right);
        move1.makePreverifiedMove(player_1, board);
        assertEquals(1, player_1.getScore());

        TigerConstructionMove move2 = new TigerConstructionMove(upperRight);
        assertEquals(false, move2.canPerformMove(player_1, board));
        Hexagon levelThreeHexagon = new Hexagon();
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.VOLCANO);
        assertEquals(3, levelThreeHexagon.getLevel());
        board.setHexagonAt(upperRight, levelThreeHexagon);
        assertEquals(2, player_1.getTigerCount());
        assertEquals(false, move2.canPerformMove(player_1, board));
    }
    @Test
    public void cantPlaceTigerWithExsistingTigerInSettlement() {
        Board board = new Board();
        board.placeStartingTile();
        Coordinate upperRight = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);
        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);
        Coordinate right = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.RIGHT);
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(right);
        move1.makePreverifiedMove(player_1, board);
        assertEquals(1,player_1.getScore());

        TigerConstructionMove move2 = new TigerConstructionMove(upperRight);
        assertEquals(false, move2.canPerformMove(player_1, board));
        Hexagon levelThreeHexagon = new Hexagon();
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        assertEquals(3, levelThreeHexagon.getLevel());
        board.setHexagonAt(upperRight, levelThreeHexagon);
        assertEquals(2, player_1.getTigerCount());
        assertEquals(true, move2.canPerformMove(player_1, board));

        TigerConstructionMove move3 = new TigerConstructionMove(upperRight);
        move3.makePreverifiedMove(player_1, board);
        assertEquals(1, player_1.getTigerCount());
        assertEquals(76, player_1.getScore());

        Coordinate testCoordinate = upperRight.getNeighboringCoordinateAt(HexagonNeighborDirection.RIGHT);
        Hexagon levelThreeHexagon2 = new Hexagon();
        levelThreeHexagon2.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        levelThreeHexagon2.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        levelThreeHexagon2.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        TigerConstructionMove move4 = new TigerConstructionMove(testCoordinate);
        assertEquals(3, levelThreeHexagon2.getLevel());
        board.setHexagonAt(testCoordinate, levelThreeHexagon2);
        assertEquals(1, player_1.getTigerCount());
        assertEquals(false, move4.canPerformMove(player_1, board));

    }
}
