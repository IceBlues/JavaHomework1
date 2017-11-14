import java.util.Scanner;

public class Menu {
    private static Scanner in = new Scanner(System.in);

    /**
     * This method should be invoked at the beginning of program.
     * The method will be invoked when all resources-files was loaded. It will judge user statement(login or not)
     * and invoke suitable method to show different main-menu which make true the user can do command only they
     * have enough authority to user.
     */
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

    /*
     * The method will be invoked by menuMain method, when the user not login.
     *
     * @return A boolean variable that control the main-menu show continue or not if it's true
     * the menuMain will invoke suitable main-menu again or it'll be end.
     */
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
                System.out.println(selectErrorMessage);
                break;
        }
        return isContinue;
    }

    /*
     *  The method will be invoked by menuMain method, when the user has been login.
     */
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
                    System.out.println(selectErrorMessage);
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
                    System.out.println(selectErrorMessage);
                    break;
            }
        }
    }

    /*
     *The method will be invoked when user want to login. It'll invoke suitable method
     * in StoreSystemAccount class.
     */
    private static void menuLogin() {
        StoreSystemAccount.login();
    }

    /*
     * This method will be invoked when user want to purchase. For different employee(staff or manager)
     * It'll create suitable receipt(Staff or Manager) and perform purchase operation.
     */
    private static void menuPurchase() {
        if(StoreSystemAccount.getAuthority().equals("E")) {
            new ReceiptStaff().listReceipt();
        }
        else if(StoreSystemAccount.getAuthority().equals("M")){
            new ReceiptManager().listReceipt();
        }
    }

    /*
     * The method will be invoked when user want to check stock, product or products sold for
     * a certain period of time.
     */
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
                    System.out.println(selectErrorMessage);
                    break;
            }
        }
    }

    /*
     * The method will be invoked when user want to return products, for staff and manager they'll be invoked
     * suitable return method.
     */
    private static void menuReturn() {
        if (StoreSystemAccount.getAuthority().equals("E")) {
            ReturnProductsHandler.StaffProductsReturn();
        }
        else if (StoreSystemAccount.getAuthority().equals("M")) {
            ReturnProductsHandler.ManagerProductsReturn();
        }
    }

}
