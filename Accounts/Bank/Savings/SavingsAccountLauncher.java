package Bank.Savings;
import Account.Account;
import Account.AccountLauncher;
import Accounts.IllegalAccountType;
import Accounts.Transaction.Transactions;
import Bank.Bank;
import Bank.BankLauncher;
import Main.Main;

public class SavingsAccountLauncher extends AccountLauncher {
    
     
    /**
     * Initializes the savings account functionality for the currently logged-in user.
     * Prints the account balance statement if a savings account is logged in.
     * Otherwise, prints a message indicating that no account is logged in.
     * @throws IllegalAccountType 
     */
    public static void savingsAccountInit() throws IllegalAccountType {
        while (true) {
            try {
                Main.showMenuHeader("Savings Account Menu");
                Main.showMenu(51);
                Main.setOption();
                int choice = Main.getOption();
                switch (choice) {
                    case 1:
                        Main.showMenuHeader("Balance Statement");
                        System.out.println(getLoggedAccount().getAccountBalanceStatement());
                        continue;
                    case 2:
                        depositProcess();
                        continue;
                    case 3:
                        withdrawProcess();
                        continue;
                    case 4:
                        fundTransferProcess();
                        continue;
                    case 5:
                        System.out.println(getLoggedAccount().getTransactionsInfo());
                        continue;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (IllegalAccountType err) {
                System.out.println(err.getMessage());
            }
        }
    }

    
    /**
     * A description of the entire Java function.
     *
     */
    private static void depositProcess() {
        SavingsAccount loggedAccount = getLoggedAccount();

        if (loggedAccount == null) {
            System.out.println("No account found!");
            return;
        }

        double depositAmount;

        while (true) {
            String depositAmount_str = Main.prompt("Enter deposit amount: ", true);

            try {
                depositAmount = Double.parseDouble(depositAmount_str);
                if (depositAmount <= 0) {
                    System.out.println("Invalid amount!");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount!");
            }
        }

        boolean depositSuccess = loggedAccount.cashDeposit(depositAmount);
        if (depositSuccess) {
            System.out.println("Deposit successful!");
            System.out.println("Balance: " + loggedAccount.getBalance());
            getLoggedAccount().addNewTransaction(getLoggedAccount().getAccountNumber(), Transactions.Deposit, "A successful deposit.");
        } else {
            System.out.println("Deposit failed. Please try again.");
        }
    }

   
    /**
     * Initiates the withdrawal process for the logged-in savings account.
     * Prompts the user to enter the withdrawal amount and performs the withdrawal if the amount is valid and sufficient.
     * Prints appropriate messages indicating the success or failure of the withdrawal process.
     */
    private static void withdrawProcess() {
        
        SavingsAccount loggedAccount = getLoggedAccount();
        // Check if a savings account is logged in
        if (loggedAccount != null) {
            loggedAccount.toString();
            String withdrawAmount = Main.prompt("Enter the amount to withdraw: ", true);
            double amount = Double.parseDouble(withdrawAmount);
                        
            if (amount <= 0) {
                // Check if the withdrawal amount is valid
                System.out.println("Invalid amount. Please enter a positive value.");
                return;
            }
            
            if (loggedAccount.getBalance() >= amount) {
                if (loggedAccount.withdrawal(amount)) { 
                    System.out.println("Balance: " + loggedAccount.getBalance());
                    getLoggedAccount().addNewTransaction(getLoggedAccount().getAccountNumber(), Transactions.Withdraw, "A successful withdrawal.");
                } else {
                    System.out.println("Withdrawal failed");
                }
            } else {
                System.out.println("Insufficient balance for withdrawal.");
            }
        } else {
            System.out.println("No account logged in.");
        }
    }
    
    /**
     * A method that deals with the fund transfer process transaction.
     * 
     * @throws IllegalAccountType
     */
    private static void fundTransferProcess() throws IllegalAccountType {
        SavingsAccount loggedAccount = getLoggedAccount();
        
        System.out.println("[1] Internal transfer \n[2] External transfer");
        Main.setOption();

        switch (Main.getOption()) {
            case 1:
                String internalAccNum = Main.prompt("Account Number: ", true);
                double internalAmount = Double.parseDouble(Main.prompt("Amount: ", true));
        
                SavingsAccount internalAccount = (SavingsAccount) loggedAccount.getBank().getBankAccount(loggedAccount.getBank(), internalAccNum);
                if (loggedAccount.transfer(internalAccount, internalAmount)) {
                    getLoggedAccount().addNewTransaction(getLoggedAccount().getAccountNumber(), Transactions.FundTransfer, "A successful fund transfer.");
                } else {
                    System.out.println("Transfer unsuccessful!");
                }
                break;
    
            case 2:
                int externalBankID = Integer.parseInt(Main.prompt("Bank ID: ", true));
                String externalAccNum = Main.prompt("Account Number: ", true);
                double externalAmount = Double.parseDouble(Main.prompt("Amount: ", true));

                for (Bank bank : BankLauncher.getBANKS()) {
                    if (bank.getID() == externalBankID) {
                        Account externalAccount = bank.getBankAccount(bank, externalAccNum);
                        if (loggedAccount.transfer(bank, externalAccount, externalAmount)) {
                            System.out.println("Balance: " + loggedAccount.getBalance());
                            getLoggedAccount().addNewTransaction(getLoggedAccount().getAccountNumber(), Transactions.FundTransfer, "A successful fund transfer.");
                        } else {
                            System.out.println("Transfer unsuccessful!");
                        }
                        
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * Method to get the currently logged Savings Account
     * 
     * @return Savings Account instance of the currently logged account.
     */
    protected static SavingsAccount getLoggedAccount() {
        Account account = AccountLauncher.getLoggedAccount();
        if (account instanceof SavingsAccount) {
            return (SavingsAccount) account;
        } else {
            System.out.println("No logged-in savings account found.");
            return null;
        }
    }
}
