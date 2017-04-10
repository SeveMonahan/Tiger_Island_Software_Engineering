package TigerIsland;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    FileWriter writer;
    String fileName;

    Logger(String fileName) {
        this.fileName = fileName;
    }

    public void print(String time , String message) {
        try {
            writer = new FileWriter(new File(fileName), true);
            writer.write( time + ":  " + message );
            writer.write(System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
