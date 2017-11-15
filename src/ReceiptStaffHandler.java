import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;

public class ReceiptStaffHandler {
    private static ArrayList<ReceiptStaff> receiptStaffList = new ArrayList<ReceiptStaff>();
    private static File receipts;
    private static int serialNumber = 0;

    /**
     * This method should be invoked at the beginning of program.
     * All of staff receipt-message in receipt-file should be imported into receiptStaffList.
     *
     * @param receiptFile The file contains staff receipt-message in resources folder
     */
    public static void initialize(String receiptFile) {
        try {
            receipts = new File("resources/" + receiptFile + ".txt");
            Scanner in = new Scanner(receipts);
            String message = "";//ReceiptStaff message in file.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (in.hasNext()) {
                message = in.nextLine();
                addReceipt(message);
            }

            in.close();

            if(!receiptStaffList.isEmpty()) {
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
        ReceiptStaff receiptStaff = new ReceiptStaff(subString[0]);

        for(int i = 1;i < subString.length;i += 2){
            receiptStaff.addProduct(subString[i], Integer.parseInt(subString[i+1]));
        }

        receiptStaffList.add(receiptStaff);
    }

    /**
     * Add a staff receipt to receiptStaffList.
     *
     * @param receiptStaff A receipt will be added to receiptStaffList
     */
    public static void addReceipt(ReceiptStaff receiptStaff) {
        receiptStaffList.add(receiptStaff);
    }

    /**
     * Update information from receiptStaffList to receipt-file in resources folder.
     */
    public static void freshReceiptFile(){
        try {
            PrintWriter printWriter = new PrintWriter(receipts);
            for(ReceiptStaff R : receiptStaffList){
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

    /**
     * This method allow user input a range of time(year-month-day) and check the sold products in this range
     */
    public static void checkRangeProducts(){
        LinkedHashMap<String, ProductSold> soldList = new LinkedHashMap<String, ProductSold>();
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
                    for(ReceiptStaff R : receiptStaffList){
                        if(R.getDate().compareTo(first) >= 0 && R.getDate().compareTo(second) <= 0){
                            LinkedHashMap<String, ProductSold> productList = R.getReceiptProductList();
                            for(Entry<String, ProductSold> entry : productList.entrySet()){
                                ProductSold P = entry.getValue();
                                if(soldList.containsKey(P.getId())){
                                    ProductSold inSoldList = soldList.get(P.getId());
                                    inSoldList.setNumber(inSoldList.getNumber() + P.getNumber());
                                }
                                else{
                                    soldList.put(P.getId(), new ProductSold(P.getId(), P.getNumber()));
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

    /**
     * Get a staff receipt in receiptStaffList by its ID
     *
     * @param receiptID The ID of receipt that was the unique ID of the receipt
     * @return A ReceiptStaff Object
     */
    public static ReceiptStaff getReceiptByID(String receiptID){
        ReceiptStaff result = null;
        for(ReceiptStaff R : receiptStaffList){
            if(receiptID.equals(R.getReceiptID())){
                result = R;
                break;
            }
        }

        return result;
    }

    /**
     * Get the local tax(China or Ireland)
     *
     * @return The local tax
     */
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

    /**
     * The method can get receipt's serial number, when a new staff receipt was constructed, it need it.
     *
     * @return The serialNumber now
     */
    public static int getSerialNumber() {
        return serialNumber;
    }

    /**
     * The method should be invoked to get next serial number. When a new receipt is added to receiptList,
     * also need this method to update serial number.
     */
    public static void nextSerialNumber() {
        serialNumber ++;
    }
}
