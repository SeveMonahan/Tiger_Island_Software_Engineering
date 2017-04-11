package TigerIsland;

import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toUnsignedString;

public class PostMan {
    private static PostMan myPostMan;
    private int pid = -1;
    private String gid1 = "";
    private String gid2 = "";
    private int rounds = -1;
    private int currentRound = 0;
    private TournamentStatus status = TournamentStatus.CHALLENGE;
    private boolean gameOver = false;
    private boolean readGameOneScore = false;
    private boolean roundsOver = false;
    private Thread t1;
    private Thread t2;
    private LinkedList<ServerRequestAskingUsToMove> tileMailBox; // For AI to make a move
    private LinkedList<MoveInGameIncoming> moveMailBox; // For opponent
    private String moveID = "";

    private NetworkClient output_taker;

    private PostMan(NetworkClient output_taker) {
        this.output_taker = output_taker;
    }

    static PostMan grabPostMan(NetworkClient output_taker) {
        if( myPostMan == null ) {
            myPostMan = new PostMan(output_taker);
        }
        return myPostMan;
    }

    public void setpid(int pid) {
        this.pid = pid;
    }

    // This will be execute everytime we want to start a match
    public void StartMatch() {
        tileMailBox = new LinkedList<>();
        moveMailBox = new LinkedList<>();

        PlayerController ai_01 = new SmartAIController(Color.BLACK);
        NetworkPlayerController network_01 = new NetworkPlayerController(Color.WHITE, "Strawberry", this);

        PlayerController ai_02 = new SmartAIController(Color.BLACK);
        NetworkPlayerController network_02 = new NetworkPlayerController(Color.WHITE, "Chocolate", this);

        Match match_01 = new Match(this, ai_01, network_01, "Strawberry");
        Match match_02 = new Match(this, network_02, ai_02, "Chocolate");

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
    public synchronized void postNetworkPlayerMessage(MoveInGameIncoming moveInGameIncoming) {
        moveMailBox.push(moveInGameIncoming);
        notifyAll();
    }

    public synchronized void postTileMessage(ServerRequestAskingUsToMove serverRequestAskingUsToMove) {
        tileMailBox.push(serverRequestAskingUsToMove);
        notifyAll();
    }

    public synchronized void mailAIMessages(GameMoveOutgoingTransmission gameMoveOutgoingTransmission) {
        Marshaller marshaller = new Marshaller();
        String parsedString = marshaller.convertTileMoveAndConstructionMoveToString(gameMoveOutgoingTransmission);
        parsedString = parsedString.replace("**********move_id**********",moveID);
        parsedString = parsedString.replace("Strawberry", gid1);
        parsedString = parsedString.replace("Chocolate", gid2);
        output_taker.sendMessage( parsedString );
    }

    public synchronized Tile accessTileMailBox(String gid) {
        Tile tile = null;

        for(ServerRequestAskingUsToMove serverRequestAskingUsToMove : tileMailBox) {
            if(serverRequestAskingUsToMove.getGid().equals(gid)) {
                tile = serverRequestAskingUsToMove.getTile();
                tileMailBox.remove(serverRequestAskingUsToMove);
                return tile;
            }
        }
        return tile;
    }

    public synchronized MoveInGameIncoming accessNetworkMailBox(String gid) {
        MoveInGameIncoming moveInGameIncoming = null;

        for(MoveInGameIncoming gmtFromMailBox : moveMailBox) {
            if( gmtFromMailBox.getGid().toString().equals(gid) ) {
                moveInGameIncoming = gmtFromMailBox;
                moveMailBox.remove(gmtFromMailBox);
                return moveInGameIncoming;
            }
        }
        return moveInGameIncoming;
    }

    private String readLine(){

        try {
            String message = output_taker.readLine();
            System.out.println("Server: " + message);
            return message;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void HandleServerRequestAskingUsToMoveMessage(String message){
        ServerRequestAskingUsToMove serverRequestAskingUsToMove = Parser.commandToObject(message);

        if (serverRequestAskingUsToMove.getGid().equals(gid1)) {
            serverRequestAskingUsToMove.setGid("Strawberry");
        }
        else if (serverRequestAskingUsToMove.getGid().equals(gid2)){
            serverRequestAskingUsToMove.setGid("Chocolate");
        }
        else {
            System.out.println("Recieved a Server Request Asking us to Move with an unknown gid");
        }

        postTileMessage(serverRequestAskingUsToMove);

        printServerRequestAskingUsToMove(serverRequestAskingUsToMove);

    }

    private void HandleFirstMakeAMoveMessage(){
        String message = readLine();
        String[] token = stringSplitter(message);

        gid1 = token[5]; //assign this to thread 1
        System.out.println("Determined that gid#1 is: " + gid1);

        HandleServerRequestAskingUsToMoveMessage(message);
    }

    private void HandleFirstGameStateUpdate(){
        String message_1 = readLine();
        String message_2 = readLine();

        MoveInGameIncoming Move_1 = Parser.opponentMoveStringToGameMove(message_1);
        MoveInGameIncoming Move_2 = Parser.opponentMoveStringToGameMove(message_2);

        // We don't even know which message is which, so distinguish them.
        if(Move_1.getGid().equals(gid1)){
            gid2 = Move_2.getGid();
        }else{
            gid2 = Move_1.getGid();
        }

        for(MoveInGameIncoming moveInGameIncoming : new MoveInGameIncoming[] {Move_1, Move_2}) {
            if (!moveInGameIncoming.getPid().equals(toUnsignedString(pid))) { // post only if opponent's move
                readTransmission(moveInGameIncoming);

                if (moveInGameIncoming.getGid().equals(gid1)) {
                    moveInGameIncoming.setGid("Strawberry");
                } else if (moveInGameIncoming.getGid().equals(gid2)) {
                    moveInGameIncoming.setGid("Chocolate");
                } else {
                    System.out.println("Unknown gid in MoveInGameIncoming");
                }

                postNetworkPlayerMessage(moveInGameIncoming);
            }
        }

        // TODO: Must handle the first two messages recieved about forfeiting.
        String message = readLine();
        String[] token = stringSplitter(message);

        gid2 = token[5]; //assign this to thread 2
        System.out.println("Determined that gid#2 is: " + gid1);

        HandleServerRequestAskingUsToMoveMessage(message);
    }

    private boolean HandleMoveAndReturnWhetherThereIsANewMove(){

        return false;
    }

    public void HandleMatch() {
        readLine(); // Eat "NEW MATCH BEGINNING NOW YOUR OPPONENT IS ..."

        StartMatch();

        HandleFirstMakeAMoveMessage();

        HandleFirstGameStateUpdate();

        while(HandleMoveAndReturnWhetherThereIsANewMove()){
            ;
        }
        readLine(); // Eat "GAME OVER" lines
        readLine();
    }

    public void HandleRound(){
        String message = readLine();
        String[] token = stringSplitter(message);

        int rid = parseInt(token[2]);

        System.out.println("Start new Round: " + rid);

        HandleMatch();

        readLine(); //Skip End of Round message

    }

    public boolean HandleChallengeAndReturnWhetherThereIsANewChallenge(){
        String message = readLine();

        String[] token = stringSplitter(message);

        int cid = parseInt(token[2]);
        int rounds = parseInt(token[6]);

        System.out.println("--------Starting new challenge!--------");
        System.out.println("CID: " + cid + " Rounds: " + rounds);

        for(int i = 0; i < rounds; i++){
            HandleRound();
        }

        String end_message = readLine();

        return !end_message.contains("NEW CHALLENGE");
    }

    /// The main loop of the program, which eventually calls System.exit() when
    /// its time to leave
    public void main_loop(){
       while(HandleChallengeAndReturnWhetherThereIsANewChallenge()){
           ;
       }

       endOfTournament();
    }

    // Exits the program
    private void endOfTournament(){
        System.out.println("We have successfully completed the Tournament! Done!");
        System.exit(0);
    }
    private boolean gidSet = false;
/*
    void respondToServerMessage(String message) {
        String[] token = stringSplitter(message);

        int oid;
        int rid;

        if (status == TournamentStatus.CHALLENGE) { //challenge protocol
            int cid;
            if (roundsOver) {
                else if (message.contains("WAIT FOR THE NEXT CHALLENGE TO BEGIN")) {
                    System.out.println("waiting for next challenge...");
                    rounds = 0;
                    currentRound = 0;
                }
            }
            if (!roundsOver){
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
                oid = parseInt(token[8]);
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
                    if (!gidSet) {
                        if (gid2.isEmpty() && !token[1].equals(gid1)) {
                            gid2 = token[1]; //assign this to thread 2
                            System.out.println("grabbed gid2:" + gid2);
                            gidSet = true;
                        }
                    }

                    HandleIncomingGameMove(message, gid1, gid2, pid);
                }
                else if (message.contains("MAKE YOUR MOVE IN GAME")){ //type 1 message (command telling us to make a move)

                    if (!gidSet) {
                        if (gid1.isEmpty()) {
                            gid1 = token[5]; //assign this to thread 1
                            System.out.println("grabbed gid1:" + gid1);
                        }
                    }

                    ServerRequestAskingUsToMove serverRequestAskingUsToMove = Parser.commandToObject(message);
                    //printServerRequestAskingUsToMove(test);
                    moveID = serverRequestAskingUsToMove.getMoveNumber();
                    if (serverRequestAskingUsToMove.getGid().equals(gid1)) {
                        serverRequestAskingUsToMove.setGid("Strawberry");
                    }
                    else if (serverRequestAskingUsToMove.getGid().equals(gid2)){
                        serverRequestAskingUsToMove.setGid("Chocolate");
                    }
                    else {
                        System.out.println("wat gid");
                    }
                    System.out.println("sending to thread: " + serverRequestAskingUsToMove.getGid());
                    postTileMessage(serverRequestAskingUsToMove);
                    printServerRequestAskingUsToMove(serverRequestAskingUsToMove);
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
    */

    private void HandleIncomingGameMove(String message, String gid1, String gid2, int pid) {
        String[] arr = stringSplitter(message);

        MoveInGameIncoming opponentMove = Parser.opponentMoveStringToGameMove(message);

        if (opponentMove != null) {

            readTransmission(opponentMove);
            //take in opponent's move only
            if (!opponentMove.getPid().equals(toUnsignedString(pid))) { // post only if opponent's move
                System.out.println(opponentMove.getPid() + " " + pid + " reading opponent's move. Sending to AI...");
                if (opponentMove.getGid().equals(gid1)) {
                    opponentMove.setGid("Strawberry");
                }
                else if (opponentMove.getGid().equals(gid2)) {
                    opponentMove.setGid("Chocolate");
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
        else { // We're assuming somebody forfeited. If the Server sent us a move which was invalid, we would also hit this conditional.
            System.out.println("We think somebody forfeited!");

            System.out.println("Forfeit message: " + message);

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
        }
    }

    public static void readTransmission(MoveInGameIncoming sendSomewhere) {
        System.out.println("------READING THE FOLLOWING------");
        System.out.println("gid: "+ sendSomewhere.getGid());
        System.out.println("move number: " + sendSomewhere.getMoveID());
        System.out.println("pid: " + sendSomewhere.getPid());
        System.out.println("coordinate: " + sendSomewhere.getConstructionMoveTransmission().getCoordinate().getX() + " " + sendSomewhere.getConstructionMoveTransmission().getCoordinate().getY());
        System.out.println("---------------------------------");
    }

    public static void printServerRequestAskingUsToMove(ServerRequestAskingUsToMove sendSomewhere) {
        System.out.print("-- THE SERVER ASKED US TO MOVE --"                        +"\n"
                         + "In the game with ID: "+ sendSomewhere.getGid()          +"\n"
                         + "Move Number : " + sendSomewhere.getMoveNumber()         +"\n"
                         + "Time allowed: : " + sendSomewhere.getTime()             +"\n"
                         + "Tile to place : " + sendSomewhere.getTile().toString()  +"\n"
                         +"---------------------------------\n");
    }

    private static String[] stringSplitter(String message) {
        return message.split("\\s+");
    }
}
