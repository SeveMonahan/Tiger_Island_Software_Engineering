package TigerIsland.UnitTests;
import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class TigerConstructionMoveTest {
    @Test
    public void canPerformMoveTest() {
        Board board = new Board();
        board.placeStartingTile();
        Coordinate upperRight = new Coordinate(100, 100).getNeighboringCoordinate(HexagonNeighborDirection.UPPERRIGHT);
        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);
        Coordinate right = new Coordinate(100, 100).getNeighboringCoordinate(HexagonNeighborDirection.RIGHT);
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(right);
        move1.makePreverifiedMove(player_1, board);

        TigerConstructionMove move2= new TigerConstructionMove(upperRight);

        assertEquals(false, move2.canPerformMove(player_1, board));

        Hexagon levelTwoHexagon = new Hexagon();
        levelTwoHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        levelTwoHexagon.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        levelTwoHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        assertEquals(3, levelTwoHexagon.getLevel());
        board.setHexagonAt(upperRight, levelTwoHexagon);

        assertEquals(2, player_1.getTigerCount());
        assertEquals(true, move2.canPerformMove(player_1, board));
    }
    @Test
    public void makePreverifiedMoveTest() {
        Board board = new Board();
        board.placeStartingTile();
        Coordinate upperRight = new Coordinate(100, 100).getNeighboringCoordinate(HexagonNeighborDirection.UPPERRIGHT);
        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);
        Coordinate right = new Coordinate(100, 100).getNeighboringCoordinate(HexagonNeighborDirection.RIGHT);
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(right);
        move1.makePreverifiedMove(player_1, board);
        assertEquals(1,player_1.getScore());

        TigerConstructionMove move2 = new TigerConstructionMove(upperRight);
        assertEquals(false, move2.canPerformMove(player_1, board));
        Hexagon levelTwoHexagon = new Hexagon();
        levelTwoHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        levelTwoHexagon.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        levelTwoHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        assertEquals(3, levelTwoHexagon.getLevel());
        board.setHexagonAt(upperRight, levelTwoHexagon);
        assertEquals(2, player_1.getTigerCount());
        assertEquals(true, move2.canPerformMove(player_1, board));

        TigerConstructionMove move3 = new TigerConstructionMove(upperRight);
        move3.makePreverifiedMove(player_1, board);

        assertEquals(1, player_1.getTigerCount());
        assertEquals(76, player_1.getScore());
    }
}
