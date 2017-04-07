package TigerIsland;

import java.util.ArrayList;

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

    public ArrayList<GameState> getChildren(){
        ArrayList<GameState> result = new ArrayList<GameState>();
        int minX = board.getMinXRange();
        int maxX = board.getMaxXRange();
        int minY = board.getMinYRange();
        int maxY = board.getMaxYRange();

        for(int i = minX; i < maxX; i++){
            for(int j= minY; j < maxY; j++){
                for(BuildOption option : new BuildOption[]{BuildOption.BUILDTIGER, BuildOption.BUILDTOTORO, BuildOption.FOUNDSETTLEMENT}){
                    ConstructionMoveTransmission possibleConstructionMove = new ConstructionMoveTransmission(option, new Coordinate(i, j));
                    GameState child = GameState.createGameStateFromConstructionMove(this, possibleConstructionMove);

                    if(child != null){
                        result.add(child);
                    }
                }
            }
        }

        return result;
    }

}

