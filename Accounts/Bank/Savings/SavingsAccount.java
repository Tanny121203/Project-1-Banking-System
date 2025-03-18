package Bank.Savings;
import Account.Account;
import Accounts.IllegalAccountType;
import Bank.Bank;
import Interfaces.Withdrawal;
import Interfaces.Deposit;
import Interfaces.FundTransfer;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public SavingsAccount(Bank bank,  String accountNumber, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {
        super(bank, accountNumber, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }


    /**
     * Generates an account balance statement including account number, owner details, email, and balance.
     *
     * @return          the formatted account balance statement
     */
    public String getAccountBalanceStatement() {
        String format = String.format("%.2f", balance); 

        String account_statement = "Account Balance Statement:\n" +
                                   "Account Number: " + getAccountNumber() + "\n" +
                                   "Owner: " + getOwnerFullName() + "\n" +
                                   "Email: " + getOWNEREMAIL() + "\n" +
                                   "Balance: " + format + "\n";
            
        return account_statement;
    }

    /**
     * Checks if the account has sufficient balance to cover a specified amount.
     *
     * @param amount the amount to be checked against the account balance
     * @return true if the account has enough balance, false otherwise
     */
    private boolean hasEnoughBalance(double amount) {
        double newBalance = this.balance + amount;
        return newBalance >= 0.0;
    }

    
    /*
     * Warns the account owner that their balance is not enough for the transaction to proceed
     * successfully.
     */
    private void insufficientBalance() {
        System.out.println("Insufficient balance for the transaction. Please check your account balance.");
    }

    /*      
      * Adjusts the account balance of this savings account based on the amount to be adjusted.
      * If the result of the adjustment brings the balance below 0.0, the balance is forcibly reset to 0.0.
      *
      * @param amount the amount to be added or subtracted from the account balance
      */
      private void adjustAccountBalance(double amount) {
        if (!hasEnoughBalance(amount)) {
            amount = 0.0;
            insufficientBalance();
            return;
        }
        this.balance += amount;
    }


    /**
     * Returns a string representation of the object by delegating to the {@code getAccountBalanceStatement} method.
     *
     * @return a formatted string containing account balance information
     */
    public String toString() {
        return getAccountBalanceStatement();
    }

    /**
     * A method to transfer money from one account to another within the same bank.
     *
     * @param  bank    the bank from which the transfer is taking place
     * @param  account the account from which the transfer is being made
     * @param  amount  the amount of money to be transferred
     * @return         true if the transfer is successful, false otherwise
     * @throws IllegalAccountType if the account type is invalid for the transfer
     */
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        if (account instanceof SavingsAccount) {
            withdrawal(amount + bank.getPROCESSINGFEE());
            ((SavingsAccount) bank.getBankAccount(bank, account.getAccountNumber())).cashDeposit(amount);
            return true;
        } else {
            throw new IllegalAccountType("Attempted transfer from illegal account type.");
        }
    }
   

    /**
     * A method to transfer an amount from one account to another.
     *
     * @param  account  the account to transfer to
     * @param  amount   the amount to transfer
     * @return          true if the transfer is successful, false otherwise
     * @throws IllegalAccountType if the account type is invalid
     */
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (account instanceof SavingsAccount) {
            withdrawal(amount);
            ((SavingsAccount) account).cashDeposit(amount);
            return true;
        } else {
            throw new IllegalAccountType("Attempted transfer from illegal account type.");
        }
    }

    /**
     * Perform a cash deposit if there are enough funds in the account.
     *
     * @param  amount   the amount to deposit
     * @return          true if the deposit was successful, false otherwise
     */ 
    @Override
    public boolean cashDeposit(double amount) {
        if (amount > getBank().getDEPOSITLIMIT()) {
            System.out.println("Deposit amount exceeds the deposit limit.");
            return false;
        }
        adjustAccountBalance(amount);
        return true;
    }


    /**
     * withdrawal function to deduct the specified amount from the balance
     *
     * @param  amount  the amount to be withdrawn
     * @return         true if withdrawal is successful, false if there is insufficient balance
     */
    @Override
    public boolean withdrawal(double amount) {
        if (hasEnoughBalance(amount)) {
            this.balance -= amount;
            return true;
        } else {
            insufficientBalance();
            return false;
        } 
    }
}
