import java.util.Scanner;

public class MenuReturn implements Menu {
    @Override
    public Menu run() {
        Menu menuReturn = MenuHandler.menuMain;

        if(SystemAccount.isLogin()) {
            if(SystemAccount.getAuthority().equals("E")) {
                GoodsReturnHandler.EmployeeGoodsReturn();
            }
            else if(SystemAccount.getAuthority().equals("M")){
                String managerSelection = "";
                Scanner in = new Scanner(System.in);
                System.out.println("Do you want to return good to Supplier/Customer(S/C)");
                managerSelection = in.nextLine().toUpperCase();

                switch (managerSelection) {
                    case "S": GoodsReturnHandler.ManagerGoodsReturn(); break;
                    case "C": GoodsReturnHandler.EmployeeGoodsReturn(); break;
                    default: System.out.println("Select Error"); break;
                }
            }
        }
        else{
            System.out.println("Please login first.");
        }
        return  menuReturn;
    }
}
