package TigerIsland;

import static java.lang.Integer.parseInt;

public class Controller {
    public static int cid = 0;
    public static int oid = 0;
    public static int rid = 0;
    public static int rounds = 0;
    public static int currentRound = 1;
    public static int state = 0;
    public static boolean gameOver = false;
    public static boolean readGameOneScore = false;
    /*
    States:
    0 = challenge
    1 = round
    2 = match
    3 = move
    */
    public static void decoder(String message) {
        String[] arr = stringSplitter(message);
        if (state == 0) { //challenge protocol
            cid = parseInt(arr[2]);
            rounds = parseInt(arr[6]);
            System.out.println("grabbed cid: " + cid + " and rounds: " + rounds);
            state++;
        }
        else if (state == 1) { //round protocol
            if (gameOver) {
                if (message.contains("END OF ROUND") && message.contains(" OF ")) {
                    System.out.println("round " + currentRound + " finished!");
                    currentRound++;
                    if (currentRound <= rounds) {
                        System.out.println("Moving on to next game...");
                        gameOver = false;
                    }
                }
            } else {
                rid = parseInt(arr[2]);
                rounds = parseInt(arr[4]);
                System.out.println("grabbed rid: " + rid);
                state++;
            }
        }
        else if (state == 2) { //match protocol
            if (gameOver) {
                if (readGameOneScore) {
                    System.out.println("read game two score");
                    readGameOneScore = false;
                    state--;
                } else {
                    readGameOneScore = true;
                    System.out.println("read game one score");
                }
            } else {
                oid = parseInt(arr[8]);
                System.out.println("grabbed opponent cid: " + oid);
                state++;
            }
        }
        else { //move protocol (this is where it diverges into two games)
            if (message.contains("gg")) {
                System.out.println("gg was called for both games!");
                gameOver = true;
                state--;
            }
            //send to parser
            //if gameover or whatever then gameOver = true and state = state - 1
        }
    }
    private static void stringArrayPrinter(String[] arr) {
        for (String s : arr) {
            System.out.println(s);
        }
    }

    private static String[] stringSplitter(String message) {
        return message.split(" ");
    }
}
