import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class SupplyHandler {
    private static LinkedHashMap<String, ProductSupply> productList = new LinkedHashMap<String, ProductSupply>();
    private static File supplier;

    /**
     * This method should be invoked at the beginning of program.
     * All of supplyProduct-message in supplier-file should be imported into productList.
     *
     * @param supplierFile The file name contains supplyProduct-message in resources folder
     */
    public static void initialize(String supplierFile){
        supplier = new File("resources/" + supplierFile + ".txt");

        try {
            Scanner in = new Scanner(supplier);
            while(in.hasNext()){
                String subString[] = in.nextLine().split(",");
                //ID, name, number, price
                productList.put(subString[0],new ProductSupply(subString[0], subString[1], Integer.parseInt(subString[2]), Double.parseDouble(subString[3])));
            }
        }
        catch (IOException e){
            System.out.println("File not found");
        }
    }

    /**
     * Get a product should be returned from stock and add it to supplier.
     *
     * @param returnProduct A product return from stock
     */
    public static void addSupplyFromReturnProduct(ProductStock returnProduct){
        ProductSupply product = productList.get(returnProduct.getId());

        product.setNumber(product.getNumber() + returnProduct.getNumber());
    }

    /**
     * Get the supply product that was sold and minus it from productList.
     *
     * @param P A supply product be sold
     */
    public static void soldProduct(ProductSupply P){
        ProductSupply soldProduct = productList.get(P.getId());
        soldProduct.setNumber(soldProduct.getNumber() - P.getNumber());
    }

    /**
     * Get the product in supplier productList by its ID.
     *
     * @param id The unique ID of the product
     * @return A supply product
     */
    public static ProductSupply getProductByID(String id){
        return productList.get(id);
    }

    /**
     * Update information from productList to supplier-file in resources folder.
     */
    public static void freshSupplierFile(){
        try {
            PrintWriter out = new PrintWriter(supplier);
            for(Entry<String, ProductSupply>entry : productList.entrySet()){
                ProductSupply P = entry.getValue();
                out.println(P.getId() + "," + P.getName() + "," + P.getNumber() + "," + P.getPrice());
            }

            out.close();
        }
        catch (IOException e){
            System.out.println("File not found");
        }
    }
}
