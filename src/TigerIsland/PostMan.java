package TigerIsland;

import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toUnsignedString;

public class PostMan {
    // Members
    private static PostMan myPostMan;
    private String pid = "-1"; // Our player ID.
    private String gid1 = "";
    private String gid2 = "";
    private String moveID = "";

    // Need to add these here so that we can access these games.
    private Match match_01;
    private Match match_02;

    private NetworkClient output_taker;

    // Constructors
    private PostMan(NetworkClient output_taker) {
        this.output_taker = output_taker;
    }

    public static PostMan grabPostMan(NetworkClient output_taker) {
        if( myPostMan == null ) {
            myPostMan = new PostMan(output_taker);
        }
        return myPostMan;
    }

    // Setters
    public void setPid(String pid) {
        this.pid = pid;
    }

    // Methods
    public void main_loop(){
        while(HandleChallengeAndReturnWhetherThereIsANewChallenge()){
            ; // Do not delete this semicolon. It loops the whole program.
        }
        endOfTournament();
    }
    public boolean HandleChallengeAndReturnWhetherThereIsANewChallenge(){
        String message = readLine();
        assert(message.contains("NEW CHALLENGE"));

        String[] token = stringSplitter(message);

        int cid = parseInt(token[2]);
        int rounds = parseInt(token[6]);

        System.out.println("--------Starting new challenge!--------");
        System.out.println("CID: " + cid + " Rounds: " + rounds);

        for(int i = 0; i < rounds; i++){
            HandleRound();
        }

        String end_message = readLine();
        assert end_message.contains("WAIT FOR THE NEXT CHALLENGE TO BEGIN") || end_message.contains("END OF CHALLENGES");

        return !end_message.contains("NEXT CHALLENGE");
        // same as end_message.contains("END OF CHALLENGES");
    }
    public void HandleRound(){
        String message = readLine();
        String[] token = stringSplitter(message);

        int rid = parseInt(token[2]);

        System.out.println("Start new Round: " + rid);

        HandleMatch();

        String end_message = readLine(); //Skip End of Round message
        assert(end_message.contains("END OF ROUND"));

    }
    public void HandleMatch() {
        String new_match = readLine(); // Eat "NEW MATCH BEGINNING NOW YOUR OPPONENT IS ..."
        assert(new_match.contains("NEW MATCH"));

        StartMatch();

        // The Server asks us to make a move and gives us a tile.
        HandleMakeAMoveMessage(true);

        int activeGames = HandleGameStateUpdateAndReturnActiveGames(2, true);

        while(activeGames != 0){
            HandleMakeAMoveMessage(false);
            activeGames = HandleGameStateUpdateAndReturnActiveGames(activeGames, false);
        }

        String game_over_1 = readLine(); // Eat "GAME OVER" lines
        String game_over_2 = readLine();

        assert(game_over_1.contains("OVER PLAYER"));
        assert(game_over_2.contains("OVER PLAYER"));
    }
    // This will be execute every time we want to start a match
    public void StartMatch() {
        // Note: The player that's marked "_01" in each game must always be WHITE.

        /// The First game
        PlayerController ai_01 = new DumbController(Color.WHITE);
        OpponentPlayerController opponent_02 = new OpponentPlayerController(Color.BLACK, "Strawberry", this);
        OutputPlayerAI output = new OutputPlayerAI("Strawberry", Color.BLACK, this);

        match_01 = new Match(ai_01, opponent_02, "Strawberry", output);

        /// The Second game
        PlayerController ai_02 = new DumbController(Color.BLACK);
        OpponentPlayerController opponent_01 = new OpponentPlayerController(Color.WHITE, "Chocolate", this);
        OutputPlayerAI output_2 = new OutputPlayerAI("Chocolate", Color.WHITE, this);

        match_02 = new Match(ai_02, opponent_01, "Chocolate", output_2);
    }

