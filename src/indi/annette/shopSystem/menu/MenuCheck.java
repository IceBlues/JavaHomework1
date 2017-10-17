package indi.annette.shopSystem.menu;

import indi.annette.shopSystem.SystemAccount;
import indi.annette.shopSystem.purchase.stock.StockHandler;

public class MenuCheck implements Menu{
    @Override
    public Menu run() {
        Menu menuReturn = MenuHandler.menuMain;
        if(SystemAccount.isLogin()) {
            if(SystemAccount.getAuthority().equals("M")) {
                String result;

                result = StockHandler.checkStock();
                System.out.println(result);
            }
            else{
                System.out.println("You have not enough authority.");
            }
        }
        else{
            System.out.println("Please login first.");
        }

        return menuReturn;
    }
}
