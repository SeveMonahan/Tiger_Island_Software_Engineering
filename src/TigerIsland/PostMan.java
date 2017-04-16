package TigerIsland;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toUnsignedString;

public class PostMan {
    // Members
    private static PostMan myPostMan;
    private String pid = "-1"; // Our player ID.
    private String moveID = "";

    // Need to add these here so that we can access these games.
    private Match match_01;
    private Match match_02;

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

        // Keep reading Game Move Updates until we hit a MAKE A MOVE or until both games are over.
        while (true) {
            String nextLine = "";
            try {
                nextLine = output_taker.peekLine();
            } catch (IOException e) {
                break;
            }
            if (nextLine.contains("MAKE YOUR MOVE")) { // MAKE YOUR MOVE
                HandleMakeYourMoveMessage();
            } else if (nextLine.contains("MOVE") && nextLine.contains("PLAYER")) { // Move Update
                HandleSingleGameStateUpdateAndReturnIfStillActive();
            } else if (nextLine.contains("GAME") && nextLine.contains("OVER")) { // Both games are over.
                break;
            }
        }

        String game_over_1 = readLine(); // Eat "GAME OVER" lines
        String game_over_2 = readLine();

        assert(game_over_1.contains("OVER PLAYER"));
        assert(game_over_2.contains("OVER PLAYER"));
    }

    public void StartMatch() {
        tileMessage = null;
        networkMovement = null;

    }

    // The Server asks us to make a move and gives us a tile.
    private void HandleMakeYourMoveMessage() {
        String message = readLine();
        assert message.contains("MAKE YOUR MOVE IN GAME");

        // If we haven't seen our first MAKE A MOVE line yet...
        if(match_01 == null) {
            String[] token = stringSplitter(message);
            String gid1 = token[5]; //assign this to thread 1
            System.out.println("Determined that gid#1 is: " + gid1);

            PlayerController ai_01 = new SmartAIController(Color.WHITE);
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

        printServerRequestAskingUsToMove(serverRequestAskingUsToMove);

        if(match_01.gameID.equals(serverRequestAskingUsToMove.getGid())){
            match_01.makeMove();
        }else{
            match_02.makeMove();
        }

    }

    private void HandleSingleGameStateUpdateAndReturnIfStillActive() {
        String line = readLine();
        // Handles all 4 cases of forfeiting and the case where you lose because you can't build.

        if (line.contains("FORFEIT") || line.contains("UNABLE")) {
            assert line.contains("FORFEITED") || line.contains("LOST: UNABLE TO BUILD");
        } else {
            MoveInGameIncoming moveUpdate = Parser.getMoveInGameIncomingObjectFromLine(line);

            assert line.contains("PLACED");

            // If, we're looking at a game update in gid2 for the first time...
            if (match_02 == null) {
                String gid2 = moveUpdate.getGid();
                System.out.println("Determined that gid#2 is: " + gid2);

                PlayerController ai_02 = new SmartAIController(Color.BLACK);
                NetworkPlayerController network_01 = new NetworkPlayerController(Color.WHITE, gid2, this);
                OutputPlayerAI output_2 = new OutputPlayerAI(gid2, Color.BLACK, this);

                match_02 = new Match(this, network_01, ai_02, gid2, output_2);
            }

            passMoveInGameIncomingToMatchObject(moveUpdate);
        }
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
        if(match_01.gameID.equals(moveInGameIncoming.getGid())){
            match_01.makeMove();
        }else{
            match_02.makeMove();
        }
    }

    public void postTileMessage(ServerRequestAskingUsToMove serverRequestAskingUsToMove) {
        tileMessage = serverRequestAskingUsToMove;
    }

    public Tile accessTileMailBox(String gid) {
        return tileMessage.getTile();
    }

    public MoveInGameIncoming accessNetworkMailBox(String gid) {
        return networkMovement;
    }

    public void mailAIMessages(GameMoveOutgoingTransmission gameMoveOutgoingTransmission) {
        Marshaller marshaller = new Marshaller();
        String parsedString = marshaller.convertTileMoveAndConstructionMoveToString(gameMoveOutgoingTransmission);
        parsedString = parsedString.replace("**********move_id**********", moveID);
        output_taker.sendMessage( parsedString );
    }

    // Debug methods.
    public static void printMoveInGameIncoming(MoveInGameIncoming incomingMessage) {
        /*System.out.println("-SERVER INFORMED US OF FOLLOWING MOVE--"
                           +"gid: "+ incomingMessage.getGid()                                                 + "\n"
                           +"move number: " + incomingMessage.getMoveID()                                     + "\n"
                           +"pid: " + incomingMessage.getPid()                                                + "\n"
                           +"coordinate: " + incomingMessage.getConstructionMoveTransmission().getCoordinate().getX() *//* \n excluded on purpose *//*
                           + " " + incomingMessage.getConstructionMoveTransmission().getCoordinate().getY()   + "\n"
                           +"---------------------------------\n");*/
    }
    public static void printServerRequestAskingUsToMove(ServerRequestAskingUsToMove incomingMessage) {
        /*System.out.print("-- THE SERVER ASKED US TO MOVE --"                        +"\n"
                         + "In the game with ID: "+ incomingMessage.getGid()          +"\n"
                         + "Move Number : " + incomingMessage.getMoveNumber()         +"\n"
                         + "Time allowed: : " + incomingMessage.getTime()             +"\n"
                         + "Tile to place : " + incomingMessage.getTile().toString()  +"\n"
                         +"---------------------------------\n");*/
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
