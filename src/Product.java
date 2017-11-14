public abstract class Product {
    private String id;
    private String name;
    private int number;

    /**
     * Construct an Product Object.
     * The product Object is not really be created, but its subclass as a product need this method.
     *
     * @param id the productID that was the unique ID of the product
     * @param number the product number they have now
     */
    public Product(String id, int number){
        this.id = id;
        this.number = number;
    }

    /**
     * Construct an Product Object.
     * The product Object is not really be created, but its subclass as a product need this method.
     *
     * @param id the productID that was the unique ID of the product
     * @param name the product display-name
     * @param number the product number It has now
     */
    public Product(String id, String name, int number){
        this.id = id;
        this.name = name;
        this.number = number;
    }

    /**
     * Get a product ID.
     *
     * @return productID
     */
    public String getId() {
        return id;
    }

    /**
     * Get a product display-name.
     *
     * @return product's display-name
     */
    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    /**
     * Set product number.
     *
     * @param number The product number it has now.
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
