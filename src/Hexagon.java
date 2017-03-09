class Hexagon {
    private int level;
    private Terrain terrain;
    public int tileHashCode;
    private int population;

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

    boolean isVolcanoHex(){
        if(this.terrain == Terrain.VOLCANO)
            return true;
        else
            return false;
    }

    void increasePopulation(int byPopulationOf){
        population += byPopulationOf;
    }

    void decreasePopulation(int byPopulationOf){
        population -= byPopulationOf;
    }

    int getPopulation(){
        return this.population;
    }
}
