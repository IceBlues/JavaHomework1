import java.io.*;

public class StoreManagerSystem {

    /**
     *The program entrance. At beginning of program, load resources files and
     * turn to main menu. At end of program, make true the resources files was fresh.
     *
     * @param args The resources files name : Employee-file StaffReceipt-file Stock-file and Supplier-file
     */
    public static void main(String[] args) {
        try {
            if (args.length == 4) {
                EmployeeHandler.initialize(args[0]);
                ReceiptStaffHandler.initialize(args[1]);
                StockHandler.initialize(args[2]);
                SupplyHandler.initialize(args[3]);

                System.out.println("Welcome to Bless-No-Bug sports shop");

                Menu.menuMain();

                ReceiptStaffHandler.freshReceiptFile();
                StockHandler.freshStockFile();
                SupplyHandler.freshSupplierFile();
            }
            else {
                System.out.print("Usage: java EmployeeStaff-file Receipts-file Stock-file Supplier-file");
            }
        }
        catch (IOException e){
            System.out.println("DataFiles Error");
        }
    }
}