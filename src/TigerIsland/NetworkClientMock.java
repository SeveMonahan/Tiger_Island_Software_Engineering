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

public class NetworkClientMock extends NetworkClient {
    private int line_num;
    private int result_num;

    private String string_array[];
    private String result_array[];

    public NetworkClientMock(String[] string_array){
        super(null, null);
        line_num = 0;
        this.string_array = string_array;
    }

    public String readLine() throws IOException {
        return string_array[line_num++];
    }

    public synchronized void sendMessage(String stringToServer) {
        result_array[result_num++] = stringToServer;
    }

    public String[] get_array(){
        return result_array;
    }

}