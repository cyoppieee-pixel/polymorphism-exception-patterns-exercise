// base fee x 1.5
public class ExpressDelivery implements DeliveryService {
    public String getName() { return "Express"; }

    public double computeFee(double baseFee) { return baseFee * 1.5; }

    public void checkAllowed(Parcel parcel) { }
}