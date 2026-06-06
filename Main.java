import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
* Main class for the account manager application.
* Controls user interaction and navigation between menus and opening    .
*/
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
        
        System.out.println("=-=-=-=-=-=-= Welcome =-=-=-=-=-=-=");
        
        
        boolean appOpen = true;
        while (appOpen){
            System.out.println("Please select option: \n" + "1\tAccount Menu\n" + 
            "2\tClose application\n");
            int input = app.getValidMenuChoice(scan, 2);
            if (input == 1){
                app.accountMenu(scan); 
            } else if (input == 2){
                System.out.println("\n\nSee you next time!");
                appOpen = false;
            }
        }
        
    }
    
    /**
     * Displays account menu to handle user selection
     * 
     * @param scan Scanner for user input
     */
    public void accountMenu(Scanner scan){
        boolean running = true;
        
        while(running){
            System.out.println("\n-----------Account Menu-----------\n" + 
            "What would you like to do?\n");
            System.out.println("1 Add New Account");
            System.out.println("2 Search, Update, or Delete Accounts");
            System.out.println("3 Security Reports");
            System.out.println("4 Return to Main Menu");
            
            int input = getValidMenuChoice(scan, 4);
            if (input == 1){
                System.out.println("\n------------------\nNew Account");
                String newService = getNonEmptyInput(scan, "Enter service: ");
                String newUser = getNonEmptyInput(scan, "Enter Username: ");
                String newEmail = getNonEmptyInput(scan, "Enter Email/Phone: ");
                String newPass = getNonEmptyInput(scan, "Enter Password: ");
                
                // Collects account details and adds if no duplicate exists
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
                securityReportMenu(scan);
            } else if (input == 4){
                running = false;
            }
        }
    }
    
    /**
     * Display search menu and allows user to find accounts
     * by email/phone or service, then view, update, or delete them
     * 
     * @param scan Scanner for user input
     */
    public void searchUpdateDeleteMenu(Scanner scan){
        boolean running = true;
        
        while(running){
            System.out.println("\nFind accounts by Email/Phone or Service to view info");
            System.out.println("1 List Accounts by Email/Phone");
            System.out.println("2 List Accounts by Service");
            System.out.println("3 Go Back");
            
            int input = getValidMenuChoice(scan, 3);
            if (input == 1){
                
                //Lists accounts by input email/phone
                System.out.println("Please enter email or phone");
                String emailInput = scan.nextLine();
                ArrayList<Account> availableAccounts = manager.listAccountsByEmail(emailInput);
                if (availableAccounts.size() == 0){
                    System.out.println("No accounts found with that email/phone!");
                    System.out.println("\nPress enter to continue\n");
                    scan.nextLine();
                } else {
                    
                    System.out.println("\nSearch results for " + emailInput + "\n----------");
                    for (int i = 0; i < availableAccounts.size(); i++){
                        System.out.println((i+1) + "\t" + availableAccounts.get(i).getServiceName());
                    }
                    System.out.println((availableAccounts.size() + 1) + "\tBack to Account Menu");
                    System.out.println("Select service");
                    int chosen = getValidMenuChoice(scan, availableAccounts.size() + 1) - 1;
                    // Last chance returns to menu, otherwise views selected account
                    if (chosen < availableAccounts.size()) {
                        viewAccountInfo(scan, availableAccounts.get(chosen));
                    } else {
                        running = false;
                    }
                }
                
            } else if (input == 2){
                
                //Searches accounts matching by service name
                System.out.println("Please enter name of service");
                String serviceInput = scan.nextLine();
                ArrayList<Account> availableAccounts = manager.listAccountsByService(serviceInput);
                if (availableAccounts.size() == 0){
                    System.out.println("No accounts found with that service!");
                } else {
                    
                    System.out.println("\nSearch results for " + serviceInput + "\n----------");
                    for (int i = 0; i < availableAccounts.size(); i++){
                        System.out.println((i+1) + "\t" + availableAccounts.get(i).getEmailOrPhone());
                    }
                    System.out.println((availableAccounts.size() + 1) + "\tBack to Account Menu");
                    System.out.println("Select email/phone");
                    int chosen = getValidMenuChoice(scan, availableAccounts.size() + 1) - 1;
                    // Last chance returns to menu, otherwise views selected account
                    if (chosen < availableAccounts.size()) {
                        viewAccountInfo(scan, availableAccounts.get(chosen));
                    } else {
                        running = false;
                    }
                }
                
            } else if (input == 3){
                running = false;
            }
        }
    }
    
    /**
     * Display account detail and allows user to update or delete the account.
     * 
     * @param scan Scanner for user input
     * @param selectedAccount Account object to modify
     */
    public void viewAccountInfo(Scanner scan, Account selectedAccount){
        boolean viewing = true;
        
        while (viewing){
            System.out.println("\n============= Viewing " + selectedAccount.getUsername() + "'s Info =============");
            System.out.println("Service: " + selectedAccount.getServiceName());
            System.out.println("Username: " + selectedAccount.getUsername());
            System.out.println("Email/Phone: " + selectedAccount.getEmailOrPhone());
            System.out.println("Password: " + selectedAccount.getPassword());
            System.out.println("\nWhat would you like to do?" + 
            "\n1\tChange Username" + "\n2\tChange Email/Phone" +
            "\n3\tChange Password" + "\n4\tDelete Account" + "\n5\tGo Back");
            
            int input = getValidMenuChoice(scan, 5);
            
            if(input == 1){
                //Searches accounts matching entered email/phone
                String newUsername = getNonEmptyInput(scan, "Enter the new username: ");
                selectedAccount.setUsername(newUsername);
                System.out.println("Success! Your username has been changed.");
                System.out.println("\nPress enter to continue\n");
                scan.nextLine();
                
            } else if(input == 2) {
                System.out.println("Enter the new email/phone: ");
                String newEmail = scan.nextLine();  
                if (newEmail.length() > 0){
                    selectedAccount.setEmailOrPhone(newEmail);
                    System.out.println("Success! Your email/phone number has been changed.");
                    System.out.println("\nPress enter to continue\n");
                    scan.nextLine();
                } else {
                    System.out.println("Email/phone cannot be empty.");
                }
                
            } else if(input == 3){
                boolean isStrong = false;
                while (!isStrong) {
                    System.out.println("(E)\tEnter custom password\n(G)\tGenerate secure password\n(N)\tNevermind");
                    String choice = scan.nextLine();
                    if (choice.equalsIgnoreCase("E")){
                        System.out.println("Please enter password." + "\n Secure passwords are: at least 12 characters; " +
                        "have at least 1 upper case, lower case, number, and special character. ");
                        
                        String passInput = scan.nextLine();
                        if(selectedAccount.checkStrength(passInput)){
                            selectedAccount.setPassword(passInput);
                            System.out.println("Success! Your password has been changed.");
                            System.out.println("\nPress enter to continue\n");
                            scan.nextLine();
                            isStrong = true;
                        } else {
                                // If password is weak, confirm if user wants to keep it anyway
                                System.out.println("Weak password. Are you sure to want to continue? (yes/no)");
                                    boolean answered = false;
                                    while (!answered){
                                        String yesNo = scan.nextLine();
                                        if (yesNo.equalsIgnoreCase("yes")) {
                                            selectedAccount.setPassword(passInput);
                                            System.out.println("\nPress enter to continue\n");
                                            scan.nextLine();
                                            answered = true;
                                            isStrong = true;
                                        } else if (yesNo.equalsIgnoreCase("no")){
                                            System.out.println("Please select option");
                                            answered = true;
                                        } else {
                                            System.out.println("Please answer yes or no");
                                        }
                                    }
                        }
                    } else if (choice.equalsIgnoreCase("G")) {
                        // Generate and assign a password that satisfies checkStrength
                        String generatedPass = selectedAccount.generateStrongPassword();
                        selectedAccount.setPassword(generatedPass);
                        System.out.println("Success! Your new password is " + generatedPass);
                        isStrong = true;
                    } else if (choice.equalsIgnoreCase("N")) {
                        isStrong = true;
                    }
                }
                
            } else if (input == 4) {
                // Confirm before permanently deleting account
                System.out.println("Are you sure you want to delete? (yes/no)");
                boolean answered = false;
                while (!answered){
                    String yesNo = scan.nextLine();
                    if (yesNo.equalsIgnoreCase("yes")) {
                        manager.deleteAccount(selectedAccount);
                        System.out.println("Account deleted successfully.");
                        viewing = false;
                    } else if (yesNo.equalsIgnoreCase("no")){
                        System.out.println("No changes made.");
                        answered = true;
                    } else {
                        System.out.println("Please answer yes or no");
                    }
                }
            } else if (input == 5) {
                viewing = false;
            }
        }
    }
    
    /**
     * Display security report menu and controls user selection.
     * 
     * @param scan Scanner for user input
     */
    public void securityReportMenu(Scanner scan){
        boolean choosing = true;
        
        while(choosing){
            System.out.println("\n-----------Security Reports-----------\n" + 
            "What would you like to do?\n");
            System.out.println("1 Accounts with Weak Password");
            System.out.println("2 Reused Password Risks");
            System.out.println("3 Back");
            
            int input = getValidMenuChoice(scan, 3);
            if (input == 1){
                printWeakPasswordReport(scan, manager.getWeakPasswordAccounts());
            } else if (input == 2){
                printReuseReport(scan, manager.getReusedPasswordAccounts());
            } else if (input == 3){
                choosing = false;
            }
        }
    }
    
    /**
     * Displays all accounts with weak passwords and prompts user to choose one to update
     * 
     * @param scan Scanner for user input
     * @param weakAccounts list of accounts with password that failed checkStrength
     */
    public void printWeakPasswordReport(Scanner scan, ArrayList<Account> weakAccounts){
        System.out.println("\nAccounts with weak passwords\n----------");
        for (int i = 1; i <= weakAccounts.size(); i++){
            Account currAccount = weakAccounts.get(i - 1);
            System.out.println(i + "\tService: " + currAccount.getServiceName() + 
            "\n   Email/Phone: " + currAccount.getEmailOrPhone() + 
            "\n   Password: " + currAccount.getPassword() + "\n");
        }
        System.out.println((weakAccounts.size() + 1) + "\tNo changes");
    
        int choice = getValidMenuChoice(scan, weakAccounts.size() + 1);
        
        //Last option means no change, otherwise move to selected account
        if (choice != weakAccounts.size() + 1){
            viewAccountInfo(scan, weakAccounts.get(choice - 1));
        }
        System.out.println("\nPress enter to continue");
        scan.nextLine();
    }
    
    /**
     * Prints all accounts with reused passwords along with their risk level.
     * 
     * @param scan Scanner for user input
     * @param reusedAccounts list of accounts sharing a password with another account
     */
    public void printReuseReport(Scanner scan, ArrayList<Account> reusedAccounts){
        System.out.println("\nAccounts with reused passwords\n----------");
        for (int i = 1; i <= reusedAccounts.size(); i++){
            Account currAccount = reusedAccounts.get(i - 1);
            int count = manager.getPasswordReuseCount(currAccount.getPassword());
            boolean sameService = manager.getSameServicePasswordMatch(currAccount.getPassword(), currAccount.getServiceName());
            // Low risk if multiple accounts share same email (Likely external login)
            boolean sameEmail = manager.listAccountsByEmail(currAccount.getEmailOrPhone()).size() > 1;
            System.out.println(i + "\tService: " + currAccount.getServiceName() +
            "\n   Email/Phone: " + currAccount.getEmailOrPhone() +
            "\n   Password: " + currAccount.getPassword() +
            "\n   Risk: " + getRiskLabel(sameEmail, sameService, count) + "\n");
        }
        System.out.println("\nPress enter to continue");
        scan.nextLine();
    }
    
    /**
     * Determines risk level of a reused password based on inter-email reuse,
     * same service reuse, and total count reuse.
     * 
     * @param sameEmail true if other accounts with this password share the same email
     * @param sameService true if another account on the same service uses this password
     * @param count total number of accounts using this password
     * @return String representing risk level
     */
    public String getRiskLabel(boolean sameEmail, boolean sameService, int count){
        if (sameService && !sameEmail){
            return "CRITICAL";
        } else if (!sameEmail && count >= 5){
            return "CRITICAL";
        } else if (!sameEmail && count >= 2){
            return "High Risk";
        } else if (sameEmail){
            return "Low Risk";
        } else {
            return "Medium Risk";
        }
    }
    
    /**
     * Continuously prompts user until a valid integer between 1 and maxOption is entered.
     * 
     * @param scan Scanner for user input
     * @param maxOption maximum valid menu option
     * @return valid integer selection
     */
    public int getValidMenuChoice(Scanner scan, int maxOption){
        String input = scan.nextLine();
        boolean valid = false;
        while (!valid){
            for (int i = 1; i <= maxOption; i++){
                if (input.equals("" + i)){
                    valid = true;
                }
            }
            
            if (!valid){
                System.out.println("Invalid option.");
                input = scan.nextLine();
            }
        }
        return Integer.parseInt(input);
    }
    
    /**
     * Continuously prompts user until a non-empty string is entered.
     * 
     * @param scan Scanner for user input
     * @param prompt message to display to user
     * @return non-empty string entered by user
     */
    public String getNonEmptyInput(Scanner scan, String prompt){
        System.out.println(prompt);
        String input = scan.nextLine();
        while (input.length() == 0){
            System.out.println("Field cannot be empty");
            input = scan.nextLine();
        }
        return input;
    }
    
}
