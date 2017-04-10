package TigerIsland.IntegrationTesting;

import TigerIsland.*;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FullGameTests {

    /*
    private int dumb_wins = 0;
    private int smart_wins = 0;
    public void FullGameTest_01() {
        FileWriter writer;

        String gid = "Best vs. Dumb";
        PlayerController ai_01 = new SmartAIController(Color.BLACK);
        PlayerController ai_02 = new GenuisAIController(Color.WHITE);
        OutputPlayerActions logger = new OutputPlayerLogger(gid, Color.BLACK );
        TileBag tileBag = new RandomTileBag();

        Referee referee = new Referee(ai_01, ai_02, logger, tileBag);

        if(referee.execute()){
            dumb_wins++;
        }

        try {
            writer = new FileWriter(new File("log.txt"), true);
            writer.write("\nNEW GAME:\n");
            writer.write(System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gid = "SmartAI vs. Best";
        ai_02 = new SmartAIController(Color.BLACK);
        ai_01 = new GenuisAIController(Color.WHITE);
        logger = new OutputPlayerLogger(gid, Color.BLACK );
        tileBag = new RandomTileBag();

        referee = new Referee(ai_01, ai_02, logger, tileBag);

        if(referee.execute()){
            dumb_wins++;
        }

        gid = "SmartAI vs. Best";
        ai_01 = new SmartAIController(Color.BLACK);
        ai_02 = new DumbController(Color.WHITE);
        logger = new OutputPlayerLogger(gid, Color.BLACK );
        tileBag = new RandomTileBag();

        referee = new Referee(ai_01, ai_02, logger, tileBag);

        if(referee.execute()){
            smart_wins++;
        }

        gid = "SmartAI vs. Best";
        ai_02 = new SmartAIController(Color.BLACK);
        ai_01 = new DumbController(Color.WHITE);
        logger = new OutputPlayerLogger(gid, Color.BLACK );
        tileBag = new RandomTileBag();

        referee = new Referee(ai_01, ai_02, logger, tileBag);

        if(referee.execute()){
            smart_wins++;
        }
    }

    @Test
    public void Many(){
        dumb_wins = 0;
        smart_wins = 0;
        for(int i = 0; i < 20; i++){
            FullGameTest_01();
        }

        System.out.print("GenuisAI games won:");
        System.out.println(dumb_wins);

        System.out.print("Dumb games won:");
        System.out.println(smart_wins);


    }
    */

}
