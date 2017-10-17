package indi.annette.shopSystem.purchase.receipt;

public class SoldGood {
    private String name;
    private String id;
    private int number;
    private double price;

    SoldGood(String id, int number) {
        this.name = name;
        this.id = id;
        this.number = number;
    }

    SoldGood(String name, String id, int number, double price) {
        this.name = name;
        this.id = id;
        this.number = number;
        this.price = price;

    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }


}
