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

        if(t1 != null){
            t1.stop();
        }

        if(t2 != null){
            t2.stop();
        }

        t1 = new Thread(match_01);
        t2 = new Thread(match_02);
        t1.start();
        t2.start();
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

        for (int i = 0; i < tileMailBox.size(); i++) {

            ServerRequestAskingUsToMove serverRequestAskingUsToMove = tileMailBox.get(i);

            if (serverRequestAskingUsToMove.getGid().equals(gid)) {
                tile = serverRequestAskingUsToMove.getTile();
                tileMailBox.remove(i);
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

    private void HandleMakeAMoveMessage(boolean grabgid1){
        String message = readLine();

        if(grabgid1) {
            String[] token = stringSplitter(message);
            gid1 = token[5]; //assign this to thread 1
            System.out.println("Determined that gid#1 is: " + gid1);
        }

        HandleServerRequestAskingUsToMoveMessage(message);
    }

    private void passMoveInGameIncomingToMatchObject(MoveInGameIncoming moveInGameIncoming){
        if (!moveInGameIncoming.getPid().equals(toUnsignedString(pid))) { // post only if opponent's move
            printMoveInGameIncoming(moveInGameIncoming);

            if (moveInGameIncoming.getGid().equals(gid1)) {
                moveInGameIncoming.setGid("Strawberry");
            } else if (moveInGameIncoming.getGid().equals(gid2)) {
                moveInGameIncoming.setGid("Chocolate");
            } else {
                System.out.println("Unknown gid in MoveInGameIncoming");
            }

            postTileMessage(new ServerRequestAskingUsToMove(moveInGameIncoming.getGid(), 0,
                                    "SEEING_THIS_IS_AN_ERROR_IN_POSTMAN", moveInGameIncoming.getTileMove().getTile()));
            postNetworkPlayerMessage(moveInGameIncoming);
        }
    }

    private boolean HandleSingleGameStateUpdateAndReturnIfStillActive(boolean GrabGid2){
        String message_1 = readLine();

        if(message_1.contains("FORFEIT") || message_1.contains("UNABLE")){
            return false;
        }else {

            MoveInGameIncoming Move_1 = Parser.opponentMoveStringToGameMove(message_1);
            if(GrabGid2 && Move_1.getGid().equals(gid1)) {

            }else{
                gid2 = Move_1.getGid();
                System.out.println("Determined that gid#2 is: " + gid1);
            }

            passMoveInGameIncomingToMatchObject(Move_1);
        }
        return true;
    }

    private int HandleGameStateUpdateAndReturnActiveGames(int activeGames, boolean GrabGid2){

        int result = 0;

        if(HandleSingleGameStateUpdateAndReturnIfStillActive(GrabGid2)){
            result++;
        }

        if(activeGames == 2) {
            if(HandleSingleGameStateUpdateAndReturnIfStillActive(GrabGid2)){
                result++;
            }
        }

        return result;
    }

    public void HandleMatch() {
        readLine(); // Eat "NEW MATCH BEGINNING NOW YOUR OPPONENT IS ..."

        StartMatch();

        HandleMakeAMoveMessage(true);

        int activeGames = HandleGameStateUpdateAndReturnActiveGames(2, true);

        while(activeGames != 0){
            HandleMakeAMoveMessage(false);
            activeGames = HandleGameStateUpdateAndReturnActiveGames(activeGames, false);
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

    public static void printMoveInGameIncoming(MoveInGameIncoming sendSomewhere) {
        System.out.println("-SERVER INFORMED US OF FOLLOWING MOVE--"
                           +"gid: "+ sendSomewhere.getGid()                                                 + "\n"
                           +"move number: " + sendSomewhere.getMoveID()                                     + "\n"
                           +"pid: " + sendSomewhere.getPid()                                                + "\n"
                           +"coordinate: " + sendSomewhere.getConstructionMoveTransmission().getCoordinate().getX() /* \n excluded on purpose */
                           + " " + sendSomewhere.getConstructionMoveTransmission().getCoordinate().getY()   + "\n"
                           +"---------------------------------\n");
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
