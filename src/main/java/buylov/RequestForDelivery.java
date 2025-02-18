package buylov;

import java.util.Objects;

public class RequestForDelivery {
    private int distance;
    private CargoDimensions dimensions;
    private boolean isFragile;
    private Workload workload;
    public static String messageForDistance = "Значение должно быть больше 0";

    public RequestForDelivery(int distance, CargoDimensions dimensions, boolean isFragile, Workload workload) {
        setDistance(distance);
        setDimensions(dimensions);
        setFragile(isFragile);
        setWorkload(workload);
    }

    public void setDistance(int distance) {
        //if (distance <= 0) throw new IllegalArgumentException(messageForDistance);
        this.distance = distance;
    }

    public void setDimensions(CargoDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public void setFragile(boolean isFragile) {
        this.isFragile = isFragile;
    }

    public void setWorkload(Workload workload) {
        this.workload = workload;
    }

    public int getDistance() {
        return distance;
    }

    public CargoDimensions getDimensions() {
        return dimensions;
    }

    public boolean getFragility() {
        return isFragile;
    }

    public Workload getWorkload() {
        return workload;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RequestForDelivery that = (RequestForDelivery) o;
        return distance == that.distance && isFragile == that.isFragile && dimensions == that.dimensions && workload == that.workload;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, dimensions, isFragile, workload);
    }
}
