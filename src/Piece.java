public class Piece {
    private PieceType type;
    private Color color;

    Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    PieceType getPieceType() { return type; }
    Color getPieceColor() { return color; }
}
