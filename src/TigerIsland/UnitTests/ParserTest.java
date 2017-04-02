package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

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

        messageParser.readMessage(serverLine1);
        messageParser.readMessage(serverLine2);
        messageParser.readMessage(serverLine3);
    }
}
