package model;

public class InHouse extends Part{
    int machineId;

    public InHouse(int id, String name, double stock, double price, int max, int min) {
        super(id, name, stock, price, max, min);
    }

}
