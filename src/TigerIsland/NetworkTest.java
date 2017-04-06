package TigerIsland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
       The server has to be run first, and it takes in a port for an argument.
       Use 4432.

       So the server and client are configured to send a line back and forth to each other.
       Right now I have them hooked up to standard input (the terminal).
       The goal is to have the client hooked up to the parser so it can reply back to the server automatically.

       The server sends out the first message, and the client must reply with a message in order to receive
       the next message from the server. This back and forth continues until server replies with "Bye."

*/
public class NetworkTest {
    public static void main(String[] args) throws IOException {
        int gid = 0;
        int cid = 0;
        int pid = 0;
        int numChallenges = 0;
        int numRounds = 0;
        int tileMoveTime = 0;
        String tileString = "";

        if (args.length != 1) {
            System.err.println("argument error");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        System.out.println("Now listening for connections...");
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("Client connected!");
            String inputLine, outputLine;
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            // Initiate conversation with client
            while (1 == 1) {
                outputLine = stdIn.readLine();
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}