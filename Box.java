//charged by the bigger of actual and volumetric weight, max 50 kg
public class Box extends Parcel {
    private int length, width, height;

    public Box(String trackingNumber, String receiverName, double weight,
               int length, int width, int height) {
        super(trackingNumber, receiverName, weight);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public String getType() { return "Box"; }

    @Override
    public double getChargeableWeight() {
        // Round up to the next whole kg
        double volumetric = ((double) length * width * height) / 3500;
        return Math.ceil(Math.max(getWeight(), volumetric));
    }

    @Override
    public double getBaseFee() { return 40 * getChargeableWeight(); }
}
