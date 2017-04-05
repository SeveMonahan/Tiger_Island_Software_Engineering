package TigerIsland;

import java.util.ArrayList;

public class GameStateWTile extends GameState {
    private Tile tile;

    GameStateWTile(GameState original, Tile tile) {
        super(original);
        this.tile = tile;
    }

    public ArrayList<GameStateBeforeBuildAction> getChildren(){
        ArrayList<GameStateBeforeBuildAction> result = new ArrayList<GameStateBeforeBuildAction>();
       for(int i = 0; i <= 200; i++){
           for(int j=0; j <= 200; j++){
               for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()){
                   TileMove possibleTileMove = new TileMove(tile, direction, new Coordinate(i, j));
                   GameStateBeforeBuildAction child = new GameStateBeforeBuildAction(this, possibleTileMove);

                   if(child.getSuccess()){
                       result.add(child);
                   }
               }
           }
       }

       return result;
    }
}
