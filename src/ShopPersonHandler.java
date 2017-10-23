import java.io.*;
import java.util.*;

public class ShopPersonHandler{
    private static ArrayList<ShopPerson>shopPersonList = new ArrayList<ShopPerson>();

    public static void initialize(String employeeFileName)throws IOException{
        File employees = new File("resources/" + employeeFileName +".txt");
        Scanner in = new Scanner(employees);
        while(in.hasNext()){
            String temp [] = in.nextLine().toUpperCase().split(",");
            if(temp.length == 2){
                if(temp[1].startsWith("E") || temp[1].startsWith("M")){
                    if(temp[1].startsWith("M")){
                        shopPersonList.add(new Manager(temp[0], temp[1]));
                    }
                    else if(temp[1].startsWith("E")){
                        shopPersonList.add(new Employee(temp[0], temp[1]));
                    }
                }
                else{
                    System.out.print("Employee-files contest Error.");
                }
            }
            else{
                System.out.print("Employee-files format Error.");
            }
        }

        in.close();
    }

    public static ShopPerson getShopPersonByID(String id) {
        id = id.toUpperCase();
        ShopPerson returnPerson = null;
        for(ShopPerson T : shopPersonList){
            if(T.getId().equals(id)){
                returnPerson = T;
                break;
            }
        }

        return returnPerson;
    }
}
