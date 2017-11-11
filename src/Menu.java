import java.util.Scanner;

public class Menu {
    private static Scanner in = new Scanner(System.in);

    public static void menuMain() {
        boolean isContinue = true;
        while (isContinue) {
            if (!StoreSystemAccount.isLogin()) {
                isContinue = menuNotLogin();
            }
            else {
                menuIsLogin();
            }
        }
    }

    private static boolean menuNotLogin() {
        String selectErrorMessage = "Select Error";

        boolean isContinue = true;
        System.out.println("Log I)n E)xit");
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine().toUpperCase();
        switch (userInput) {
            case "I":
                menuLogin();
                break;
            case "E":
                isContinue = false;
                break;
            default:
                System.out.print(selectErrorMessage);
                break;
        }
        return isContinue;
    }

    private static void menuIsLogin() {
        String selectErrorMessage = "Select Error";

        if (StoreSystemAccount.getAuthority().equals("E")) {
            System.out.println("P)urchase  R)eturn    Log O)ut");
            Scanner in = new Scanner(System.in);

            String userInput = in.nextLine().toUpperCase();
            switch (userInput) {
                case "P":
                    menuPurchase();
                    break;
                case "R":
                    menuReturn();
                    break;
                case "O":
                    StoreSystemAccount.logout();
                    break;
                default:
                    System.out.print(selectErrorMessage);
                    break;
            }
        }
        else {
            System.out.println("P)urchase  C)heck  R)eturn    Log O)ut");
            Scanner in = new Scanner(System.in);

            String userInput = in.nextLine().toUpperCase();
            switch (userInput) {
                case "P":
                    menuPurchase();
                    break;
                case "R":
                    menuReturn();
                    break;
                case "C":
                    menuCheck();
                    break;
                case "O":
                    StoreSystemAccount.logout();
                    break;
                default:
                    System.out.print(selectErrorMessage);
                    break;
            }
        }
    }

    private static void menuLogin() {
        StoreSystemAccount.login();
    }

    private static void menuPurchase() {
        if(StoreSystemAccount.getAuthority().equals("E")) {
            new ReceiptStaff().listReceipt();
        }
        else if(StoreSystemAccount.getAuthority().equals("M")){
            new ReceiptManager().listReceipt();
        }
    }

    private static void menuCheck() {
        String selectErrorMessage = "Select Error";
        String checkSelect;
        boolean isContinue = true;

        while(isContinue) {
            System.out.println("S)tock  P)roduct  R)ange Sold Products  E)xit");
            checkSelect = in.nextLine().toUpperCase();

            switch (checkSelect) {
                case "S":
                    StockHandler.checkStock();
                    break;
                case "P":
                    StockHandler.checkStockProduct();
                    break;
                case "R":
                    ReceiptStaffHandler.checkRangeProducts();
                    break;
                case "E":
                    isContinue = false;
                    break;
                default:
                    System.out.print(selectErrorMessage);
                    break;
            }
        }
    }

    private static void menuReturn() {
        if (StoreSystemAccount.getAuthority().equals("E")) {
            ReturnProductsHandler.EmployeeProductsReturn();
        }
        else if (StoreSystemAccount.getAuthority().equals("M")) {
            ReturnProductsHandler.ManagerProductsReturn();
        }
    }

}
