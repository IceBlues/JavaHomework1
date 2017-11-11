public class ProductSupply extends Product {
    private double price;

    ProductSupply(String id, String name, int number){
        super(id, name, number);
    }

    ProductSupply(String id, String name, int number, double price){
        super(id, name, number);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
