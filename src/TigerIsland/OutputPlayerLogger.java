package TigerIsland;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputPlayerLogger implements  OutputPlayerActions{
    private Color color;
    private String gid;

    public OutputPlayerLogger(String gid, Color colors){
        this.gid = gid;
        this.color = colors;
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

        GameMoveOutgoingTransmission gmt = new GameMoveOutgoingTransmission(gid, moveNumber, tileMove, constructionMoveTransmission);

        Color turnColor;
        if( gameStateEndOfTurn.isMyTurn(Color.BLACK) ) {
            turnColor = Color.BLACK;
        } else {
            turnColor = Color.WHITE;
        }

        Marshaller marshaller = new Marshaller();
        String message = marshaller.convertTileMoveAndConstructionMoveToString(gmt);
        FileWriter writer;
        try {
            writer = new FileWriter(new File("log.txt"), true);
            writer.write( turnColor + ":  " + message );
            writer.write(System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}