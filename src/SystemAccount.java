import java.util.*;

public class SystemAccount {
    private static boolean isLogin = false;
    private static String id;
    private static String name;
    private static String authority;

    public static void login(){
        boolean isContinueLogin = true;

        while(isContinueLogin) {
            if (!isLogin) {
                System.out.println("Please input Work-ID :");
                Scanner in = new Scanner(System.in);
                String userInput = in.nextLine();
                Employee nowPerson = EmployeeHandler.getEmployeeByID(userInput);

                //Login Successful.
                if (nowPerson != null) {
                    id = nowPerson.getId();
                    name = nowPerson.getName();
                    authority = id.substring(0, 1);
                    isLogin = true;
                    isContinueLogin = false;

                    System.out.println("Welcome " + name);
                }
                else {
                    System.out.println("Login Error, Do you want to try again?(Y/N)");
                    userInput = in.nextLine().toUpperCase();
                    switch (userInput) {
                        case "Y":
                            isContinueLogin = true;
                            break;
                        case "N":
                            isContinueLogin = false;
                            break;
                        default:
                            System.out.println("Select Error. Please try again");
                            isContinueLogin = true;
                            break;
                    }
                }
            }
            else{
                System.out.println("You have been login.");
                isContinueLogin = false;
            }
        }

    }

    public static void logout(){
        if(isLogin){
            isLogin = false;
            System.out.println("Bye " + name);
        }
        else{
            System.out.println("Please login first.");
        }

    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static String getAuthority() {
        return authority;
    }
}
