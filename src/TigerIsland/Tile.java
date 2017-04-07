package src.TigerIsland;

public class Tile {
    private Terrain terrain_1;
    private Terrain terrain_2;

    public Tile(Terrain terrain_1, Terrain terrain_2){
        this.terrain_1 = terrain_1;
        this.terrain_2 = terrain_2;
    }

    public Terrain[] getTerrainsClockwiseFromVolcano(){
        Terrain[] result = new Terrain[3];

        result[0] = Terrain.VOLCANO;
        result[1] = terrain_1;
        result[2] = terrain_2;

        return result;
    }
}
