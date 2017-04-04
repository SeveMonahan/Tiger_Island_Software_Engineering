package TigerIsland;

public class Parser {

    //Takes in "MAKE YOUR MOVE IN GAME <gid> WITHIN <timemove> SECOND: MOVE <#> PLACE <tile>" and outputs Tile object
    public Tile makeYourMoveStringToTile(String makeYourMoveString) {
        String[] moveStringSplitBySpaceArray = makeYourMoveString.split("\\s+");

        String tileString = moveStringSplitBySpaceArray[12];
        return tileStringToTile(tileString);
    }


    //Takes in "GAME <gid> MOVE <#> PLAYER <pid> <place> <build>" and outputs a TileMove object
    public TileMove opponentMoveStringToTileMove(String opponentMoveString){
        String[] opponentMoveStringSplitBySpaceArray = opponentMoveString.split("\\s+");

        String tileString = opponentMoveStringSplitBySpaceArray[7];
        Tile tile = tileStringToTile(tileString);

        int x = Integer.parseInt(opponentMoveStringSplitBySpaceArray[9]);
        int y = Integer.parseInt(opponentMoveStringSplitBySpaceArray[10]);
        int z = Integer.parseInt(opponentMoveStringSplitBySpaceArray[11]);
        Coordinate coordinate = new Coordinate(x, y, z);

        int orientation = Integer.parseInt(opponentMoveStringSplitBySpaceArray[12]);

        //Assign UPPERLEFT direction as arbitrary only to be converted to its actual direction
        //This has to be done because you can not create a null instance of an enum
        HexagonNeighborDirection direction = HexagonNeighborDirection.UPPERLEFT;
        direction = direction.intToDirection(orientation);

        return new TileMove(tile, direction, coordinate);
    }


    //Converts string such as "JUNGLE+LAKE" to a tile object
    private Tile tileStringToTile(String tileString){
        String [] tileStringSplitByPlusSign = tileString.split("\\+");
        Terrain Aterrain = Terrain.valueOf(tileStringSplitByPlusSign[0]);
        Terrain Bterrain = Terrain.valueOf(tileStringSplitByPlusSign[1]);
        return new Tile(Aterrain, Bterrain);
    }
}
