package game.map.population;

public enum SpawnChance {
    LOW(0,2),
    HIGH(3,10);

    protected final int low;
    protected final int high;

    SpawnChance(int low, int high) {
        this.low = low;
        this.high = high;
    }
}
