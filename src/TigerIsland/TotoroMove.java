package TigerIsland;

class TotoroConstructionMove extends ConstructionMoveJustCoordinate {
    TotoroConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public boolean isValidPlace(Color color, Board board) {
        Totoro totoro = new Totoro(color);
        return totoro.isPlacementValid(coordinate, board);
    }

    @Override
    public boolean makeValidPlaceIfAble(Color color, Board board) {

        return false;
    }
}
