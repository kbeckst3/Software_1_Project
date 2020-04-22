package model;

public class Outsourced extends Part {
    String companyName;

    public Outsourced(int id, String name, int stock, double price, int max, int min, String companyName) {
        super(id, name, stock, price, max, min);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
