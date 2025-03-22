package Bank;

import java.util.ArrayList;
import Account.Account;
import Bank.Credit.CreditAccount;
import Main.Field;
import Main.FieldValidator;
import Main.Main;
import Bank.Savings.SavingsAccount;
import Interfaces.Comparator;
    
public class Bank {
    private int ID;
    private String name, passcode;
    private final double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;
    private double PROCESSINGFEE;
    private final ArrayList<Account> BANKACCOUNTS;
    
    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = 50000.0;
        this.WITHDRAWLIMIT = 50000.0;
        this.CREDITLIMIT = 100000.0;
        this.PROCESSINGFEE = 10.0;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double PROCESSINGFEE) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = DEPOSITLIMIT;
        this.WITHDRAWLIMIT = WITHDRAWLIMIT;
        this.CREDITLIMIT = CREDITLIMIT;
        this.PROCESSINGFEE = PROCESSINGFEE;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public double getDEPOSITLIMIT() {
        return DEPOSITLIMIT;
    }

    public double getWITHDRAWLIMIT() {
        return WITHDRAWLIMIT;
    }

    public double getCREDITLIMIT() {
        return CREDITLIMIT;
    }

    public double getPROCESSINGFEE() {
        return PROCESSINGFEE;
    }

    public ArrayList<Account> getBANKACCOUNTS() {
        return BANKACCOUNTS;
    }

    /**
     * Show accounts of the specified account type, sorted based on the type of account.
     *
     * @param  accountType	The class of the account type to be shown
     * @return         		void
     */
    public <T> void showAccounts(Class<T> accountType) {
        if (accountType == Account.class) {
            for (Account account : getBANKACCOUNTS()) {
                System.out.println(account);
            }
            return;
        }
        for (Account account : getBANKACCOUNTS()) {
            if (account.getClass() == accountType) {
                System.out.println(account);
            }
        }
    }

    /**
     * Retrieves a bank account from the specified bank using the account number.
     *
     * @param  bank       the bank from which to retrieve the account
     * @param  accountNum the account number of the bank account
     * @return            the bank account with the specified account number, or null if not found
     */
    public Account getBankAccount(Bank bank, String accountNum) {
        for (Account accs : BANKACCOUNTS) {
            if (accs.getAccountNumber().equals(accountNum)) {
                return accs;
            }
        }
        return null;
    }
  
    /**
     * Creates a new account by prompting the user for account type, first name, last name, email, username, and pin.
     *
     * @throws NumberFormatException       if the input is not a valid number format
     * @throws IllegalArgumentException    if the input violates a condition
     * @return                            an ArrayList of Field objects representing the user's input
     */
    public ArrayList<Field<String, ?>> createNewAccount() throws IllegalArgumentException {
        FieldValidator<String, String> validateString = new Field.StringFieldValidator();
        ArrayList<Field<String, ?>> createNew = new ArrayList<>();
        
        Main.showMenuHeader("Create New Account");
        // Prompt for first name
        String firstName;
        while (true) {
            try {
                Field<String, String> firstNameField = new Field<>("Enter first name: ", String.class, "3", validateString);
                firstNameField.setFieldValue("Enter first name: ");
                firstName = firstNameField.getFieldValue();
                if (firstName.length() >= 3) {
                    createNew.add(firstNameField);
                    break;
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid name.");
            }
        }
        // Prompt for last name
        String lastName;
        while (true) {
            try {
                Field<String, String> lastNameField = new Field<>("Enter last name: ", String.class, "3", validateString);
                lastNameField.setFieldValue("Enter last name: ");
                lastName = lastNameField.getFieldValue();
                if (lastName.length() >= 3) {
                    createNew.add(lastNameField);
                    break;
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid name.");
            }
        }
    
        // Prompt for email
        String email;
        while (true) {
            try {
                Field<String, String> emailField = new Field<>("Enter email: ", String.class, "", new Field.StringFieldValidator());
                emailField.setFieldValue("Enter email: ");
                email = emailField.getFieldValue();
                if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:gmail|yahoo|\\w+\\.)+[a-zA-Z]{2,}$")) {
                    createNew.add(emailField);
                    break;
                } else {
                    System.out.println("Invalid email! Please input a valid email.");
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid email address.");
            }
        }

        // Prompt for account number
        String accountNum;
        while (true) {
            try {
                Field<String, String> accountNumField = new Field<>("Enter account number: ", String.class, "4", validateString);
                accountNumField.setFieldValue("Enter account number: ");
                int num = Integer.parseInt(accountNumField.getFieldValue());
                accountNum = String.format("%d", num);
                if (accountExists(this, accountNum)) {
                    System.out.println("Sorry, this account number already exists. Please input a new one.");
                    continue;
                }
                if (accountNum.length() >= 4) {
                    createNew.add(accountNumField);
                    break;
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid account number.");
            }
        }

        // Prompt for pin
        String pin;
        while (true) {
            try {
                Field<String, String> pinField = new Field<>("Enter pin: ", String.class, "", validateString);
                pinField.setFieldValue("Enter pin: ");
                pin = pinField.getFieldValue();
                String pinTemp = pin;
                if (pinTemp.length() >= 4) {
                    createNew.add(pinField);
                    pin = pinTemp;
                    break;
                } else {
                    System.out.println("Invalid input! Please input a valid pin (at least 4 digits).");
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid pin (at least 4 digits).");
            }
        }    

        return createNew;


    }
            
    /**
     * Create a new credit account for the bank customer.
     *
     * @return         	The newly created CreditAccount
     */
    public CreditAccount createNewCreditAccount() {
        ArrayList<Field<String, ?>> fields = createNewAccount();    
        Bank bank = BankLauncher.getLoggedBank();
        CreditAccount credit;

        String firstName = (String) fields.get(0).getFieldValue();
        String lastName = (String) fields.get(1).getFieldValue();
        String email = (String) fields.get(2).getFieldValue();
        String accountNum = (String) fields.get(3).getFieldValue();
        String pin = (String) fields.get(4).getFieldValue();
        
        credit = new CreditAccount(bank, accountNum, firstName, lastName, email, pin, CREDITLIMIT);
        System.out.println("Account created successfully!");
        return credit;
    }

    /**
     * Creates a new savings account with the provided information and initial balance.
     *
     * @return         	the newly created SavingsAccount
     */
    public SavingsAccount createNewSavingsAccount() {
        //Create a new account using the common account creation method
        ArrayList<Field<String, ?>> fields = createNewAccount();
        
        //Create a new Bank instance using account information
        Bank bank = BankLauncher.getLoggedBank();
        SavingsAccount savings;
        
        String firstName = (String) fields.get(0).getFieldValue();
        String lastName = (String) fields.get(1).getFieldValue();
        String email = (String) fields.get(2).getFieldValue();
        String accountNum = (String) fields.get(3).getFieldValue();
        String pin = (String) fields.get(4).getFieldValue();

        while (true) {
            Field<Double, Double> initialBalanceField = new Field<>("InitialBalance", Double.class, 0.0, new Field.DoubleFieldValidator());
            initialBalanceField.setFieldValue("Enter initial balance: ", true);
    
            double initialBalance = initialBalanceField.getFieldValue();
            
            //Validate and create the SavingsAccount if initial balance is non-negative
            if (initialBalance >= 0) {
                savings = new SavingsAccount(bank, accountNum, firstName, lastName, email, pin, initialBalance);
                System.out.println("Account created successfully!");
                return savings;
            } else {
                System.out.println("Initial balance must be non-negative");
                continue;
            }
        }
    }

    /**
     * Adds a new account to the bank accounts list if it doesn't already exist.
     *
     * @param  account   the account to be added
     */
    public void addNewAccount(Account account) throws IllegalArgumentException, NullPointerException {
        if (account == null) {
            throw new NullPointerException("The account cannot be null.");
        }

        String accountNumStr = account.getAccountNumber().toString();
        if (accountExists(this, accountNumStr)) {
            throw new IllegalArgumentException(
                "An account with the same account number " +
                accountNumStr + " already exists!"
            );
        }

        BANKACCOUNTS.add(account);
    }

    /**
     * Check if the account exists in the bank.
     *
     * @param  bank       the bank object
     * @param  accountNum the account number
     * @return           true if the account exists, false otherwise
     */
    public static boolean accountExists(Bank bank, String accountNum) {
        for (Account accs : bank.getBANKACCOUNTS()) {
            if (accs.getAccountNumber().toString().equals(accountNum)) {
                return true; 
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the entire object, including bank name and account details.
     *
     * @return         	string representation of the object
     */
    public String toString() {
        String res = "Bank Name: " + name + "\n";

        int i = 0;
        while (i < BANKACCOUNTS.size()) {
            Account account = BANKACCOUNTS.get(i);
            String accountType = "";

            if (account instanceof CreditAccount) {
                accountType = "Credit Account";

            } else if (account instanceof SavingsAccount) {
                accountType = "Savings Account";

            } else {
                accountType = "Account Type not listed in Bank!";
            }

            res += "Account Type: " + accountType + "\n";
            res += "Account Details: " + account.toString() + "\n";
            i++;
        }

        return res;
    }

    public static class BankComparator implements Comparator<Bank> {
        /**
         * Compares two Bank objects based on their ID, name, and passcode.
         *
         * @param  b1  the first Bank object to compare
         * @param  b2  the second Bank object to compare
         * @return     returns 0 if the two Bank objects are equal, -1 otherwise
         */
        @Override
        public int compare(Bank b1, Bank b2) {
            return (b1.getID() == b2.getID() &&
                    b1.getName().equals(b2.getName()) &&
                    b1.getPasscode().equals(b2.getPasscode())) ? 0 : -1;
        }
    }

    // Inner class for BankIdComparator
    public static class BankIdComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return Integer.compare(b1.getID(), b2.getID());
        }
    }

    // Inner class for BankCredentialsComparator
    public static class BankCredentialsComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            String b1Pass = b1.getPasscode();
            String b2Pass = b2.getPasscode();
            String b1Name = b1.getName();
            String b2Name = b2.getName();

            if (!b1Pass.equals(b2Pass)) {
                return b1Pass.compareTo(b2Pass);
            }

            if (!b1Name.equals(b2Name)) {
                return b1Name.compareTo(b2Name);
            }

            return 0;
        }
    }

}
