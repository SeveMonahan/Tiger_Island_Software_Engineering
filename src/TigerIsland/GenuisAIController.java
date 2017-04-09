package TigerIsland;

import TigerIsland.UnitTests.GameStateEndOfTurnTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GenuisAIController implements PlayerController {
    Color color;
    long startTime;

    public GenuisAIController(Color color){
        this.color = color;
    }

    private ArrayList<GameStateBeforeBuildAction> getCloseTiles(GameStateWTile gameStateWTile){
        ArrayList<GameStateBeforeBuildAction> result = new ArrayList<GameStateBeforeBuildAction>();

        Board board = gameStateWTile.getBoard();
        Tile tile = gameStateWTile.getTile();

        int minX = board.getMinXRange();
        int maxX = board.getMaxXRange();
        int minY = board.getMinYRange();
        int maxY = board.getMaxYRange();

        for(int i = minX; i < maxX; i++){
            for(int j= minY; j < maxY; j++){
                Coordinate current_coordinate = new Coordinate(i, j);
                Terrain terrain = board.getHexagonAt(current_coordinate).getTerrain();

                if(terrain != Terrain.VOLCANO){
                    continue;
                }

                for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()){
                    TileMove possibleTileMove = new TileMove(tile, direction, new Coordinate(i, j));
                    GameStateBeforeBuildAction child = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(gameStateWTile, possibleTileMove);

                    if(child != null){
                        result.add(child);
                    }
                }
            }
        }

        for(int i = 97; i <= 103; i++){
            for(int j= 97; j <= 103; j++){
                Coordinate current_coordinate = new Coordinate(i, j);
                Terrain terrain = board.getHexagonAt(current_coordinate).getTerrain();

                if(terrain != Terrain.EMPTY){
                    continue;
                }

                for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()){
                    TileMove possibleTileMove = new TileMove(tile, direction, new Coordinate(i, j));
                    GameStateBeforeBuildAction child = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(gameStateWTile, possibleTileMove);

                    if(child != null){
                        result.add(child);
                    }
                }
            }
        }

        if(!result.isEmpty()){
            return result;
        }

        for(int i = 94; i <= 106; i++){
            for(int j= 94; j <= 106; j++){
                Coordinate current_coordinate = new Coordinate(i, j);
                Terrain terrain = board.getHexagonAt(current_coordinate).getTerrain();

                if(terrain != Terrain.EMPTY){
                    continue;
                }

                for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()){
                    TileMove possibleTileMove = new TileMove(tile, direction, new Coordinate(i, j));
                    GameStateBeforeBuildAction child = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(gameStateWTile, possibleTileMove);

                    if(child != null){
                        result.add(child);
                    }
                }
            }
        }

        if(!result.isEmpty()){
            return result;
        }

        for(int i = minX; i < maxX; i++){
            for(int j= minY; j < maxY; j++){
                Coordinate current_coordinate = new Coordinate(i, j);
                Terrain terrain = board.getHexagonAt(current_coordinate).getTerrain();

                if(terrain != Terrain.EMPTY){
                    continue;
                }

                for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()){
                    TileMove possibleTileMove = new TileMove(tile, direction, new Coordinate(i, j));
                    GameStateBeforeBuildAction child = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(gameStateWTile, possibleTileMove);

                    if(child != null){
                        result.add(child);
                    }
                }
            }
        }

        return result;

    }

    private ArrayList<GameStateEndOfTurn> getBuildMoves(GameStateBeforeBuildAction gameStateBeforeBuildAction){
        Board board = gameStateBeforeBuildAction.getBoard();

        ArrayList<GameStateEndOfTurn> result = new ArrayList<>();
        int minX = board.getMinXRange();
        int maxX = board.getMaxXRange();
        int minY = board.getMinYRange();
        int maxY = board.getMaxYRange();

        for(int i = 96; i < 104; i++){
            for(int j= 96; j < 104; j++){
                Coordinate coordinate = new Coordinate(i,j);

                if(board.getHexagonAt(coordinate).getTerrain() == Terrain.EMPTY){
                    continue;
                }

                Queue<ConstructionMoveInternal> ConstructionMovePossibilities = getConstructionMovePossibilities(coordinate, board);

                while(!ConstructionMovePossibilities.isEmpty()) {
                    GameStateEndOfTurn currentGameState = GameStateEndOfTurn.createGameStateFromConstructionMove(gameStateBeforeBuildAction,
                            ConstructionMovePossibilities.remove());
                    if(currentGameState != null){
                        result.add(currentGameState);
                    }
                }
            }
        }

        if(!result.isEmpty()){
            return result;
        }

        for(int i = minX; i < maxX; i++){
            for(int j= minY; j < maxY; j++){
                Coordinate coordinate = new Coordinate(i,j);

                if(board.getHexagonAt(coordinate).getTerrain() == Terrain.EMPTY){
                    continue;
                }

                Queue<ConstructionMoveInternal> ConstructionMovePossibilities = getConstructionMovePossibilities(coordinate, board);

                while(!ConstructionMovePossibilities.isEmpty()) {
                    GameStateEndOfTurn currentGameState = GameStateEndOfTurn.createGameStateFromConstructionMove(gameStateBeforeBuildAction,
                                                                                                                 ConstructionMovePossibilities.remove());
                    if(currentGameState != null){
                        result.add(currentGameState);
                    }
                }
            }
        }

        GameStateEndOfTurn currentGameState = GameStateEndOfTurn.createGameStateFromConstructionMove(gameStateBeforeBuildAction, new UnableToBuildConstructionMove());

        result.add(currentGameState);

        return result;
    }

    private Queue<ConstructionMoveInternal> getConstructionMovePossibilities(Coordinate coordinate, Board board) {
        Queue<ConstructionMoveInternal> result = new LinkedList<>();

        if(board.getHexagonAt(coordinate).containsPieces()){
            for(Terrain terrain : new Terrain[]{Terrain.GRASS, Terrain.JUNGLE, Terrain.LAKE, Terrain.ROCK}){
                result.add(new ExpandSettlementConstructionMove(coordinate, terrain));
            }
            return result;
        }

        //else
        result.add(new FoundSettlementConstructionMove(coordinate));
        result.add(new TigerConstructionMove(coordinate));
        result.add(new TotoroConstructionMove(coordinate));
        return result;
    }

    private PriorityQueue<GameStateEndOfTurn> bestNewGameStates(GameStateWTile gameStateWTile){
        ArrayList<GameStateBeforeBuildAction> beforeBuildActions = getCloseTiles(gameStateWTile);

        PriorityQueue<GameStateEndOfTurn> pqueue = new PriorityQueue<GameStateEndOfTurn>();

        for (GameStateBeforeBuildAction gameState : beforeBuildActions) {
            ArrayList<GameStateEndOfTurn> leaf_list = getBuildMoves(gameState);

            for (GameStateEndOfTurn current_child : leaf_list) {
                pqueue.add(current_child);

                if(pqueue.size() > 20){
                    pqueue.poll();
                }
            }

        }

        return pqueue;
    }

    public GameStateEndOfTurn newGameState(GameStateWTile gameStateWTile) {
        long startTime = System.currentTimeMillis();

        PriorityQueue<GameStateEndOfTurn> pqueue = bestNewGameStates(gameStateWTile);

        GameStateEndOfTurn best_state = null;

        int bestNetScoreGain = -10000;

        while(pqueue.size() != 0){
            GameStateEndOfTurn current_state = pqueue.poll();

            GameStateWTile gameStateTile2 = current_state.getChild(new Tile(Terrain.UNKNOWN, Terrain.UNKNOWN));

            PriorityQueue<GameStateEndOfTurn> leafNodes = bestNewGameStates(gameStateTile2);

            while(leafNodes.size() != 1){
                leafNodes.poll();
            }

            GameStateEndOfTurn bestLeafNode = leafNodes.poll();

            int netScoreGain = current_state.activePlayerScore() - bestLeafNode.activePlayerScore();

            if(netScoreGain > bestNetScoreGain){
                best_state = current_state;
                bestNetScoreGain = netScoreGain;
            }

            if(System.currentTimeMillis() - startTime > 1000){
                System.out.println("PANIC!");
                return best_state;
            }

        }

        return best_state;
    }
}
