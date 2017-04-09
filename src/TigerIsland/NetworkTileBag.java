package TigerIsland;

import java.util.LinkedList;

public class NetworkTileBag implements TileBag {

    private LinkedList<Tile> bag;
    private int numberOfTilesInBag;
    private PostMan tileBagPostMan;

    public NetworkTileBag(PostMan postMan) {
        bag = new LinkedList<Tile>();
        this.tileBagPostMan = postMan;
    }

    @Override
    public Tile drawTile() {
        // TODO somehow need to get notified of a new tile then pull it from here.
        numberOfTilesInBag--;
        return this.bag.pop();
    }

    public LinkedList<Tile> getAllTilesInBag() {
        return this.bag;
    }
    @Override
    public int getNumberOfTilesInBag() {
        return numberOfTilesInBag;
    }
}
