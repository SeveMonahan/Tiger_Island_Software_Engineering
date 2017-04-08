package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HexagonTest {
    @Test
    public void getlevel() throws Exception{
        Hexagon TestHexagon = new Hexagon();
        assertEquals(0, TestHexagon.getLevel());
    }

    @Test
    public void changeTerrainTypeThoughExplosion() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.LAKE);
        assertEquals(1, TestHexagon.getLevel());
        assertEquals(Terrain.LAKE, TestHexagon.getTerrain());
    }

    @Test
    public void isVolcanoTest() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.VOLCANO);
        assertEquals(true, TestHexagon.isVolcano());
    }

    @Test
    public void cloneHexagonMeeple() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.setOccupationStatus(Color.WHITE, PieceStatusHexagon.MEEPLE);

        assertEquals(Color.WHITE, TestHexagon.getOccupationColor());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon.getPiecesStatus());

        Hexagon CloneHexagon = Hexagon.cloneHexagon(TestHexagon);

        assertEquals(Color.WHITE, CloneHexagon.getOccupationColor());
        assertEquals(PieceStatusHexagon.MEEPLE, CloneHexagon.getPiecesStatus());

        CloneHexagon.setOccupationStatus(Color.BLACK, PieceStatusHexagon.TIGER);

        assertEquals(Color.BLACK, CloneHexagon.getOccupationColor());
        assertEquals(PieceStatusHexagon.TIGER, CloneHexagon.getPiecesStatus());

        assertEquals(Color.WHITE, TestHexagon.getOccupationColor());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon.getPiecesStatus());
    }

    @Test
    public void cloneHexagonMeepleInBoard() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate chosenCoordinate = new Coordinate(101, 101);
        Hexagon TestHexagon = board.getHexagonAt(chosenCoordinate);

        TestHexagon.setOccupationStatus(Color.WHITE, PieceStatusHexagon.MEEPLE);

        assertEquals(Color.WHITE, TestHexagon.getOccupationColor());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon.getPiecesStatus());

        Board cloneBoard = Board.cloneBoard(board);

        Hexagon CloneHexagon = cloneBoard.getHexagonAt(chosenCoordinate);

        assertEquals(Color.WHITE, CloneHexagon.getOccupationColor());
        assertEquals(PieceStatusHexagon.MEEPLE, CloneHexagon.getPiecesStatus());

        CloneHexagon.setOccupationStatus(Color.BLACK, PieceStatusHexagon.TIGER);

        assertEquals(Color.BLACK, CloneHexagon.getOccupationColor());
        assertEquals(PieceStatusHexagon.TIGER, CloneHexagon.getPiecesStatus());

        assertEquals(Color.WHITE, TestHexagon.getOccupationColor());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon.getPiecesStatus());
    }
}