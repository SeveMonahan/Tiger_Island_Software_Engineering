public enum DirectionsHex {
    UPPERLEFT,
    UPPERRIGHT,
    LEFT,
    RIGHT,
    LOWERLEFT,
    LOWERRIGHT;

    DirectionsHex getNextClockwise(){
        switch(this) {
            case UPPERLEFT:
                return UPPERRIGHT;
            case UPPERRIGHT:
                return LEFT;
            case LEFT:
                return LOWERLEFT;
            case LOWERLEFT:
                return LOWERRIGHT;
            case LOWERRIGHT:
                return RIGHT;
            case RIGHT:
                return UPPERLEFT;
        }

        // Error result
        return DirectionsHex.LEFT;
    }
}
