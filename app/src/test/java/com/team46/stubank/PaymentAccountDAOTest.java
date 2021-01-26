package com.team46.stubank;

import com.team46.stubank.data_access.AccountDAO;
import com.team46.stubank.data_access.PaymentAccountDAO;
import com.team46.stubank.data_access.UserDAO;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing for the PaymentAccountDAO class
 *
 *
 * @author  Douglas Gray
 * @version 1.0
 * @since   2021-01-20
 */

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

        paymentAccount = new PaymentAccount();
        paymentAccount.setAccountID(Integer.parseInt(user.getAccountID()));
        paymentAccount.setUserDetailsID(user.getUserDetailsID());
        paymentAccountDAO.insertPaymentAccount(paymentAccount);
    }

    @Test
    public void testGetPaymentAccount() {
        paymentAccountDAO = new PaymentAccountDAO();

        PaymentAccount newPaymentAccount = paymentAccountDAO.getPaymentAccount(paymentAccount.getPaymentActID());
        String accountNum = accountDAO.getAccountNumber(user.getAccountID());
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
        User updatedUser = new User();

        updatedUser.setFirstName("firstTest2");
        updatedUser.setLastName("lastTest2");
        updatedUser.setEmail("PaymentAct2@test.com");
        updatedUser.setPhoneNumber("07573298");
        updatedUser.setUsername("paymentAccount2");
        updatedUser.setPassword("paymentaccount123");
        updatedUser.setDob("2003-05-03");
        userDAO.insertUser(updatedUser);
        
        paymentAccount.setAccountID(Integer.parseInt(updatedUser.getAccountID()));
        paymentAccount.setUserDetailsID(updatedUser.getUserDetailsID());
        paymentAccountDAO.updatePaymentAccount(paymentAccount);

        PaymentAccount newPaymentAccountUpdated = paymentAccountDAO.getPaymentAccount(paymentAccount.getPaymentActID());

        Assert.assertEquals(newPaymentAccountUpdated.getFirstName(), updatedUser.getFirstName());
        Assert.assertEquals(newPaymentAccountUpdated.getLastName(), updatedUser.getLastName());
        
        userDAO.deleteUser(user);
        userDAO.deleteUser(updatedUser);
        paymentAccountDAO.deletePaymentAccount(paymentAccount);
    }

    @Test
    public void testGetPaymentAccountUserID() {
        paymentAccountDAO = new PaymentAccountDAO();

        PaymentAccount newPaymentAccount = paymentAccountDAO.getPaymentAccountUserID(paymentAccount.getUserDetailsID());
        String accountNum = accountDAO.getAccountNumber(user.getAccountID());
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
