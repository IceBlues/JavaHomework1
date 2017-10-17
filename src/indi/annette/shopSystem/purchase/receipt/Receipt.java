package indi.annette.shopSystem.purchase.receipt;

import indi.annette.shopSystem.SystemAccount;
import indi.annette.shopSystem.purchase.stock.StockGood;
import indi.annette.shopSystem.purchase.stock.StockHandler;
import java.text.SimpleDateFormat;
import java.util.*;

public class Receipt {
    private ArrayList<SoldGood> receiptGoodList = new ArrayList<SoldGood>();
    private String receiptID;

    public Receipt(){

    }

    Receipt(String receiptID) {
        this.receiptID = receiptID;
    }

    Receipt(String receiptID, ArrayList<SoldGood> receiptGoodList) {
        this.receiptGoodList = receiptGoodList;
        this.receiptID = receiptID;
    }

    public void listReceipt() {
        if(SystemAccount.isLogin()) {
            boolean isContinuePurchase = true;
            Scanner in = new Scanner(System.in);
            String userInput;
            while (isContinuePurchase) {
                addGood();

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
                            System.out.println("Please Input Correct selection.");
                    }
                }
            }

            complete();
        }
        else{
            System.out.println("Please login first.");
        }
    }

    void addGood(String id, int number){
        receiptGoodList.add(new SoldGood(id,number));
    }

    private void addGood(){
            StockGood aGood = null;
            String aGoodID = null;
            Scanner in = new Scanner(System.in);

            System.out.println("Please input good-ID :");
            aGoodID = in.nextLine().toUpperCase();
            aGood = StockHandler.getGoodByID(aGoodID);

            if(aGood != null) {
                System.out.println("Current Stock: " + aGood.getNumber());
                System.out.println("Price: " + aGood.getPrice());
                try {
                    int number = 0;
                    boolean isNotFound = true;
                    for(SoldGood S : receiptGoodList) {
                        if (S.getId().equals(aGoodID)) {
                            System.out.println("What's number you want to change now?");
                            number = Integer.parseInt(in.nextLine());
                            if (number > 0 && number <= aGood.getNumber()) {
                                S.setNumber(number);
                                System.out.println("Good number change succeed");
                            }
                            else{
                                System.out.println("The StockNumber is not enough");
                            }

                            isNotFound = false;
                            break;
                        }
                    }
                    if(isNotFound){
                        System.out.println("How many you want to buy?");
                        number = Integer.parseInt(in.nextLine());
                        if (number > 0 && number <= aGood.getNumber()) {
                            receiptGoodList.add(new SoldGood(aGood.getName(), aGoodID, number, aGood.getPrice()));
                            System.out.println("Good add succeed");
                        }
                        else {
                            System.out.println("The StockNumber is not enough");
                        }
                    }

                }
                catch (Exception e) {
                    System.out.println("Number-format Error, please input positive integer");
                }
            }
            else {
                System.out.println("The purchase not found");
            }
    }

    private void complete(){
        double total = 0;
        String userInput = "";
        Scanner in = new Scanner(System.in);
        System.out.println("Transaction Details");
        for(SoldGood G : receiptGoodList){
            double aGoodTotal = G.getPrice()*G.getNumber();
            System.out.println(G.getName() + "\t\t\t" + G.getNumber() + "\t\t" + aGoodTotal);
            total += aGoodTotal;
        }
        System.out.println("Subtotal\t\t\t" + total);

        double localTax = ReceiptHandler.getLocalTax();
        System.out.println("Tax(" + localTax*100 + "%)" + "\t\t" + total*localTax);
        total += total*localTax;

        System.out.println("Total" + "\t\t\t\t" + total);
        System.out.println();

        receiptID = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()) + (ReceiptHandler.getSerialNumber() + 1);
        ReceiptHandler.nextSerialNumber();

        System.out.println(receiptID);
        System.out.println("C)ontinue  S)top");
        boolean isNotCorrectChoose = true;
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
                    System.out.println("Please Input Correct selection");
            }
        }

       if(userInput.equals("C")){
            //Add this receipt to receipt-list
            ReceiptHandler.addReceipt(new Receipt(receiptID, receiptGoodList));
            //update stock-list
            for(SoldGood S : receiptGoodList) {
                StockHandler.freshStockWithSoldGood(S);
            }

            StockHandler.freshStockFile();
            ReceiptHandler.freshReceiptFile();
            System.out.println("Thank you for your purchase. Your receipt has been printed");
        }
    }

    public SoldGood getGoodByID(String goodId){
        SoldGood result = null;

        for(SoldGood S : receiptGoodList){
            if(goodId.equals(S.getId())){
                result = S;
                break;
            }
        }

        return result;
    }

    String getReceiptID() {
        return receiptID;
    }

    ArrayList<SoldGood> getReceiptGoodList() {
        return receiptGoodList;
    }
}
