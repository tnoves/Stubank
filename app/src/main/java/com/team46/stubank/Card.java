package com.team46.stubank;

import com.team46.stubank.data_access.CardDao;
import com.team46.stubank.data_access.UserDAO;

import java.io.Serializable;

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
            Card paymentCard = new CardDao().getCard(account.getAccountNumber());

            // get user of payment account
            User paymentUser = new UserDAO().getUser(paymentCard.getUserId());

            // debit x amount to payment account
            if (paymentCard != null) {
                paymentCard.debit(amount, paymentUser);
                paymentCard.update(paymentUser);
            }

            // deduct amount from cards balance
            balance -= amount;

            // update this card
            update(paymentUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean debit(double amount, User user) {
        refresh();

        // add amount to card
        balance += amount;

        // update card in database
        update(user);

        return true;
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
