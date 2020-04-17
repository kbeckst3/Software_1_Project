package model;

public class Outsourced extends Part {
    String companyName;

    public Outsourced(int id, String name, int stock, double price, int max, int min) {
        super(id, name, stock, price, max, min);
    }
}
