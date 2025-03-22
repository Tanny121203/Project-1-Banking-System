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
import java.util.ArrayList;

public class TestSavings {

    /**
     * Test fund transfer in 1 same bank
     */
//    @Test
//    public void test1() {
//
//        // Create bank input
//        String in1 = "Land Bank of the Philippines\n12345678\n50000.0\n50000.0\n100000.0\n10.0\n";
//        // Log in bank
//        String in2 = "Land Bank of the Philippines\n12345678\n";
//        // Create savings account #1
//        String in3 = "2\n2\n";
//        // Manual Savings input
//        String in4 = "20010-00001\n1234\nJohn\nDoe\njd@gmail.com\n500.0\n";
//        // Creating savings account #2
//        String in5 = "2\n2\n";
//        // Manual Credit Input
//        String in6 = "20010-00002\n1234\nJane\nDoe\njaned@gmail.com\n1000.0\n";
//        // Logout
//        String in7 = "3\n";
//
//        String input = in1 + in2 + in3 + in4 + in5 + in6 + in7;
//
//        InputStream original = System.in;
//
//        try {
//            ByteArrayInputStream instream = new ByteArrayInputStream(input.getBytes());
//
//            System.setIn(instream);
//            BankLauncher.createNewBank();
//            // Do stuff
//            BankLauncher.bankLogin();
//
//            SavingsAccount saccount1 = (SavingsAccount) BankLauncher.findAccount("20010-00001");
//            SavingsAccount saccount2 = (SavingsAccount) BankLauncher.findAccount("20010-00002");
//
//            saccount1.cashDeposit(1000.0);
//            Assert.assertEquals(1500.0, saccount1.getAccountBalance(), 0.00001);
//            saccount2.cashDeposit(10000.0);
//            Assert.assertEquals(11000.0, saccount2.getAccountBalance(), 0.00001);
//            Assert.assertEquals(false, saccount2.cashDeposit(1000000000.0));
//            try {
//                saccount2.transfer(saccount1, 5000.0);
//                Assert.assertEquals(6000.0, saccount2.getAccountBalance(), 0.00001);
//                Assert.assertEquals(6500.0, saccount1.getAccountBalance(), 0.00001);
//
//                Assert.assertThrows(IllegalAccountType.class, () -> {
//                    saccount2.transfer(new CreditAccount(new Bank(1, "Test","2323"), null, null, null, null, null), 2000.0);
//                });
//            } catch(IllegalAccountType exception) {
//            }
//
//            String saccount2logs = saccount2.getTransactionsInfo();
//            int sacc2logCount = saccount2logs.isEmpty() ? 0 : saccount2logs.split("\n").length;
//
//            String saccount1logs = saccount1.getTransactionsInfo();
//            int sacc1logCount = saccount1logs.isEmpty() ? 0 : saccount1logs.split("\n").length;
//
//            Assert.assertEquals(3, sacc2logCount);
//            Assert.assertEquals(3, sacc1logCount);
//        } finally {
//            System.setIn(original);
//        }
//    }
    @Test
    public void test1() {

        // Create bank input
        String in1 = "0\nLand Bank of the Philippines\n12345678\n50000.0\n50000.0\n100000.0\n10.0\n";
        // Log in bank
        String in2 = "Land Bank of the Philippines\n12345678\n";
        // Create savings account #1
        // still doesn't work from here
//        String in3 = "2\n2\n";
//        // Manual Savings input
//        String in4 = "20010-00001\n1234\nJohn\nDoe\njd@gmail.com\n500.0\n";
//        // Creating savings account #2
//        String in5 = "2\n2\n";
//        // Manual Credit Input
//        String in6 = "20010-00002\n1234\nJane\nDoe\njaned@gmail.com\n1000.0\n";
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
            Bank loggedBank = BankLauncher.getLoggedBank();

// Manually create accounts:
            SavingsAccount savings = new SavingsAccount(loggedBank, "20010-00001", "John", "Doe", "jd@gmail.com", "12345", 500.0);
            SavingsAccount savings1 = new SavingsAccount(loggedBank, "20010-00002", "Jane", "Doe", "janed@gmail.com", "12345", 1000.0);

// Add them to the bank:
            loggedBank.addNewAccount(savings);
            loggedBank.addNewAccount(savings1);
            SavingsAccount saccount1 = (SavingsAccount) BankLauncher.findAccount("20010-00001");
            SavingsAccount saccount2 = (SavingsAccount) BankLauncher.findAccount("20010-00002");

            saccount1.cashDeposit(1000.0);
            Assert.assertEquals(1500.0, saccount1.getBalance(), 0.00001);
            saccount2.cashDeposit(10000.0);
            Assert.assertEquals(11000.0, saccount2.getBalance(), 0.00001);
            Assert.assertEquals(false, saccount2.cashDeposit(1000000000.0));
            try {
                saccount2.transfer(saccount1, 5000.0);
                Assert.assertEquals(6000.0, saccount2.getBalance(), 0.00001);
                Assert.assertEquals(6500.0, saccount1.getBalance(), 0.00001);

                Assert.assertThrows(IllegalAccountType.class, () -> {
                    saccount2.transfer(new CreditAccount(new Bank(1, "Test","2323"), "20010-00001", "Ni", "Guard", "null", "1111",2000.00), 2000.0);
                });
            } catch(IllegalAccountType exception) {
            }

            String saccount2logs = saccount2.getTransactionsInfo();
            int sacc2logCount = saccount2logs.isEmpty() ? 0 : saccount2logs.split("\n").length;

            String saccount1logs = saccount1.getTransactionsInfo();
            int sacc1logCount = saccount1logs.isEmpty() ? 0 : saccount1logs.split("\n").length;

            Assert.assertEquals(3, sacc2logCount);
            Assert.assertEquals(3, sacc1logCount);
        } finally {
            System.setIn(original);
        }
    }

    /**
     * Test fund transfer on 2 different banks
     */
