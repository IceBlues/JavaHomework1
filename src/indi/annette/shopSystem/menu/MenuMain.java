package indi.annette.shopSystem.menu;

import java.util.*;

public class MenuMain implements Menu {
    @Override
    public Menu run() {
        Menu menuReturn = null;
        System.out.println("Log I)n  Log O)ut  P)urchase  R)eturn C)heck Stock");
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine().toUpperCase();
        menuReturn = MenuHandler.userMenuList.get(userInput);

        if(menuReturn == null){
            System.out.println("Please input correct selection.");
            menuReturn = MenuHandler.menuMain;
        }

        return menuReturn;
    }
}
