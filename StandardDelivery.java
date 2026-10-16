// base fee
public class StandardDelivery implements DeliveryService {
    public String getName() { return "Standard"; }

    public double computeFee(double baseFee) { return baseFee; }

    public void checkAllowed(Parcel parcel) { }
}