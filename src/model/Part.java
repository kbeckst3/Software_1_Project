package model;


public abstract class Part {

    private int id;
    private String name;
    private int stock;
    private double price;
    private int max;
    private int min;

    public Part(int id, String name, int stock, double price, int max, int min) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.max = max;
        this.min = min;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}

