package TigerIsland.IntegrationTesting;

import TigerIsland.*;
import org.junit.Test;

public class FullGameTests {
    @Test
    public void FullGameTest_01() {
        String gid = "bullshit";
        DumbController ai_01 = new DumbController(Color.BLACK);
        DumbController ai_02 = new DumbController(Color.WHITE);
        OutputPlayerActions logger = new OutputPlayerLogger(gid, Color.BLACK );
        TileBag tileBag = new RandomTileBag();
        PostMan postManFaux = new PostMan();

        Referee referee = new Referee(ai_01, ai_02, logger, tileBag, postManFaux);

        referee.run();
    }
}
