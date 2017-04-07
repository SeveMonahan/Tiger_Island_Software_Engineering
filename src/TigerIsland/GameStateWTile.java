package TigerIsland;

import java.util.ArrayList;

public class GameStateWTile extends GameState {
    private Tile tile;

    GameStateWTile(GameStateEndOfTurn original, Tile tile) {
        super(original);
        this.tile = tile;
    }

    public ArrayList<GameStateBeforeBuildAction> getChildren(){
       ArrayList<GameStateBeforeBuildAction> result = new ArrayList<GameStateBeforeBuildAction>();
        int minX = board.getMinXRange();
        int maxX = board.getMaxXRange();
        int minY = board.getMinYRange();
        int maxY = board.getMaxYRange();

        for(int i = minX; i < maxX; i++){
            for(int j= minY; j < maxY; j++){
                Coordinate current_coordinate = new Coordinate(i, j);
                Terrain terrain = board.getHexagonAt(current_coordinate).getTerrain();

                if(terrain != Terrain.EMPTY && terrain != Terrain.VOLCANO){
                    continue;
                }

               for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()){
                   TileMove possibleTileMove = new TileMove(tile, direction, new Coordinate(i, j));
                   GameStateBeforeBuildAction child = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(this, possibleTileMove);

                   if(child != null){
                       result.add(child);
                   }
               }
           }
       }

       return result;
    }
}
