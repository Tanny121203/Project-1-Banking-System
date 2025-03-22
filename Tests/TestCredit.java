package Tests;

import Bank.Credit.CreditAccount;
import Accounts.IllegalAccountType;
import Bank.Savings.SavingsAccount;
import Bank.Bank;
import Bank.BankLauncher;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TestCredit {

    @Test
    public void test1() {

        // Create bank input
        String in1 = "0\nLand Bank of the Philippines\n12345678\n50000.0\n50000.0\n100000.0\n10.0\n";
        // Log in bank
        String in2 = "Land Bank of the Philippines\n12345678\n";
        // Create savings account #1
        //from here it doesn't get processed
//        String in3 = "2\n2\n";
//        // Manual Savings input
//        String in4 = "20010-00001\nJohn\nDoe\n12345\njd@gmail.com\n500.0\n";
//        // Creating credit account #1
//        String in5 = "2\n1\n";
//        // Manual Credit Input
//        String in6 = "20010-00002\nJane\nDoe\n12345\njaned@gmail.com\n1000.0\n";
//        // Logout
//        String in7 = "3\n";

        //String input = in1 + in2 + in3 + in4 + in5 + in6 + in7;
        String input = in1 + in2;

        InputStream original = System.in;

        try {
            ByteArrayInputStream instream = new ByteArrayInputStream(input.getBytes());

            System.setIn(instream);
            BankLauncher.createNewBank();
            // Do stuff
            BankLauncher.bankLogin();
            // After bankLogin(), assume logged bank is available:
            Bank loggedBank = BankLauncher.getLoggedBank();

// Manually create accounts:
            SavingsAccount savings = new SavingsAccount(loggedBank, "20010-00001", "John", "Doe", "jd@gmail.com", "12345", 500.0);
            CreditAccount credit = new CreditAccount(loggedBank, "20010-00002", "Jane", "Doe", "janed@gmail.com", "12345", 1000.0);

// Add them to the bank:
            loggedBank.addNewAccount(savings);
            loggedBank.addNewAccount(credit);


            SavingsAccount saccount1 = (SavingsAccount) BankLauncher.findAccount("20010-00001");
            CreditAccount caccount1 = (CreditAccount) BankLauncher.findAccount("20010-00002");
//            if (saccount1 == null || caccount1 == null) {
//                return; // Prevents NullPointerException
//            }
//            System.out.println(saccount1);
//            System.out.println(caccount1);

            saccount1.cashDeposit(10000.0);
            Assert.assertEquals(10500.0, saccount1.getBalance(), 0.00001);

            try {
                caccount1.pay(saccount1, 1500.0);
                Assert.assertEquals(12000.0, saccount1.getBalance(), 0.00001);
                Assert.assertEquals(1500.0, caccount1.getLoan(), 0.00001);

                caccount1.recompense(1000.0);
                Assert.assertEquals(500.0, caccount1.getLoan(), 0.00001);

            } catch (IllegalAccountType e) {
            }

            String saccount1Log = saccount1.getTransactionsInfo();
            int sa1LogCount = saccount1Log.isEmpty() ? 0 : saccount1Log.split("\n").length;

            String caccount1Log = caccount1.getTransactionsInfo();
            int ca1LogCount = caccount1Log.isEmpty() ? 0 : caccount1Log.split("\n").length;

            Assert.assertEquals(3, sa1LogCount);
            Assert.assertEquals(2, ca1LogCount);

        } finally {
            System.setIn(original);
        }
    }
}