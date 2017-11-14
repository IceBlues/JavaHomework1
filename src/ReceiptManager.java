import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ReceiptManager {
    private LinkedHashMap<String, ProductSupply> receiptProductList = new LinkedHashMap<String, ProductSupply>();

    /**
     * This method will start a shopping process that buy some products from supplier to store stock.
     */
    public void listReceipt(){
        String selectErrorMessage = "Error Select";
        String productNotFoundError = "The product not found";
        String productNotEnoughError = "The product not enough";
        String numberErrorMessage = "Please input correct number";
        Scanner in = new Scanner(System.in);
        String productID = "";

        boolean isContinue = true;
        while(isContinue) {
            System.out.println("Hello,manager. Please input the productID you want to purchase to your store: ");
            productID = in.nextLine().toUpperCase();
            ProductSupply aProduct = SupplyHandler.getProductByID(productID);
            if (aProduct != null) {
                String productMessage = "";
                productMessage += "Name: " + aProduct.getName() + "\nNumber: " + aProduct.getNumber() + "\nPrice: " + aProduct.getPrice() + "\n";
                System.out.println(productMessage);

                if (receiptProductList.get(productID) == null) {
                    int buyNumber = 0;
                    System.out.println("How many you want to buy?");

                    try {
                        buyNumber = Integer.parseInt(in.nextLine());
                    }
                    catch (Exception E) {
                        System.out.println("Error number");
                    }

                    if (buyNumber > 0 && buyNumber <= aProduct.getNumber()) {
                        ProductSupply purchaseProduct = new ProductSupply(productID, aProduct.getName(), buyNumber, aProduct.getPrice());
                        receiptProductList.put(productID, purchaseProduct);
                        System.out.println("Product add succeed");
                    }
                    else if(buyNumber <= 0){
                        System.out.println(numberErrorMessage);
                    }
                    else {
                        System.out.println(productNotEnoughError);
                    }
                }
                else{
                    int fixNumber = 0;
                    System.out.println("What number you want to buy now?");

                    try {
                        fixNumber = Integer.parseInt(in.nextLine());
                    }
                    catch (Exception E) {
                        System.out.println("Error number");
                    }

                    if(fixNumber > 0 && fixNumber <= aProduct.getNumber()) {
                        receiptProductList.get(productID).setNumber(fixNumber);
                        System.out.println("The product change succeed");
                    }
                    else if(fixNumber <= 0){
                        if(fixNumber == 0){
                            receiptProductList.remove(productID);
                            System.out.println("You have remove this product");
                        }
                        else {
                            System.out.println(numberErrorMessage);
                        }
                    }
                    else{
                        System.out.println(productNotEnoughError);
                    }
                }

                System.out.println("P)urchase  C)omplete");
            }
            else {
                System.out.println(productNotFoundError);
            }

            String select = in.nextLine().toUpperCase();
            boolean isNotCorrectChoose = true;
            while(isNotCorrectChoose) {
                switch (select) {
                    case "P":
                        isContinue = true;
                        isNotCorrectChoose = false;
                        break;
                    case "C":
                        isContinue = false;
                        isNotCorrectChoose = false;
                        break;
                    default:
                        System.out.println(selectErrorMessage);
                        break;
                }
            }

        }

        if (!receiptProductList.isEmpty()) {
            complete();
        }
        else {
            System.out.println("Look forward to your next purchase");
        }
    }

    //Complete the shopping process and if the transaction successful, update product-message to resources-files.
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
