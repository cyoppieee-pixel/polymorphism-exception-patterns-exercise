//flat Php 50 fee, max 0.5 kg
public class Document extends Parcel {
    private int pages;

    public Document(String trackingNumber, String receiverName, double weight, int pages) {
        super(trackingNumber, receiverName, weight);
        this.pages = pages;
    }

    @Override
    public String getType() { return "Document"; }

    @Override
    public double getChargeableWeight() { return getWeight(); }

    @Override
    public double getBaseFee() { return 50; }

    public int getPages() { return pages; }
}
