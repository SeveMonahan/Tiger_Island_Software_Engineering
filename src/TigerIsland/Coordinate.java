package TigerIsland;

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

    public int offset(int y){
        if (y % 2 == 1) {
            return 1;
        }
        else{
            return 0;
        }
    }
}