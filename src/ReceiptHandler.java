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

    public static void checkRangeProducts(){
        HashMap<String, ProductSold> soldList = new HashMap<String, ProductSold>();
        Scanner in = new Scanner(System.in);
        String dateRangeErrorMessage = "Please input correct date range";
        String dateFormatErrorMessage = "Error date format, it should like 1990-01-01";
        StringBuilder result = new StringBuilder("");
        String firstDate;
        String secondDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

        System.out.println("Please input start date like year(YYYY)-month(MM)-day(DD):");
        firstDate = in.nextLine();
        System.out.println("Please input end date like year(YYYY)-month(MM)-day(DD):");
        secondDate = in.nextLine();
        if(firstDate.matches(format) && secondDate.matches(format)){
            try {
                Date first = dateFormat.parse(firstDate);
                Date second = dateFormat.parse(secondDate);
                if(first.compareTo(second) <= 0){
                    for(Receipt R : receiptList){
                        if(R.getDate().compareTo(first) >= 0 && R.getDate().compareTo(second) <= 0){
                            HashMap<String, ProductSold> productList = R.getReceiptProductList();
                            for(Entry<String, ProductSold> entry : productList.entrySet()){
                                ProductSold P = entry.getValue();
                                if(soldList.containsKey(P.getId())){
                                    ProductSold inSoldList = soldList.get(P.getId());
                                    inSoldList.setNumber(inSoldList.getNumber() + P.getNumber());
                                }
                                else{
                                    soldList.put(P.getId(), P);
                                }
                            }
                        }
                    }

                    for(Entry<String, ProductSold> entry : soldList.entrySet()){
                        result.append("ProductID: ");
                        result.append(entry.getKey());
                        result.append("\n");
                        result.append("ProductSoldNumber: ");
                        result.append(entry.getValue().getNumber());
                        result.append("\n");
                    }

                    System.out.println(result.toString());
                }
                else{
                    System.out.println(dateRangeErrorMessage);
                }
            }
            catch (ParseException P){
                System.out.println(dateFormatErrorMessage);
            }
        }
        else{
            System.out.println(dateFormatErrorMessage);
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
