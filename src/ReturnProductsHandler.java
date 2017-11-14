import java.util.*;

public class ReturnProductsHandler {

    /**
     * This method will start a return process that return sold products from customer to stock.
     */
    public static void StaffProductsReturn() {
        System.out.println("Please input receiptStaff ID(year-months-day hour:minutes:second number) :");
        Scanner in = new Scanner(System.in);
        String numberErrorMessage = "Please input correct number";
        String productNotSoldMessage = "The product was not sold in this receipt";
        String selectErrorMessage = "Select Error. Default choose Continue";
        String receiptNotFound = "Receipt not found";

        String receiptID = in.nextLine();
        ReceiptStaff receiptStaff = ReceiptStaffHandler.getReceiptByID(receiptID);

        if (receiptStaff != null) {
            if (compareDateDays(receiptStaff.getFullDate()) <= 30) {
                boolean isContinue = true;
                String continueSelect = "";
                while (isContinue) {
                    System.out.println("Please input product-ID : ");
                    String productId = in.nextLine().toUpperCase();
                    ProductSold soldProduct = receiptStaff.getProductByID(productId);

                    if (soldProduct != null) {
                        int soldNumber = soldProduct.getNumber();
                        int returnNumber = 0;
                        System.out.println(soldNumber + " had been sold");
                        System.out.println("How many you want to return?");

                        try {
                            returnNumber = Integer.parseInt(in.nextLine());
                            if (returnNumber <= 0 || returnNumber > soldNumber) {
                                System.out.println(numberErrorMessage);
                            }
                            else {
                                soldProduct.setNumber(soldNumber - returnNumber);
                                StockHandler.addStockByReturnProduct(productId, returnNumber);
                            }
                        }
                        catch (Exception e) {
                            System.out.println("Error number");
                        }
                    }
                    else {
                        System.out.println(productNotSoldMessage);
                    }

                    System.out.println("C)ontinue  E)nd");
                    continueSelect = in.nextLine().toUpperCase();
                    switch (continueSelect) {
                        case "C":
                            isContinue = true;
                            break;
                        case "E":
                            isContinue = false;
                            break;
                        default:
                            System.out.println(selectErrorMessage);
                            isContinue = true;
                            break;
                    }
                }

                StockHandler.freshStockFile();
                ReceiptStaffHandler.freshReceiptFile();
                System.out.println("Return complete, have a product fun");
            }
            else {
                System.out.println("Your receipt is more than 30 days");
            }
        }
        else {
            System.out.println(receiptNotFound);
        }
    }

    /**
     * This method will start a return process that return stock products from stock to supplier.
     */
    public static void ManagerProductsReturn() {
        boolean isContinue = true;
        System.out.println("Welcome manager, Do you really want to return products to supplier?(Y/N)");
        Scanner in = new Scanner(System.in);
        String userInput = "";
        String selectErrorMessage = "Select Error";
        String numberErrorMessage = "Please input correct number";
        String productNotFound = "Receipt not found";

        userInput = in.nextLine().toUpperCase();
        if (userInput.equals("Y")) {
            isContinue = true;
        }
        else if (userInput.equals("N")) {
            isContinue = false;
        }
        else {
            System.out.println(selectErrorMessage);
            isContinue = false;
        }

        if(isContinue) {
            while (isContinue) {
                System.out.println("Please input product-ID: ");
                String productId = in.nextLine().toUpperCase();

                ProductStock aProduct = StockHandler.getProductByID(productId);

                if (aProduct != null) {
                    System.out.println("Name: " + aProduct.getName());
                    System.out.println("Number: " + aProduct.getNumber());
                    System.out.println("Please input return number: ");
                    int returnNumber = 0;

                    try {
                        returnNumber = Integer.parseInt(in.nextLine());
                        if (returnNumber > 0 && returnNumber <= aProduct.getNumber()) {
                            ProductStock returnProduct = new ProductStock(productId, aProduct.getName(), returnNumber);
                            StockHandler.returnProduct(returnProduct);
                            SupplyHandler.addSupplyFromReturnProduct(returnProduct);
                        }
                        else {
                            System.out.println(numberErrorMessage);
                        }
                    }
                    catch (Exception e) {
                        System.out.println(numberErrorMessage);
                    }
                }
                else {
                    System.out.println(productNotFound);
                }

                boolean isSelect = false;
                while (!isSelect) {
                    System.out.println("C)ontinue  E)nd");
                    userInput = in.nextLine().toUpperCase();
                    if (userInput.equals("C")) {
                        isContinue = true;
                        isSelect = true;
                    }
                    else if (userInput.equals("E")) {
                        isContinue = false;
                        isSelect = true;
                    }
                    else {
                        System.out.println(selectErrorMessage);
                    }
                }
            }
            System.out.println("Return complete!");
            StockHandler.freshStockFile();
            SupplyHandler.freshSupplierFile();
        }
    }

    /*
     *Compare the number of days interval between now day and receipt created day.
     *
     * @param receiptDate The receipt was constructed date
     * @return The days between now day and receipt constructed day
     */
    private static long compareDateDays(Date receiptDate) {
        Date nowDate = new Date();
        long result = 1;

        result += (long) ((nowDate.getTime() - receiptDate.getTime()) / (1000 * 60 * 60 * 24) + 0.5);

        return result;
    }
}
