import java.io.*;
import java.util.*;

public class EmployeeHandler {
    private static ArrayList<Employee>shopPersonList = new ArrayList<Employee>();

    public static void initialize(String employeeFileName)throws IOException{
        File employees = new File("resources/" + employeeFileName +".txt");
        Scanner in = new Scanner(employees);
        String filesContestErrorMessage = "Employee-files contest Error";
        String filesFormatErrorMessage = "Employee-files format Error";

        while(in.hasNext()){
            String temp [] = in.nextLine().toUpperCase().split(",");
            if(temp.length == 2){
                if(temp[1].startsWith("E") || temp[1].startsWith("M")){
                    if(temp[1].startsWith("M")){
                        shopPersonList.add(new EmployeeManager(temp[0], temp[1]));
                    }
                    else if(temp[1].startsWith("E")){
                        shopPersonList.add(new EmployeeStaff(temp[0], temp[1]));
                    }
                }
                else{
                    System.out.print(filesContestErrorMessage);
                }
            }
            else{
                System.out.print(filesFormatErrorMessage);
            }
        }

        in.close();
    }

    public static Employee getEmployeeByID(String id) {
        id = id.toUpperCase();
        Employee returnPerson = null;
        for(Employee T : shopPersonList){
            if(T.getId().equals(id)){
                returnPerson = T;
                break;
            }
        }

        return returnPerson;
    }
}
