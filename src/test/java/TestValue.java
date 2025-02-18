import buylov.RequestForDelivery;

public class TestValue {
    RequestForDelivery request;
    double cost;
    String message;

    public TestValue(RequestForDelivery request, double cost) {
        this.request = request;
        this.cost = cost;
    }

    public RequestForDelivery getRequest() {
        return request;
    }

    public double getCost() {
        return cost;
    }


}
