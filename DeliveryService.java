public interface DeliveryService {
    String getName();

    double computeFee(double baseFee);

    // Throws if the service can't be used for the parcel
    void checkAllowed(Parcel parcel) throws ParcelException;
}