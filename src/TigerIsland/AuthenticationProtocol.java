package TigerIsland;

import java.io.IOException;

public class AuthenticationProtocol {
    public static int authenticationProtocol(String tournamentPass, String username, String password, NetworkClient output_taker) throws IOException {
        String stringFromServer;
        String stringToServer;

        // "WELCOME TO ANOTHER EDITION OF THUNDERDOME!"
        final String Welcome_string = output_taker.readLine();

        stringToServer = "ENTER THUNDERDOME " + tournamentPass;
        output_taker.sendMessage(stringToServer);

        // "TWO SHALL ENTER, ONE SHALL LEAVE"
        final String Two_shall_enter_string = output_taker.readLine();

        stringToServer = "I AM " + username + " " + password;
        output_taker.sendMessage(stringToServer);

        final String Recieve_Player_id_string = output_taker.readLine();
        stringFromServer = Recieve_Player_id_string.replace("WAIT FOR THE TOURNAMENT TO BEGIN ", "");
        int pid = Integer.parseInt(stringFromServer);

        System.out.print("Our Player ID is: ");
        System.out.println(pid);
        System.out.println("Authentical Protocol complete.");

        return pid;

    }
}