class Hexagon {
    private int level;
    private Terrain terrain;

    private void incrementLevel(){
        level++;
    }

    Hexagon() {
        level = 0;
        terrain = Terrain.EMPTY;
    }

    int getLevel(){
        return level;
    }

    Terrain getTerrain(){
        return terrain;
    }

    void changeTerrainTypeThoughExplosion(Terrain new_terrain){
        terrain = new_terrain;
        incrementLevel();
    }
}
