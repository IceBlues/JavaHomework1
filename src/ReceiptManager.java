import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ReceiptManager {
    private LinkedHashMap<String, ProductSupply> receiptProductList = new LinkedHashMap<String, ProductSupply>();

    public void listReceipt(){
        String selectErrorMessage = "Error Select";
        String productNotFoundError = "The product not found";
        String productNotEnoughError = "The product not enough";
        Scanner in = new Scanner(System.in);
        String productID = "";
        int buyNumber = 0;

        boolean isContinue = true;
        while(isContinue) {
            System.out.println("Please input the productID you want to purchase: ");
            productID = in.nextLine().toUpperCase();
            ProductSupply aProduct = SupplyHandler.getProductByID(productID);
            if (aProduct != null) {
                String productMessage = "";
                productMessage += "Name: " + aProduct.getName() + "\nNumber: " + aProduct.getNumber() + "\nPrice: " + aProduct.getPrice() + "\n";
                System.out.println(productMessage);

                System.out.println("How many you want to buy?");

                try {
                    buyNumber = Integer.parseInt(in.nextLine());
                }
                catch (Exception E) {
                    System.out.println("Error number");
                }

                if (buyNumber <= aProduct.getNumber()) {
                    ProductSupply purchaseProduct = new ProductSupply(productID, aProduct.getName(), buyNumber, aProduct.getPrice());
                    receiptProductList.put(productID, purchaseProduct);
                    System.out.println("P)urchase  C)omplete");
                    String select = in.nextLine().toUpperCase();
                    switch (select){
                        case "P":
                            isContinue = true;
                            break;
                        case "C":
                            isContinue = false;
                            break;
                        default:
                            System.out.println(selectErrorMessage);
                            break;
                    }
                }
                else {
                    System.out.println(productNotEnoughError);
                }
            }
            else {
                System.out.println(productNotFoundError);
            }
        }

        if (!receiptProductList.isEmpty()) {
            complete();
        }
        else {
            System.out.println("Look forward to your next purchase");
        }
    }

    private void complete(){
        Scanner in = new Scanner(System.in);
        String selectErrorMessage = "Error Select";

        System.out.println("Transaction Details");
        double total = 0;

        for (Map.Entry<String, ProductSupply> entry : receiptProductList.entrySet()) {
            ProductSupply G = entry.getValue();
            double aProductTotal = G.getPrice() * G.getNumber();
            System.out.println(G.getName() + "\t\t\t" + G.getNumber() + "\t" + aProductTotal);
            total += aProductTotal;
        }

        System.out.println("Total\t\t\t" + total);

        System.out.println("C)ontinue  S)top");
        boolean isNotCorrectChoose = true;
        String userInput = "";

        while (isNotCorrectChoose) {
            userInput = in.nextLine().toUpperCase();
            switch (userInput) {
                case "C": {
                    isNotCorrectChoose = false;
                    break;
                }
                case "S": {
                    isNotCorrectChoose = false;
                    break;
                }
                default:
                    isNotCorrectChoose = true;
                    System.out.println(selectErrorMessage);
            }
        }

        if(userInput.equals("C")){
            for(Map.Entry<String, ProductSupply> entry : receiptProductList.entrySet()){
                ProductSupply P = entry.getValue();
                StockHandler.addStockFromSupply(P);
                SupplyHandler.soldProduct(P);
            }
            StockHandler.freshStockFile();
            SupplyHandler.freshSupplierFile();
        }

    }
}
