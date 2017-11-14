import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class StockHandler {
    private static LinkedHashMap<String, ProductStock> productList = new LinkedHashMap<String, ProductStock>();
    private static File stocks;

    /**
     * This method should be invoked at the beginning of program.
     * All of stockProduct-message in stock-file should be imported into productList.
     *
     * @param stockFile The file name contains stockProduct-message in resources folder
     */
    public static void initialize(String stockFile) {
        stocks = new File("resources/" + stockFile + ".txt");
        try {
            Scanner in = new Scanner(stocks);
            String[] productsProperty;
            while (in.hasNext()) {
                productsProperty = in.nextLine().split(",");
                try {
                    //ID, name, number, price
                    productList.put(productsProperty[0].toUpperCase(), new ProductStock(productsProperty[0].toUpperCase(), productsProperty[1], Integer.parseInt
                            (productsProperty[2]),
                            Double.parseDouble(productsProperty[3])));
                }
                catch (NumberFormatException e) {
                    System.out.println("The stock-file constant Error.");
                }
            }

            in.close();
        }
        catch (IOException e) {
            System.out.println("Stock-file not found.");
        }
    }

    /**
     * Get a product should be returned to supplier and minus it in stock.
     *
     * @param returnProduct A product need to be return.
     */
    public static void returnProduct(ProductStock returnProduct){
        ProductStock product = productList.get(returnProduct.getId());

        product.setNumber(product.getNumber() - returnProduct.getNumber());
    }

    /**
     * Get a product should be sold to customer and minus it in stock.
     *
     * @param product A product should be sold
     */
    public static void freshStockWithSoldProduct(ProductSold product) {
        ProductStock P = productList.get(product.getId());
        if (P != null) {
            P.setNumber(P.getNumber() - product.getNumber());
        }
    }

    /**
     * Get a product in stock productList by its ID.
     *
     * @param id The productID that is the unique ID of the product
     * @return A product in stock
     */
    public static ProductStock getProductByID(String id) {
        return productList.get(id);
    }

    /**
     *Get a product purchase from supplier and add it to stock.
     *
     * @param product The product purchase from supplier
     */
    public static void addStockFromSupply(ProductSupply product){
        ProductStock productStock = productList.get(product.getId());

        if(productStock != null){
            productStock.setNumber(productStock.getNumber() + product.getNumber());
        }
        else{
            productList.put(product.getId(), new ProductStock(product.getId(), product.getName(), product.getNumber(), product.getPrice()));
        }
    }

    /**
     * Add product that was returned by customer.
     *
     * @param productID The product ID that is the unique number of product
     * @param returnNumber The product number that need return
     */
    public static void addStockByReturnProduct(String productID, int returnNumber) {
        ProductStock P = productList.get(productID);
        if (P != null) {
            P.setNumber(P.getNumber() + returnNumber);
        }
    }


    /**
     * Update information from productList to product-file in resources folder.
     */
    public static void freshStockFile() {
        try {
            PrintWriter out = new PrintWriter(stocks);
            for (Entry<String, ProductStock> entry : productList.entrySet()) {
                ProductStock product = entry.getValue();
                out.print(product.getId() + ",");
                out.print(product.getName() + ",");
                out.print(product.getNumber() + ",");
                out.println(product.getPrice());
            }

            out.close();
        }
        catch (IOException e) {
            System.out.println("Stock-file not found.");
        }
    }

    /**
     * Get and print all of the product ID in stock.
     */
    public static void checkStock() {
        StringBuilder result = new StringBuilder();

        for (Entry<String, ProductStock> entry : productList.entrySet()) {
            ProductStock P = entry.getValue();
            result.append("Code:\t\t\t");
            result.append(P.getId());
            result.append("\n");
        }

        System.out.println(result.toString());
    }

    /**
     * This method allow user input a product ID and print its message if it's in stock.
     */
    public static void checkStockProduct() {
        String productNotFoundMessage = "Product was not found in stock";
        System.out.println("Please input productID: ");
        Scanner in = new Scanner(System.in);
        String productID = in.nextLine().toUpperCase();
        StringBuilder result = new StringBuilder();

        ProductStock P = productList.get(productID);
        if(P != null) {
            result.append("Code:\t\t\t");
            result.append(P.getId());
            result.append("\nName:\t\t\t");
            result.append(P.getName());
            result.append("\nNumber:\t\t\t");
            result.append(P.getNumber());
            result.append("\nPrice:\t\t\t");
            result.append(P.getPrice());
            result.append("\n\n");
        }
        else{
            result.append(productNotFoundMessage);
        }

        System.out.println(result.toString());
    }


}
