import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;

public class ReceiptHandler {
    private static ArrayList<Receipt> receiptList = new ArrayList<Receipt>();
    private static File receipts;
    private static int serialNumber = 0;
    public static void initialize(String receiptFile) {
        try {
            receipts = new File("resources/" + receiptFile + ".txt");
            Scanner in = new Scanner(receipts);
            String message = "";//Receipt message in file.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (in.hasNext()) {
                message = in.nextLine();
                addReceipt(message);
            }

            in.close();

            if(!receiptList.isEmpty()) {
                String receiptID[] = message.split(",");
                String dateMessage[] = receiptID[0].split(" ");
                    if (dateFormat.format(new Date()).equals(dateMessage[0])) {
                        serialNumber = Integer.parseInt(dateMessage[2]) + 1;
                    }
                    else {
                        serialNumber = 1;
                    }
            }
        }
        catch (IOException e){
            System.out.println("receiptFile not found");
        }
    }

    /*add receipt from file*/
    private static void addReceipt(String message){
        String subString[] = message.split(",");
        Receipt receipt = new Receipt(subString[0]);

        for(int i = 1;i < subString.length;i += 2){
            receipt.addProduct(subString[i], Integer.parseInt(subString[i+1]));
        }

        receiptList.add(receipt);
    }

    public static void addReceipt(Receipt receipt) {
        receiptList.add(receipt);
    }

     /*Simple fresh receipt-file*/
    public static void freshReceiptFile(){
        try {
            PrintWriter printWriter = new PrintWriter(receipts);
            for(Receipt R : receiptList){
                String result = "";
                result += R.getReceiptID();
                for(Entry<String, ProductSold> entry : R.getReceiptProductList().entrySet()){
                    ProductSold S = entry.getValue();
                    result += "," + S.getId() + "," + S.getNumber();
                }
                printWriter.println(result);
            }

            printWriter.close();
        }
        catch (IOException e){
            System.out.println("receiptFile not found");
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

    public static double getLocalTax(){
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

    public static int getSerialNumber() {
        return serialNumber;
    }

    public static void nextSerialNumber() {
        serialNumber ++;
    }
}
