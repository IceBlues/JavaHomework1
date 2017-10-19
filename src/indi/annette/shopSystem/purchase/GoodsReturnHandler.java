package indi.annette.shopSystem.purchase;

import indi.annette.shopSystem.purchase.receipt.Receipt;
import indi.annette.shopSystem.purchase.receipt.ReceiptHandler;
import indi.annette.shopSystem.purchase.receipt.SoldGood;
import indi.annette.shopSystem.purchase.stock.StockGood;
import indi.annette.shopSystem.purchase.stock.StockHandler;
import indi.annette.shopSystem.purchase.supplier.SupplyGood;
import indi.annette.shopSystem.purchase.supplier.SupplyHandler;

import java.io.IOException;
import java.util.*;

public class GoodsReturnHandler {
    public static void EmployeeGoodsReturn(){
        System.out.println("Please input receipt ID(year-months-day hour:minutes:second number) :");
        Scanner in = new Scanner(System.in);

        String receiptID = in.nextLine();
        Receipt receipt = ReceiptHandler.getReceiptByID(receiptID);

        if(receipt != null){
            boolean isContinue = true;
            while(isContinue){
                System.out.println("Please input good-ID : ");
                String goodId = in.nextLine().toUpperCase();
                SoldGood soldGood = receipt.getGoodByID(goodId);

                if(soldGood != null)  {
                    int soldNumber = soldGood.getNumber();
                    int returnNumber = 0;
                    System.out.println(soldNumber + " had been sold");
                    System.out.println("How many you want to return?");

                    try {
                        returnNumber = Integer.parseInt(in.nextLine());
                        if (returnNumber <= 0 || returnNumber > soldNumber) {
                            System.out.println("Please input correct number");
                        }
                        else{
                            soldGood.setNumber(soldNumber - returnNumber);
                            StockHandler.freshStockByReturnGood(goodId, returnNumber);
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Error number");
                    }
                }
                else{
                    System.out.println("The good was not sold in this receipt");
                }

                System.out.println("C)ontinue  S)top");
                String continueSelect = in.nextLine().toUpperCase();
                switch (continueSelect) {
                    case "C":
                        isContinue = true;
                        break;
                    case "S":
                        isContinue = false;
                        break;
                    default:
                        System.out.println("Select Error. Default choose No");
                        isContinue = false;
                        break;
                }
            }

            StockHandler.freshStockFile();
            ReceiptHandler.freshReceiptFile();
            System.out.println("Return succeed, have a good fun");
        }
        else{
            System.out.println("Receipt not found");
        }
    }

    public static void ManagerGoodsReturn(){
        boolean isContinue = true;
        System.out.println("Welcome manager, Do you want to return goods to supplier?(Y/N)");
        Scanner in = new Scanner(System.in);
        String userInput = "";

        userInput = in.nextLine();
        if(userInput.equals("Y")){
            isContinue = true;
        }
        else if(userInput.equals("N")){
            isContinue = false;
        }
        else{
            System.out.println("Select Error");
            isContinue = false;
        }

        while(isContinue){
            System.out.println("Please input good-ID: ");
            String goodId = in.nextLine();

            StockGood aGood = StockHandler.getGoodByID(goodId);

            if(aGood != null){
                System.out.println("Name: " + aGood.getName());
                System.out.println("Price: " + aGood.getPrice());
                System.out.println("Please input return number: ");
                int returnNumber = 0;

                try {
                    returnNumber = Integer.parseInt(in.nextLine());
                    if(returnNumber > 0 && returnNumber <= aGood.getNumber()) {
                        aGood.setNumber(aGood.getNumber() - returnNumber);
                        SupplyHandler.addSupplyGood(goodId, returnNumber);
                    }
                    else{
                        System.out.println("Error number");
                    }
                }
                catch (Exception e){
                    System.out.println("Error number");
                }
            }
            else{
                System.out.println("Good not found");
            }

            boolean isSelect = false;
            while(!isSelect){
                System.out.println("C)ontinue  S)top");
                userInput = in.nextLine().toUpperCase();
                if(userInput.equals("C")){
                    isContinue = true;
                    isSelect = true;
                }
                else if(userInput.equals("S")){
                    isContinue = false;
                    isSelect = true;
                }
                else{
                    System.out.println("Please input correct selection");
                }
            }
        }

        StockHandler.freshStockFile();
        SupplyHandler.freshSupplierFile();
    }

    public static long compareDateDays(Date nowDate, Date receiptDate){
        long result;

        result = (long)((nowDate.getTime() - receiptDate.getTime()) / (1000 * 60 * 60 *24) + 0.5);

        return result;
    }
}
