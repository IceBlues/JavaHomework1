import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class SupplyHandler {
    private static LinkedHashMap<String, ProductSupply> productList = new LinkedHashMap<String, ProductSupply>();
    private static File supplier;

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

    public static void addSupplyFromReturnProduct(ProductSupply returnProduct){
        ProductSupply product = productList.get(returnProduct.getId());

        product.setNumber(product.getNumber() + returnProduct.getNumber());
    }

    public static void soldProduct(ProductSupply P){
        ProductSupply soldProduct = productList.get(P.getId());
        soldProduct.setNumber(soldProduct.getNumber() - P.getNumber());
    }

    public static ProductSupply getProductByID(String id){
        return productList.get(id);
    }

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
