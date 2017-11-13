import java.io.*;
import java.util.*;

public class EmployeeHandler {
    private static ArrayList<Employee>shopPersonList = new ArrayList<Employee>();

    /**
     * This method should be invoked at the beginning of program.
     * All of employee-message in employee-file should be imported into shopPersonList.
     *
     * @param employeeFileName The file contains employee-message in resources folder
     * @throws IOException Not found employee-file
     */
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

    /**
     *Get an Employee Object in shopPersonList by its workID.
     *
     * @param id An employee's workID
     * @return An Employee Object
     */
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
