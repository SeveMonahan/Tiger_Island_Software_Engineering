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

        Coordinate coordinates[] = new Coordinate[3];

        Coordinate c_1 = coordinates[0] = tileMove.getCoordinate();
        coordinates[1] = c_1.getNeighboringCoordinateAt(tileMove.getDirection());
        coordinates[2] = c_1.getNeighboringCoordinateAt(tileMove.getDirection().getNextClockwise());

        int result = 0;
        for(Coordinate c : coordinates){
            if(board.getHexagonAt(c).getOccupationColor() == playerWhoseTurn.getColor()){
                result -= 10;
            }
        }

        return result;
    }

    public int netEvalScore(){
        Player active_player = this.getInactivePlayer();

        int active_player_pieces = active_player.getMeeplesCount()*2 + active_player.getMeeplesCount() + active_player.getTotoroCount();

        Player inactive_player = this.getActivePlayer();

        int inactive_player_pieces = inactive_player.getMeeplesCount()*2 + inactive_player.getMeeplesCount() + inactive_player.getTotoroCount();

        return ((active_player_pieces - inactive_player_pieces)*100 + adjacent_hexs_score());
    }

    @Override
    public int compareTo(GameStateEndOfTurn compare) {
        return this.netEvalScore() - compare.netEvalScore();
    }
}
