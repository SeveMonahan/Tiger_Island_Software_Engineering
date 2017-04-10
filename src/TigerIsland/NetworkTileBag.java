package TigerIsland;

import java.util.LinkedList;

public class NetworkTileBag implements TileBag {

    private LinkedList<Tile> bag;
    private int numberOfTilesInBag;
    private PostMan tileBagPostMan;
    private String gameID;

    public NetworkTileBag(PostMan postMan, String gameID) {
        bag = new LinkedList<Tile>();
        this.tileBagPostMan = postMan;
        this.gameID = gameID;
    }

    @Override
    public Tile drawTile() {
        System.out.println(this.gameID + " is attempting to draw a tile...");
        Tile tile = tileBagPostMan.accessTileMailBox(gameID);
        while(tile == null){
            try {
                synchronized (tileBagPostMan) {
                    System.out.println(this.gameID + " waiting for tiles to get to the mailbox...");
                    tileBagPostMan.wait();
                    System.out.println(this.gameID + " has risen from the dead!");
                    tile = tileBagPostMan.accessTileMailBox(gameID);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(tile != null) {
                bag.push(tile);
                numberOfTilesInBag++;
            }
        }
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
