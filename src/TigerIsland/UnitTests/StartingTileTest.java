package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StartingTileTest {
    @Test
    public void initializedBoardWithStartingTileTerrainTypeTest() {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate center = new Coordinate(100,100);
        Coordinate upperLeft = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);
        Coordinate upperRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);
        Coordinate lowerRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERRIGHT);
        Coordinate lowerLeft = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERLEFT);

        Hexagon centerHexagon = board.getHexagonAt(center);
        Hexagon upperLeftHexagon = board.getHexagonAt(upperLeft);
        Hexagon upperRightHexagon = board.getHexagonAt(upperRight);
        Hexagon lowerRightHexagon = board.getHexagonAt(lowerRight);
        Hexagon lowerLeftHexagon = board.getHexagonAt(lowerLeft);

        assertEquals(Terrain.VOLCANO, centerHexagon.getTerrain());
        assertEquals(Terrain.JUNGLE, upperLeftHexagon.getTerrain());
        assertEquals(Terrain.LAKE, upperRightHexagon.getTerrain());
        assertEquals(Terrain.GRASS, lowerRightHexagon.getTerrain());
        assertEquals(Terrain.ROCK, lowerLeftHexagon.getTerrain());
    }

    @Test
    public void initializedBoardWithStartingTileShouldLevelOneTest() {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate center = new Coordinate(100,100);
        Coordinate upperLeft = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);
        Coordinate upperRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);
        Coordinate lowerRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERRIGHT);
        Coordinate lowerLeft = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERLEFT);

        Hexagon centerHexagon = board.getHexagonAt(center);
        Hexagon upperLeftHexagon = board.getHexagonAt(upperLeft);
        Hexagon upperRightHexagon = board.getHexagonAt(upperRight);
        Hexagon lowerRightHexagon = board.getHexagonAt(lowerRight);
        Hexagon lowerLeftHexagon = board.getHexagonAt(lowerLeft);

        assertEquals(1, centerHexagon.getLevel());
        assertEquals(1, upperLeftHexagon.getLevel());
        assertEquals(1, upperRightHexagon.getLevel());
        assertEquals(1, lowerRightHexagon.getLevel());
        assertEquals(1, lowerLeftHexagon.getLevel());
    }

    @Test
    public void initializedBoardWithStartingTileHashCodeTest() {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate center = new Coordinate(100,100);
        Coordinate upperLeft = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);
        Coordinate upperRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);
        Coordinate lowerRight = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERRIGHT);
        Coordinate lowerLeft = center.getNeighboringCoordinateAt(HexagonNeighborDirection.LOWERLEFT);

        Hexagon centerHexagon = board.getHexagonAt(center);
        Hexagon upperLeftHexagon = board.getHexagonAt(upperLeft);
        Hexagon upperRightHexagon = board.getHexagonAt(upperRight);
        Hexagon lowerRightHexagon = board.getHexagonAt(lowerRight);
        Hexagon lowerLeftHexagon = board.getHexagonAt(lowerLeft);

        assertEquals(0, centerHexagon.getTileHashCode());
        assertEquals(0, upperLeftHexagon.getTileHashCode());
        assertEquals(0, upperRightHexagon.getTileHashCode());
        assertEquals(0, lowerRightHexagon.getTileHashCode());
        assertEquals(0, lowerLeftHexagon.getTileHashCode());
    }
}