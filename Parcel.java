// base for all parcel types
public abstract class Parcel {
    private String trackingNumber;
    private String receiverName;
    private double weight;
    private DeliveryService service;
    private String status = "Pending";

    public Parcel(String trackingNumber, String receiverName, double weight) {
        this.trackingNumber = trackingNumber;
        this.receiverName = receiverName;
        this.weight = weight;
    }

    public abstract String getType();

    public abstract double getChargeableWeight();

    public abstract double getBaseFee();

    public double getDeliveryFee() {
        return service.computeFee(getBaseFee());
    }


    public void setService(DeliveryService newService) throws ParcelException {
        newService.checkAllowed(this);
        service = newService;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public String getReceiverName() { return receiverName; }
    public double getWeight() { return weight; }
    public DeliveryService getService() { return service; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}