//    @Test
//    public void test2() {
//        // Create bank 1 input
//        String in1 = "BDO\naabbccdd\n50000.0\n50000.0\n100000.0\n10.0\n";
//        // Create bank 2 input
//        String bn2 = "BDP\n12345667\n75000.0\n75000.0\n150000.0\n15.0\n";
//        // Log in bank 1
//        String in2 = "BDO\naabbccdd\n";
//        // Create savings account #1 for bank #1
//        String in3 = "2\n2\n";
//        // Manual Savings 1 input
//        String in4 = "20010-00001\n1234\nJohn\nDoe\njd@gmail.com\n500.0\n";
//        // Creating credit account #1 for bank #1
//        String in5 = "2\n1\n";
//        // Manual Credit Account 1 Input
//        String in6 = "20010-00002\n1234\nJane\nDoe\njaned@gmail.com\n";
//        // Logout
//        String in7 = "3\n";
//
//        // Login on Bank 2
//        String log2 = "BDP\n12345667\n";
//        // Creating savings in Bank 2
//        String acc2 = "2\n2\n";
//        // Manual Bank 2 Savings input
//        String acc3 = "20011-00001\n5566\nJose\nRizel\njprizal@gmail.com\n5000.0\n";
//        String logout2 = "3\n";
//
//        String input = in1 + bn2 + in2 + in3 + in4 + in5 + in6 +
//                in7 + log2 + acc2 + acc3 + logout2;
//
//        InputStream original = System.in;
//
//        try {
//            ByteArrayInputStream instream = new ByteArrayInputStream(input.getBytes());
//
//            System.setIn(instream);
//            BankLauncher.createNewBank();
//            BankLauncher.createNewBank();
//            // Do stuff for first bank
//            BankLauncher.bankLogin();
//            // Do stuff on second bank
//            BankLauncher.bankLogin();
//
//            Bank bdo = BankLauncher.getBank(new Bank.BankIdComparator(), new Bank(0, null, null));
//            Bank bdp = BankLauncher.getBank(new Bank.BankIdComparator(), new Bank(1, null, null));
//
//            SavingsAccount bank1sa = (SavingsAccount) BankLauncher.findAccount("20010-00001");
//            SavingsAccount bank2sa = (SavingsAccount) BankLauncher.findAccount("20011-00001");
//            CreditAccount bank1ca = (CreditAccount) BankLauncher.findAccount("20010-00002");
//
//            bank1sa.cashDeposit(5000.0);
//            Assert.assertEquals(5500.0, bank1sa.getAccountBalance(), 0.00001);
//            try {
//                bank1sa.transfer(bank2sa.getBank(), bank2sa, 4000.0);
//                Assert.assertEquals(9000.0, bank2sa.getAccountBalance(), 0.00001);
//                Assert.assertEquals(5500.0 - 4000.0 - bdo.getProcessingFee(), bank1sa.getAccountBalance(), 0.00001);
//                Assert.assertThrows(IllegalAccountType.class, () -> {
//                    bank1sa.transfer(bank1ca, 1000.0);
//                });
//
//                // Check logs
//
//                String bank1saLogs = bank1sa.getTransactionsInfo();
//                int bank1saCount = bank1saLogs.isEmpty() ? 0 : bank1saLogs.split("\n").length;
//
//                String bank2saLogs = bank2sa.getTransactionsInfo();
//                int bank2saCount = bank2saLogs.isEmpty() ? 0 : bank2saLogs.split("\n").length;
//
//                String bank1caLogs = bank1ca.getTransactionsInfo();
//                int bank1caCount = bank1caLogs.isEmpty() ? 0 :bank1caLogs.split("\n").length;
//
//                Assert.assertEquals(3, bank1saCount);
//                Assert.assertEquals(2, bank2saCount);
//                Assert.assertEquals(1, bank1caCount);
//            } catch (IllegalAccountType exception) {
//
//            }
//        } finally {
//            System.setIn(original);
//        }
//    }
    @Test
    public void test2() {
        // --- Step 1: Create two banks interactively ---
        // Bank 1 input: BDO with processing fee 10.0
        String in1 = "0\nBDO\n12345667\n50000.0\n50000.0\n100000.0\n10.0\n";
        // Bank 2 input: BDP with processing fee 15.0
        String bn2 = "1\nBDP\n12345667\n75000.0\n75000.0\n150000.0\n15.0\n";
        // Combine only the bank creation inputs (no login inputs)
        String banksInput = in1 + bn2;

        InputStream original = System.in;
        try {
            // Set System.in to the banks creation input
            ByteArrayInputStream instream = new ByteArrayInputStream(banksInput.getBytes());
            System.setIn(instream);

            // Create two banks interactively:
            BankLauncher.createNewBank();  // Creates Bank 1 (BDO)
            BankLauncher.createNewBank();  // Creates Bank 2 (BDP)


            // Retrieve the banks directly from BankLauncher.getBANKS()
            ArrayList<Bank> banks = BankLauncher.getBANKS();
            System.out.println("Total banks created: " + BankLauncher.bankSize());
            System.out.println("All banks:");
            for (Bank b : banks) {
                System.out.println("Bank Name: " + b.getName() + ", ID: " + b.getID() +
                        ", Processing Fee: " + b.getPROCESSINGFEE());
            }
            // According to your debug output, the first bank (BDO) is at index 0 and second (BDP) at index 1.
            Bank bdo = banks.get(0);  // Expected processing fee 10.0
            Bank bdp = banks.get(1);  // Expected processing fee 15.0
            System.out.println(bdo.getPROCESSINGFEE());
            System.out.println("Bank 1: " + (bdo != null ? bdo.getName() : "Not Found"));
            System.out.println("Bank 2: " + (bdp != null ? bdp.getName() : "Not Found"));
            Assert.assertNotNull("Bank BDO should not be null", bdo);
            Assert.assertNotNull("Bank BDP should not be null", bdp);
            Assert.assertEquals(2, BankLauncher.bankSize());

            // --- Step 2: Manually create accounts using the retrieved bank objects ---
            // For Bank 1 (BDO): create a SavingsAccount and a CreditAccount.
            SavingsAccount bank1sa = new SavingsAccount(bdo, "20010-00001", "John", "Doe", "jd@gmail.com", "1234", 500.0);
            CreditAccount bank1ca = new CreditAccount(bdo, "20010-00002", "Jane", "Doe", "janed@gmail.com", "1234", 1000.0);
            bank1ca.setBank(bdo);
            bank1sa.setBank(bdo);
            bdo.addNewAccount(bank1sa);
            bdo.addNewAccount(bank1ca);

            // For Bank 2 (BDP): create a SavingsAccount.
            SavingsAccount bank2sa = new SavingsAccount(bdp, "20011-00001", "Jose", "Rizel", "jprizal@gmail.com", "5566", 5000.0);
            bdp.addNewAccount(bank2sa);

            // --- Step 3: Retrieve accounts using findAccount() ---
            SavingsAccount  foundBank1sa = (SavingsAccount) BankLauncher.findAccount("20010-00001");
            SavingsAccount foundBank2sa = (SavingsAccount) BankLauncher.findAccount("20011-00001");
            CreditAccount foundBank1ca = (CreditAccount) BankLauncher.findAccount("20010-00002");
            Assert.assertNotNull("Bank 1 Savings account should be found", foundBank1sa);
            Assert.assertNotNull("Bank 2 Savings account should be found", foundBank2sa);
            Assert.assertNotNull("Bank 1 Credit account should be found", foundBank1ca);

            // --- Step 4: Perform operations on Bank 1 accounts ---
            // Deposit additional funds into Bank 1 Savings account.
            foundBank1sa.cashDeposit(5000.0);  // 500 + 5000 = 5500
            Assert.assertEquals(5500.0, foundBank1sa.getBalance(), 0.00001);

            try {
                // Transfer 4000 from bank1sa (sender) to bank2sa (receiver)
                foundBank1sa.transfer(bank2sa.getBank(), bank2sa, 4000.0);
                // Expected for Bank 2 Savings account: 5000 + 4000 = 9000
                Assert.assertEquals(9000.0, bank2sa.getBalance(), 0.00001);
                // Expected for Bank 1 Savings account: 5500 - 4000 - processing fee of bdo (10.0) = 1490.0
                System.out.println("foundBank1sa's bank processing fee: " + foundBank1sa.getBank().getPROCESSINGFEE());
                Assert.assertEquals(5500.0 - 4000.0 - bdo.getPROCESSINGFEE(), foundBank1sa.getBalance(), 0.00001);

                Assert.assertThrows(IllegalAccountType.class, () -> {
                    foundBank1sa.transfer(bank1ca.getBank(), bank1ca, 1000.0);
                });
            } catch (IllegalAccountType exception) {
                // Handle exception if needed.
            }

            // --- Step 5: Verify transaction logs for each account ---
            String bank1saLogs = foundBank1sa.getTransactionsInfo();
            int bank1saCount = bank1saLogs.isEmpty() ? 0 : bank1saLogs.split("\n").length;

            String bank2saLogs = foundBank2sa.getTransactionsInfo();
            int bank2saCount = bank2saLogs.isEmpty() ? 0 : bank2saLogs.split("\n").length;

            String bank1caLogs = foundBank1ca.getTransactionsInfo();
            int bank1caCount = bank1caLogs.isEmpty() ? 0 : bank1caLogs.split("\n").length;

            Assert.assertEquals(3, bank1saCount);
            Assert.assertEquals(2, bank2saCount);
            Assert.assertEquals(1, bank1caCount);

        } finally {
            System.setIn(original);
        }
    }
}

