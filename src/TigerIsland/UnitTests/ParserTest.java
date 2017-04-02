package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class ParserTest {
    @Test
    public void authenticationProtocol() {
        String serverLine1 = "WELCOME TO ANOTHER EDITION OF THUNDERDOME!";
        String serverLine2 = "TWO SHALL ENTER, ONE SHALL LEAVE";
        String serverLine3 = "WAIT FOR THE TOURNAMENT TO BEGIN <pid>";

        final String tournamentPassword = "tournamentPasswordExample";
        final String username = "usernameExample";
        final String password = "passwordExample";
        Parser messageParser = new Parser(tournamentPassword, username, password);

        boolean isLine1ReadSuccess = messageParser.readMessage(serverLine1);
        boolean isLine2ReadSuccess = messageParser.readMessage(serverLine2);
        boolean isLine3ReadSuccess = messageParser.readMessage(serverLine3);

        assertEquals(true, isLine1ReadSuccess);
        assertEquals(true, isLine2ReadSuccess);
        assertEquals(true, isLine3ReadSuccess);

    }

    @Test
    public void challengeProtocol() {
        String serverLine1 = "NEW CHALLENGE <cid> YOU WILL PLAY 5 MATCH";
        String serverLine2 = "END OF CHALLENGES";
        String serverLine3 = "WAIT FOR THE NEXT CHALLENGE TO BEGIN";

        Parser messageParser = new Parser();

        boolean isLine1ReadSuccess = messageParser.readMessage(serverLine1);

        final int roundsExpected = 5;
        assertEquals(roundsExpected, messageParser.getRounds());

        boolean isLine2ReadSuccess = messageParser.readMessage(serverLine2);
        boolean isLine3ReadSuccess = messageParser.readMessage(serverLine3);
        assertEquals(true, isLine1ReadSuccess);
        assertEquals(true, isLine2ReadSuccess);
        assertEquals(true, isLine3ReadSuccess);
    }
}
