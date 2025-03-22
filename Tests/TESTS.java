package Tests;

import Account.Account;
import Accounts.IllegalAccountType;
import Accounts.Transaction;
import Bank.Credit.CreditAccount;
import Bank.Savings.SavingsAccount;
import Bank.Bank;
import Bank.BankLauncher;

public class TESTS {
    public static void main(String[] args) throws IllegalAccountType {
        Bank bank = new Bank(1, "Land Bank", "12345");
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

        credit.addNewTransaction(credit.getAccountNumber(), Transaction.Transactions.Recompense, "A successful recompense.");
        credit.addNewTransaction(credit.getAccountNumber(), Transaction.Transactions.Payment, "A successful payment.");
        credit.addNewTransaction(credit.getAccountNumber(), Transaction.Transactions.Recompense, "A successful recompense.");
        Bank bank2 = new Bank(2, "BDO", "12345", 20000.0, 20000.0, 50000.0, 15);
        SavingsAccount savings1 = new SavingsAccount(bank, "005", "Bull", "Seye", "bs@gmail.com", "1234", 0);
        bank2.addNewAccount(savings1);

        System.out.println(credit.getOwnerFullName());
        System.out.println(credit2.getOwnerFullName());
        System.out.println(credit3.getOwnerFullName());
        System.out.println(savings.getOwnerFullName());
        System.out.println(savings2.getOwnerFullName());
        System.out.println(bank2.getDEPOSITLIMIT());
        savings1.cashDeposit(21000);

        System.out.println(savings1.getAccountBalanceStatement());
        Account testfind = BankLauncher.findAccount("001");
//        if (testfind != null) {
//            System.out.println("Found account: " + testfind.getOwnerFullName());
//        } else {
//            System.out.println("No account found with account number 001");
//        }
        System.out.println(testfind);
        //System.out.println(bank);
        System.out.println(bank.getBANKACCOUNTS());
        System.out.println();
    }
}