    // The Server asks us to make a move and gives us a tile.
    private void HandleMakeAMoveMessage(boolean grabgid1){
        String message = readLine();
        assert message.contains("MAKE YOUR MOVE IN GAME");

        if(grabgid1) {
            String[] token = stringSplitter(message);
            gid1 = token[5];
            System.out.println("Determined that gid#1 is: " + gid1);
        }

        HandleServerRequestAskingUsToMoveMessage(message);
    }
    private void HandleServerRequestAskingUsToMoveMessage(String message){
        ServerRequestAskingUsToMove serverRequestAskingUsToMove = Parser.commandToObject(message);

        moveID = serverRequestAskingUsToMove.getMoveNumber();

        if (serverRequestAskingUsToMove.getGid().equals(gid1)) {
            serverRequestAskingUsToMove.setGid("Strawberry");
        }
        else if (serverRequestAskingUsToMove.getGid().equals(gid2)){
            serverRequestAskingUsToMove.setGid("Chocolate");
        }
        else {
            System.out.println("Received a Server Request Asking us to Move with an unknown gid");
        }

        // Do we need this? -Cameron
        // updateTileMailBox(serverRequestAskingUsToMove);
        // The mailbox now has something in it.
        // Our AI in the correct game should get the tile and make a move.

        String gameID = serverRequestAskingUsToMove.getGid();
        // "Draw tile."
        Tile tile = serverRequestAskingUsToMove.getTile();
        if (gameID == match_01.getGid()) {
            match_01.sendTileToAI(tile);
        }
        else if (gameID == match_02.getGid()) {
            match_02.sendTileToAI(tile);
        }

        printServerRequestAskingUsToMove(serverRequestAskingUsToMove);
    }

    // The Server lets us know what our opponent did.
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
    private boolean HandleSingleGameStateUpdateAndReturnIfStillActive(boolean GrabGid2){
        String message_1 = readLine();
                ;
        // Handles all 4 cases of forfeiting and the case losing because you can't build

        if(message_1.contains("FORFEIT") || message_1.contains("UNABLE")){
            assert message_1.contains("FORFEITED") || message_1.contains("LOST: UNABLE TO BUILD");
            return false;
        }else {
            MoveUpdate Move_1 = Parser.opponentMoveStringToGameMove(message_1);
            assert message_1.contains("PLACED"); // TODO doesn't check if our own move
            if(GrabGid2 && !(Move_1.getGid().equals(gid1))) {
                gid2 = Move_1.getGid();
                System.out.println("Determined that gid#2 is: " + gid2);
            }

            // It is now safe for someone to draw the opponent move update from the mailbox.
            // We should update the corresponding game with the opponent's move.
            String gameID = Move_1.getGid();
            if (gameID == match_01.getGid()) {
                match_01.updateGameStateUsingOpponentMove(Move_1);
            }
            else if (gameID == match_02.getGid()) {
                match_02.updateGameStateUsingOpponentMove(Move_1);
            }
        }
        return true;
    }

    private void endOfTournament(){
        System.out.println("We have successfully completed the Tournament! Done!");
        System.exit(0);
    }

    public synchronized void mailAIMessages(GameMoveOutgoingTransmission gameMoveOutgoingTransmission) {
        Marshaller marshaller = new Marshaller();
        String parsedString = marshaller.convertTileMoveAndConstructionMoveToString(gameMoveOutgoingTransmission);
        parsedString = parsedString.replace("**********move_id**********", moveID);
        parsedString = parsedString.replace("Strawberry", gid1);
        parsedString = parsedString.replace("Chocolate", gid2);
        output_taker.sendMessage( parsedString );
    }

    // Debug methods.
    public static void printMoveInGameIncoming(MoveUpdate incomingMessage) {
        System.out.println("-SERVER INFORMED US OF FOLLOWING MOVE--"
                           +"gid: "+ incomingMessage.getGid()                                                 + "\n"
                           +"move number: " + incomingMessage.getMoveID()                                     + "\n"
                           +"pid: " + incomingMessage.getPid()                                                + "\n"
                           +"coordinate: " + incomingMessage.getConstructionMoveTransmission().getCoordinate().getX() /* \n excluded on purpose */
                           + " " + incomingMessage.getConstructionMoveTransmission().getCoordinate().getY()   + "\n"
                           +"---------------------------------\n");
    }
    public static void printServerRequestAskingUsToMove(ServerRequestAskingUsToMove incomingMessage) {
        System.out.print("-- THE SERVER ASKED US TO MOVE --"                        +"\n"
                         + "In the game with ID: "+ incomingMessage.getGid()          +"\n"
                         + "Move Number : " + incomingMessage.getMoveNumber()         +"\n"
                         + "Time allowed: : " + incomingMessage.getTime()             +"\n"
                         + "Tile to place : " + incomingMessage.getTile().toString()  +"\n"
                         +"---------------------------------\n");
    }

    // Other methods.
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
    private static String[] stringSplitter(String message) {
        return message.split("\\s+");
    }
}
