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

    public boolean checkLevel(Hexagon hexagon){
        if(hexagon.getLevel() > 0)
            return true;
        else return false;
    }

    public boolean checkIfPopulated(Hexagon hexagon){
        if(hexagon.getPopulation() > 0)
            return true;
        else return false;
    }

    public void populateHex(Hexagon hexagon){
        int level = hexagon.getLevel();
        if(hexagon.isVolcanoHex() == false && checkLevel(hexagon) == true){
            hexagon.increasePopulation(level);
            count -= level;
        }
        else
            System.out.print("can't add meeples to VOLCANO hex tile");
    }

    public void nuke(Hexagon hexagon){
        hexagon.decreasePopulation(hexagon.getPopulation());
    }
}
