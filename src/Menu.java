import java.util.Scanner;

public class Menu {
    private static String notLoginMessage = "Please login first";

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
        if (StoreSystemAccount.isLogin()) {
            StockHandler.checkStock();
        }
        else {
            System.out.println(notLoginMessage);
        }
    }

    private static void menuReturn() {
        if (StoreSystemAccount.isLogin()) {
            ReturnProductsHandler.ProductsReturn();
        }
        else {
            System.out.println(notLoginMessage);
        }
    }
}
