package TigerIsland;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameStateBeforeBuildAction extends GameState {

    private GameStateBeforeBuildAction(GameState original, TileMove tilemove) {
        super(original);
        board.placeTile(tilemove);
        lastTileMove = tilemove;
    }

    public static GameStateBeforeBuildAction createGameStateBeforeBuildAction(GameStateWTile original, TileMove tilemove) {
        String original_tile_string = original.getTile().toString();
        String tilemove_string = tilemove.getTile().toString();
        assert(original_tile_string.equals(tilemove_string));

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

        GameStateEndOfTurn currentGameState = GameStateEndOfTurn.createGameStateFromConstructionMove(this, new UnableToBuildConstructionMove());

        result.add(currentGameState);

        return result;
    }

    private Queue<ConstructionMoveInternal> getConstructionMovePossibilities(Coordinate coordinate) {
        Queue<ConstructionMoveInternal> result = new LinkedList<>();

        if(board.getHexagonAt(coordinate).containsPieces()){
           for(Terrain terrain : new Terrain[]{Terrain.GRASS, Terrain.JUNGLE, Terrain.LAKE, Terrain.ROCK, Terrain.PADDY}){
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

