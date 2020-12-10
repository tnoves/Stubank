package com.team46.stubank;

import java.util.Date;

public class Card {
    private String name;
    private double balance;
    private String cardType;
    private String accountNum;
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

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
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
    public boolean makePayment(double amount) {
        // card doesn't have enough money to send amount
        if (balance < amount) {
            return false;
        }

        // TODO: send money from this card to a payment account

        return true;
    }

    public boolean makePayment(double amount, Date recurringPaymentDate) {
        // make initial payment
        boolean initialPaymentMade = makePayment(amount);

        if (!initialPaymentMade) {
            return false;
        }

        // TODO: set up payment schedule based on recurring payment date

        return true;
    }

    public boolean debit(double amount) {
        // TODO: verify amount for security

        // add amount to card
        balance += amount;

        return true;
    }

    private void update() {
        // TODO: Push updated card details to the database (DAO)
    }

    public void refresh() {
        // TODO: Get updated card details from database (DAO)
    }

    public void delete() {
        // TODO: Delete this card from database (DAO)
    }
}
