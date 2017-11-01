import java.io.*;

public class SystemMain {
    public static void main(String[] args) {
        try {
            if (args.length == 4) {
                EmployeeHandler.initialize(args[0]);
                ReceiptHandler.initialize(args[1]);
                StockHandler.initialize(args[2]);
                SupplyHandler.initialize(args[3]);

                System.out.println("Welcome to Bug shop.");

                Menu.menuMain();

                ReceiptHandler.freshReceiptFile();
                StockHandler.freshStockFile();
            }
            else {
                System.out.print("Usage: java EmployeeStaff-file Receipts-file Stock-file Supplier-file");
            }
        }
        catch (IOException e){
            System.out.println("DataFiles Error.");
        }
    }
}