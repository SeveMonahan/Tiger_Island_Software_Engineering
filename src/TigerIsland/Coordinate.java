package TigerIsland;
import java.lang.Math;

public class Coordinate {
    private int x;
    private int y;
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinate(int xC, int yC, int zC){
        int col = xC + (zC-(zC&1))/2;
        int row = -zC;

        this.x = col+100;
        this.y = row+100;
    }

    public int[] ConvertToCube(){
        int x = this.x;
        int y = this.y;
        x = x - 100;
        y = 100 - y;

        int xC = x - (y - (y&1))/2;
        int zC = y;
        int yC = -xC - zC;

       int cubeCordinates[] = {xC,yC,zC};
       return cubeCordinates;
    }

    private int offset(int y){
        if (y % 2 == 1) {
            return 1;
        }
        else{
            return 0;
        }
    }

    public Coordinate getNeighboringCoordinate(HexagonNeighborDirection direction) {
        int x = getX();
        int y = getY();
        switch(direction){
            case LEFT:
                return new Coordinate(x - 1, y);
            case RIGHT:
                return new Coordinate(x + 1, y);
            case UPPERLEFT:
                return new Coordinate(x - 1 + offset(y), y + 1);
            case UPPERRIGHT:
                return new Coordinate(x + offset(y), y + 1);
            case LOWERLEFT:
                return new Coordinate(x - 1 + offset(y), y - 1);
            case LOWERRIGHT:
                return new Coordinate(x + offset(y), y - 1);
            default:
                return new Coordinate(0, 0);
        }
    }

    public Coordinate[] getNeighboringCoordinates() {
        int i = 0;
        Coordinate[] neighbors = new Coordinate[6];
        for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()) {
            neighbors[i] = this.getNeighboringCoordinate(direction);
            i++;
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object other){
        Coordinate coordinate = (Coordinate) other;
        if( other == this){
            return true;
        }

        if(coordinate.getX() == this.getX() && coordinate.getY() ==  this.getY()){
            return true;
        }

        return false;
    }


    @Override
    public int hashCode() {
        int result = this.getX();
        result = 31 * result + this.getY();
        return result;
    }

}