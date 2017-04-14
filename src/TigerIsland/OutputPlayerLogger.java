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

    public void dispatchInformation(GameStateEndOfTurn gameStateEndOfTurn, PlayerController playerController){
        String moveNumber = "3";
        TileMove tileMove = gameStateEndOfTurn.getLastTileMove();
        ConstructionMoveInternal constructMove = gameStateEndOfTurn.getLastConstructionMove();

        GameMoveOutgoingTransmission gmt = new GameMoveOutgoingTransmission(gid, moveNumber, tileMove, constructMove);

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