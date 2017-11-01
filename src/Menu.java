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
        SystemAccount.login();
    }

    private static void menuLogout() {
        SystemAccount.logout();
    }

    private static void menuPurchase() {
        if (SystemAccount.isLogin()) {
            new Receipt();
        }
        else {
            System.out.println(notLoginMessage);
        }
    }

    private static void menuCheck() {
        if (SystemAccount.isLogin()) {
            StockHandler.checkStock();
        }
        else {
            System.out.println(notLoginMessage);
        }
    }

    private static void menuReturn() {
        if (SystemAccount.isLogin()) {
            ReturnProductsHandler.ProductsReturn();
        }
        else {
            System.out.println(notLoginMessage);
        }
    }
}
