package TigerIsland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class testServer {
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

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {

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