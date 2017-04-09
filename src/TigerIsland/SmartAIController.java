package TigerIsland;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SmartAIController implements PlayerController {
    Color color;
    int restriction_number;

    public SmartAIController(Color color){
        this.color = color;
        restriction_number = 0;
    }

    private int adjacent_hexs_score(TileMove tileMove, Board board){
        Coordinate coordinates[] = new Coordinate[3];

        Coordinate c_1 = coordinates[0] = tileMove.getCoordinate();
        coordinates[1] = c_1.getNeighboringCoordinateAt(tileMove.getDirection());
        coordinates[2] = c_1.getNeighboringCoordinateAt(tileMove.getDirection().getNextClockwise());

        int result = 0;

        for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()){
            for(int i = 0; i < 3; i++){
                Coordinate neighbor = coordinates[i].getNeighboringCoordinateAt(direction);
                Hexagon hexagon_here = board.getHexagonAt(neighbor);
                if(hexagon_here.getLevel() != 0){
                    result++;
                }

                if(hexagon_here.containsPieces() && hexagon_here.getOccupationColor() == color){
                        result += 5;
                }
            }
        }

        return result;
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

        for(int i = 97 - restriction_number; i < 103 + restriction_number; i++){
            for(int j= 97 - restriction_number; j < 103 + restriction_number; j++){
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

        System.out.println("Fallthrough");
        restriction_number += 10;

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

        for(int i = 97; i < 103; i++){
            for(int j= 97; j < 103; j++){
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

    private GameStateEndOfTurn bestNewGameStates(GameStateWTile gameStateWTile){
        ArrayList<GameStateBeforeBuildAction> beforeBuildActions = getCloseTiles(gameStateWTile);

        GameStateEndOfTurn result = null;

        int bestScoreSoFar = -1;

        for (GameStateBeforeBuildAction gameState : beforeBuildActions) {
            ArrayList<GameStateEndOfTurn> leaf_list = getBuildMoves(gameState);

            for (GameStateEndOfTurn current_child : leaf_list) {
                if (current_child.netEvalScore() > bestScoreSoFar) {
                    result = current_child;
                    bestScoreSoFar = current_child.netEvalScore();
                }
            }

        }

        return result;
    }

    public GameStateEndOfTurn newGameState(GameStateWTile gameStateWTile) {
        long startTime = System.currentTimeMillis();

        GameStateEndOfTurn result = bestNewGameStates(gameStateWTile);

        System.out.println(System.currentTimeMillis() - startTime);

        return result;
    }
}
