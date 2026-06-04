import java.util.List;
import java.util.ArrayList;

public class Manager {
   
    private ArrayList<Account> accountsList;
   
    public Manager() {
        accountsList = new ArrayList<Account>();
    }

    public void addAccount(Account newAccount) {
        accountsList.add(newAccount);
    }
   
    public void deleteAccount(Account account) {
        accountsList.remove(account);
    }
   
    public boolean checkExistingAccount(String service, String email){
        boolean exists = false;
        for (Account currAccount : accountsList){
            if (service.equalsIgnoreCase(currAccount.getServiceName())){
                if (email.equalsIgnoreCase(currAccount.getEmailOrPhone())){
                    exists = true;
                }
            }
        }
        return exists;
    }
   
    public ArrayList<Account> listAccountsByEmail(String target){
        System.out.println("\nSearch results for " + target + "\n----------");
       
        ArrayList<Account> foundAccounts = new ArrayList<>();
        for (Account currAccount : accountsList){
            if (currAccount.getEmailOrPhone().equalsIgnoreCase(target)){
                foundAccounts.add(currAccount);
            }
        }
        return foundAccounts;
    }
   
    public ArrayList<Account> listAccountsByService(String target){
        System.out.println("\nSearch results for " + target + "\n----------");
       
        ArrayList<Account> foundAccounts = new ArrayList<>();
        for (Account currAccount : accountsList){
            if (currAccount.getServiceName().equalsIgnoreCase(target)){
                foundAccounts.add(currAccount);
            }
        }
        return foundAccounts;
    }
}
