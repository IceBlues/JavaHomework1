package indi.annette.shopSystem.purchase;

import indi.annette.shopSystem.purchase.receipt.Receipt;
import indi.annette.shopSystem.purchase.receipt.ReceiptHandler;
import indi.annette.shopSystem.purchase.receipt.SoldGood;
import indi.annette.shopSystem.purchase.stock.StockHandler;
import java.util.*;

public class GoodsReturnHandler {
    public static void goodsReturn(){
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
                        System.out.println("Input format error");
                    }
                }
                else{
                    System.out.println("The good was not sold in this receipt.");
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
                        System.out.println("Select Error. Default choose No.");
                        isContinue = false;
                        break;
                }
            }

            StockHandler.freshStockFile();
            ReceiptHandler.freshReceiptFile();
            System.out.println("Return succeed, have a good fun.");
        }
        else{
            System.out.println("Receipt not found.");
        }
    }

    public static long compareDateDays(Date nowDate, Date receiptDate){
        long result;

        result = (long)((nowDate.getTime() - receiptDate.getTime()) / (1000 * 60 * 60 *24) + 0.5);

        return result;
    }
}
