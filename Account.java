import java.util.List;
import java.util.ArrayList;

public class Account {
    //Name of service tied to account
    private String serviceName;
   
    //Username of account
    private String username;
   
    //Email or phone number the account was created with
    private String emailOrPhone;
   
    //Password of account
    private String password;
   
    //Subscription object tied to account
    private Subscription subDetails;
   
    //Characters used to generate strong password
    private static String[] characterPool = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
        "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+"
    };
   
    //Constructs object with all information excluding subscription
    public Account(String service, String user, String email, String pass){
        serviceName = service;
        username = user;
        emailOrPhone = email;
        password = pass;
    }
   
    //Constructs object with all information including subscription
    public Account(String service, String user, String email, String pass, Subscription sub){
        serviceName = service;
        username = user;
        emailOrPhone = email;
        password = pass;
        subDetails = sub;
    }
   
    //Returns name of service tied to account
    public String getServiceName(){
        return this.serviceName;
    }
   
    //Sets name of service tied to account
    public void setServiceName(String service){
        serviceName = service;
    }
   
    //Returns username of account
    public String getUsername(){
        return this.username;
    }
   
    //Sets username of account
    public void setUsername(String user){
        username = user;
    }
   
    //Returns the email or phone number the account was created with
    public String getEmailOrPhone(){
        return this.emailOrPhone;
    }
   
    //Sets the email or phone number the account was created with
    public void setEmailOrPhone(String email){
        emailOrPhone = email;
    }
   
    //Returns the password to the account
    public String getPassword(){
        return this.password;
    }
   
    //Sets the password to the account
    public void setPassword(String pass){
        password = pass;
    }
   
    //Returns subscription object tied to account
    public Subscription getSubDetails(){
        return this.subDetails;
    }
   
    //Sets subscription object tied to account
    public void setSubDetails(Subscription sub){
        subDetails = sub;
    }
   
    /**
    * Generates strong password of 12 characters.
    *
    * @return Returns String containing 12 randomized characters
    * from character pool.
    */
    public String generateStrongPassword(){
        String pass = "";
       
        for (int i = 0; i < 12; i++){
            int randomIndex = (int) (Math.random() * characterPool.length);
            pass += characterPool[randomIndex];
        }
       
        if (checkStrength(pass)){
            return pass;
        } else {
            System.out.println(pass);
            return generateStrongPassword();
        }
    }
   
    /**
    * Checks if password is strong.
    *
    * @param password A string of characters
    * @return Returns true if password length >= 12;
    * password contains at least one upper case, lower case, digit,
    * and special character; and no two sequential characters
    * are the same.
    */
    public boolean checkStrength(String password){
        if (password.length() < 12) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;
       
        for (int i = 0; i < password.length(); i++) {
            String ch = password.substring(i, i + 1);
           
            //Finds the same character
            for (int j = 0; j < characterPool.length; j++) {
                //Determines if upper or lower case, digit or symbol
                if (ch.equals(characterPool[j])) {
                    if (j >= 0 && j <= 25) {
                        hasUpper = true;
                    } else if (j >= 26 && j <= 51) {
                        hasLower = true;
                    } else if (j >= 52 && j <= 61) {
                        hasDigit = true;
                    } else if (j >= 62 && j <= characterPool.length) {
                        hasSymbol = true;
                    }
                }
            }
           
            //Checks if character is same as next
            if (i < password.length() - 1) {
                String nextCh = password.substring(i + 1, i + 2);
                if (ch.equals(nextCh)) {
                    return false;
                }
            }
        }
       
        return hasUpper && hasLower && hasDigit && hasSymbol;
    }
   
    //Test method
    public String accountInfo(){
        String temp =
        "Service: " + getServiceName() + "\n" +
        "Username: " + getUsername() + "\n" +
        "Email/Phone: " + getEmailOrPhone() + "\n" +
        "Password: " + getPassword() + "\n";
        return temp;
    }
   
}
