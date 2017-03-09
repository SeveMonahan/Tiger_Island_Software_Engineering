/**
 * Created by jayC on 3/8/17.
 */
public class Meeples {
    private int count;

    Meeples(){
        count = 20;
    }

    public int getCount() {
        return count;
    }

    public void populateHex(Hexagon hexagon){
        int level = hexagon.getLevel();
        if(hexagon.isVolcanoHex() == false){
            hexagon.increasePopulation(level);
        }
        else
            System.out.print("can't add meeples to VOLCANO hex tile");
    }

}
