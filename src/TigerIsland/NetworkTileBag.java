package TigerIsland;

import java.util.LinkedList;

public class NetworkTileBag implements TileBag {
    private LinkedList<Tile> bag;
    private int numberOfTilesInBag;
    private PostMan tileBagPostMan;
    private String gameID;

    // Constructors
    public NetworkTileBag(PostMan postMan, String gameID) {
        bag = new LinkedList<Tile>();
        this.tileBagPostMan = postMan;
        this.gameID = gameID;
        numberOfTilesInBag = 48;
    }

    // Methods
    // Do we still need these "Override" keywords? Or can we delete them? -Cameron
    @Override
    public Tile drawTile() {
        numberOfTilesInBag--;
        return tileBagPostMan.accessTileMailBox(gameID);
    }

    public LinkedList<Tile> getAllTilesInBag() {
        return this.bag;
    }

    @Override
    public int getNumberOfTilesInBag() {
        return numberOfTilesInBag;
    }
}
