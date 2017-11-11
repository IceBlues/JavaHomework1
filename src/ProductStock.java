public class ProductStock extends Product {
    private double price;

    ProductStock(String id, String name, int stockNum, double price) {
        super(id, name, stockNum);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
