package com.team46.stubank;

import com.team46.stubank.data_access.TransactionDAO;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionDAOTest extends TestCase {
    private TransactionDAO transactionDAO;

    @Test
    public void testInsertTransaction() {
        transactionDAO = new TransactionDAO();

        Transaction transaction = new Transaction(
                "123456578",
                10.00,
                "2021-17-01",
                "1",
                5.00,
                "test",
                null);

        transactionDAO.insertTransaction(transaction);

        Transaction check = (transactionDAO.getCardTransactions("12345678")).get(0);

        Assert.assertEquals(transaction.getCardNumber(), check.getCardNumber());
        Assert.assertEquals(transaction.getBalanceAtTransaction(), check.getBalanceAtTransaction());
        Assert.assertEquals(transaction.getPaymentType(), check.getPaymentType());

        transactionDAO.deleteTransaction(check.getTransactionID());
    }
}
