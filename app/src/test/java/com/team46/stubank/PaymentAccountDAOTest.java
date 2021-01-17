package com.team46.stubank;

import com.team46.stubank.data_access.PaymentAccountDAO;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class PaymentAccountDAOTest extends TestCase {
    private PaymentAccountDAO paymentAccountDAO;

    @Test
    public void testGetPaymentAccount(){
        paymentAccountDAO = new PaymentAccountDAO();

        PaymentAccount paymentAccount = new PaymentAccount();
        paymentAccount.setPaymentActID("1");
        paymentAccount.setUserDetailsID("1");
        paymentAccount.setAccountID("1");

        paymentAccount.setFirstName("TestFirst");
        paymentAccount.setLastName("TestLast");
        paymentAccount.setSortCode("00-00-00");
        paymentAccount.setAccountNumber(1.0);

        paymentAccountDAO.insertPaymentAccount(paymentAccount);

        try {
            PaymentAccount newPaymentAccount = paymentAccountDAO.getPaymentAccount(paymentAccount.getPaymentActID());
            Assert.assertEquals(paymentAccount.getPaymentActID(), newPaymentAccount.getPaymentActID() );
            paymentAccountDAO.deletePaymentAccount(paymentAccount);
        }
        catch (final NullPointerException e) {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testInsertPaymentAccount(){
        paymentAccountDAO = new PaymentAccountDAO();

        PaymentAccount paymentAccount = new PaymentAccount();
        paymentAccount.setPaymentActID("1");
        paymentAccount.setUserDetailsID("1");
        paymentAccount.setAccountID("1");

        paymentAccount.setFirstName("TestFirst");
        paymentAccount.setLastName("TestLast");
        paymentAccount.setSortCode("00-00-00");
        paymentAccount.setAccountNumber(1.0);

        paymentAccountDAO.insertPaymentAccount(paymentAccount);
        PaymentAccount newPaymentAccount = paymentAccountDAO.getPaymentAccount("1");

        Assert.assertEquals(paymentAccount.getPaymentActID(), newPaymentAccount.getPaymentActID() );

        paymentAccountDAO.deletePaymentAccount(paymentAccount);
    }

    @Test
    public void testDeletePaymentAccount(){
        paymentAccountDAO = new PaymentAccountDAO();

        PaymentAccount paymentAccount = new PaymentAccount();
        paymentAccount.setPaymentActID("1");
        paymentAccount.setUserDetailsID("1");
        paymentAccount.setAccountID("1");

        paymentAccount.setFirstName("TestFirst");
        paymentAccount.setLastName("TestLast");
        paymentAccount.setSortCode("00-00-00");
        paymentAccount.setAccountNumber(1.0);

        paymentAccountDAO.insertPaymentAccount(paymentAccount);
        paymentAccountDAO.deletePaymentAccount(paymentAccount);

        try {
            PaymentAccount newPaymentAccount = paymentAccountDAO.getPaymentAccount(paymentAccount.getPaymentActID());
            paymentAccountDAO.deletePaymentAccount(paymentAccount);
        }
        catch (final NullPointerException e){
            Assert.assertTrue(true);
        }
    }


}
