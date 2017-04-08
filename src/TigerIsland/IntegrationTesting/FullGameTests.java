package TigerIsland.IntegrationTesting;

import TigerIsland.*;
import org.junit.Test;

public class FullGameTests {
    @Test
    public void FullGameTest_01() {
        String gid = "bullshit";
        AIController ai_01 = new AIController(Color.BLACK);
        AIController ai_02 = new AIController(Color.WHITE);
        OutputPlayerActions logger = new OutputPlayerLogger(gid, Color.BLACK );
        TileBag tileBag = new RandomTileBag();

        Referee referee = new Referee(ai_01, ai_02, logger, tileBag);

        referee.Execute();
    }
}
