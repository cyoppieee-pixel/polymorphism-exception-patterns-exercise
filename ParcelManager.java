import java.util.ArrayList;

// singleton: one shared list of parcels
public class ParcelManager {
    private static ParcelManager instance;
    private ArrayList<Parcel> parcels = new ArrayList<>();

    private ParcelManager() { }

    public static ParcelManager getInstance() {
        if (instance == null) {
            instance = new ParcelManager();
        }
        return instance;
    }

    public void addParcel(Parcel parcel) {
        parcels.add(parcel);
    }

    public Parcel findParcel(String trackingNumber) throws ParcelException {
        for (Parcel p : parcels) {
            if (p.getTrackingNumber().equals(trackingNumber)) {
                return p;
            }
        }
        throw new ParcelException("Parcel not found!");
    }

    public boolean exists(String trackingNumber) {
        for (Parcel p : parcels) {
            if (p.getTrackingNumber().equals(trackingNumber)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Parcel> getParcels() {
        return parcels;
    }
}