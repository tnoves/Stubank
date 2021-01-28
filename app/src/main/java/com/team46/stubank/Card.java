package com.team46.stubank;

import com.team46.stubank.data_access.CardDao;
import com.team46.stubank.data_access.PaymentAccountDAO;
import com.team46.stubank.data_access.TransactionDAO;
import com.team46.stubank.data_access.UserDAO;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Card implements Serializable {
    private int userId;
    private String cardNumber;
    private double balance;
    private String cardType;
    private int accountId;
    private String accountNum;
    private String sortCode;
    private String cvcCode;
    private String expiryEnd;
    private String paymentProcessor;
    private boolean active;

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getAccountId() { return accountId; }

    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getCvcCode() {
        return cvcCode;
    }

    public void setCvcCode(String cvcCode) {
        this.cvcCode = cvcCode;
    }

    public String getExpiryEnd() {
        return expiryEnd;
    }

    public void setExpiryEnd(String expiryEnd) {
        this.expiryEnd = expiryEnd;
    }

    public String getPaymentProcessor() {
        return  paymentProcessor;
    }

    public void setPaymentProcessor(String paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //* ===== Card Functionality ===== *//
    public boolean makePayment(double amount, User user, PaymentAccount account) {
        try {
            refresh();

            // card doesn't have enough money to send amount
            if (balance < amount) {
                return false;
            }

            // get card of payment account
            User paymentUser = new UserDAO().getUserByDetails(account.getUserDetailsID());

            ArrayList<Card> cards = new CardDao().getAllCards(paymentUser.getUserID());
            Card paymentCard = null;

            for (Card card: cards) {
                if (card.getAccountId() == account.getAccountID()) {
                    // set payment card
                    paymentCard = card;
                    break;
                }
            }

            if (paymentCard == null) {
                return false;
            }

            // debit x amount to payment account
            if (paymentCard != null) {
                paymentCard.debit(amount, paymentUser, user);
                paymentCard.update(paymentUser);
            }

            // deduct amount from cards balance
            credit(amount, user, paymentUser);

            // update this card
            update(paymentUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean debit(double amount, User userToDebit, User userToCredit) {
        try {
            refresh();

            // add amount to card
            balance += amount;

            // retrieve all payment accounts for this user
            PaymentAccountDAO paymentAccountDAO = new PaymentAccountDAO();
            ArrayList<PaymentAccount> paymentAccounts = paymentAccountDAO
                    .getAllPaymentAccount(userToDebit.getUserDetailsID());

            int paymentAccountId = -999;

            // check if payment account exists from user crediting
            for (PaymentAccount paymentAccount : paymentAccounts) {
                if (paymentAccount.getAccountID() == Integer.parseInt(userToCredit.getAccountID())) {
                    paymentAccountId = paymentAccount.getPaymentActID();
                    break;
                }
            }

            if (paymentAccountId == (-999)) {
                PaymentAccount paymentAccountCredit = new PaymentAccount();
                paymentAccountCredit.setAccountID(Integer.parseInt(userToCredit.getAccountID()));
                paymentAccountCredit.setUserDetailsID(userToDebit.getUserDetailsID());

                paymentAccountDAO.insertPaymentAccount(paymentAccountCredit);
                paymentAccountId = paymentAccountCredit.getPaymentActID();
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            Date current = cal.getTime();
            String currentDate = dateFormat.format(current);

            Transaction transaction = new Transaction(
                    cardNumber,
                    balance,
                    currentDate,
                    paymentAccountId,
                    amount,
                    "Debit");

            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.insertTransaction(transaction);

            // update card in database
            update(userToDebit);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean credit(double amount, User userToCredit, User userToDebit) {
        try {
            refresh();

            // add amount to card
            balance -= amount;

            // retrieve all payment accounts for this user
            PaymentAccountDAO paymentAccountDAO = new PaymentAccountDAO();
            ArrayList<PaymentAccount> paymentAccounts = paymentAccountDAO
                    .getAllPaymentAccount(userToCredit.getUserDetailsID());

            int paymentAccountId = -999;

            // check if payment account exists from user crediting
            for (PaymentAccount paymentAccount : paymentAccounts) {
                if (paymentAccount.getAccountID() == Integer.parseInt(userToDebit.getAccountID())) {
                    paymentAccountId = paymentAccount.getPaymentActID();
                    break;
                }
            }

            if (paymentAccountId == (-999)) {
                PaymentAccount paymentAccountDebit = new PaymentAccount();
                paymentAccountDebit.setAccountID(Integer.parseInt(userToDebit.getAccountID()));
                paymentAccountDebit.setUserDetailsID(userToDebit.getUserDetailsID());

                paymentAccountDAO.insertPaymentAccount(paymentAccountDebit);
                paymentAccountId = paymentAccountDebit.getPaymentActID();
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            Date current = cal.getTime();
            String currentDate = dateFormat.format(current);

            Transaction transaction = new Transaction(
                    cardNumber,
                    balance,
                    currentDate,
                    paymentAccountId,
                    (-1 * amount),
                    "Credit");

            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.insertTransaction(transaction);

            // update card in database
            update(userToCredit);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean topup(double amount, User user) {
        try {
            refresh();

            balance += amount;

            // retrieve all payment accounts for this user
            PaymentAccountDAO paymentAccountDAO = new PaymentAccountDAO();
            ArrayList<PaymentAccount> paymentAccounts = paymentAccountDAO
                    .getAllPaymentAccount(user.getUserDetailsID());

            int paymentAccountId = -999;

            // check if payment account exists from user crediting
            for (PaymentAccount paymentAccount : paymentAccounts) {
                if (paymentAccount.getAccountID() == Integer.parseInt(user.getAccountID())) {
                    paymentAccountId = paymentAccount.getPaymentActID();
                    break;
                }
            }

            if (paymentAccountId == (-999)) {
                PaymentAccount paymentAccountDebit = new PaymentAccount();
                paymentAccountDebit.setAccountID(Integer.parseInt(user.getAccountID()));
                paymentAccountDebit.setUserDetailsID(user.getUserDetailsID());

                paymentAccountDAO.insertPaymentAccount(paymentAccountDebit);
                paymentAccountId = paymentAccountDebit.getPaymentActID();
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            Date current = cal.getTime();
            String currentDate = dateFormat.format(current);

            Transaction transaction = new Transaction(
                    cardNumber,
                    balance,
                    currentDate,
                    paymentAccountId,
                    (amount),
                    "Topup");

            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.insertTransaction(transaction);

            update(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void update(User user) {
        // push updated card details to the database (DAO)
        CardDao dao = new CardDao();

        dao.updateCard(this, user);
    }

    public void refresh() {
        // get updated card details from database (DAO)
        CardDao dao = new CardDao();

        Card updatedCard = dao.getCard(cardNumber);

        cardNumber = updatedCard.cardNumber;
        balance = updatedCard.balance;
        cardType = updatedCard.cardType;
        accountNum = updatedCard.accountNum;
        sortCode = updatedCard.sortCode;
        cvcCode = updatedCard.cvcCode;
        expiryEnd = updatedCard.expiryEnd;
        paymentProcessor = updatedCard.paymentProcessor;
        active = updatedCard.active;
    }

    public void delete() {
        // delete this card from database (DAO)
        CardDao dao = new CardDao();
        dao.deleteCard(this);
    }
}
