package TigerIsland;

public class Parser {

    //Takes in "MAKE YOUR MOVE IN GAME <gid> WITHIN <timemove> SECOND: MOVE <#> PLACE <tile>" and outputs Tile object
    public Tile makeYourMoveStringToTile(String makeYourMoveString) {
        String[] moveStringSplitBySpaceArray = makeYourMoveString.split("\\s+");

        final int tileIndex = 12;

        String tileString = moveStringSplitBySpaceArray[tileIndex];
        return tileStringToTile(tileString);
    }


    //Takes in "GAME <gid> MOVE <#> PLAYER <pid> <place> <build>" and outputs a TileMove object
    public TileMove opponentMoveStringToTileMove(String opponentMoveString){
        String[] opponentMoveStringSplitBySpaceArray = opponentMoveString.split("\\s+");

        final int tileIndex = 7;
        final int xIndex = 9;
        final int yIndex = 10;
        final int zIndex = 11;
        final int orientationIndex = 12;

        String tileString = opponentMoveStringSplitBySpaceArray[tileIndex];
        Tile tile = tileStringToTile(tileString);

        Coordinate coordinate = extractCoordinateFromString(opponentMoveStringSplitBySpaceArray, xIndex, yIndex, zIndex);

        int orientation = Integer.parseInt(opponentMoveStringSplitBySpaceArray[orientationIndex]);

        //Assign UPPERLEFT direction as arbitrary only to be converted to its actual direction
        //This has to be done because you can not create a null instance of an enum
        HexagonNeighborDirection direction = HexagonNeighborDirection.UPPERLEFT;
        direction = direction.intToDirection(orientation);

        return new TileMove(tile, direction, coordinate);
    }

    public BuildMove opponentMoveStringToBuildMove(String opponentMoveString){
        String[] opponentMoveStringSplitBySpaceArray = opponentMoveString.split("\\s+");
        final int buildKeyword1Index = 13;
        final int buildKeyword2Index = 14;
        String buildKeyword1 = opponentMoveStringSplitBySpaceArray[buildKeyword1Index];
        String buildKeyword2 = opponentMoveStringSplitBySpaceArray[buildKeyword2Index];

        if(buildKeyword1.equals("FOUNDED")) {
            final int xIndex = 16;
            final int yIndex = 17;
            final int zIndex = 18;

            Coordinate coordinate = extractCoordinateFromString(opponentMoveStringSplitBySpaceArray, xIndex, yIndex, zIndex);

            BuildOption buildOption = BuildOption.FOUNDSETTLEMENT;

            return new BuildMove(buildOption, coordinate);
        }
        else if(buildKeyword1.equals("EXPANDED")){
            final int xIndex = 16;
            final int yIndex = 17;
            final int zIndex = 18;
            final int terrainIndex = 19;

            Coordinate coordinate = extractCoordinateFromString(opponentMoveStringSplitBySpaceArray, xIndex, yIndex, zIndex);

            BuildOption buildOption = BuildOption.EXPANDSETTLEMENT;

            Terrain terrain = Terrain.valueOf(opponentMoveStringSplitBySpaceArray[terrainIndex]);

            return new BuildMove(buildOption, coordinate, terrain);
        }
        else if(buildKeyword1.equals("BUILT") && buildKeyword2.equals("TOTORO")){
            final int xIndex = 17;
            final int yIndex = 18;
            final int zIndex = 19;

            Coordinate coordinate = extractCoordinateFromString(opponentMoveStringSplitBySpaceArray, xIndex, yIndex, zIndex);

            BuildOption buildOption = BuildOption.BUILDTOTORO;

            return new BuildMove(buildOption, coordinate);
        }
        else if(buildKeyword1.equals("BUILT") && buildKeyword2.equals("TIGER")){
            final int xIndex = 17;
            final int yIndex = 18;
            final int zIndex = 19;

            Coordinate coordinate = extractCoordinateFromString(opponentMoveStringSplitBySpaceArray, xIndex, yIndex, zIndex);

            BuildOption buildOption = BuildOption.BUILDTIGER;

            return new BuildMove(buildOption, coordinate);
        }
        else return null;
    }

    //Converts string such as "JUNGLE+LAKE" to a tile object
    private Tile tileStringToTile(String tileString){
        String [] tileStringSplitByPlusSign = tileString.split("\\+");

        final int AterrainIndex = 0;
        final int BterrainIndex = 1;

        Terrain Aterrain = Terrain.valueOf(tileStringSplitByPlusSign[AterrainIndex]);
        Terrain Bterrain = Terrain.valueOf(tileStringSplitByPlusSign[BterrainIndex]);
        return new Tile(Aterrain, Bterrain);
    }

    private Coordinate extractCoordinateFromString(String[] stringArrayContainingCoordinate, int xIndex, int yIndex, int zIndex){
        int x = Integer.parseInt(stringArrayContainingCoordinate[xIndex]);
        int y = Integer.parseInt(stringArrayContainingCoordinate[yIndex]);
        int z = Integer.parseInt(stringArrayContainingCoordinate[zIndex]);
        return new Coordinate(x, y, z);
    }
}
