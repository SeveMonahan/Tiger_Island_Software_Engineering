public class Player {

    private int score;
    private int meepleCount;
    private int totoroCount;

    Player() {
        meepleCount = 0;
        totoroCount = 0;
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void modifyScore(int modification) {
        if(score + modification >= 0) {
            score = score + modification;
        }
    }

    public void deductTotoro() {
        totoroCount--;
    }

    public void deductMeeple(int level) {
        meepleCount = meepleCount - ( level * level );
    }
}
