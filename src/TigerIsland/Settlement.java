package TigerIsland;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Settlement {

    public int getSettlementSize(Board board, Coordinate sourceCoordinate) {
        int size = 0;
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Coordinate currentCoordinate = sourceCoordinate;
        Hexagon currentHexagon = board.getHexagon(currentCoordinate);
        if (currentHexagon.containsPieces()) {
            Color playerColor = currentHexagon.getOccupationColor();
            if (currentHexagon.getOccupationColor() == playerColor) {
                coordinateQueue.add(currentCoordinate);
            }
            while (!coordinateQueue.isEmpty()) {
                currentCoordinate = coordinateQueue.remove();
                currentHexagon = board.getHexagon(currentCoordinate);
                map.put(currentCoordinate,true);
                size++;
                Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
                for (Coordinate neighbor : neighbors) {
                    currentHexagon = board.getHexagon(neighbor);
                    if (!map.containsKey(neighbor)) {
                        map.put(neighbor, true);
                        if (currentHexagon.containsPieces()) {
                            if(currentHexagon.getOccupationColor() == playerColor) {
                                coordinateQueue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        return size;
    }



    public boolean getSettlementContainsTotoro(Board board, Coordinate sourceCoordinate) {
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Coordinate currentCoordinate = sourceCoordinate;
        Hexagon currentHexagon = board.getHexagon(currentCoordinate);
        if (currentHexagon.containsPieces()) {
            Color playerColor = currentHexagon.getOccupationColor();
            if (currentHexagon.getOccupationColor() == playerColor) {
                coordinateQueue.add(currentCoordinate);
            }
            while (!coordinateQueue.isEmpty()) {
                currentCoordinate = coordinateQueue.remove();
                currentHexagon = board.getHexagon(currentCoordinate);
                map.put(currentCoordinate,true);

                if(currentHexagon.getOccupationStatus() == HexagonOccupationStatus.TOTORO){
                    return true;
                }

                Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
                for (Coordinate neighbor : neighbors) {
                    currentHexagon = board.getHexagon(neighbor);
                    if (!map.containsKey(neighbor)) {
                        map.put(neighbor, true);
                        if (currentHexagon.containsPieces()) {
                            if(currentHexagon.getOccupationColor() == playerColor) {
                                coordinateQueue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean getSettlementContainsTiger(Board board, Coordinate sourceCoordinate) {
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Coordinate currentCoordinate = sourceCoordinate;
        Hexagon currentHexagon = board.getHexagon(currentCoordinate);
        if (currentHexagon.containsPieces()) {
            Color playerColor = currentHexagon.getOccupationColor();
            if (currentHexagon.getOccupationColor() == playerColor) {
                coordinateQueue.add(currentCoordinate);
            }
            while (!coordinateQueue.isEmpty()) {
                currentCoordinate = coordinateQueue.remove();
                currentHexagon = board.getHexagon(currentCoordinate);
                map.put(currentCoordinate,true);

                if(currentHexagon.getOccupationStatus() == HexagonOccupationStatus.TIGER){
                    return true;
                }

                Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
                for (Coordinate neighbor : neighbors) {
                    currentHexagon = board.getHexagon(neighbor);
                    if (!map.containsKey(neighbor)) {
                        map.put(neighbor, true);
                        if (currentHexagon.containsPieces()) {
                            if(currentHexagon.getOccupationColor() == playerColor) {
                                coordinateQueue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Queue<Coordinate> expandSettlementFloodFill(Board board, Coordinate coordinate, Player player, Terrain terrain) {
        HashMap<Coordinate, Boolean> searched = new HashMap<>();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Queue<Coordinate> expansion = new LinkedList<>();

        coordinateQueue.add(coordinate);
        searched.put(coordinate, true);

        while (!coordinateQueue.isEmpty()) {
            Coordinate currentCoordinate = coordinateQueue.remove();
            Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
            for(Coordinate neighbor : neighbors){
                Hexagon hexagon = board.getHexagon(neighbor);
                if( !searched.containsKey(neighbor) &&
                        hexagon.getTerrain() ==  terrain &&
                        hexagon.getOccupationStatus() == HexagonOccupationStatus.EMPTY){
                    // expand to this hexagon

                    coordinateQueue.add(neighbor);
                    expansion.add(neighbor);
                } else if( !searched.containsKey(neighbor) &&
                        hexagon.getOccupationStatus() ==  HexagonOccupationStatus.MEEPLE) {
                    coordinateQueue.add(neighbor);
                    // Unsure if we need the above here... need additional tests for this...
                }
                searched.put(neighbor, true);
            }
        }
        return expansion;
    }
}
