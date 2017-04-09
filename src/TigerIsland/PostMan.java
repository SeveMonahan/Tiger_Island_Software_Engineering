package TigerIsland;

import java.util.LinkedList;

import static java.lang.Integer.parseInt;

public class PostMan {
    private static int cid = -1;
    private static int oid = -1;
    private static int rid = -1;
    private static String gid1 = "";
    private static String gid2 = "";
    private static int rounds = -1;
    private static int currentRound = 0;
    private static TournamentStatus status = TournamentStatus.CHALLENGE;
    private static boolean gameOver = false;
    private static boolean readGameOneScore = false;
    private static boolean roundsOver = false;
    private Match match_01;
    private Match match_02;

    private LinkedList<GameMoveIncomingTransmission> mailBox;

    // This will be execute everytime we want to start a match
    public void StartMatch() {
        PlayerController ai_01 = new DumbController(Color.BLACK);
        NetworkPlayerController network_01 = new NetworkPlayerController(Color.WHITE);

        PlayerController ai_02 = new DumbController(Color.BLACK);
        NetworkPlayerController network_02 = new NetworkPlayerController(Color.WHITE);

        match_01 = new Match(this, ai_01, network_01, "bullshit");
        match_02 = new Match(this, network_02, ai_02, "bullshit");

        match_01.run();
        match_02.run();
    }

    public void postNetworkPlayerMessage(GameMoveIncomingTransmission gameMoveIncomingTransmission) {
        mailBox.push(gameMoveIncomingTransmission);
        notifyAll(); // We have a new message... please check if you can use it.
    }

    public void postTileMessage(GameMoveIncomingTransmission gameMoveIncomingTransmission) {
        mailBox.push(gameMoveIncomingTransmission);
        notifyAll(); // We have a new message... please check if you can use it.
    }

    public synchronized GameMoveIncomingTransmission accessTileMailBox(String gid) {
        GameMoveIncomingTransmission gameMoveIncomingTransmission = null;
        // Search to see if we have a message with the correct gameID here
        return gameMoveIncomingTransmission;
    }

    public synchronized GameMoveIncomingTransmission accessNetworkMailBox(String gid) {
        GameMoveIncomingTransmission gameMoveIncomingTransmission = null;
        // Search to see if we have a message with the correct gameID here
        return gameMoveIncomingTransmission;
    }

    private static boolean gidSet = false;
    public static void decoder(String message) {
        String[] arr = stringSplitter(message);
        if (status == TournamentStatus.CHALLENGE) { //challenge protocol
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
                status = TournamentStatus.ROUND;
            }
        }
        else if (status == TournamentStatus.ROUND) { //round protocol
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
                        status = TournamentStatus.CHALLENGE;
                    }
                }
            } else {
                rid = parseInt(arr[2]);
                rounds = parseInt(arr[4]);
                System.out.println("grabbed rid: " + rid);
                status = TournamentStatus.MATCH;
            }
        }
        else if (status == TournamentStatus.MATCH) { //match protocol
            if (gameOver) {
                if (readGameOneScore) {
                    System.out.println("read game two score");
                    readGameOneScore = false;
                    status = TournamentStatus.ROUND;
                } else {
                    readGameOneScore = true;
                    System.out.println("read game one score");
                }
            } else {
                oid = parseInt(arr[8]);
                System.out.println("grabbed opponent cid: " + oid);
                status = TournamentStatus.MOVE;
            }
        }
        else { //move protocol (this is where it diverges into two games)
            if (message.contains("gg")) {
                System.out.println("gg was called for both games!");
                gameOver = true;
                status = TournamentStatus.MATCH;
            }
            else {
                if (!message.contains("MAKE YOUR MOVE") && message.contains("PLAYER")) { //type 2 message (handled by parser)
                    if (gidSet == false) {
                        if (gid1.isEmpty()) {
                            gid1 = arr[5];
                            System.out.println("grabbed gid1:" + gid1);
                        }
                        else if (gid2.isEmpty()) {
                            gid2 = arr[5];
                            System.out.println("grabbed gid2:" + gid2);
                            gidSet = true;
                        }
                    }
                    GameMoveIncomingTransmission sendSomewhere = Parser.opponentMoveStringToGameMove(message);
                    readTransmission(sendSomewhere);
                    //TODO: send this somewhere
                }
                else if (message.contains("MAKE YOUR MOVE IN GAME")){ //type 1 message (command telling us to make a move)
                    GameMoveIncomingCommand test = Parser.commandToObject(message);
                    readCommand(test);
                }
                else { //couldn't read string

                }
            }
        }
    }

    public static void readTransmission(GameMoveIncomingTransmission sendSomewhere) {
        System.out.println("------READING THE FOLLOWING------");
        System.out.println("gid: "+ sendSomewhere.getGid());
        System.out.println("move number: " + sendSomewhere.getMoveNumber());
        System.out.println("pid: " + sendSomewhere.getPid());
        System.out.println("coordinate: " + sendSomewhere.getConstructionMoveTransmission().getCoordinate().getX() + " " + sendSomewhere.getConstructionMoveTransmission().getCoordinate().getY());
        System.out.println("---------------------------------");
    }
    public static void readCommand(GameMoveIncomingCommand sendSomewhere) {
        System.out.println("------ READING THE COMMAND ------");
        System.out.println("gid: "+ sendSomewhere.getGid());
        System.out.println("move number: " + sendSomewhere.getMoveNumber());
        System.out.println("time: " + sendSomewhere.getTime());
        System.out.println("tile: " + sendSomewhere.getTile().toString());
        System.out.println("---------------------------------");
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
