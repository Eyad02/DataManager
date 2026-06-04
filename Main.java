import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    private Manager manager = new Manager();
    private int currentMonth;
    private int currentDay;
    private int currentYear;
   
   
    public static void main(String[] args)
    {  
        Main app = new Main();
        Scanner scan = new Scanner(System.in);
       
       
       
        //Temp account
         Account avery = new Account("Costco", "averyl", "avery@gmail.com", "Av3ry_");
         //Password testing
         String myPassword = avery.generateStrongPassword();
         System.out.println(myPassword);
         
        System.out.println(avery.accountInfo());
        if (avery.checkStrength(avery.getPassword())){
            System.out.println(avery.getPassword());
        } else {
            System.out.println(avery.generateStrongPassword());
        }
       
       
       
        System.out.println("=-=-=-=-=-=-= Welcome =-=-=-=-=-=-=");
       
        //Sets the current date
        app.updateDate(scan);
       
        System.out.println("Please select option: \n" +
        "1\tAccount Menu\n" + "2\tSubscription Menu\n" + "3\tUpdate Date\n");
        int input = scan.nextInt();
        scan.nextLine();
       
        while (input != 1 && input != 2 && input != 3){
            System.out.println("INVALID option. Try again!");
            input = scan.nextInt();
        }
        if (input == 1){
            app.accountMenu(scan);
        } else if (input == 2){
            //app.subscriptionMenu(scan);
        } else if (input == 3){
            app.updateDate(scan);
        }
       
    }
   
    public void accountMenu(Scanner scan){
        boolean running = true;
       
        while(running){
            System.out.println("\n-----------Account Menu-----------\n" +
            "What would you like to do?\n");
            System.out.println("1 Add New Account");
            System.out.println("2 Search, Update, or Delete Accounts");
            System.out.println("3 List Dashboard Reports");
            System.out.println("4 Return to Main Menu");
           
            int input = scan.nextInt();
            scan.nextLine();
            while (input != 1 && input != 2 && input != 3 && input != 4){
                System.out.println("INVALID option. Try again!");
                input = scan.nextInt();
            }
            if (input == 1){
                System.out.println("\nNew Account");
                System.out.println("Enter Service: ");
                String newService = scan.nextLine();
                System.out.println("Enter Username: ");
                String newUser = scan.nextLine();
                System.out.println("Enter Email or Phone: ");
                String newEmail = scan.nextLine();
                System.out.println("Enter Password: ");
                String newPass = scan.nextLine();
               
                if(!manager.checkExistingAccount(newService, newEmail)){
                    Account newAccount = new Account(newService, newUser, newEmail, newPass);
                    manager.addAccount(newAccount);
                    System.out.println("Account successfully added! Hello, " + newUser + "!");
                } else {
                    System.out.println("Account with that email already exists!");
                }
                 
            } else if (input == 2){
                System.out.println("\n------------------");
                searchUpdateDeleteMenu(scan);
            } else if (input == 3){
                //Implement
            } else if (input == 4){
                running = false;
            }
        }
    }
   
    public void searchUpdateDeleteMenu(Scanner scan){
        boolean running = true;
       
        while(running){
            System.out.println("\nSelect an option");
            System.out.println("1 List Accounts by Email/Phone");
            System.out.println("2 List Accounts by Service");
            System.out.println("3 Go Back");
           
            int input = scan.nextInt();
            scan.nextLine();
            while (input != 1 && input != 2 && input != 3){
                System.out.println("INVALID option. Try again!");
                input = scan.nextInt();
                scan.nextLine();
            }
            if (input == 1){
               
                //Lists accounts by input email/phone
                System.out.println("Please enter email or phone");
                String emailInput = scan.nextLine();
                ArrayList<Account> availableAccounts = manager.listAccountsByEmail(emailInput);
                if (availableAccounts.size() == 0){
                    System.out.println("No accounts found with that email/phone!");
                } else {
                    for (int i = 0; i < availableAccounts.size(); i++){
                        System.out.println((i+1) + "\t" + availableAccounts.get(i).getServiceName());
                    }
                    System.out.println((availableAccounts.size()) + "\tBack to Account Menu");
                    System.out.println("Select service");
                    input = scan.nextInt();
                    scan.nextLine();
                    if (input > availableAccounts.size()){
                        running = false;
                    } else {
                        viewAccountInfo(scan, availableAccounts.get(input-1));
                    }
                }
               
            } else if (input == 2){
               
                //Lists accounts by service name
                System.out.println("Please enter name of service");
                String serviceInput = scan.nextLine();
                //manager.listAccountsByService(emailInput);
                ArrayList<Account> availableAccounts = manager.listAccountsByService(serviceInput);
                if (availableAccounts.size() == 0){
                    System.out.println("No accounts found with that service!");
                } else {
                    for (int i = 0; i < availableAccounts.size(); i++){
                        System.out.println((i+1) + "\t" + availableAccounts.get(i).getEmailOrPhone());
                    }
                    System.out.println((availableAccounts.size() + 1) + "\tBack to Account Menu");
                    System.out.println("Select email/phone");
                    input = scan.nextInt();
                    scan.nextLine();
                    if (input > availableAccounts.size()){
                        running = false;
                    } else {
                        viewAccountInfo(scan, availableAccounts.get(input-1));
                    }
                }
               
            } else if (input == 3){
                running = false;
            }
        }
    }
   
    public void viewAccountInfo(Scanner scan, Account selectedAccount){
        System.out.println("\n============= Viewing " + selectedAccount.getUsername() + "'s Info =============");
        System.out.println("Service: " + selectedAccount.getServiceName());
        System.out.println("Username: " + selectedAccount.getUsername());
        System.out.println("Email/Phone: " + selectedAccount.getEmailOrPhone());
        System.out.println("Password: " + selectedAccount.getPassword());
        System.out.println("\nWhat would you like to do?" +
        "\n1\tChange Service" + "\n2\tChange Username" + "\n3\tChange Email" +
        "\n4\tChange Password" + "\n5\tGenerate Strong Password" + "\n6\tDelete Account" + "\n7\tGo Back");
       
       
       
    }
   
    public void subscriptionMenu(Scanner scan){
        //Not implemented
    }
   
    public boolean isValidDate(int month, int day, int year) {
        int maxDays = 31;
       
        if (month == 4 || month == 6 || month == 9 || month == 11){
            maxDays = 30;
        } else if (month == 2){
            if ((2000 + year) % 4 == 0) {
                maxDays = 29;
            } else {
                maxDays = 28;
            }
        }
        return (month >= 1 && month <= 12 && day >= 1 && day <= maxDays &&
        year >= 1000 && year <= 9999);
    }
   
    /**
    * Takes input of any M(D)-D(D)-YYYY format to set date.
    *
    * @param scan Scanner class for input
    */
    public void updateDate(Scanner scan){
        boolean isInvalid = true;
        while (isInvalid) {
            System.out.println("Please enter date (MM-DD-YY): ");
            String input = scan.nextLine();
            String[] date = input.split("-");
           
            //Checks for hyphen separation
            if (date.length == 3) {
                int month = Integer.parseInt(date[0]);
                int day = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);
               
                //Checks if valid date
                if (isValidDate(month, day, year)) {
                    currentMonth = month;
                    currentDay = day;
                    currentYear = year;
                    System.out.println("Current date: " + currentMonth + "/" + currentDay + "/" + currentYear);
                    isInvalid = false;
                } else {
                    System.out.println("Invalid date entered. Please try again.\n");
                }
            } else {
                System.out.println("Must separate numbers with hyphens. Please try again.\n");
            }
        }
    }
}
