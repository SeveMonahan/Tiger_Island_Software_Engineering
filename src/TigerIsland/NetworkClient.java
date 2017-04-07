package TigerIsland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Integer.parseInt;

/*
       Arguments:
       <hostname> <port> <tournamentPass> <username> <password>
       Client handles authentication automatically
*/

public class NetworkClient {
    public static int pid = 0;
    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.err.println(
                    "Arguments should be in this order:\n<hostname> <port> <tournamentPass> <username> <password>");
            System.exit(1);
        }
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
            System.out.println(pid);
            challengeProtocol(out, in);
        } catch (UnknownHostException e) {
            System.err.println("Can't find the host!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Can't connect to the host!");
            System.exit(1);
        }
    }

    public static void challengeProtocol(PrintWriter out, BufferedReader in) throws IOException {
        System.out.println("Now executing the challenge protocol...");
        BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
        String stringFromServer;
        String stringToServer;
        while ((stringFromServer = in.readLine()) != null) {
            // TODO send below to parser
            System.out.println("Server: " + stringFromServer);
            if (stringFromServer.equals("Bye.")) {
                break;
            }
            stringToServer = stdIn.readLine();
            if (stringToServer != null) {
                System.out.println("Client: " + stringToServer);
                sendMessage(out, stringToServer);
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
}