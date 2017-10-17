package indi.annette.shopSystem;

import indi.annette.shopSystem.menu.MenuHandler;
import indi.annette.shopSystem.purchase.receipt.ReceiptHandler;
import indi.annette.shopSystem.purchase.stock.StockHandler;
import indi.annette.shopSystem.user.ShopPersonHandler;
import java.io.*;

public class SystemMain {
        public static void main(String[] args) {
            try {
                if (args.length == 4) {
                    ShopPersonHandler.initialize(args[0]);
                    ReceiptHandler.initialize(args[1]);
                    StockHandler.initialize(args[2]);
                    MenuHandler.initialize();

                    System.out.println("Welcome to Bug shop.");

                    MenuHandler.start();

                    ReceiptHandler.freshReceiptFile();
                    StockHandler.freshStockFile();
                }
                else {
                    System.out.print("Usage: java Employee-file Receipts-file Stock-file Supplier-file");
                }
            }
            catch (IOException e){
                System.out.println("DataFiles Error.");
            }
        }
    }