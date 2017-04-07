package TigerIsland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
       The client is run after the server. It takes in the hostname and the chosen port for the arguments.
       Google
*/
public class NetworkClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "There's a problem with the arguments!");
            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try (
                Socket netSocket = new Socket(host, port);
                PrintWriter out = new PrintWriter(netSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(netSocket.getInputStream()))
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                // TODO send below to parser
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;
                sendMessage(out, stdIn);
            }
        } catch (UnknownHostException e) {
            System.err.println("Can't find the host!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Can't connect to the host!");
            System.exit(1);
        }
    }

    private static void sendMessage(PrintWriter out, BufferedReader stdIn) throws IOException {
        String stringToServer;
        stringToServer = stdIn.readLine();
        if (stringToServer != null) {
            //System.out.println("Client: " + fromUser);
            out.println(stringToServer);
        }
    }
}