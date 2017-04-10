package TigerIsland;

import java.util.Arrays;
import java.util.LinkedList;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toUnsignedString;

//TODO: game over message from server kills both threads
//
public class PostMan {
    private static PostMan myPostMan;
    public static int pid = -1;
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
    private static Match match_01;
    private static Match match_02;
    private static Thread t1;
    private static Thread t2;
    private LinkedList<GameMoveIncomingCommand> tileMailBox; // For AI to make a move
    private LinkedList<GameMoveIncomingTransmission> moveMailBox; // For opponent
    private LinkedList<String> AIMailBox; // What we're sending to the damn server
    private static String moveID = "";
    private static String properTile = "";
    private PostMan() {}

    static PostMan grabPostMan() {
        if( myPostMan == null ) {
            myPostMan = new PostMan();
        }
        return myPostMan;
    }

    // This will be execute everytime we want to start a match
    public void StartMatch() {
        tileMailBox = new LinkedList<>();
        moveMailBox = new LinkedList<>();
        AIMailBox = new LinkedList<>();

        PlayerController ai_01 = new SmartAIController(Color.BLACK);
        NetworkPlayerController network_01 = new NetworkPlayerController(Color.WHITE, "A11", this);

        PlayerController ai_02 = new SmartAIController(Color.BLACK);
        NetworkPlayerController network_02 = new NetworkPlayerController(Color.WHITE, "B11", this);

        match_01 = new Match(this, ai_01, network_01, "A11");
        match_02 = new Match(this, network_02, ai_02, "B11");

        t1 = new Thread(match_01);
        t2 = new Thread(match_02);
        t1.start();
        t2.start();
    }

    public void killThread(int x) {
        if (x == 1) {
            if (t1.isAlive())
                t1.stop();
        }
        else {
            if (t2.isAlive())
                t2.stop();
        }
    }
    public synchronized void postNetworkPlayerMessage(GameMoveIncomingTransmission gameMoveIncomingTransmission) {
        moveMailBox.push(gameMoveIncomingTransmission);
        notifyAll();
    }

    public synchronized void postTileMessage(GameMoveIncomingCommand gameMoveIncomingCommand) {
        tileMailBox.push(gameMoveIncomingCommand);
        notifyAll();
    }

    public synchronized void mailAIMessages(GameMoveOutgoingTransmission gameMoveOutgoingTransmission) {
        // printOurMove(gameMoveOutgoingTransmission);
        Marshaller marshaller = new Marshaller();
        String parsedString = marshaller.convertTileMoveAndConstructionMoveToString(gameMoveOutgoingTransmission);
        parsedString = parsedString.replace("**********move_id**********",moveID);
        // AIMailBox.push(parsedString);
        parsedString = parsedString.replace("A11", gid1);
        parsedString = parsedString.replace("B11", gid2);
        String [] parsedArray = parsedString.split("\\s+");
        String badTile = parsedArray[5];
        // parsedString = parsedString.replace(badTile, properTile);
        NetworkClient.setOutputLine( parsedString );
    }

    public synchronized Tile accessTileMailBox(String gid) {
        Tile tile = null;

        for(GameMoveIncomingCommand gameMoveIncomingCommand : tileMailBox) {
            if(gameMoveIncomingCommand.getGid().equals(gid)) {
                tile = gameMoveIncomingCommand.getTile();
                tileMailBox.remove(gameMoveIncomingCommand);
                return tile;
            }
        }
        return tile;
    }

    public synchronized GameMoveIncomingTransmission accessNetworkMailBox(String gid) {
        GameMoveIncomingTransmission gameMoveIncomingTransmission = null;

        for(GameMoveIncomingTransmission gmtFromMailBox : moveMailBox) {
            if( gmtFromMailBox.getGid().toString().equals(gid) ) {
                gameMoveIncomingTransmission = gmtFromMailBox;
                moveMailBox.remove(gmtFromMailBox);
                return gameMoveIncomingTransmission;
            }
        }
        return gameMoveIncomingTransmission;
    }

