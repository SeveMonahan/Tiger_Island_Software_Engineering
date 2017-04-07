package TigerIsland;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Settlement {

    Coordinate[] settlement;

    Settlement(Queue<Coordinate> coordinates) {
        settlement = new Coordinate[coordinates.size()];
        int i = 0;
        while(!coordinates.isEmpty()) {
            settlement[i] = coordinates.remove();
            i++;
        }
    }

    public int getSettlementSize() {
        return settlement.length;
    }

    public boolean getSettlementContainsTotoro(Board board) {
        for(Coordinate currentLocation : settlement) {
            Hexagon hexagon = board.getHexagonAt(currentLocation);
            if(hexagon.getOccupationStatus() == HexagonOccupationStatus.TOTORO){
                return true;
            }
        }
        return false;
    }

    public boolean getSettlementContainsTiger(Board board) {
        for(Coordinate currentLocation : settlement) {
            Hexagon hexagon = board.getHexagonAt(currentLocation);
            if(hexagon.getOccupationStatus() == HexagonOccupationStatus.TIGER){
                return true;
            }
        }
        return false;
    }

    public Queue<Coordinate> expandSettlementFloodFill(Board board, Player player, Terrain terrain) {
        Coordinate coordinate = this.settlement[0];

        HashMap<Coordinate, Boolean> searched = new HashMap<>();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Queue<Coordinate> expansion = new LinkedList<>();

        coordinateQueue.add(coordinate);
        searched.put(coordinate, true);

        while (!coordinateQueue.isEmpty()) {
            Coordinate currentCoordinate = coordinateQueue.remove();
            Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
            for(Coordinate neighbor : neighbors){
                Hexagon hexagon = board.getHexagonAt(neighbor);
                if( !searched.containsKey(neighbor) &&
                        hexagon.getTerrain() ==  terrain &&
                        hexagon.getOccupationStatus() == HexagonOccupationStatus.EMPTY){
                    // expand to this hexagon

                    coordinateQueue.add(neighbor);
                    expansion.add(neighbor);
                } else if( !searched.containsKey(neighbor) &&
                        hexagon.getOccupationStatus() ==  HexagonOccupationStatus.MEEPLE &&
                        hexagon.getOccupationColor() == player.getColor() ) {
                    coordinateQueue.add(neighbor);
                    // Unsure if we need the above here... need additional tests for this...
                }
                searched.put(neighbor, true);
            }
        }
        return expansion;
    }

    public boolean expandSettlementWithCheck(Board board, Player player, Terrain terrain) {
        Coordinate coordinate = settlement[0];

        Color color = board.getHexagonAt(coordinate).getOccupationColor();
        HexagonOccupationStatus occupationStatus = board.getHexagonAt(coordinate).getOccupationStatus();
        if( player.getColor() == color
                && occupationStatus == HexagonOccupationStatus.MEEPLE){
            Queue<Coordinate> settlement = expandSettlementFloodFill(board, player, terrain);
            if(settlement.size() <= player.getMeeplesCount()) {
                performFloodFill(board, player, settlement );
                return true;
            }
            return false;
        }
        return false;
    }

    private void performFloodFill(Board board, Player player, Queue<Coordinate> expansion ) {
        while( !expansion.isEmpty() ) {
            player.placeMeepleOnHexagon(expansion.remove(), board);
        }
    }
}