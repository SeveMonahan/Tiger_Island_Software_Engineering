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


    public int[] ConvertToCube(){
        int x = this.x;
        int y = this.y;
        x = x - 100;
        y = 100 - y;

        int yC = x - (y - (y&1))/2;
        int xC = y;
        int zC = -yC - xC;

       int cubeCordinates[] = {xC,yC,zC};
       return cubeCordinates;
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