    private static boolean gidSet = false;
    public void decoder(String message) {
        String[] arr = stringSplitter(message);
        if (message.contains("test")) {
            NetworkClient.setOutputLine("test");
        }
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
            System.out.println("Match about to start!");
            StartMatch();
            System.out.println("Match started!");
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
                t1.stop();
                t2.stop();
            }
            else {
                if (!message.contains("MAKE YOUR MOVE") && message.contains("PLAYER") && !message.contains("OVER PLAYER")) { //type 2 message (handled by parser)
                    if (gidSet == false) {
                        if (gid2.isEmpty() && !arr[1].equals(gid1)) {
                            gid2 = arr[1]; //assign this to thread 2
                            System.out.println("grabbed gid2:" + gid2);
                            gidSet = true;
                        }
                    }
                    GameMoveIncomingTransmission opponentMove = Parser.opponentMoveStringToGameMove(message);
                    if (opponentMove != null) {
                        readTransmission(opponentMove);
                        //take in opponent's move only
                        if (!opponentMove.getPid().equals(toUnsignedString(pid))) { // post only if opponent's move
                            System.out.println(opponentMove.getPid() + " " + pid + " reading opponent's move. Sending to AI...");
                            if (opponentMove.getGid().equals(gid1)) {
                                opponentMove.setGid("A11");
                            }
                            else if (opponentMove.getGid().equals(gid2)) {
                                opponentMove.setGid("B11");
                            }
                            else {
                                System.out.println("couldn't set transmission's GID");
                            }
                            postNetworkPlayerMessage(opponentMove);
                        }
                        else {
                            System.out.println("Reading our move...");
                        }
                    }
                    else { //if someone forfeited
                        System.out.println("someone forfeited!");
                        String gameToBeKilled = arr[1];
                        if (gameToBeKilled.equals(gid1)) {
                            killThread(1);
                        }
                        else if (gameToBeKilled.equals(gid2)) {
                            killThread(0);
                        }
                        else {
                            System.out.println("couldn't kill a game");
                        }
                        //TODO: KILL WHOEVER FORFEITED
                    }
                }
                else if (message.contains("MAKE YOUR MOVE IN GAME")){ //type 1 message (command telling us to make a move)
                    if (gidSet == false) {
                        if (gid1.isEmpty()) {
                            gid1 = arr[5]; //assign this to thread 1
                            System.out.println("grabbed gid1:" + gid1);
                        }
                    }
                    GameMoveIncomingCommand test = Parser.commandToObject(message);
                    //readCommand(test);
                    moveID = test.getMoveNumber();
                    if (test.getGid().equals(gid1)) {
                        test.setGid("A11");
                    }
                    else if (test.getGid().equals(gid2)){
                        test.setGid("B11");
                    }
                    else {
                        System.out.println("wat gid");
                    }
                    System.out.println("sending to thread: " + test.getGid());
                    properTile = test.getTile().toString();
                    postTileMessage(test);
                    readCommand(test);
                }
                else { //couldn't read string
                    System.out.println("Couldn't read string! assuming game over...");
                    System.out.println("gg was called for both games!");
                    gameOver = true;
                    status = TournamentStatus.MATCH;
                    if (t1.isAlive()) {
                        t1.stop();
                    }
                    if (t2.isAlive()) {
                        t2.stop();
                    }
                }
            }
        }
    }

    public static void readTransmission(GameMoveIncomingTransmission sendSomewhere) {
        System.out.println("------READING THE FOLLOWING------");
        System.out.println("gid: "+ sendSomewhere.getGid());
        System.out.println("move number: " + sendSomewhere.getMoveID());
        System.out.println("pid: " + sendSomewhere.getPid());
        System.out.println("coordinate: " + sendSomewhere.getConstructionMoveTransmission().getCoordinate().getX() + " " + sendSomewhere.getConstructionMoveTransmission().getCoordinate().getY());
        System.out.println("---------------------------------");
    }
    public static void printOurMove(GameMoveOutgoingTransmission gameMoveOutgoingTransmission) {
        Marshaller marshaller = new Marshaller();
        String outgoing = marshaller.convertTileMoveAndConstructionMoveToString(gameMoveOutgoingTransmission);
        System.out.println("------- Sending our move -------");
        System.out.println("Our move: " + outgoing);
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
        return message.split("\\s+");
    }
}
