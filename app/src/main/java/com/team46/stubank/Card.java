package com.team46.stubank;

import com.team46.stubank.data_access.CardDao;

import java.util.Date;

public class Card {
    private String name;
    private double balance;
    private String cardType;
    private Double accountNum;
    private String sortCode;
    private String expiryEnd;
    private String paymentProcessor;
    private boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        balance = balance;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        cardType = cardType;
    }

    public Double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Double accountNum) {
        accountNum = accountNum;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        sortCode = sortCode;
    }

    public String getExpiryEnd() {
        return expiryEnd;
    }

    public void setExpiryEnd(String expiryEnd) {
        expiryEnd = expiryEnd;
    }

    public String getPaymentProcessor() {
        return  paymentProcessor;
    }

    public void setPaymentProcessor(String paymentProcessor) {
        paymentProcessor = paymentProcessor;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        active = active;
    }

    //* ===== Card Functionality ===== *//
    public boolean makePayment(double amount, PaymentAccount account) {
        try {
            refresh();

            // card doesn't have enough money to send amount
            if (balance < amount) {
                return false;
            }

            // deduct amount from cards balance
            balance -= amount;

            // get card of payment account
            Card paymentCard = new CardDao().getCard(account.getAccountNumber());

            // debit x amount to payment account
            if (paymentCard != null) {
                paymentCard.debit(amount);
                paymentCard.update();
            }

            // update this card
            update();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean makePayment(double amount, PaymentAccount account, Date recurringPaymentDate) {
        // make initial payment
        boolean initialPaymentMade = makePayment(amount, account);

        if (!initialPaymentMade) {
            return false;
        }

        // TODO: set up payment schedule based on recurring payment date

        return true;
    }

    public boolean debit(double amount) {
        // TODO: verify amount for security

        refresh();

        // add amount to card
        balance += amount;

        // update card in database
        update();

        return true;
    }

    private void update() {
        // push updated card details to the database (DAO)
        CardDao dao = new CardDao();

        dao.updateCard(this);
    }

    public void refresh() {
        // get updated card details from database (DAO)
        CardDao dao = new CardDao();

        Card updatedCard = dao.getCard(accountNum);

        name = updatedCard.name;
        balance = updatedCard.balance;
        cardType = updatedCard.cardType;
        accountNum = updatedCard.accountNum;
        sortCode = updatedCard.sortCode;
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
