package Bank;

import java.util.ArrayList;
import Account.Account;
import Bank.Credit.CreditAccount;
import Bank.Savings.SavingsAccount;
import Main.Menu;
import Main.Main;
import Main.Field;
import Interfaces.Comparator;

public class BankLauncher {
    private static ArrayList<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;

    /**
     * Checks if the user is logged in by verifying if the loggedBank object is not null.
     *
     * @return  true if the user is logged in, false otherwise
     */
    public static boolean isLogged() {
        return loggedBank != null;
    }

    /**
     * This function initializes the bank system, logs in the user, and displays a menu for bank operations.
     */
    public static void bankInit() {
        bankLogin();
        if (isLogged()) {
            boolean menuContinue = true;

            while (menuContinue) {
                Main.showMenuHeader("Bank Menu");
                Main.showMenu(Menu.BankMenu.menuIdx);

                try {
                    Main.setOption(); 
                    int choice = Main.getOption();

                    switch (choice) {
                        case 1:
                            showAccounts(); 
                            break;
                        case 2:
                            newAccounts(); 
                            break;
                        case 3:
                            logout(); 
                            menuContinue = false;
                            break;
                        default:
                            System.out.println("Invalid option! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                }
            }
        } else {
            System.out.println("Please login to your bank account.");
        }
    }

    /**
     * Show accounts based on user input.
     */
    public static void showAccounts() {
        while (true) {
            Main.showMenuHeader("Show Accounts");
            Main.showMenu(32);
            Main.setOption();

            switch (Main.getOption()) {
                case 1:
                    Main.showMenuHeader("Credit Accounts");
                    getLoggedBank().showAccounts(CreditAccount.class);
                    continue;
                case 2:
                    Main.showMenuHeader("Savings Accounts");
                    getLoggedBank().showAccounts(SavingsAccount.class);
                    continue;
                case 3:
                    Main.showMenuHeader("Accounts");
                    getLoggedBank().showAccounts(Account.class);
                    continue;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    /**
     * Performs the bank login process by prompting the user for the bank name and PIN.
     * It checks if the provided bank name and PIN match any of the registered banks.
     * If a match is found, it sets the logged bank session.
     * If no match is found, it displays an error message.
     */
    public static void bankLogin() {
        Main.showMenuHeader("Bank Login");
            String bankName = Main.prompt("Enter bank name: ", false);
        String pin = Main.prompt("Enter PIN: ",true);
        
        for (Bank bank : BANKS) {
            if (bank.getName().equals(bankName) && bank.getPasscode().equals(pin)) {
                setLogSession(bank);
                break;
            }
        }
        if (loggedBank == null) {
            System.out.println("Invalid bank name or PIN. Please try again.");
        }        
    }

 /**
 * Prompts the user to create a new account and adds it to the logged-in bank.
 * Displays a menu for selecting the type of account (credit or savings) to create.
 */
    private static void newAccounts() {
        Main.showMenuHeader("New Account Type");
        Main.showMenu(33);
        Main.setOption();
        switch (Main.getOption()) {
            case 1: // Creates and adds a new Credit Account to the logged-in bank.
                CreditAccount newCreditAcc = getLoggedBank().createNewCreditAccount();
                getLoggedBank().addNewAccount(newCreditAcc);
                break;
            case 2: // Creates and adds a new Savings Account to the logged-in bank.
                SavingsAccount newSavingAcc = getLoggedBank().createNewSavingsAccount();
                getLoggedBank().addNewAccount(newSavingAcc);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
    
     /**
     * Sets up a new login session for the logged-in bank user.
     * If another bank account is already logged in, prints a message and returns.
     * Otherwise, sets the provided bank as the logged-in bank and prints a success message.
     * 
     * @param b The bank user that successfully logged in.
     */
    private static void setLogSession(Bank b) {
        // Checks if another bank account is already logged in
        if (isLogged()) {
            System.out.println("Another bank account is currently logged in");
            return;
        }
        // Sets the provided bank as the logged-in bank
        setLoggedBank(b);
        System.out.println("Bank account logged in successfully");
    }

    // Janos and Mia here
    private static void logout() {
        loggedBank = null;
        System.out.println("Logout successful. Session destroyed.");
    }

    /**
 * Allows the creation of a new bank by prompting the user to enter the bank name.
 * If the provided name is empty, it displays an error message and prompts the user again.
 * 
 * This method displays a menu header for creating a new bank and prompts the user to enter the bank name.
 * It ensures that the provided name is not empty before proceeding.
 * 
 * If the user enters an empty name, it displays an error message and prompts the user again until a valid name is provided.
 */
    public static void createNewBank() {
        Main.showMenuHeader("Create New Bank");
        Field<Integer,Integer> idField = new Field<>("ID", Integer.class, -1, new Field.IntegerFieldValidator());
        Field<String,String> nameField = new Field<>("Name", String.class, "", new Field.StringFieldValidator());
        Field<String,Integer> passcodeField = new Field<>("Passcode", String.class, 5, new Field.StringFieldLengthValidator());
        Field<Double,Double> depositLimitField = new Field<>("Deposit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double,Double> withdrawLimitField = new Field<>("Witdraw Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double,Double> creditLimitField = new Field<>("Credit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double,Double> processingFeeField = new Field<>("Processing Fee", Double.class, 0.0, new Field.DoubleFieldValidator());

        try {
            idField.setFieldValue("Bank ID: ",true);
            nameField.setFieldValue("Bank Name: ",false);
            passcodeField.setFieldValue("Bank Passcode: ");
            depositLimitField.setFieldValue("Deposit Limit (0 for default): ");
            withdrawLimitField.setFieldValue("Withdraw Limit (0 for default): ");
            creditLimitField.setFieldValue("Credit Limit (0 for default): ");
            processingFeeField.setFieldValue("Processing Fee (0 for default): ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format! Please enter a valid number.");
            return; 
        }

        int id = idField.getFieldValue();
        String name = nameField.getFieldValue();
        String passcode = passcodeField.getFieldValue();
        double depositLimit = depositLimitField.getFieldValue();
        double withdrawLimit = withdrawLimitField.getFieldValue();
        double creditLimit = creditLimitField.getFieldValue();
        double processingFee = processingFeeField.getFieldValue();
    
        Bank newBank;
        if (depositLimit == 0.0 && withdrawLimit == 0.0 && creditLimit == 0.0 && processingFee == 0.0) {
            newBank = new Bank(id, name, passcode);
        } else {
            newBank = new Bank(id, name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee);
        }

        System.out.println("Bank created successfully.");
        addBank(newBank);
    } 

    /**
     * Displays the menu of registered banks, showing each bank's name, ID, and position in the list.
     */
    public static void showBanksMenu() {
        if (BANKS == null || BANKS.isEmpty()) {
            System.out.println("No banks registered or created.");
            return;
        }
    
        System.out.println("Registered Banks:");
        System.out.println("-------------------------------------");
    
        for (int i = 0; i < BANKS.size(); i++) {
            Bank bank = BANKS.get(i);
            System.out.println((i + 1) + ". " + bank.getName());
            System.out.println("   ID: " + bank.getID());
            System.out.println("-------------------------------------");
        }
    }

    /**
     * Add a bank to the list of banks.
     *
     * @param  b    the bank to be added
     * @return      void
     */
    private static void addBank(Bank b) {
        BANKS.add(b);
    }

    // Janos and Mia here
    public static Bank getBank(Comparator<Bank> comparator, Bank bank) {
        for (Bank registeredBank : BANKS) {
            if (comparator.compare(registeredBank, bank) == 0) {
                return registeredBank;
            }
        }
        return null;
    }

   /**
 * Finds the Account object based on the provided account number across all registered banks.
 * 
 * @param accountNum The account number of the target Account.
 * @return The Account object if it exists, or null if not found.
 * 
 * This method iterates through all registered banks and checks if the provided account number exists in each bank.
 * If the account number exists in any bank, it retrieves the corresponding Account object using the bank's method
 * `getBankAccount()`. It returns the found Account object. If the account number is not found in any bank, it returns null.
 */
   public static Account findAccount(String accountNum) {
       for (Bank bank : getBANKS()) {
           for (Account acc : bank.getBANKACCOUNTS()) {
               if (acc.getAccountNumber().trim().equals(accountNum.trim())) {
                   return acc;
               }
           }
       }
       return null;
   }




    /**
     * Retrieves the size of the bank.
     *
     * @return         	the size of the bank
     */
    public static int bankSize() {
        return BANKS.size();
    }

    /**
     * A description of the entire Java function.
     *
     * @return         description of return value
     */
    public static ArrayList<Bank> getBANKS() {
        if (BANKS == null) {
            BANKS = new ArrayList<>();
            return BANKS;
        } else if (BANKS.isEmpty()) {
            return BANKS;   
        }
        return BANKS;
    }

    /**
     * Retrieves the currently logged-in bank instance.
     *
     * @return The logged-in bank instance.
     */
    public static Bank getLoggedBank() {
        return loggedBank;
    }

    /**
     * Sets the logged bank.
     *
     * @param  loggedBank the bank to be set as logged
     */
    public static void setLoggedBank(Bank loggedBank) {
        BankLauncher.loggedBank = loggedBank;
    }
}