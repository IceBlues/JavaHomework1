package indi.annette.shopSystem.menu;

import indi.annette.shopSystem.SystemAccount;

public class MenuLogin implements Menu {
    @Override
    public Menu run() {
        Menu menuReturn = MenuHandler.menuMain;

        SystemAccount.login();

        return menuReturn;
    }
}
