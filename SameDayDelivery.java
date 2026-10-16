//(base fee x 2) + 100, up to 10 kg only
public class SameDayDelivery implements DeliveryService {
    public String getName() { return "Same Day"; }

    public double computeFee(double baseFee) { return (baseFee * 2) + 100; }

    public void checkAllowed(Parcel parcel) throws ParcelException {
        if (parcel.getChargeableWeight() > 10) {
            throw new ParcelException("Same Day delivery is only for parcels up to 10 kg!");
        }
    }
}