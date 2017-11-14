public class ProductSold extends Product {
    private double price;

    /**
     * Create a product Object that be sold with id and number.
     *
     * @param id the productID that was the unique ID of the product
     * @param number the product number It has now
     */
    ProductSold(String id, int number) {
        super(id, number);
    }

    /**
     * Construct a ProductSold Object with name, id, number and price.
     *
     * @param name The product display-name
     * @param id The productID that was the unique ID of the product
     * @param number The product number It has now
     * @param price The product price It has now
     */
    ProductSold(String name, String id, int number, double price) {
        super(id, name, number);
        this.price = price;
    }

    /**
     * Get the price this product has now.
     *
     * @return The product price
     */
    public double getPrice() {
        return price;
    }
}
