import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by jayC on 3/8/17.
 */
public class MeeplesTest {
    @Test
    public void meeplesCount() throws Exception {
        Meeples myMeeples = new Meeples();
        assertEquals(20, myMeeples.getCount());
    }
}
