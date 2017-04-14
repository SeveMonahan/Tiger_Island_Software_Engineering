package TigerIsland;

import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toUnsignedString;

public class PostMan {
    // Members
    private static PostMan myPostMan;
    private String pid = "-1"; // Our player ID.
    private String gid2 = "";
    private String moveID = "";

    // Need to add these here so that we can access these games.
    private Match match_01;
    private Match match_02;

    private LinkedList<ServerRequestAskingUsToMove> tileMailBox; // Holds the server move request that gives us a tile.
    private LinkedList<MoveInGameIncoming> moveHistoryMailBox; // Holds the moves that we use to update our games.

    private ServerRequestAskingUsToMove tileMessage;
    private MoveInGameIncoming networkMovement;

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

    public void HandleRound() {
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
/*    public void StartMatch() {
        tileMailBox = new LinkedList<>();
        moveHistoryMailBox = new LinkedList<>();

        /// The First game
        // The player is _01 must always be WHITE;
        PlayerController ai_01 = new DumbController(Color.WHITE);
        NetworkPlayerController network_02 = new NetworkPlayerController(Color.BLACK, "Strawberry", this);
        OutputPlayerAI output = new OutputPlayerAI("Strawberry", Color.WHITE, this);

        match_01 = new Match(this, ai_01, network_02, "Strawberry", output);

        /// The Second game
        PlayerController ai_02 = new DumbController(Color.BLACK);
        NetworkPlayerController network_01 = new NetworkPlayerController(Color.WHITE, "Chocolate", this);
        OutputPlayerAI output_2 = new OutputPlayerAI("Chocolate", Color.BLACK, this);

        match_02 = new Match(this, network_01, ai_02, "Chocolate", output_2);
    }
    */

    public void StartMatch() {
        tileMessage = null;
        networkMovement = null;

    }

    // The Server asks us to make a move and gives us a tile.
    private void HandleMakeAMoveMessage(boolean grabgid1){
        String message = readLine();
        assert message.contains("MAKE YOUR MOVE IN GAME");

        if(grabgid1) {
            String[] token = stringSplitter(message);
            String gid1 = token[5]; //assign this to thread 1
            System.out.println("Determined that gid#1 is: " + gid1);

            PlayerController ai_01 = new DumbController(Color.WHITE);
            NetworkPlayerController network_02 = new NetworkPlayerController(Color.BLACK, gid1, this);
            OutputPlayerAI output = new OutputPlayerAI(gid1, Color.WHITE, this);

            match_01 = new Match(this, ai_01, network_02, gid1, output);
        }

        HandleServerRequestAskingUsToMoveMessage(message);
    }

    private void HandleServerRequestAskingUsToMoveMessage(String message){
        ServerRequestAskingUsToMove serverRequestAskingUsToMove = Parser.commandToObject(message);

        moveID = serverRequestAskingUsToMove.getMoveNumber();

        // Update tileMailBox.
        postTileMessage(serverRequestAskingUsToMove);
        // It is now safe for someone to draw a tile from the mailbox.
        // Our AI in the correct game should get the tile and make a move.
        // The code should look something like this, right? -Cameron
        /*String gameID = serverRequestAskingUsToMove.getGid();
        if (gameID == match_01.gameID) {
            // TODO: Tell the AI in match_01 to make a move based on the constraints in serverRequestAskingUsToMove.
            // Might need to add a new function that looks something like this:
            // match_01.makeMoveUsingConstraints(serverRequestAskingUsToMove);
        }
        else if (gameID == match_02.gameID) {
            // TODO: Tell the AI in match_02 to make a move based on the constraints in serverRequestAskingUsToMove.
            // Might need to add a new function that looks something like this:
            // match_02.makeMoveUsingConstraints(serverRequestAskingUsToMove);
        }*/

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
        // Handles all 4 cases of forfeiting and the case losing because you can't build

        if(message_1.contains("FORFEIT") || message_1.contains("UNABLE")){
            assert message_1.contains("FORFEITED") || message_1.contains("LOST: UNABLE TO BUILD");
            return false;
        }else {
            MoveInGameIncoming Move_1 = Parser.opponentMoveStringToGameMove(message_1);
            assert message_1.contains("PLACED");
            if(GrabGid2 && !(Move_1.getGid().equals(match_01.gameID))) {
                gid2 = Move_1.getGid();
                System.out.println("Determined that gid#2 is: " + gid2);
            }

            // Update moveHistoryMailBox and tileMailBox.
            // Can we just use moveHistoryMailBox? -Cameron
            passMoveInGameIncomingToMatchObject(Move_1);
            // It is now safe for someone to draw the opponent move update from the mailbox.
            // We should update the corresponding game with the opponent's move.
            // The code should look something like this, right? -Cameron
            /*String gameID = Move_1.getGid();
            if (gameID == match_01.gameID) {
                // TODO: Tell the thing that controls the opponent to update the match with the Move_1.
                // Might need to add a new function that looks something like this:
                // match_01.updateGameUsingOpponentMove(Move_1);
            }
            else if (gameID == match_02.gameID) {
                // TODO: Tell the thing that controls the opponent to update the match with the Move_1.
                // Might need to add a new function that looks something like this:
                // match_02.updateGameUsingOpponentMove(Move_1);
            }*/
        }
        return true;
    }

    private void endOfTournament(){
        System.out.println("We have successfully completed the Tournament! Done!");
        System.exit(0);
    }

    // Add things to mailbox methods.
    private void passMoveInGameIncomingToMatchObject(MoveInGameIncoming moveInGameIncoming){
        if (!moveInGameIncoming.getPid().equals(pid)) { // post only if opponent's move
            printMoveInGameIncoming(moveInGameIncoming);

            postTileMessage(new ServerRequestAskingUsToMove(moveInGameIncoming.getGid(), 0,
                    "SEEING_THIS_IS_AN_ERROR_IN_POSTMAN", moveInGameIncoming.getTileMove().getTile()));
            postNetworkPlayerMessage(moveInGameIncoming);
        }
    }
    public synchronized void postNetworkPlayerMessage(MoveInGameIncoming moveInGameIncoming) {
        networkMovement = moveInGameIncoming;
    }
    public synchronized void postTileMessage(ServerRequestAskingUsToMove serverRequestAskingUsToMove) {
        tileMessage = serverRequestAskingUsToMove;
    }

    public synchronized Tile accessTileMailBox(String gid) {
        return tileMessage.getTile();
    }

    public MoveInGameIncoming accessNetworkMailBox(String gid) {
        return networkMovement;
    }

    public synchronized void mailAIMessages(GameMoveOutgoingTransmission gameMoveOutgoingTransmission) {
        Marshaller marshaller = new Marshaller();
        String parsedString = marshaller.convertTileMoveAndConstructionMoveToString(gameMoveOutgoingTransmission);
        parsedString = parsedString.replace("**********move_id**********", moveID);
        output_taker.sendMessage( parsedString );
    }

    // Debug methods.
    public static void printMoveInGameIncoming(MoveInGameIncoming incomingMessage) {
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
