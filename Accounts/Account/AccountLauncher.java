package Account;
import Accounts.IllegalAccountType;
import Bank.Bank;
import Bank.BankLauncher;
import Bank.Credit.CreditAccount;
import Bank.Credit.CreditAccountLauncher;
import Bank.Savings.SavingsAccount;
import Bank.Savings.SavingsAccountLauncher;
import Main.Main;
import Main.Field;

public class AccountLauncher {
    private static Account loggedAccount;
    private static Bank assocBank;
    
    public static Bank getAssocBank() {
        return assocBank;
    }

    public static void setAssocBank(Bank assocBank) {
        AccountLauncher.assocBank = assocBank;
    }

    /**
    * Checks if a user is currently logged in by verifying the presence of a logged account.
     *
     * @return true if a user is logged in, false otherwise
     */
    private static boolean isLoggedIn() {
        return loggedAccount != null;
    }
    
    /**
     * A function to handle account login.
     * @throws IllegalAccountType 
     * 
     */
    public static void accountLogin() throws IllegalAccountType {
        Main.showMenuHeader("Account Login");
        if (isLoggedIn()) {
            destroyLogSession();
        }
        
        while (assocBank == null) {
            System.out.println("Select a bank first");
            assocBank = selectBank();
            if (assocBank == null) {
                System.out.println("Invalid bank selection. Please try again.");
            }
        }

        String accountNum;
        String pin;
        do {
            Main.showMenuHeader("Account Login");
            accountNum = Main.prompt("Enter account number: ", true);
            pin = Main.prompt("Enter PIN: ", true);
            loggedAccount = checkCredentials(accountNum, pin);
            if (loggedAccount == null) {
                System.out.println("Invalid account number or PIN. Please try again.");
            }
        } while (loggedAccount == null);
        
        if (loggedAccount != null) {
            System.out.println("Login successful.");
            setLogSession(loggedAccount);
            if (loggedAccount.getClass() == SavingsAccount.class) {
                SavingsAccountLauncher.savingsAccountInit();
            }
            else if (loggedAccount.getClass() == CreditAccount.class) {
                CreditAccountLauncher.creditAccountInit();
            }
        }
    }
    
    /**
    * Bank selection screen before the user is prompted to login. User is prompted for the Bank ID
    * with corresponding bank name.
    * 
    * @return Bank object based on selected ID
    */
    private static Bank selectBank() {
        Main.showMenuHeader("Bank Selection");
        BankLauncher.showBanksMenu();
        Field<Integer, Integer> bankID = new Field<Integer,Integer>("ID", Integer.class, -1, new Field.IntegerFieldValidator());
        Field<String, String> bankName = new Field<String,String>("Name", String.class, "", new Field.StringFieldValidator());
        Field<String, String> bankPass = new Field<String,String>("Passcode", String.class, "", new Field.StringFieldValidator());
        bankID.setFieldValue("Enter bank id: ",true);
        bankName.setFieldValue("Enter bank name: ",false);
        bankPass.setFieldValue("Enter bank passcode: ");

        for (Bank bank : BankLauncher.getBANKS()) {
            if (bank.getID() == bankID.getFieldValue() && bank.getName().equals(bankName.getFieldValue())) {
                System.out.println("Bank selected: " + bankName.getFieldValue());
                return bank;
            }
        }
        return null;
    }
    
    /**
     * Sets the log session for the given account.
     *
     * @param  account   the account for which the log session is being set
     */
    private static void setLogSession(Account account) {
        System.out.println("Session created for account number  " + loggedAccount.getAccountNumber());
    }

    /**
     * Destroys the current log session if a user is logged in, resetting the loggedAccount to null.
     * Prints relevant messages indicating the success or absence of a log session.
     */
    private static void destroyLogSession() {
        // Check if a user is logged in
        if (isLoggedIn()) {
            // Log the destruction of the log session for the currently logged account
            System.out.println("Destroying log session for account: " + loggedAccount.getAccountNumber());
            // Reset the loggedAccount to null
            loggedAccount = null;
            assocBank = null;
            // Print a message indicating the successful destruction of the log session
            System.out.println("Log session destroyed.");
        } 
        
        else {
            // Print a message indicating that no user is logged in, and log session destruction is not required
            System.out.println("No user logged in. Log session destruction not required.");
        }
    }
    
    /**
    * Checks inputted credentials during account login.
    * 
    * @param accountNum – Account number.
    * @param pin – 4-digit pin.
    * 
    * @return Account object if it passes verification. Null if not.
    */
    public static Account checkCredentials(String accountNum, String pin) {
        Account selAccount = assocBank.getBankAccount(assocBank, accountNum);
        if (selAccount != null && selAccount.getAccountNumber().equals(accountNum) && selAccount.getPin().equals(pin)) {
            return selAccount;
        } else {
            System.out.println("Invalid account number or PIN for account " + accountNum + ".");
            return null;
        }
    }
    
    /**
     * Retrieves the currently logged in account.
     *
     * @return the logged in account
     */
    protected static Account getLoggedAccount() {
        return loggedAccount;
    }
}
