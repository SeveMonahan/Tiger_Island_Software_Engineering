package TigerIsland;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Settlement {
    // Members
    Coordinate[] settlement;

    // Constructor
    Settlement(Queue<Coordinate> coordinates) {
        settlement = new Coordinate[coordinates.size()];
        int i = 0;
        while(!coordinates.isEmpty()) {
            settlement[i] = coordinates.remove();
            i++;
        }
    }

    // Methods
    public Coordinate[] getSettlementCoordinates() {
        return settlement;
    }

    public int getSettlementSize() {
        return settlement.length;
    }

    public boolean containsTotoro(Board board) {
        for(Coordinate currentLocation : settlement) {
            Hexagon hexagon = board.getHexagonAt(currentLocation);
            if(hexagon.getPiecesStatus() == PieceStatusHexagon.TOTORO){
                return true;
            }
        }
        return false;
    }
    public boolean containsTiger(Board board) {
        for(Coordinate currentLocation : settlement) {
            Hexagon hexagon = board.getHexagonAt(currentLocation);
            if(hexagon.getPiecesStatus() == PieceStatusHexagon.TIGER) {
                return true;
            }
        }
        return false;
    }

    public Queue<Coordinate> expandSettlementFloodFill(Board board, Player player, Terrain terrain) {
        Coordinate coordinate = settlement[0];

        HashMap<Coordinate, Boolean> searched = new HashMap<>();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Queue<Coordinate> expansion = new LinkedList<>();

        coordinateQueue.add(coordinate);
        searched.put(coordinate, true);

        while ( !coordinateQueue.isEmpty()) {
            Coordinate currentCoordinate = coordinateQueue.remove();
            Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();

            for (Coordinate neighbor : neighbors) {
                // If our original settlement contains the neighbor we can use it to expand further...
                boolean insideSettlement = false;
                for(Coordinate coord : settlement) {
                    if(coord == neighbor ) {
                        insideSettlement = true;
                    }
                }
                Hexagon hexagon = board.getHexagonAt(neighbor);
                if( !searched.containsKey(neighbor) &&
                        hexagon.getTerrain() ==  terrain &&
                        !hexagon.containsPieces()) {
                    // ...then, expand to this hexagon.
                    coordinateQueue.add(neighbor);
                    expansion.add(neighbor);
                } else if( !searched.containsKey(neighbor) &&
                         insideSettlement == true) {
                    coordinateQueue.add(neighbor);
                }
                searched.put(neighbor, true);
            }
        }
        return expansion;
    }
}