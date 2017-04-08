package TigerIsland;

import java.io.FileWriter;
import java.io.IOException;

public class OutputPlayerLogger implements  OutputPlayerActions{
    private Color color;
    private String gid;

    public OutputPlayerLogger(String gid, Color colors){
        this.gid = gid;
        this.color = color;
    }

    public void dispatchInformation(GameStateEndOfTurn gameStateEndOfTurn){
        int moveNumber = 1;
        TileMove tileMove = gameStateEndOfTurn.getLastTileMove();
        ConstructionMoveInternal constructMove = gameStateEndOfTurn.getLastConstructionMove();
        BuildOption buildOption = BuildOption.UNABLETOBUILD;
        if(constructMove instanceof ExpandSettlementConstructionMove) { //expand
            buildOption = BuildOption.EXPANDSETTLEMENT;
        } else if(constructMove instanceof FoundSettlementConstructionMove ) { // found
            buildOption = BuildOption.FOUNDSETTLEMENT;
        } else if(constructMove instanceof TigerConstructionMove ) { // tiger
            buildOption = BuildOption.BUILDTIGER;
        } else if(constructMove instanceof TotoroConstructionMove) { // totoro
            buildOption = BuildOption.BUILDTOTORO;
        }

        Coordinate buildCoordinate = constructMove.getCoordinate(); //

        ConstructionMoveTransmission constructionMoveTransmission = new ConstructionMoveTransmission(buildOption, buildCoordinate);

        GameMoveTransmission gmt = new GameMoveTransmission(gid, moveNumber, tileMove, constructionMoveTransmission);

        Marshaller marshaller = new Marshaller();
        String message = marshaller.convertTileMoveAndConstructionMoveToString(gmt);

        try {
            FileWriter writer = new FileWriter("log.txt", true);
            writer.write( message );
            writer.write("\r\n");   // write new line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
