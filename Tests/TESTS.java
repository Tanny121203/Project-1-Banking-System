package Tests;

import Account.Account;
import Accounts.IllegalAccountType;
import Accounts.Transaction;
import Bank.Bank;
import Bank.BankLauncher;
import Bank.Credit.CreditAccount;
import Bank.Savings.SavingsAccount;

import java.lang.reflect.Method;

public class TESTS {
    public static void main(String[] args) throws IllegalAccountType {
        // Create bank instance
        Bank bank = new Bank(1, "Land Bank of the Philippines", "12345", 50000.0, 50000.0, 100000.0, 10.0);

        // Use reflection to call the private addBank method in BankLauncher
        try {
            Method addBankMethod = BankLauncher.class.getDeclaredMethod("addBank", Bank.class);
            addBankMethod.setAccessible(true);
            addBankMethod.invoke(null, bank);  // Invoke the static method with bank as parameter
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create accounts and add them to the bank
        CreditAccount credit = new CreditAccount(bank, "001", "Nigga", "Khent", "jg@gmail.com", "1234", 100);
        SavingsAccount savings = new SavingsAccount(bank, "002", "King", "Kong", "cb@gmail.com", "1234", 25000.0);
        CreditAccount credit2 = new CreditAccount(bank, "003", "Bitch", "Oten", "va@gmail.com", "1234", 100);
        CreditAccount credit3 = new CreditAccount(bank, "005", "Ambatu", "Khan", "jg@gmail.com", "1234", 100);

        bank.addNewAccount(credit);
        bank.addNewAccount(savings);
        bank.addNewAccount(credit2);
        bank.addNewAccount(credit3);

        SavingsAccount savings2 = new SavingsAccount(bank, "004", "Am", "Kamming", "bs@gmail.com", "1234", 0);
        bank.addNewAccount(savings2);

        // Add some transactions to credit account
        credit.addNewTransaction(credit.getAccountNumber(), Transaction.Transactions.Recompense, "A successful recompense.");
        credit.addNewTransaction(credit.getAccountNumber(), Transaction.Transactions.Payment, "A successful payment.");
        credit.addNewTransaction(credit.getAccountNumber(), Transaction.Transactions.Recompense, "A successful recompense.");

        // Create another bank and register it similarly (if needed)
        Bank bank2 = new Bank(2, "BDO", "12345", 20000.0, 20000.0, 50000.0, 15);
        try {
            Method addBankMethod2 = BankLauncher.class.getDeclaredMethod("addBank", Bank.class);
            addBankMethod2.setAccessible(true);
            addBankMethod2.invoke(null, bank2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SavingsAccount savings1 = new SavingsAccount(bank2, "005", "Bull", "Seye", "bs@gmail.com", "1234", 0);
        bank2.addNewAccount(savings1);

        // Debug: Print out accounts in bank 1 and bank 2
        System.out.println("Accounts in bank 1 (" + bank.getName() + "): " + bank.getBANKACCOUNTS());
        System.out.println("Accounts in bank 2 (" + bank2.getName() + "): " + bank2.getBANKACCOUNTS());

        // Use findAccount to retrieve an account (by account number as a String)
        Account testfind = BankLauncher.findAccount("001");
        System.out.println("Found account: " + (testfind != null ? testfind.getOwnerFullName() : "null"));
    }
}
