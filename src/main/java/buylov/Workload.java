package buylov;

public enum Workload {
    NORMAL(1),
    INCREASED(1.2),
    HIGH(1.4),
    HIGHEST(1.6);

    private final double loadFactor;

    Workload(double loadFactor) {
        this.loadFactor = loadFactor;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

}
