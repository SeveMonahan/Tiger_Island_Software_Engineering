package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TotoroTest {
    @Test
    public void shouldInitializeToThreeTotoro() {
        Player player = new Player(Color.WHITE);
        assertEquals(3, player.getTotoroCount());
    }

    @Test
    public void totoroShouldStopTilePlacement() {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate leftCoordinate = new Coordinate(99,100);

        Tile tile = new Tile(Terrain.ROCK, Terrain.ROCK);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.LEFT, leftCoordinate);

        Coordinate target = new Coordinate(98,101);
        Hexagon targetHexagon = board.getHexagonAt(target);
        targetHexagon.setOccupationStatus(Color.WHITE, PieceStatusHexagon.TOTORO);

        tileMove = new TileMove(tile, HexagonNeighborDirection.UPPERLEFT, leftCoordinate);
        boolean isValidMove = board.placeTile(tileMove);

        assertEquals(false, isValidMove);
        assertEquals(PieceStatusHexagon.TOTORO, targetHexagon.getPiecesStatus());
    }

    @Test
    public void buildTotoroSanctuaryFailsDueToVolcano() throws Exception {
        Board board = new Board();
        board.placeStartingTile();
        Player player = new Player(Color.WHITE);

        Tile tile = new Tile(Terrain.ROCK, Terrain.ROCK);
        Coordinate coordinate = new Coordinate(98,100);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.UPPERRIGHT, coordinate);
        board.placeTile(tileMove);

        Coordinate TestCoordinate1 = new Coordinate(100, 101);
        Coordinate TestCoordinate2 = new Coordinate(99,101);
        Coordinate TestCoordinate3 = new Coordinate(99,100);
        Coordinate TestCoordinate4 = new Coordinate(99,99);
        Coordinate TestCoordinate5 = new Coordinate(100,99);

        Hexagon TestHexagon1 = board.getHexagonAt(TestCoordinate1);
        Hexagon TestHexagon2 = board.getHexagonAt(TestCoordinate2);
        Hexagon TestHexagon3 = board.getHexagonAt(TestCoordinate3);
        Hexagon TestHexagon4 = board.getHexagonAt(TestCoordinate4);
        Hexagon TestHexagon5 = board.getHexagonAt(TestCoordinate5);

        TestHexagon1.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon2.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon3.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon4.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon5.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);

        Settlement settlement = board.getSettlement(TestCoordinate1);
        assertEquals(5, settlement.getSettlementSize());
        assertEquals(false, settlement.containsTotoro(board));

        Coordinate target = new Coordinate(98,100);
        Hexagon targetHexagon = board.getHexagonAt(target);

        boolean isSuccess = player.buildTotoroSanctuary(target, board);
        assertEquals(false, isSuccess);
        assertEquals(PieceStatusHexagon.EMPTY, targetHexagon.getPiecesStatus());
        assertEquals(3, player.getTotoroCount());
    }

    @Test
    public void buildTotoroSanctuaryFailsBecauseHexagonContainsPieces() throws Exception {
        Board board = new Board();
        board.placeStartingTile();
        Player player = new Player(Color.WHITE);

        Tile tile = new Tile(Terrain.ROCK, Terrain.ROCK);
        Coordinate coordinate = new Coordinate(98,100);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.UPPERRIGHT, coordinate);
        board.placeTile(tileMove);

        Coordinate TestCoordinate1 = new Coordinate(100, 101);
        Coordinate TestCoordinate2 = new Coordinate(99,101);
        Coordinate TestCoordinate3 = new Coordinate(99,100);
        Coordinate TestCoordinate4 = new Coordinate(99,99);
        Coordinate TestCoordinate5 = new Coordinate(100,99);
        Coordinate target = new Coordinate(98,101);

        Hexagon TestHexagon1 = board.getHexagonAt(TestCoordinate1);
        Hexagon TestHexagon2 = board.getHexagonAt(TestCoordinate2);
        Hexagon TestHexagon3 = board.getHexagonAt(TestCoordinate3);
        Hexagon TestHexagon4 = board.getHexagonAt(TestCoordinate4);
        Hexagon TestHexagon5 = board.getHexagonAt(TestCoordinate5);
        Hexagon targetHexagon = board.getHexagonAt(target);

        TestHexagon1.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon2.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon3.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon4.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon5.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        targetHexagon.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);

        Settlement settlement = board.getSettlement(TestCoordinate1);
        assertEquals(6, settlement.getSettlementSize());
        assertEquals(false, settlement.containsTotoro(board));

        boolean isSuccess = player.buildTotoroSanctuary(target, board);
        assertEquals(false, isSuccess);
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagon.getPiecesStatus());
        assertEquals(3, player.getTotoroCount());
    }

    @Test
    public void buildTotoroSanctuaryFailsDueToNoAdjacentSettlements() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Player player = new Player(Color.WHITE);

        Coordinate coordinate = new Coordinate(100,101);
        Hexagon hexagon = board.getHexagonAt(coordinate);
        boolean isSuccess = player.buildTotoroSanctuary(coordinate, board);

        assertEquals(false, isSuccess);
        assertEquals(0, player.getScore());
        assertEquals(3, player.getTotoroCount());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void buildTotoroSanctuaryFailsDueToSizeOfAdjacentSettlement() throws Exception {
        Board board = new Board();
        board.placeStartingTile();
        Player player = new Player(Color.WHITE);

        Tile tile = new Tile(Terrain.ROCK, Terrain.ROCK);
        Coordinate coordinate = new Coordinate(98,100);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.UPPERRIGHT, coordinate);
        board.placeTile(tileMove);

        Coordinate TestCoordinate1 = new Coordinate(100, 101);
        Coordinate TestCoordinate2 = new Coordinate(99,101);
        Coordinate TestCoordinate3 = new Coordinate(99,100);

        Hexagon TestHexagon1 = board.getHexagonAt(TestCoordinate1);
        Hexagon TestHexagon2 = board.getHexagonAt(TestCoordinate2);
        Hexagon TestHexagon3 = board.getHexagonAt(TestCoordinate3);

        TestHexagon1.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon2.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon3.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);

        Settlement settlement = board.getSettlement(TestCoordinate1);
        assertEquals(3, settlement.getSettlementSize());
        assertEquals(false, settlement.containsTotoro(board));

        Coordinate target = new Coordinate(98,101);
        Hexagon targetHexagon = board.getHexagonAt(target);

        boolean isSuccess = player.buildTotoroSanctuary(target, board);
        assertEquals(false, isSuccess);
        assertEquals(PieceStatusHexagon.EMPTY, targetHexagon.getPiecesStatus());
        assertEquals(3, player.getTotoroCount());
    }

    @Test
    public void buildTotoroSanctuaryFailsBecauseAdjacentSettlementAlreadyHasTotoro() throws Exception {
        Board board = new Board();
        board.placeStartingTile();
        Player player = new Player(Color.WHITE);

        Tile tile = new Tile(Terrain.ROCK, Terrain.ROCK);
        Coordinate coordinate = new Coordinate(98,100);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.UPPERRIGHT, coordinate);
        board.placeTile(tileMove);

        Coordinate TestCoordinate1 = new Coordinate(100, 101);
        Coordinate TestCoordinate2 = new Coordinate(99,101);
        Coordinate TestCoordinate3 = new Coordinate(99,100);
        Coordinate TestCoordinate4 = new Coordinate(99,99);
        Coordinate TestCoordinate5 = new Coordinate(100,99);

        Hexagon TestHexagon1 = board.getHexagonAt(TestCoordinate1);
        Hexagon TestHexagon2 = board.getHexagonAt(TestCoordinate2);
        Hexagon TestHexagon3 = board.getHexagonAt(TestCoordinate3);
        Hexagon TestHexagon4 = board.getHexagonAt(TestCoordinate4);
        Hexagon TestHexagon5 = board.getHexagonAt(TestCoordinate5);

        TestHexagon1.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon2.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon3.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon4.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
        TestHexagon5.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);

        Settlement settlement = board.getSettlement(TestCoordinate1);
        assertEquals(5, settlement.getSettlementSize());
        assertEquals(false, settlement.containsTotoro(board));

        Coordinate target = new Coordinate(98,100);
        Hexagon targetHexagon = board.getHexagonAt(target);
        targetHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        boolean isSuccess = player.buildTotoroSanctuary(target, board);
        assertEquals(true, isSuccess);
        assertEquals(PieceStatusHexagon.TOTORO, targetHexagon.getPiecesStatus());
        assertEquals(2, player.getTotoroCount());
        assertEquals(200, player.getScore());

        target = new Coordinate(98,101);
        targetHexagon = board.getHexagonAt(target);

        isSuccess = player.buildTotoroSanctuary(target, board);
        assertEquals(false, isSuccess);
        assertEquals(PieceStatusHexagon.EMPTY, targetHexagon.getPiecesStatus());
        assertEquals(2, player.getTotoroCount());
        assertEquals(200, player.getScore());
    }

    private Board getBasicBoardWithHexagonAroundStartWithWhiteMeeples(){
        Board board = new Board();
        board.placeStartingTile();
        board.placeTile(new TileMove(new Tile (Terrain.JUNGLE, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate (98,100)));
        board.placeTile(new TileMove(new Tile (Terrain.JUNGLE, Terrain.JUNGLE), HexagonNeighborDirection.LEFT, new Coordinate (102,100)));

        Player player = new Player(Color.WHITE);

        for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()) {
            assertEquals(true, player.foundSettlement(new Coordinate(100, 100).getNeighboringCoordinateAt(direction), board));
        }

        return board;
    }

    @Test
    public void boardTest() throws Exception {
        Board board = getBasicBoardWithHexagonAroundStartWithWhiteMeeples();
        Settlement settlement = board.getSettlement(new Coordinate(100, 101) );

        assertEquals(false, settlement.containsTotoro(board));
        assertEquals(6, settlement.getSettlementSize());
    }

    @Test
    public void placeTotoroSuccess() throws Exception {
        Player player = new Player(Color.WHITE);

        Board board = getBasicBoardWithHexagonAroundStartWithWhiteMeeples();

        boolean result = player.buildTotoroSanctuary(new Coordinate(98, 99), board);

        assertEquals(true, result);
    }

}
