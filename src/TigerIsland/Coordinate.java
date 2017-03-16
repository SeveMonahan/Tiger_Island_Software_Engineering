package TigerIsland;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Coordinate getHexagonNeighborCoordinate(HexagonNeighborDirection direction){
        switch(direction){

            case LEFT:
                return new Coordinate(getX()-1, getY());
            case RIGHT:
               return new Coordinate(getX()+1, getY());
            case UPPERLEFT:
                return new Coordinate(getX()-1 + offset(getY()), getY()+1);
            case UPPERRIGHT:
                return new Coordinate(getX() + offset(getY()), getY()+1);
            case LOWERLEFT:
                return new Coordinate(getX()-1 + offset(getY()), getY()-1);
            case LOWERRIGHT:
                return new Coordinate(getX() + offset(getY()), getY()-1);
        }

        //Effectively an error
        return new Coordinate(0, 0);
    }

    private int offset(int y){
        if(y % 2 == 1) {
            return 1;
        }else{
            return 0;
        }
    }
}