package TigerIsland;

public class Tile {
    private Terrain terrain_1;
    private Terrain terrain_2;

    public Tile(Terrain terrain_1, Terrain terrain_2){
        this.terrain_1 = terrain_1;
        this.terrain_2 = terrain_2;
    }

    public Terrain[] getTerrainsClockwiseFromVolcano(){
        Terrain[] terrains = new Terrain[3];

        terrains[0] = Terrain.VOLCANO;
        terrains[1] = terrain_1;
        terrains[2] = terrain_2;

        return terrains;
    }

    public String toString() {
        String string = terrain_1.name() + " " + terrain_2.name();
        return string;
    }
}
