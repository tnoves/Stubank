package com.team46.stubank;

import java.util.Date;

public class Transaction {
    String cardNumber;
    Double balance;
    String dateTransaction;
    String paymentActID;
    Double paymentAmount;
    String paymentType;

    public Transaction(String cardNumber, Double balance, String dateTransaction, String paymentActID, Double paymentAmount, String paymentType) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.dateTransaction = dateTransaction;
        this.paymentActID = paymentActID;
        this.paymentAmount = paymentAmount;
        this.paymentType = paymentType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Double getBalanceAtTransaction() {
        return balance;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public String getPaymentAccountID() {
        return paymentActID;
    }

    public Double getAmount() {
        return paymentAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
