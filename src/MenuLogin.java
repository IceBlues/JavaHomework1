public class MenuLogin implements Menu {
    @Override
    public Menu run() {
        Menu menuReturn = MenuHandler.menuMain;

        SystemAccount.login();

        return menuReturn;
    }
}
