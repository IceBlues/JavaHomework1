import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class Receipt {
    private HashMap<String, ProductSold> receiptProductList = new HashMap<String, ProductSold>();
    private String receiptID;

    Receipt() {
        listReceipt();
    }

    Receipt(String receiptID) {
        this.receiptID = receiptID;
    }

    private Receipt(String receiptID, HashMap<String, ProductSold> receiptProductList) {
        this.receiptProductList = receiptProductList;
        this.receiptID = receiptID;
    }

    public void listReceipt() {
        boolean isContinuePurchase = true;
        Scanner in = new Scanner(System.in);
        String userInput;
        String selectErrorMessage = "Please Input Correct selection";

        while (isContinuePurchase) {
            addProduct();

            System.out.println("P)urchase  C)omplete Transaction");

            boolean isNotCorrectChoose = true;
            while (isNotCorrectChoose) {
                userInput = in.nextLine().toUpperCase();
                switch (userInput) {
                    case "P": {
                        isContinuePurchase = true;
                        isNotCorrectChoose = false;
                        break;
                    }
                    case "C": {
                        isContinuePurchase = false;
                        isNotCorrectChoose = false;
                        break;
                    }
                    default:
                        isNotCorrectChoose = true;
                        System.out.println(selectErrorMessage);
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

    public void addProduct(String id, int number) {
        receiptProductList.put(id, new ProductSold(id, number));
    }

    private void addProduct() {
        ProductStock aProduct = null;
        String aProductID = null;
        Scanner in = new Scanner(System.in);
        String stockNumberNotEnoughMessage = "The StockNumber is not enough";
        String purchaseNotFoundMessage = "The purchase not found";

        System.out.println("Please input product-ID :");
        aProductID = in.nextLine().toUpperCase();
        aProduct = StockHandler.getProductByID(aProductID);

        if (aProduct != null) {
            System.out.println("Current Stock: " + aProduct.getNumber());
            System.out.println("Price: " + aProduct.getPrice());
            try {
                int number = 0;
                ProductSold S = receiptProductList.get(aProductID);
                if (S == null) {
                    System.out.println("How many you want to buy?");
                    number = Integer.parseInt(in.nextLine());
                    if (number > 0 && number <= aProduct.getNumber()) {
                        receiptProductList.put(aProductID, new ProductSold(aProduct.getName(), aProductID, number, aProduct.getPrice()));
                        System.out.println("Product add succeed");
                    }
                    else {
                        System.out.println(stockNumberNotEnoughMessage);
                    }
                }
                else {
                    System.out.println("What's number you want to change now?");
                    number = Integer.parseInt(in.nextLine());
                    if (number > 0 && number <= aProduct.getNumber()) {
                        S.setNumber(number);
                        System.out.println("Product number change succeed");
                    }
                    else {
                        System.out.println(stockNumberNotEnoughMessage);
                    }
                }

            }
            catch (Exception e) {
                System.out.println("Number-format Error, please input positive integer");
            }
        }
        else {
            System.out.println(purchaseNotFoundMessage);
        }
    }

    private void complete() {
        double total = 0;
        String userInput = "";
        Scanner in = new Scanner(System.in);
        String selectErrorMessage = "Please Input Correct selection";

        System.out.println("Transaction Details");
        for (Entry<String, ProductSold> entry : receiptProductList.entrySet()) {
            ProductSold G = entry.getValue();
            double aProductTotal = G.getPrice() * G.getNumber();
            System.out.println(G.getName() + "\t\t\t" + G.getNumber() + "\t" + aProductTotal);
            total += aProductTotal;
        }
        System.out.println("Subtotal\t\t\t" + total);

        double localTax = ReceiptHandler.getLocalTax();
        System.out.println("Tax(" + localTax * 100 + "%)" + "\t\t\t" + total * localTax);
        total += total * localTax;

        System.out.println("Total" + "\t\t\t\t" + total);
        System.out.println();

        receiptID = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()) + (ReceiptHandler.getSerialNumber());

        System.out.println(receiptID);
        System.out.println("C)ontinue  S)top");
        boolean isNotCorrectChoose = true;
        while (isNotCorrectChoose) {
            userInput = in.nextLine().toUpperCase();
            switch (userInput) {
                case "C": {
                    isNotCorrectChoose = false;
                    ReceiptHandler.nextSerialNumber();
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

        if (userInput.equals("C")) {
            //Add this receipt to receipt-list
            ReceiptHandler.addReceipt(new Receipt(receiptID, receiptProductList));
            //update stock-list
            for (Entry<String, ProductSold> entry : receiptProductList.entrySet()) {
                ProductSold S = entry.getValue();
                StockHandler.freshStockWithSoldProduct(S);
            }

            StockHandler.freshStockFile();
            ReceiptHandler.freshReceiptFile();
            System.out.println("Thank you for your purchase. Your receipt has been printed");
        }
    }

    public ProductSold getProductByID(String productId) {
        return receiptProductList.get(productId);
    }

    String getReceiptID() {
        return receiptID;
    }

    public HashMap<String, ProductSold> getReceiptProductList() {
        return receiptProductList;
    }

    public Date getDate() {
        Date result = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String subString[] = receiptID.split(" ");
        if (subString.length != 0) {
            try {
                result = format.parse(subString[0]);
            }
            catch (ParseException p) {
                System.out.println("date error");
            }
        }
        return result;
    }

    public Date getFullDate(){
        Date result = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String subString[] = receiptID.split(" ");
        if (subString.length != 0) {
            try {
                result = format.parse(subString[0] + " " + subString[1]);
            }
            catch (ParseException p) {
                System.out.println("date error");
            }
        }
        return result;
    }
}
