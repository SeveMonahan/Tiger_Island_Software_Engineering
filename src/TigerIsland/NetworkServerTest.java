package TigerIsland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/*
       The server has to be run first, and it takes in a port for an argument.
       Use 4432.

       So the4 server and client are configured to send a line back and forth to each other.
       Right now I have them hooked up to standard input (the terminal).
       The goal is to have the client hooked up to the parser so it can reply back to the server automatically.

       The server sends out the first message, and the client must reply with a message in order to receive
       the next message from the server. This back and forth continues until server replies with "Bye."

       WELCOME TO ANOTHER EDITION OF THUNDERDOME!
       TWO SHALL ENTER, ONE SHALL LEAVE
       WAIT FOR THE TOURNAMENT TO BEGIN 945
*/
public class NetworkServerTest {

    public static void main(String[] args) throws IOException, InterruptedException {
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
                        new InputStreamReader(clientSocket.getInputStream()))
        ) {
            System.out.println("Client connected!");
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            // Initiate conversation with client
            out.println("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");
            out.println("TWO SHALL ENTER, ONE SHALL LEAVE");
            out.println("WAIT FOR THE TOURNAMENT TO BEGIN 853");
            String[] messages = {
                    "NEW CHALLENGE 346 YOU WILL PLAY 2 MATCHES",
                    "BEGIN ROUND 1 OF 2",
                    "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 34",
                    "MAKE YOUR MOVE IN GAME A WITHIN 1 SECOND: MOVE 1 PLACE LAKE+ROCK",
                    "GAME A MOVE 1 PLAYER 34 PLACED GRASS+GRASS AT -2 1 1 1 FOUNDED SETTLEMENT AT -2 2 0",
                    "GAME B MOVE 1 PLAYER 65 PLACED GRASS+GRASS AT -4 3 1 1 FOUNDED SETTLEMENT AT -4 4 0",
                    "MAKE YOUR MOVE IN GAME B WITHIN 2 SECONDS: MOVE 2 PLACE JUNGLE+GRASS",
                    "GAME A MOVE 2 PLAYER 34 PLACED LAKE+LAKE AT 3 3 1 2 FOUNDED SETTLEMENT AT 3 3 1",
                    "GAME B MOVE 2 PLAYER 65 PLACED JUNGLE+GRASS AT 1 2 1 1 FOUNDED SETTLEMENT AT 1 2 1",
                    "MAKE YOUR MOVE IN GAME A WITHIN 1 SECOND: MOVE 2 PLACE JUNGLE+GRASS",
                    "GAME A MOVE 2 PLAYER 65 PLACED LAKE+LAKE AT 3 3 1 2 FOUNDED SETTLEMENT AT 3 3 1",
                    "GAME B MOVE 2 PLAYER 34 FORFEITED: ILLEGAL BUILD",
                    "GAME A MOVE 2 PLAYER 34 PLACED LAKE+LAKE AT 3 3 1 2 FOUNDED SETTLEMENT AT 3 3 1",
                    "gg",
                    "GAME A OVER PLAYER 65 100 PLAYER 34 5",
                    "GAME B OVER PLAYER 65 200 PLAYER 34 150",
                    "END OF ROUND 1 OF 2 WAIT FOR THE NEXT MATCH",
                    "BEGIN ROUND 2 OF 2",
                    "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 45",
                    "blah blah",
                    "test",
                    "blah",
                    "gg",
                    "GAME A OVER PLAYER 65 100 PLAYER 45 5",
                    "GAME B OVER PLAYER 65 200 PLAYER 45 150",
                    "END OF ROUND 2 OF 2",
                    "WAIT FOR THE NEXT CHALLENGE TO BEGIN",
                    "NEW CHALLENGE 888 YOU WILL PLAY 1 MATCH",
                    "BEGIN ROUND 1 OF 1",
                    "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 55",
                    "blah blah",
                    "test",
                    "blah",
                    "gg",
                    "GAME A OVER PLAYER 65 100 PLAYER 55 12",
                    "GAME B OVER PLAYER 65 200 PLAYER 55 14",
                    "END OF ROUND 1 OF 1",
                    "END OF CHALLENGES"
            };

            for (String s : messages) {
                if (s.equals("NEW CHALLENGE 888 YOU WILL PLAY 1 MATCH")) {
                    TimeUnit.SECONDS.sleep(5);
                }
                System.out.println("sending: " + s);
                TimeUnit.MILLISECONDS.sleep(1500);
                out.println(s);
            }
            /*while (false) {
                outputLine = stdIn.readLine();
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }*/
        } catch (IOException e) {
            System.out.println("Problem with the connection!");
            System.out.println(e.getMessage());
        }
    }
}