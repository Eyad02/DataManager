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
                    if (chosen < availableAccounts.size()) {
                        viewAccountInfo(scan, availableAccounts.get(chosen));
                    } else {
                        running = false;
                    }
                }
                
            } else if (input == 2){
                
                //Lists accounts by service name
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
                        System.out.println("Generating secure password");
                        String generatedPass = selectedAccount.generateStrongPassword();
                        selectedAccount.setPassword(generatedPass);
                        System.out.println("Success! Your new password is " + generatedPass);
                        isStrong = true;
                    } else if (choice.equalsIgnoreCase("N")) {
                        isStrong = true;
                    }
                }
                
            } else if (input == 4) {
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
    
    public void printWeakPasswordReport(Scanner scan, ArrayList<Account> weakAccounts){
        System.out.println("\nAccounts with weak passwords\n----------");
        for (int i = 1; i <= weakAccounts.size(); i++){
            Account currAccount = weakAccounts.get(i - 1);
            System.out.println(i + "\tService: " + currAccount.getServiceName() + 
            "\n   Email/Phone: " + currAccount.getEmailOrPhone() + 
            "\n   Password: " + currAccount.getPassword() + "\n");
        }
        
        System.out.println("Would you like to change any? (Type 0 for no)");
        int choice = -1;
        while (choice < 0 || choice > weakAccounts.size()){
            String choiceInput = scan.nextLine();
            if (isAllNumericStringArray(new String[]{choiceInput})){
                choice = Integer.parseInt(choiceInput);
                if (choice < 0 || choice > weakAccounts.size()){
                    System.out.println("Invalid option.");
                }
            } else {
                System.out.println("Invalid option.");
            }
        }
        if (choice != 0){
            viewAccountInfo(scan, weakAccounts.get(choice - 1));
        }
        System.out.println("\nPress enter to continue");
        scan.nextLine();
    }
    
    public void printReuseReport(Scanner scan, ArrayList<Account> reusedAccounts){
        System.out.println("\nAccounts with reused passwords\n----------");
        for (int i = 1; i <= reusedAccounts.size(); i++){
            Account currAccount = reusedAccounts.get(i - 1);
            int count = manager.getPasswordReuseCount(currAccount.getPassword());
            boolean sameService = manager.getSameServicePasswordMatch(currAccount.getPassword(), currAccount.getServiceName());
            boolean sameEmail = manager.listAccountsByEmail(currAccount.getEmailOrPhone()).size() > 1;
            System.out.println(i + "\tService: " + currAccount.getServiceName() +
            "\n   Email/Phone: " + currAccount.getEmailOrPhone() +
            "\n   Password: " + currAccount.getPassword() +
            "\n   Risk: " + getRiskLabel(sameEmail, sameService, count) + "\n");
        }
        System.out.println("\nPress enter to continue");
        scan.nextLine();
    }
    
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
    
    public String getNonEmptyInput(Scanner scan, String prompt){
        System.out.println(prompt);
        String input = scan.nextLine();
        while (input.length() == 0){
            System.out.println("Field cannot be empty");
            input = scan.nextLine();
        }
        return input;
    }
    
    public boolean isAllNumericStringArray(String[] parts){
        boolean noLetters = true;
        for (int i = 0; i < parts.length; i++){
            for (int j = 0; j < parts[i].length(); j++){
                String c = parts[i].substring(j, j+1);
                if ( !c.equals("0") && !c.equals("1") && !c.equals("2") && !c.equals("3") && !c.equals("4") &&
                     !c.equals("5") && !c.equals("6") && !c.equals("7") && !c.equals("8") && !c.equals("9")){
                    noLetters = false; 
                }
            }
        }
        return noLetters;
    }
    
}
