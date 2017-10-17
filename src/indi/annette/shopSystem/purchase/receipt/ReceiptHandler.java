package indi.annette.shopSystem.purchase.receipt;

import indi.annette.shopSystem.purchase.GoodsReturnHandler;
import java.io.*;
import java.text.*;
import java.util.*;

public class ReceiptHandler {
    private static ArrayList<Receipt> receiptList = new ArrayList<Receipt>();
    private static File receipts;
    private static int serialNumber = 0;
    public static void initialize(String receiptFile) {
        try {
            receipts = new File("src/resources/" + receiptFile + ".txt");
            Scanner in = new Scanner(receipts);
            String message = "";//Receipt message in file.
            Date nowDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            boolean shouldInitial = false;

            while (in.hasNext()) {
                message = in.nextLine();
                String receiptID[] = message.split(",");
                String dateMessage[] = receiptID[0].split(" ");
                try {
                    if (!shouldInitial && GoodsReturnHandler.compareDateDays(nowDate, dateFormat.parse(dateMessage[0])) <= 30) {
                        shouldInitial = true;
                    }
                    if (shouldInitial) {
                        addReceipt(message);
                    }
                }
                catch (ParseException p){
                    System.out.println("Parse Error.");
                }
            }

            in.close();
            freshReceiptFile(); //Delete 30days later message.

            if(!receiptList.isEmpty()) {
                String receiptID[] = message.split(",");
                String dateMessage[] = receiptID[0].split(" ");
                serialNumber = Integer.parseInt(dateMessage[2]);
            }
        }
        catch (IOException e){
            System.out.println("receiptFile not found.");
        }
    }

    /*add receipt from file*/
    private static void addReceipt(String message){
        String subString[] = message.split(",");
        Receipt receipt = new Receipt(subString[0]);

        for(int i = 1;i < subString.length;i += 2){
            receipt.addGood(subString[i], Integer.parseInt(subString[i+1]));
        }

        receiptList.add(receipt);
    }

    static void addReceipt(Receipt receipt) {
        receiptList.add(receipt);
    }

    static void addReceipt(String receiptID, ArrayList<SoldGood> list){
        receiptList.add(new Receipt(receiptID, list));
    }

     /*Simple fresh receipt-file*/
    public static void freshReceiptFile(){
        try {
            PrintWriter printWriter = new PrintWriter(receipts);
            for(Receipt R : receiptList){
                String result = "";
                result += R.getReceiptID();
                ArrayList<SoldGood> list = R.getReceiptGoodList();
                for(SoldGood S : list){
                    result += "," + S.getId() + "," + S.getNumber();
                }
                printWriter.println(result);
            }

            printWriter.close();
        }
        catch (IOException e){
            System.out.println("receiptFile not found.");
        }
    }

    public static Receipt getReceiptByID(String receiptID){
        Receipt result = null;
        for(Receipt R : receiptList){
            if(receiptID.equals(R.getReceiptID())){
                result = R;
                break;
            }
        }

        return result;
    }

    static double getLocalTax(){
        double taxReturn = 0;
        Locale nowLocale = Locale.getDefault();
        if(nowLocale.getCountry().equals("IE")){
            taxReturn = 0.1;
        }
        else if(nowLocale.getCountry().equals("CN")){
            taxReturn = 0;
        }
        return taxReturn;
    }

    static int getSerialNumber() {
        return serialNumber;
    }

    static void nextSerialNumber() {
        serialNumber ++;
    }
}
