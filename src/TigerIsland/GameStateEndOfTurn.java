package TigerIsland;

import java.util.Comparator;

public class GameStateEndOfTurn extends GameState implements Comparable<GameStateEndOfTurn>  {

    private GameStateEndOfTurn() {
       super();
    }

    private GameStateEndOfTurn(GameStateBeforeBuildAction original, ConstructionMoveInternal move) {
        super(original);
        move.makePreverifiedMove(playerWhoseTurn, board);
        lastConstructionMove = move;
    }

    public static GameStateEndOfTurn createInitalGameState() {
        return new GameStateEndOfTurn();
    }

    public GameStateWTile getChild(Tile tile){
        return new GameStateWTile(this, tile);
    }

    public static GameStateEndOfTurn createGameStateFromConstructionMove(GameStateBeforeBuildAction original, ConstructionMoveInternal move) {
        if(move.canPerformMove(original.playerWhoseTurn, original.board)){
            return new GameStateEndOfTurn(original, move);
        }
        return null;
    }

    public boolean checkForGameOver(){
        return lastConstructionMove instanceof UnableToBuildConstructionMove
                || playerWhoseTurn.triggeredGameEnd();
    }

    private int adjacent_hexs_score(){
        TileMove tileMove = getLastTileMove();

        if(tileMove instanceof FirstTurnTileMove){
            return 0;
        }

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

                if(hexagon_here.containsPieces() && hexagon_here.getOccupationColor() == playerWhoseTurn.getColor()){
                    result += 5;
                }
            }
        }

        return result;
    }

    public int netEvalScore(){
        return playerWhoseTurn.getScore() * 10 + adjacent_hexs_score();
    }

    @Override
    public int compareTo(GameStateEndOfTurn compare) {
        return this.netEvalScore() - compare.netEvalScore();
    }
}
