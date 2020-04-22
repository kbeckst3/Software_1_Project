package model;

public class InHouse extends Part{
    int machineId;

    public InHouse(int id, String name, int stock, double price, int max, int min, int machineId) {
        super(id, name, stock, price, max, min);
        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
