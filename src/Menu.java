import java.util.Scanner;

public class Menu {
    private static String notLoginMessage = "Please login first";
    private static Scanner in = new Scanner(System.in);

    public static void menuMain() {
        boolean isContinue = true;
        while (isContinue) {
            System.out.println("Log I)n  Log O)ut  P)urchase  R)eturn  C)heck Stock  E)xit");
            Scanner in = new Scanner(System.in);

            String userInput = in.nextLine().toUpperCase();
            if (userInput.equals("E")) {
                isContinue = false;
            }
            else {
                switch (userInput) {
                    case "I":
                        menuLogin();
                        break;
                    case "O":
                        menuLogout();
                        break;
                    case "P":
                        menuPurchase();
                        break;
                    case "R":
                        menuReturn();
                        break;
                    case "C":
                        menuCheck();
                        break;
                }
            }
        }
    }

    private static void menuLogin() {
        StoreSystemAccount.login();
    }

    private static void menuLogout() {
        StoreSystemAccount.logout();
    }

    private static void menuPurchase() {
        if (StoreSystemAccount.isLogin()) {
            new Receipt();
        }
        else {
            System.out.println(notLoginMessage);
        }
    }

    private static void menuCheck() {
        String authorityNotEnoughMessage = "You have not enough authority";
        String selectErrorMessage = "Select Error";
        if (StoreSystemAccount.isLogin()) {
            if (StoreSystemAccount.getAuthority().equals("M")) {
                System.out.println("S)tock  P)roduct  R)ange Sold Products  E)xit");

                String checkSelect = in.nextLine().toUpperCase();

                switch (checkSelect) {
                    case "S":
                        StockHandler.checkStock();
                        break;
                    case "P":
                        StockHandler.checkStockProduct();
                        break;
                    case "R":
                        ReceiptHandler.checkRangeProducts();
                        break;
                    case "E":
                        break;
                    default:
                        System.out.print(selectErrorMessage);
                        break;
                }
            }
            else {
                System.out.println(authorityNotEnoughMessage);
            }
        }
        else {
            System.out.println(notLoginMessage);
        }
    }

    private static void menuReturn() {
        if (StoreSystemAccount.isLogin()) {
            if (StoreSystemAccount.getAuthority().equals("E")) {
                ReturnProductsHandler.EmployeeProductsReturn();
            }
            else if (StoreSystemAccount.getAuthority().equals("M")) {
                String managerSelection = "";
                System.out.println("Do you want to return product to Supplier/Customer(S/C)");
                managerSelection = in.nextLine().toUpperCase();

                switch (managerSelection) {
                    case "S":
                        ReturnProductsHandler.ManagerProductsReturn();
                        break;
                    case "C":
                        ReturnProductsHandler.EmployeeProductsReturn();
                        break;
                    default:
                        System.out.println("Select Error");
                        break;
                }
            }
        }
        else {
            System.out.print(notLoginMessage);
        }
    }

}
