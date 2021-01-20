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

public class PaymentAccountDAOTest extends TestCase {
    private AccountDAO accountDAO;
    private PaymentAccountDAO paymentAccountDAO;
    private UserDAO userDAO;

    private PaymentAccount paymentAccount;
    private User user;

    @Before
    public void setUp() {
        accountDAO = new AccountDAO();
        paymentAccountDAO = new PaymentAccountDAO();
        paymentAccount = new PaymentAccount();
        userDAO = new UserDAO();

        user = new User();
        user.setFirstName("firstTest");
        user.setLastName("lastTest");
        user.setEmail("PaymentAct@test.com");
        user.setPhoneNumber("07573298");
        user.setUsername("paymentAccount");
        user.setPassword("paymentaccount123");
        user.setDob("2003-05-03");
        userDAO.insertUser(user);

        paymentAccount.setAccountID(Integer.parseInt(user.getAccountID()));
        paymentAccount.setUserDetailsID(user.getUserDetailsID());
        paymentAccount.setAccountID(Integer.parseInt(user.getAccountID()));
        paymentAccountDAO.insertPaymentAccount(paymentAccount);
    }

    @Test
    public void testGetPaymentAccount() {
        paymentAccountDAO = new PaymentAccountDAO();

        PaymentAccount newPaymentAccount = paymentAccountDAO.getPaymentAccount(paymentAccount.getPaymentActID());
        Double accountNum = accountDAO.getAccountNumber(user.getAccountID());
        String sortCode = accountDAO.getSortCodeNumber(accountDAO.getSortCodeId(user.getAccountID()));

        Assert.assertEquals(newPaymentAccount.getPaymentActID(), paymentAccount.getPaymentActID());
        Assert.assertEquals(newPaymentAccount.getAccountID(), paymentAccount.getAccountID());
        Assert.assertEquals(newPaymentAccount.getUserDetailsID(), paymentAccount.getUserDetailsID());
        Assert.assertEquals(newPaymentAccount.getFirstName(), user.getFirstName());
        Assert.assertEquals(newPaymentAccount.getLastName(), user.getLastName());
        Assert.assertEquals(newPaymentAccount.getAccountNumber(), accountNum);
        Assert.assertEquals(newPaymentAccount.getSortCode(), sortCode);

        userDAO.deleteUser(user);
        paymentAccountDAO.deletePaymentAccount(paymentAccount);
    }

    @Test
    public void testInsertPaymentAccount() {
        paymentAccountDAO = new PaymentAccountDAO();

        PaymentAccount newPaymentAccount = paymentAccountDAO.getPaymentAccount(paymentAccount.getPaymentActID());
        Assert.assertEquals(newPaymentAccount.getPaymentActID(), paymentAccount.getPaymentActID());

        userDAO.deleteUser(user);
        paymentAccountDAO.deletePaymentAccount(paymentAccount);
    }

    @Test
    public void testUpdatePaymentAccount(){
        paymentAccountDAO = new PaymentAccountDAO();
        paymentAccountDAO.updatePaymentAccount(paymentAccount);

        userDAO.deleteUser(user);
        paymentAccountDAO.deletePaymentAccount(paymentAccount);
    }

    @Test
    public void testDeletePaymentAccount(){
        paymentAccountDAO = new PaymentAccountDAO();

        paymentAccountDAO.deletePaymentAccount(paymentAccount);
        userDAO.deleteUser(user);

        try {
            paymentAccountDAO.deletePaymentAccount(paymentAccount);
        }
        catch (final NullPointerException e){
            Assert.assertTrue(true);
        }
    }


}
