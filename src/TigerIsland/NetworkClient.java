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
    private static String outputLine = null;

    private static void check_arguments(String[] args){
        if (args.length != 5) {
            System.err.println(
                    "Arguments should be in this order:\n<hostname> <port> <tournamentPass> <username> <password>");
            System.exit(1);
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {

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
            int pid = AuthenticationProtocol.authenticationProtocol(tournamentPass, username, password, out, in);
            challengeProtocol(out, in, pid);
        } catch (UnknownHostException e) {
            System.err.println("Can't find the host!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Can't connect to the host!");
            System.exit(1);
        }
    }

    public static void challengeProtocol(PrintWriter out, BufferedReader in, int pid) throws IOException, InterruptedException {
        boolean waitingForOutPut = false;

        System.out.println("Now executing the challenge protocol...");

        PostMan postMan = PostMan.grabPostMan();

        postMan.setpid(pid);

        for(String stringFromServer = in.readLine(); stringFromServer != null; stringFromServer = in.readLine()){
            long serverTime = System.currentTimeMillis();

            System.out.println("Server: " + stringFromServer);

            if (stringFromServer.equals("THANK YOU FOR PLAYING! GOODBYE")) {
                break;
            }

            postMan.decoder(stringFromServer);

            if (stringFromServer.contains("MAKE YOUR MOVE IN GAME")) {
                waitingForOutPut = true;
            }

            while (outputLine == null && waitingForOutPut) {
                TimeUnit.MILLISECONDS.sleep(100);
            }

            if (outputLine != null) {
                long difference = System.currentTimeMillis() - serverTime;
                System.out.println(" Client: " + outputLine + " Time in miliseconds since read in server line: " + serverTime);
                sendMessage(out, outputLine);
                outputLine = null;
                waitingForOutPut = false;
            }
        }
    }

    public static synchronized void sendMessage(PrintWriter out, String stringToServer) {
        out.println(stringToServer);
    }
    public static synchronized void setOutputLine(String messageToServer) {
        outputLine = messageToServer;
    }
}