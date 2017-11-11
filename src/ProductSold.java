public class ProductSold extends Product {
    private double price;
    ProductSold(String id, int number) {
        super(id, number);
    }

    ProductSold(String name, String id, int number, double price) {
        super(id, name, number);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
