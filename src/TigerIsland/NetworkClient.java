package TigerIsland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

/*
       Arguments:
       <hostname> <port> <tournamentPass> <username> <password>
       Client handles authentication automatically
*/

public class NetworkClient {
    public static String outputLine = null;
    public static int pid = 0;
    public static int gid = 0;

    public static boolean waitingForOutPut = false;

    private static void check_arguments(String[] args){
        if (args.length != 5) {
            System.err.println(
                    "Arguments should be in this order:\n<hostname> <port> <tournamentPass> <username> <password>");
            System.exit(1);
        }

    }
    public static void main(String[] args) throws IOException, InterruptedException {
        PostMan x = PostMan.grabPostMan();

        check_arguments(args);

        String host = args[0];

        int port = parseInt(args[1]);

        String tournamentPass = args[2];

        String username = args[3];

        String password = args[4];

        try (
                Socket netSocket = new Socket(host, port);
                PrintWriter out = new PrintWriter(netSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(netSocket.getInputStream()))
        ) {
            authenticationProtocol(tournamentPass, username, password, out, in);
            challengeProtocol(x, out, in);
        } catch (UnknownHostException e) {
            System.err.println("Can't find the host!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Can't connect to the host!");
            System.exit(1);
        }
    }

    public static void challengeProtocol(PostMan x,PrintWriter out, BufferedReader in) throws IOException, InterruptedException {
        System.out.println("Now executing the challenge protocol...");
        x.pid = pid;
        String stringFromServer;
        while ((stringFromServer = in.readLine()) != null) {
            long serverTime = System.currentTimeMillis();
            long difference = 0;
            System.out.println("Server: " + stringFromServer);
            if (stringFromServer.equals("THANK YOU FOR PLAYING! GOODBYE")) {
                break;
            }
            x.decoder(stringFromServer);
            if (stringFromServer.contains("MAKE YOUR MOVE IN GAME")) {
                waitingForOutPut = true;
            }
            while (outputLine == null && waitingForOutPut) {
                System.out.println("sleep");
                TimeUnit.MILLISECONDS.sleep(100);
            }
            if (outputLine != null) {
                difference = System.currentTimeMillis() - serverTime;
                System.out.println(difference + " Client: " + outputLine);
                sendMessage(out, outputLine);
                outputLine = null;
                waitingForOutPut = false;
            }
        }
    }

    public static void authenticationProtocol(String tournamentPass, String username, String password, PrintWriter out, BufferedReader in) throws IOException {
        String stringFromServer;
        String stringToServer;
        System.out.println("Executing authentication protocol...");
        while ((stringFromServer = in.readLine()) != null) {
            System.out.println("Server: " + stringFromServer);
            if (stringFromServer.equals("WELCOME TO ANOTHER EDITION OF THUNDERDOME!")) {
                stringToServer = "ENTER THUNDERDOME " + tournamentPass;
                System.out.println("Sending tournament password: " + tournamentPass);
                sendMessage(out, stringToServer);
            }
            else if (stringFromServer.equals("TWO SHALL ENTER, ONE SHALL LEAVE")){
                stringToServer = "I AM " + username + " " + password;
                System.out.println("Sending username: " + username + "\nSending password: " + password);
                sendMessage(out, stringToServer);
            }
            else {
                stringFromServer = stringFromServer.replace("WAIT FOR THE TOURNAMENT TO BEGIN ", "");
                pid = parseInt(stringFromServer);

                break;
            }
        }
    }

    public static void sendMessage(PrintWriter out, String stringToServer) {
        out.println(stringToServer);
    }
    public static void setOutputLine(String messageToServer) {
        outputLine = messageToServer;
    }
}