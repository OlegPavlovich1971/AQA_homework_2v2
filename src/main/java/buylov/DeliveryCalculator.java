package buylov;

import static buylov.RequestForDelivery.messageForDistance;

public class DeliveryCalculator {
    private static final double MIN_DELIVERY_COST = 400;
    private static final int DIMENSIONS_BIG = 200;
    private static final int DIMENSIONS_SMALL = 100;
    private static final int FRAGILITY_DIST_LIMIT = 30;
    private static final int FRAGILITY_EXTRA = 300;
    private static final int FRAGILITY_NORM = 0;
    private static final int[] DISTANCE_EXTRA_ARR = {50, 100, 200, 300};
    private static final int[] DISTANCE_BORDERS_ARR = {0, 2, 10, 30};
    public static String messageNotDelivery = "Доставка хрупкого груза на расстояние более 30км не осуществляется";

    public static double calculatorCost(RequestForDelivery value) {
        if (value.getDistance() <= 0) throw new IllegalArgumentException(messageForDistance);
        if (value.getDistance() > FRAGILITY_DIST_LIMIT && value.getFragility())
            throw new UnsupportedOperationException(messageNotDelivery);
        return Math.max((calcExtraDist(value.getDistance())
                + calcExtraDim(value.getDimensions())
                + calcExtraFrag(value.getFragility()))
                * calcFactorLoad(value.getWorkload()), MIN_DELIVERY_COST);
    }

    static int calcExtraDist(int value) {
        if (value > DISTANCE_BORDERS_ARR[3]) return DISTANCE_EXTRA_ARR[3];
        if (value > DISTANCE_BORDERS_ARR[2]) return DISTANCE_EXTRA_ARR[2];
        if (value > DISTANCE_BORDERS_ARR[1]) return DISTANCE_EXTRA_ARR[1];
        return DISTANCE_EXTRA_ARR[0];
    }

    static int calcExtraDim(CargoDimensions value) {
        return switch (value) {
            case BIG -> DIMENSIONS_BIG;
            case SMALL -> DIMENSIONS_SMALL;
        };
    }

    static int calcExtraFrag(boolean isFragile) {
        if (isFragile) return FRAGILITY_EXTRA;
        return FRAGILITY_NORM;
    }

    static double calcFactorLoad(Workload value) {
        return value.getLoadFactor();
    }


}
