package indi.annette.shopSystem.menu;

import indi.annette.shopSystem.purchase.receipt.Receipt;

public class MenuPurchase implements Menu {
    @Override
    public Menu run(){
        Menu menuReturn = MenuHandler.menuMain;

        Receipt receipt = new Receipt();
        receipt.listReceipt();

        return menuReturn;
    }
}
