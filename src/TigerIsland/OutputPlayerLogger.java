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
        // GameMoveTransmission gmt = new GameMoveTransmission(gid, moveNumber, tileMove, convertConstructionMove(constructMove));

        // String message = new Marshaller(gmt);

        try {
            FileWriter writer = new FileWriter("log.txt", true);
            writer.write("\r\n");   // write new line
            // writer.write( );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
