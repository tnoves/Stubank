package com.team46.stubank;

public class Card {
    private String name;
    private double balance;
    private String cardType;
    private String accountNum;
    private String sortCode;
    private String expiryEnd;
    private String paymentProcessor;

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

    public  void setPaymentProcessor(String paymentProcessor) {
        paymentProcessor = paymentProcessor;
    }



}
