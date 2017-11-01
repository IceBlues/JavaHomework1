public class ProductSold extends Product {
    ProductSold(String id, int number) {
        super(id, number);
    }

    ProductSold(String name, String id, int number, double price) {
        super(id, name, number, price);
    }
}
