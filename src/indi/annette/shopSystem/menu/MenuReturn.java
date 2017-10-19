package indi.annette.shopSystem.menu;

import indi.annette.shopSystem.SystemAccount;
import indi.annette.shopSystem.purchase.GoodsReturnHandler;

public class MenuReturn implements Menu{
    @Override
    public Menu run() {
        Menu menuReturn = MenuHandler.menuMain;

        if(SystemAccount.isLogin()) {
            if(SystemAccount.getAuthority().equals("E")) {
                GoodsReturnHandler.EmployeeGoodsReturn();
            }
            else if(SystemAccount.getAuthority().equals("M")){
                GoodsReturnHandler.ManagerGoodsReturn();
            }
        }
        else{
            System.out.println("Please login first.");
        }
        return  menuReturn;
    }
}
