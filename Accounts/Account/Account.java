package Account;
import java.util.ArrayList;
import Accounts.Transaction;
import Bank.Bank;
public abstract class Account {
    private Bank bank;
    private final String ACCOUNTNUMBER;
    private final String OWNERFNAME, OWNERLNAME, OWNEREMAIL;
    private String pin;
    private final ArrayList<Transaction> TRANSACTIONS;
    
    public Bank getBank() {
        return bank;
    }
    public void setBank(Bank bank) {
        this.bank = bank;
    }
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getAccountNumber() {
        return ACCOUNTNUMBER;
    }
    public String getOWNERFNAME() {
        return OWNERFNAME;
    }
    public String getOWNERLNAME() {
        return OWNERLNAME;
    }

    public String getOWNEREMAIL() {
        return OWNEREMAIL;
    }
    public ArrayList<Transaction> getTRANSACTIONS() {
        return TRANSACTIONS;
    }

    public Account(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        this.bank = bank;
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
        this.OWNERFNAME = OWNERFNAME;
        this.OWNERLNAME = OWNERLNAME;
        this.OWNEREMAIL = OWNEREMAIL;
        this.pin = pin;
        this.TRANSACTIONS = new ArrayList<>();
    }

    /**
     * Retrieves the full name of the account owner by concatenating the first and last names.
     *
     * @return the full name of the account owner
     */
    public String getOwnerFullName() {
        return this.OWNERFNAME + " " + this.OWNERLNAME;
    }

    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {
        Transaction transaction = new Transaction(accountNum, type, description);
        getTRANSACTIONS().add(transaction);
        System.out.println("New transaction added to this account.");
    }

    /**
    * A method to get transactions information.
    *
    * @return         	description of the transactions information
    */
    public String getTransactionsInfo() {
        String result = "Transactions: \n";
        for(Transaction transaction : getTRANSACTIONS()) {
            result += transaction.description + "\n";
        }
        return result;
    }
     /**
     * Returns a string representation of the account, including details such as bank information, account number,
     * owner details, PIN, and a list of transactions if available.
     *
     * @return a formatted string containing account information
     */
    public String toString() {
        //Initialize the string with a header
        String accountInfo = "Account Information:\n";
            
            //Append bank information or indicate if not available
        accountInfo += String.format("Bank:\n%s", bank != null ? bank.toString() : "No bank information available") + "\n";
            //Append account details
        accountInfo += String.format("Account Number: %s\n", ACCOUNTNUMBER);
        accountInfo += String.format("Owner: %s\n", getOwnerFullName());
        accountInfo += String.format("Owner Email: %s\n", OWNEREMAIL);
        accountInfo += String.format("PIN: %s\n", pin);

            //Append transaction details or indicate if no transactions available
        if (TRANSACTIONS != null && !TRANSACTIONS.isEmpty()) {
            accountInfo += "Transactions:\n";
            for (Transaction transaction : TRANSACTIONS) {
                accountInfo += String.format("- %s\n", transaction != null ? transaction.toString() : "Invalid Transaction");
            }
        } else {
            accountInfo += "No transactions available.\n";
        }
        // Return the final string representation
        return accountInfo;
    }


}
