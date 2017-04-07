package TigerIsland;

public class OutputPlayerExample implements  OutputPlayerActions{
    private Color color;
    private String gid;

    OutputPlayerExample(String gid, Color colors){
        this.gid = gid;
        this.color = color;
    }

    // XXX Should jut move ConstructionMoveInternal all the way down the stack.
    // Obviously an example for testing purposes.
    private ConstructionMoveTransmission convertConstructionMove(ConstructionMoveInternal constructMove){
        return new ConstructionMoveTransmission(BuildOption.FOUNDSETTLEMENT, new Coordinate(100, 100));
    }

    public void produceOutput(Color color, int moveNumber, TileMove tileMove, ConstructionMoveInternal constructMove){
        new GameMoveTransmission(gid, moveNumber, tileMove, convertConstructionMove(constructMove));
    }

}
