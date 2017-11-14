public class ProductStock extends Product {
    private double price;

    /**
     * Construct a ProductStock Object with id, name, number and price.
     *
     * @param id The productID that was the unique ID of the product
     * @param name The product display-name
     * @param stockNum The product number in stock
     * @param price The product price will be sold
     */
    ProductStock(String id, String name, int stockNum, double price) {
        super(id, name, stockNum);
        this.price = price;
    }

    /**
     * Construct a ProductStock Object with id, name, number and price.
     *
     * @param id The productID that was the unique ID of the product
     * @param name The product display-name
     * @param stockNum The product number it has now
     */
    ProductStock(String id, String name, int stockNum) {
        super(id, name, stockNum);
        this.price = price;
    }

    /**
     * Get the product price will be sold to customer
     *
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
