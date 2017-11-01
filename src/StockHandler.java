import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class StockHandler {
    private static HashMap<String, ProductStock> productList = new HashMap<String, ProductStock>();
    private static File stocks;

    public static void initialize(String stockFile) {
        stocks = new File("resources/" + stockFile + ".txt");
        try {
            Scanner in = new Scanner(stocks);
            String[] productsProperty;
            while (in.hasNext()) {
                productsProperty = in.nextLine().toUpperCase().split(",");
                try {
                    //ID, name, number, price
                    productList.put(productsProperty[0],new ProductStock(productsProperty[0], productsProperty[1], Integer.parseInt(productsProperty[2]),
                            Double.parseDouble(productsProperty[3])));
                }
                catch (NumberFormatException e) {
                    System.out.println("The stock-file constant Error.");
                }
            }

            in.close();
        }
        catch (IOException e){
            System.out.println("Stock-file not found.");
        }
    }

    public static ProductStock getProductByID(String id) {
        return productList.get(id);
    }

    public static void freshStockWithSoldProduct(ProductSold product) {
        ProductStock P = productList.get(product.getId());
        if(P != null) {
            P.setNumber(P.getNumber() - product.getNumber());
        }
    }

    public static void addStockByReturnProduct(String productID, int returnNumber){
        ProductStock P = productList.get(productID);
        if(P != null) {
            P.setNumber(P.getNumber() + returnNumber);
        }
    }


    public static void freshStockFile(){
        try {
            PrintWriter out = new PrintWriter(stocks);
            for(Entry<String, ProductStock> entry : productList.entrySet()) {
                ProductStock product = entry.getValue();
                out.print(product.getId() + ",");
                out.print(product.getName() + ",");
                out.print(product.getNumber() + ",");
                out.println(product.getPrice());
            }

            out.close();
        }
        catch (IOException e){
            System.out.println("Stock-file not found.");
        }
    }

    public static void checkStock(){

        String authorityNotEnoughMessage = "You have not enough authority";
        StringBuilder result = new StringBuilder();

        if(SystemAccount.getAuthority().equals("M")) {
            for (Entry<String, ProductStock> entry : productList.entrySet()) {
                ProductStock P = entry.getValue();
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

            System.out.println(result.toString());
        }
        else{
            System.out.println(authorityNotEnoughMessage);
        }

    }
}
