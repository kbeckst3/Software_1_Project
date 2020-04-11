package part;


import java.text.NumberFormat;

public class Part {

    private int partID;
    private String partName;
    private int partInventoryLevel;
    private double partPriceCost;
    private int partManufacturing;

    public Part(int partID, String partName, int partInventoryLevel, double partPriceCost, int partManufacturing) {

        this.partID = partID;
        this.partName = partName;
        this.partInventoryLevel = partInventoryLevel;
        this.partPriceCost = partPriceCost;
        this.partManufacturing = partManufacturing;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getPartName() { return partName; }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getPartInventoryLevel() {
        return partInventoryLevel;
    }

    public void setPartInventoryLevel(int partInventoryLevel) {
        this.partInventoryLevel = partInventoryLevel;
    }

    public String getPartPriceCost() {
        return NumberFormat.getCurrencyInstance().format(partPriceCost);
    }

    public void setPartPriceCost(double partPriceCost) {
        this.partPriceCost = partPriceCost;
    }

    public int getPartManufacturing() {
        return partManufacturing;
    }

    public void setPartManufacturing(int partManufacturing) {
        this.partManufacturing = partManufacturing;
    }


}
