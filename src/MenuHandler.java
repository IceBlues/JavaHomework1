import java.util.*;

public class MenuHandler {
    static Menu menuMain = new MenuMain();
    static Menu menuLogin = new MenuLogin();
    static Menu menuLogout = new MenuLogout();
    static Menu menuPurchase = new MenuPurchase();
    static Menu menuCheck = new MenuCheck();
    static Menu menuReturn = new MenuReturn();
    static HashMap<String, Menu> userMenuList = new HashMap<String, Menu>();

    public static void initialize(){
        userMenuList.put("I", menuLogin);
        userMenuList.put("O", menuLogout);
        userMenuList.put("P", menuPurchase);
        userMenuList.put("R", menuReturn);
        userMenuList.put("C", menuCheck);
    }

    public static void start(){
        Menu nowMenu = new MenuMain();
        while(nowMenu != null){
            nowMenu = nowMenu.run();
        }
    }
}
