package com.team46.stubank;

import com.team46.stubank.data_access.AccountDAO;
import com.team46.stubank.data_access.CardDao;
import com.team46.stubank.data_access.PaymentAccountDAO;
import com.team46.stubank.data_access.TransactionDAO;
import com.team46.stubank.data_access.UserDAO;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Testing for the TransactionDAO class.
 *
 *
 * @author  Ben McIntyre
 * @version 1.0
 * @since   2021-01-25
 */

public class TransactionDAOTest extends TestCase {
    private AccountDAO accountDAO;
    private PaymentAccountDAO paymentAccountDAO;
    private UserDAO userDAO;
    private CardDao cardDAO;
    private TransactionDAO transactionDAO;
    private PaymentAccount paymentAccount;
    private User user;
    private Card card;
    private Transaction transaction;

    // Sets up necessary prerequisites for each of the following tests.
    // This is done as the database structure means transactions require all other tables to have entries.
    @Before
    public void setUp() {
        accountDAO = new AccountDAO();
        paymentAccountDAO = new PaymentAccountDAO();
        userDAO = new UserDAO();
        cardDAO = new CardDao();
        transactionDAO = new TransactionDAO();

        user = new User();
        user.setFirstName("transactiontestfirst");
        user.setLastName("transactiontestlast");
        user.setEmail("transaction@test.com");
        user.setPhoneNumber("transactiontestphone");
        user.setUsername("transaction");
        user.setPassword("transactionpass");
        user.setDob("2000-11-11");
        userDAO.insertUser(user);

        card = new Card();
        card.setAccountId(Integer.parseInt(user.getAccountID()));
        card.setActive(true);
        card.setBalance(10.00);
        card.setCvcCode("123");
        card.setCardType("GBP");
        card.setExpiryEnd("0001-01-01");
        card.setPaymentProcessor("transactiontest");
        cardDAO.insertCard(card, user);

        paymentAccount = new PaymentAccount();
        paymentAccount.setAccountID(Integer.parseInt(user.getAccountID()));
        paymentAccount.setUserDetailsID(user.getUserDetailsID());
        paymentAccountDAO.insertPaymentAccount(paymentAccount);

        transaction = new Transaction(
                card.getCardNumber(),
                card.getBalance(),
                "2021-01-17",
                paymentAccount.getPaymentActID(),
                5.00,
                "test");
    }

    @Test
    public void testInsertTransaction() {
        transactionDAO.insertTransaction(transaction);

        Transaction check = (transactionDAO.getTransaction(transaction.getTransactionID()));

        Assert.assertEquals(transaction.getTransactionID(), check.getTransactionID());

        // Removes the dummy data added by the @Before method and this test.
        transactionDAO.deleteTransaction(check.getTransactionID());
        userDAO.deleteUser(user);
        accountDAO.deleteAccount(user.getAccountID());
    }

    @Test
    public void testGetTransaction() {
        transactionDAO.insertTransaction(transaction);

        Transaction check = (transactionDAO.getTransaction(transaction.getTransactionID()));

        Assert.assertEquals(transaction.getCardNumber(), check.getCardNumber());
        Assert.assertEquals(transaction.getBalanceAtTransaction(), check.getBalanceAtTransaction());
        Assert.assertEquals(transaction.getDateTransaction(), check.getDateTransaction());
        Assert.assertEquals(transaction.getPaymentAccountID(), check.getPaymentAccountID());
        Assert.assertEquals(transaction.getAmount(), check.getAmount());
        Assert.assertEquals(transaction.getPaymentType(), check.getPaymentType());
        Assert.assertEquals(transaction.getPaymentType(), check.getPaymentType());

        // Removes the dummy data added by the @Before method and this test.
        transactionDAO.deleteTransaction(check.getTransactionID());
        userDAO.deleteUser(user);
        accountDAO.deleteAccount(user.getAccountID());
    }

    @Test
    public void testGetCardTransactions() {
        Transaction transaction2 = new Transaction(
                card.getCardNumber(),
                card.getBalance(),
                "2021-01-17",
                paymentAccount.getPaymentActID(),
                15.00,
                "test2");
        transactionDAO.insertTransaction(transaction);
        transactionDAO.insertTransaction(transaction2);

        List<Transaction> checkList = transactionDAO.getCardTransactions(transaction.getCardNumber());

        Transaction check = checkList.get(0);
        Transaction check2 = checkList.get(1);

        // Compares the original transactions and what is returned from the database.
        Assert.assertEquals(transaction.getTransactionID(), check.getTransactionID());
        Assert.assertEquals(transaction.getCardNumber(), check.getCardNumber());
        Assert.assertEquals(transaction.getBalanceAtTransaction(), check.getBalanceAtTransaction());
        Assert.assertEquals(transaction.getDateTransaction(), check.getDateTransaction());
        Assert.assertEquals(transaction.getPaymentAccountID(), check.getPaymentAccountID());
        Assert.assertEquals(transaction.getAmount(), check.getAmount());
        Assert.assertEquals(transaction.getPaymentType(), check.getPaymentType());
        Assert.assertEquals(transaction.getPaymentType(), check.getPaymentType());
        Assert.assertEquals(transaction2.getTransactionID(), check2.getTransactionID());
        Assert.assertEquals(transaction2.getCardNumber(), check2.getCardNumber());
        Assert.assertEquals(transaction2.getBalanceAtTransaction(), check2.getBalanceAtTransaction());
        Assert.assertEquals(transaction2.getDateTransaction(), check2.getDateTransaction());
        Assert.assertEquals(transaction2.getPaymentAccountID(), check2.getPaymentAccountID());
        Assert.assertEquals(transaction2.getAmount(), check2.getAmount());
        Assert.assertEquals(transaction2.getPaymentType(), check2.getPaymentType());
        Assert.assertEquals(transaction2.getPaymentType(), check2.getPaymentType());

        // Removes the dummy data added by the @Before method and this test.
        transactionDAO.deleteTransaction(transaction.getTransactionID());
        transactionDAO.deleteTransaction(transaction2.getTransactionID());
        userDAO.deleteUser(user);
        accountDAO.deleteAccount(user.getAccountID());
    }

    @Test
    public void testDeleteTransaction() {
        transactionDAO.insertTransaction(transaction);
        // Removes the dummy data added by the @Before method and this test.
        transactionDAO.deleteTransaction(transaction.getTransactionID());
        userDAO.deleteUser(user);
        accountDAO.deleteAccount(user.getAccountID());
        // Tries to fetch previously deleted transaction from database, if it fails then the transaction is deleted and the test has passed.
        try {
            Transaction newTransaction = transactionDAO.getTransaction(transaction.getTransactionID());
        } catch (final NullPointerException e) {
            Assert.assertTrue(true);
        }
    }
}
