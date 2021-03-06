import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class ReceiptStaff {
    private LinkedHashMap<String, ProductSold> receiptProductList = new LinkedHashMap<String, ProductSold>();
    private String receiptID;

    /**
     * Construct a default ReceiptStaff Object.
     */
    ReceiptStaff() {

    }

    /**
     * Construct a ReceiptStaff Object with receiptID.
     *
     * @param receiptID The receiptID which is the unique ID of the receipt
     */
    ReceiptStaff(String receiptID) {
        this.receiptID = receiptID;
    }

    /**
     * This method will start a shopping process that sold product from stock to customer.
     */
    public void listReceipt() {
        boolean isContinuePurchase = true;
        Scanner in = new Scanner(System.in);
        String userInput;
        String selectErrorMessage = "Please Input Correct selection";

        while (isContinuePurchase) {
            addProduct();

            boolean isNotCorrectChoose = true;
            while (isNotCorrectChoose) {
                System.out.println("P)urchase  C)omplete Transaction");
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

    /**
     * Add product to receipt with its ID and sold-number.
     *
     * @param id The productID which is the unique ID of the product
     * @param number The product number that will be sold
     */
    public void addProduct(String id, int number) {
        receiptProductList.put(id, new ProductSold(id, number));
    }

    private void addProduct() {
        ProductStock aProduct = null;
        String aProductID = null;
        Scanner in = new Scanner(System.in);
        String stockNumberNotEnoughMessage = "The StockNumber is not enough";
        String purchaseNotFoundMessage = "The purchase not found";
        String numberErrorMessage = "Please input correct number";

        System.out.println("Please input product-ID :");
        aProductID = in.nextLine().toUpperCase();
        aProduct = StockHandler.getProductByID(aProductID);

        if (aProduct != null) {
            System.out.println("Name: " + aProduct.getName());
            System.out.println("Current Stock: " + aProduct.getNumber());
            System.out.println("Price: " + aProduct.getPrice());
            try {
                int number = 0;
                ProductSold S = receiptProductList.get(aProductID);
                //If first buy it
                if (S == null) {
                    System.out.println("How many you want to buy?");
                    number = Integer.parseInt(in.nextLine());
                    if (number > 0 && number <= aProduct.getNumber()) {
                        receiptProductList.put(aProductID, new ProductSold(aProduct.getName(), aProductID, number, aProduct.getPrice()));
                        System.out.println("Product add succeed");
                    }
                    else if(number <= 0){
                        System.out.println(numberErrorMessage);
                    }
                    else {
                        System.out.println(stockNumberNotEnoughMessage);
                    }
                }
                else {
                    System.out.println("What's number you want to buy now?");
                    number = Integer.parseInt(in.nextLine());
                    if (number > 0 && number <= aProduct.getNumber()) {
                        S.setNumber(number);
                        System.out.println("Product number change succeed");
                    }
                    else if(number <= 0){
                        if(number == 0){
                            receiptProductList.remove(aProductID);
                            System.out.println("You have remove this product");
                        }
                        else {
                            System.out.println(numberErrorMessage);
                        }
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

    //Complete the shopping process and if the transaction successful, update product-message to resources-files.
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

        double localTax = ReceiptStaffHandler.getLocalTax();
        System.out.println("Tax(" + localTax * 100 + "%)" + "\t\t\t" + total * localTax);
        total += total * localTax;

        System.out.println("Total" + "\t\t\t\t" + total);
        System.out.println();

        receiptID = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()) + (ReceiptStaffHandler.getSerialNumber());

        System.out.println(receiptID);
        System.out.println("C)ontinue  S)top");
        boolean isNotCorrectChoose = true;
        while (isNotCorrectChoose) {
            userInput = in.nextLine().toUpperCase();
            switch (userInput) {
                case "C": {
                    isNotCorrectChoose = false;
                    ReceiptStaffHandler.nextSerialNumber();
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
            ReceiptStaffHandler.addReceipt(this);
            //update stock-list
            for (Entry<String, ProductSold> entry : receiptProductList.entrySet()) {
                ProductSold S = entry.getValue();
                StockHandler.freshStockWithSoldProduct(S);
            }

            StockHandler.freshStockFile();
            ReceiptStaffHandler.freshReceiptFile();
            System.out.println("Thank you for your purchase. Your receipt has been printed");
        }
    }

    /**
     * Get the sold product from Receipt by its ID.
     *
     * @param productId The product ID that was the unique ID of the product
     * @return A productSold Object
     */
    public ProductSold getProductByID(String productId) {
        return receiptProductList.get(productId);
    }

    /**
     * Get the ReceiptID
     *
     * @return This receipt's ID
     */
    String getReceiptID() {
        return receiptID;
    }

    /**
     * Get all sold products in this receipt.
     *
     * @return The product list in this receipt
     */
    public LinkedHashMap<String, ProductSold> getReceiptProductList() {
        return receiptProductList;
    }

    /**
     * Get the year, month and day the receipt was printed.
     *
     * @return A Date Object which contains the date this receipt was printed
     */
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

    /**
     * Get the year, month, day, hour, minutes and second the receipt was printed.
     *
     * @return A Date Object which contains the date this receipt was printed
     */
    public Date getFullDate() {
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
