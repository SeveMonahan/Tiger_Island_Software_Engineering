class Hexagon {
    private int level;
    private Terrain terrain;

    Hexagon() {
        level = 0;
    }

    int getlevel(){
        return level;
    }

    Terrain getTerrain(){
        return terrain;
    }

    private void incrementlevel(){
        level++;
    }

    void changeTerrainTypeThoughExplosion(Terrain new_terrain){
        terrain = new_terrain;
        incrementlevel();
    }
}
