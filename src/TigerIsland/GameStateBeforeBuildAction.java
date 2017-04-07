package TigerIsland;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameStateBeforeBuildAction extends GameState {

    private GameStateBeforeBuildAction(GameState original, TileMove tilemove) {
        super(original);
        board.placeTile(tilemove);
    }

    public static GameStateBeforeBuildAction createGameStateBeforeBuildAction(GameState original, TileMove tilemove) {
        TileMoveChecker tileMoveChecker = new TileMoveChecker();
        boolean validTilePlacement = tileMoveChecker.checkForValidity(tilemove, original.getBoard());
        if (validTilePlacement)
            return new GameStateBeforeBuildAction(original, tilemove);

        return null;
    }

    public ArrayList<GameStateEndOfTurn> getChildren(){
        ArrayList<GameStateEndOfTurn> result = new ArrayList<>();
        int minX = board.getMinXRange();
        int maxX = board.getMaxXRange();
        int minY = board.getMinYRange();
        int maxY = board.getMaxYRange();

        for(int i = minX; i < maxX; i++){
            for(int j= minY; j < maxY; j++){
                Queue<ConstructionMoveInternal> ConstructionMovePossibilities = getConstructionMovePossibilities(new Coordinate(i, j));

                while(!ConstructionMovePossibilities.isEmpty()) {
                    GameStateEndOfTurn currentGameState = GameStateEndOfTurn.createGameStateFromConstructionMove(this, ConstructionMovePossibilities.remove());
                    if(currentGameState != null){
                        result.add(currentGameState);
                    }
                }
            }
        }

        return result;
    }

    private Queue<ConstructionMoveInternal> getConstructionMovePossibilities(Coordinate coordinate) {
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

}

