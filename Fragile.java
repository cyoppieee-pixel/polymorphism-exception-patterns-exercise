//box with a handling note, max 30 kg
public class Fragile extends Box {
    private String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight,
                   int length, int width, int height, String handlingNote) {
        super(trackingNumber, receiverName, weight, length, width, height);
        this.handlingNote = handlingNote;
    }

    @Override
    public String getType() { return "Fragile"; }

    // Box fee + Php 100 handling fee
    @Override
    public double getBaseFee() { return super.getBaseFee() + 100; }

    public String getHandlingNote() { return handlingNote; }
}
