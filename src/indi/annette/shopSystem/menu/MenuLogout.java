package indi.annette.shopSystem.menu;

import indi.annette.shopSystem.SystemAccount;

public class MenuLogout implements Menu {
    @Override
    public Menu run() {
        Menu menuReturn = MenuHandler.menuMain;

        SystemAccount.logout();

        return menuReturn;
    }
}
