package TigerIsland;

class TotoroConstructionMove extends ConstructionMoveJustCoordinate {
    TotoroConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public int isValidPlace(Color color, Board board) {
        Totoro totoro = new Totoro(color);
        if(totoro.isPlacementValid(coordinate, board)){
            return 1;
        }

        return 1000;
    }

    @Override
    public void makeValidPlaceIfAble(Color color, Board board) {

    }
}
