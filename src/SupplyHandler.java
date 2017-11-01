import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class SupplyHandler {
    private static HashMap<String, ProductSupply> productList = new HashMap<String, ProductSupply>();
    private static File supplier;

    public static void initialize(String supplierFile){
        supplier = new File("resources/" + supplierFile + ".txt");

        try {
            Scanner in = new Scanner(supplier);
            while(in.hasNext()){
                String subString[] = in.nextLine().split(",");
                //ID, number
                productList.put(subString[0],new ProductSupply(subString[0], Integer.parseInt(subString[1])));
            }
        }
        catch (IOException e){
            System.out.println("File not found");
        }
    }

    public static void addSupplyProduct(String id, int number){
        ProductSupply P = productList.get(id);

        if(P == null){
            productList.put(id, new ProductSupply(id, number));
        }
        else{
            P.setNumber(P.getNumber() + number);
        }
    }

    public static void freshSupplierFile(){
        try {
            PrintWriter out = new PrintWriter(supplier);
            for(Entry<String, ProductSupply>entry : productList.entrySet()){
                ProductSupply P = entry.getValue();
                out.println(P.getId() + "," + P.getNumber());
            }

            out.close();
        }
        catch (IOException e){
            System.out.println("File not found");
        }
    }
}
