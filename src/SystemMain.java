import java.io.*;

public class SystemMain {
    public static void main(String[] args) {
        try {
            if (args.length == 4) {
                ShopPersonHandler.initialize(args[0]);
                ReceiptHandler.initialize(args[1]);
                StockHandler.initialize(args[2]);
                SupplyHandler.initialize(args[3]);
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