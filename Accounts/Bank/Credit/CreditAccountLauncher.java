package Bank.Credit;


import Account.Account;
import Account.AccountLauncher;
import Accounts.IllegalAccountType;
import Accounts.Transaction.Transactions;
import Main.Main;

public class CreditAccountLauncher extends AccountLauncher {
    /**
     * Initializes the credit account and handles the account menu options.
     *
     * @throws IllegalAccountType if the account type is invalid
     */
    public static void creditAccountInit() throws IllegalAccountType {
        while (true) {
            try {
                Main.showMenuHeader("Credit Account Menu");
                Main.showMenu(41);
                Main.setOption();
                int opti = Main.getOption();
                switch (opti) {
                    case 1:
                        Main.showMenuHeader("Loan Statement");
                        System.out.println(getLoggedAccount().getLoanStatement());
                        continue;
                    case 2:
                        creditPaymentProcess();
                        continue;
                    case 3:
                        creditRecompenseProcess();
                        continue;
                    case 4:
                        System.out.println(getLoggedAccount().getTransactionsInfo());
                        continue;
                    case 5:
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
     * Processes a credit payment.
     *
     * @throws IllegalAccountType if the account type is illegal
     */
    private static void creditPaymentProcess() throws IllegalAccountType {
        CreditAccount loggedAccount = getLoggedAccount();
        String accNum = Main.prompt("Account number: ", true);
        double amount = Double.parseDouble(Main.prompt("Amount: ", true));

        Account account = getAssocBank().getBankAccount(getAssocBank(), accNum);
        if (getLoggedAccount().pay(account, amount)) {
            System.out.println("Credit: " + loggedAccount.getLoan());
            getLoggedAccount().addNewTransaction(getLoggedAccount().getAccountNumber(), Transactions.Payment, "A successful payment.");
        } else {
            System.out.println("Payment unsuccessful!");
        }
    }
    
    /**
     * Process for recompensing credit to a logged account.
     */
    private static void creditRecompenseProcess() {
        CreditAccount loggedAccount = getLoggedAccount();
        if (loggedAccount == null) {
            System.out.println("No credit account logged in.");
            return;
        }

        double amountToRecompense;
        while (true) {
            String amount = Main.prompt("Enter the amount to recompense: ", true);
            try {
                amountToRecompense = Double.parseDouble(amount);
                if (amountToRecompense <= 0) {
                    System.out.println("Amount must be greater than zero!");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount!");
            }
        }

        boolean success = loggedAccount.recompense(amountToRecompense);
        if (success) {
            System.out.println("Recompense successful!");
            getLoggedAccount().addNewTransaction(getLoggedAccount().getAccountNumber(), Transactions.Recompense, "A successful recompense.");
        } else {
            System.out.println("Recompense failed! The entered amount exceeds the credit limit!");
        }
    }

   
    /**
     * Retrieves the currently logged-in CreditAccount, if available.
     * This method delegates to AccountLauncher.getLoggedAccount() to obtain the logged account,
     * and returns it if it is an instance of CreditAccount.
     *
     * @return the currently logged-in CreditAccount, or null if not available or not a CreditAccount
     */
    protected static CreditAccount getLoggedAccount() {
        // Attempt to obtain the logged account
        Account account = AccountLauncher.getLoggedAccount();

        // Check if the obtained account is not null and is an instance of CreditAccount
        if (account != null && account instanceof CreditAccount) {
            // If so, return the logged CreditAccount
            return (CreditAccount) account;
        } else {
            // Otherwise, return null
            return null;
        }
    }
}
