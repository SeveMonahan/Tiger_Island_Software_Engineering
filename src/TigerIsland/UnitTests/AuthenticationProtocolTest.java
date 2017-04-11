import TigerIsland.AuthenticationProtocol;
import TigerIsland.NetworkClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.Object;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AuthenticationProtocolTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    // "ENTER THUNDERDOME " + tournamentPass
    // "I AM " + username + " " + password
    // "WAIT FOR THE TOURNAMENT TO BEGIN ", "");


    @Test
    public static void authenticatingCorrectly() {
        String host = "HostName";
        String tournamentPass = "tournPW";
        String username = "username";
        String password = "password";
        int port = 3455;

        try (
                Socket netSocket = new Socket(host, port);
                PrintWriter out = new PrintWriter(netSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(netSocket.getInputStream()))
        ) {
            NetworkClient output_taker = new NetworkClient(out, in);

            int pid = AuthenticationProtocol.authenticationProtocol(tournamentPass, username, password, output_taker);

            // output_taker.challengeProtocol(pid);

        } catch (UnknownHostException e) {
            System.err.println("Can't find the host!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Can't connect to the host!");
            System.exit(1);
        }

        // int pid = AuthenticationProtocol.authenticationProtocol();
    }
}
