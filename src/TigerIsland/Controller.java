package TigerIsland;

import static java.lang.Integer.parseInt;

public class Controller {
    private static int cid = 0;
    private static int oid = 0;
    private static int rid = 0;
    private static int rounds = 0;
    private static int currentRound = 0;
    private static int state = 0;
    private static boolean gameOver = false;
    private static boolean readGameOneScore = false;
    private static boolean roundsOver = false;

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
            if (roundsOver) {
                if (message.contains("END OF CHALLENGES")) {
                    System.out.println("Challenges over!");
                    System.exit(1);
                }
                else if (message.contains("WAIT FOR THE NEXT CHALLENGE TO BEGIN")) {
                    System.out.println("waiting for next challenge...");
                    cid = 0;
                    oid = 0;
                    rid = 0;
                    rounds = 0;
                    currentRound = 0;
                }
                else if (message.contains("NEW CHALLENGE")) {
                    roundsOver = false;
                }
            }
            if (!roundsOver){
                System.out.println("--------Starting new challenge!--------");
                cid = parseInt(arr[2]);
                rounds = parseInt(arr[6]);
                System.out.println("grabbed cid: " + cid + " and rounds: " + rounds);
                state++;
            }
        }
        else if (state == 1) { //round protocol
            if (gameOver) {
                if (message.contains("END OF ROUND") && message.contains(" OF ")) {
                    currentRound++;
                    System.out.println("round " + currentRound + " finished!");
                    if (currentRound < rounds) {
                        System.out.println("Moving on to next game...");
                        gameOver = false;
                    }
                    else {
                        gameOver = false;
                        roundsOver = true;
                        state--;
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
            else {
                System.out.println("game(s) in progress...");
                if (message.contains("MAKE YOUR MOVE IN GAME")) {

                }
            }
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

    private static void moveFunctionPicker (String message) {

    }
}
