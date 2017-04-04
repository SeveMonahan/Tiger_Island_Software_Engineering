package TigerIsland;

public class Parser {

    //Will eventually return a tileMove
    public Tile makeYourMoveStringToTile(String makeYourMoveString) {
        String[] moveStringSplitBySpaceArray = makeYourMoveString.split("\\s+");
        String tileString = moveStringSplitBySpaceArray[12];

        String [] tileStringSplitByPlusSign = tileString.split("\\+");
        Terrain Aterrain = Terrain.valueOf(tileStringSplitByPlusSign[0]);
        Terrain Bterrain = Terrain.valueOf(tileStringSplitByPlusSign[1]);

        return new Tile(Aterrain, Bterrain);
    }
}
