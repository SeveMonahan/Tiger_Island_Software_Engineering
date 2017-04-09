package TigerIsland;

public class FirstTurnTileMove extends TileMove{

    public FirstTurnTileMove(){
        super(new Tile(Terrain.UNKNOWN, Terrain.UNKNOWN), HexagonNeighborDirection.LEFT, new Coordinate(0, 0));
    }
}