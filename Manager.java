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
       
        ArrayList<Account> foundAccounts = new ArrayList<>();
        for (Account currAccount : accountsList){
            if (currAccount.getEmailOrPhone().equalsIgnoreCase(target)){
                foundAccounts.add(currAccount);
            }
        }
        return foundAccounts;
    }
   
    public ArrayList<Account> listAccountsByService(String target){
       
        ArrayList<Account> foundAccounts = new ArrayList<>();
        for (Account currAccount : accountsList){
            if (currAccount.getServiceName().equalsIgnoreCase(target)){
                foundAccounts.add(currAccount);
            }
        }
        return foundAccounts;
    }
   
    public ArrayList<Account> getWeakPasswordAccounts(){
        ArrayList<Account> weakAccounts = new ArrayList<>();
       
        for (Account currAccount : accountsList){
            boolean isStrong = currAccount.checkStrength(currAccount.getPassword());
            if (!isStrong){
                weakAccounts.add(currAccount);
            }
        }
        return weakAccounts;
    }
   
    public ArrayList<Account> geReusedPasswordAccounts(){
        ArrayList<Account> reusedAccounts = new ArrayList<>();
       
        for (int i = 0; i < accountsList.size(); i++){
            for (int j = 0; j < accountsList.size(); j++){
                if (i!=j){
                    if (accountsList.get(i).getPassword().equals(accountsList.get(j).getPassword())){
                        boolean alreadyAdded = false;
                        for (int k = 0; k < reusedAccounts.size(); k++){
                            if(reusedAccounts.get(k) == accountsList.get(i)){
                                alreadyAdded = true;
                            }
                        }
                       
                        if (!alreadyAdded){
                            reusedAccounts.add(accountsList.get(i));
                        }
                    }
                }
            }
        }
        return reusedAccounts;
    }
   
   
   
   
}
