package indi.annette.shopSystem.purchase.stock;

public class StockGood {
    private String code;
    private String name;
    private int number;
    private double price;


    public StockGood(String code, String name, int stockNum, double price) {
        this.code = code;
        this.name = name;
        this.number = stockNum;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
