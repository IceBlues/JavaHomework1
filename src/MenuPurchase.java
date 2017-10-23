public class MenuPurchase implements Menu {
    @Override
    public Menu run(){
        Menu menuReturn = MenuHandler.menuMain;

        Receipt receipt = new Receipt();
        receipt.listReceipt();

        return menuReturn;
    }
}
