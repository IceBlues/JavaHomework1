public class ProductSupply extends Product {
    private double price;

    /**
     * Construct a ProductSupply Object.
     *
     * @param id The productID that was the unique ID of the product
     * @param name The product display-name
     * @param number The product number that supplier can support
     * @param price The product price that supplier sold.
     */
    ProductSupply(String id, String name, int number, double price){
        super(id, name, number);
        this.price = price;
    }

    /**
     * Get the product price
     *
     * @return get the price of product sold now
     */
    public double getPrice() {
        return price;
    }
}
