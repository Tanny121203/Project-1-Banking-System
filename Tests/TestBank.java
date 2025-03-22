package Tests;

import Account.Account;
import Bank.Credit.CreditAccount;
import Bank.Savings.SavingsAccount;
import Bank.Bank;
import Bank.BankLauncher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestBank {

    private final InputStream or = System.in;


    @BeforeEach
    public void reset() {
        System.setIn(or);
    }

    @AfterEach
    public void restore() {
        System.setIn(or);
    }

    @Test
    public void test1() {
        InputStream original = System.in;

        try {
            // Test user input
            String input =
                    "0\n" +
                    "Land Bank of the Philippines\n" +
                            "12345678\n" +
                            "50000.0\n" +
                            "50000.0\n" +
                            "100000.0\n" +
                            "10.0\n";

            ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
            // Let System feed the testInput above into the Scanner inputs when they are called
            System.setIn(testInput);

            BankLauncher.createNewBank();

            Bank bank = BankLauncher.getBank(new Bank.BankIdComparator(), new Bank(0, "Land Bank of the Philippines", "12345678"));

            Assert.assertEquals("Land Bank of the Philippines", bank.getName());
            Assert.assertEquals(100000.0, bank.getCREDITLIMIT(), 0.00001);
            Assert.assertEquals(50000.0, bank.getDEPOSITLIMIT(), 0.00001);
            Assert.assertEquals(50000.0, bank.getDEPOSITLIMIT(), 0.00001);
            Assert.assertEquals(1, BankLauncher.bankSize());
        } finally {
            System.setIn(original);
        }
    }

    @Test
    public void test2() {
        InputStream original = System.in;

        try {
            String in1 =
                    "0\n" +
                    "Land Bank of the Philippines\n" +
                    "12345678\n" +
                    "50000.0\n" +
                    "50000.0\n" +
                    "100000.0\n" +
                    "10.0\n";
            String in2 =
                    "1\n" +
                    "Iglesia ni Catane\n" +
                    "001122\n" +
                    "75000.0\n" +
                    "75000.0\n" +
                    "150000.0\n" +
                    "10.0\n";

            String myinput = in1 + in2;

            ByteArrayInputStream instream1 = new ByteArrayInputStream(myinput.getBytes());

            // Create first bank
            System.setIn(instream1);
            BankLauncher.createNewBank();
            // Create second bank
            BankLauncher.createNewBank();

            // Get two banks
            Bank bank1 = BankLauncher.getBank(new Bank.BankIdComparator(), new Bank(0, null, null));
            Bank bank2 = BankLauncher.getBank(new Bank.BankIdComparator(), new Bank(1, null, null));

            Assert.assertEquals(2, BankLauncher.bankSize());

            // Test Bank 1 values
            Assert.assertEquals("Land Bank of the Philippines", bank1.getName());
            Assert.assertEquals(100000.0, bank1.getCREDITLIMIT(), 0.00001);
            Assert.assertEquals(50000.0, bank1.getDEPOSITLIMIT(), 0.00001);
            Assert.assertEquals(50000.0, bank1.getDEPOSITLIMIT(), 0.00001);

            // Test Bank 2 values
            Assert.assertEquals("Iglesia ni Catane", bank2.getName());
            Assert.assertEquals(150000.0, bank2.getCREDITLIMIT(), 0.00001);
            Assert.assertEquals(75000.0, bank2.getDEPOSITLIMIT(), 0.00001);
            Assert.assertEquals(75000.0, bank2.getDEPOSITLIMIT(), 0.00001);
        } finally {
            System.setIn(original);
        }
    }

    // Test Create an Account
    @Test
    public void test3() {
        InputStream original = System.in;

        try {
            // Create bank input
            String in1 =
                    "0\n" +
                            "Land Bank of the Philippines\n" +
                            "12345678\n" +
                            "50000.0\n" +
                            "50000.0\n" +
                            "100000.0\n" +
                            "10.0\n";
            // Log in bank
            String in2 = "Land Bank of the Philippines\n" +
                    "12345678\n";
            // Create accounts menu input
            String in3 =
                    "2\n" +
                    "2\n";
            // Manual Savings input (correct order)
            String in4 =
                    "John\n" +
                    "Doe\n" +
                    "jd@gmail.com\n" +
                    "87654321\n" +
                    "12345\n" +
                    "500.0\n";
            // Creating credit account menu input
            String in5 = "2\n" +
                    "1\n";
            // Manual Credit Input (ensure this order matches the expectation for credit accounts)
            String in6 = "Jane\n" +
                    "Doe\n" +
                    "janed@gmail.com\n" +
                    "12345678\n" +
                    "12345\n" +
                    "500.0\n";
            // Get both accounts (exit option)
            String in7 = "3\n";

            String input = in1 + in2 + in3 + in4 + in5 + in6 + in7;

            ByteArrayInputStream instream = new ByteArrayInputStream(input.getBytes());
            System.setIn(instream);

            BankLauncher.createNewBank();
            BankLauncher.bankLogin();

//            for (Bank b : BankLauncher.getBANKS()) {
//                System.out.println("DEBUG: Bank: " + b.getName() + ", Accounts: " + b.getBANKACCOUNTS().size());
//            }

            // (Make sure the public workflow that creates accounts is triggered so that in3-in7 are processed)

            // Get accounts after all of that
            Account saccount = BankLauncher.findAccount("87654321");
            Account caccount = BankLauncher.findAccount("12345678");
            System.out.println(saccount);
            System.out.println(caccount);


            //assert saccount != null;
            Assert.assertEquals("John Doe", saccount.getOwnerFullName());
            Assert.assertEquals(500.0, ((SavingsAccount) saccount).getBalance(), 0.00001);
            Assert.assertEquals("jd@gmail.com", saccount.getOWNEREMAIL());

            // assert caccount != null;
            Assert.assertEquals("Jane Doe", caccount.getOwnerFullName());
            Assert.assertEquals(0.0, ((CreditAccount) caccount).getLoan(), 0.00001);
            Assert.assertEquals("janed@gmail.com", caccount.getOWNEREMAIL());
        } finally {
            System.setIn(original);
        }
    }

}
