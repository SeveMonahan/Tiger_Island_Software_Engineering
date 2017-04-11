package TigerIsland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationProtocol {
    static int authenticationProtocol(String tournamentPass, String username, String password, PrintWriter out, BufferedReader in) throws IOException {
        String stringFromServer;
        String stringToServer;

        // "WELCOME TO ANOTHER EDITION OF THUNDERDOME!"
        final String Welcome_string = in.readLine();

        stringToServer = "ENTER THUNDERDOME " + tournamentPass;
        NetworkClient.sendMessage(out, stringToServer);

        // "TWO SHALL ENTER, ONE SHALL LEAVE"
        final String Two_shall_enter_string = in.readLine();

        stringToServer = "I AM " + username + " " + password;
        NetworkClient.sendMessage(out, stringToServer);

        final String Recieve_Player_id_string = in.readLine();
        stringFromServer = Recieve_Player_id_string.replace("WAIT FOR THE TOURNAMENT TO BEGIN ", "");
        int pid = Integer.parseInt(stringFromServer);

        System.out.print("Our Player ID is: ");
        System.out.println(pid);
        System.out.println("Authentical Protocol complete.");

        return pid;

    }
}