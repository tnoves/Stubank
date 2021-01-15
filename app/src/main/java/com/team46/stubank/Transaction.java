package com.team46.stubank;

import java.util.Date;

public class Transaction {
    Card cardPaidFrom;
    Double balance;
    Date dateTransaction;
    String paymentAccountID;
    Double paymentAmount;
    String paymentType;

    public Transaction(Card cardPaidFrom, PaymentAccount paymentAccount, Double paymentAmount, Date dateTransaction, String paymentType) {
        this.cardPaidFrom = cardPaidFrom;
        this.balance = cardPaidFrom.getBalance();
        this.dateTransaction = dateTransaction;
        this.paymentAccountID = paymentAccount.getPaymentActID();
        this.paymentAmount = paymentAmount;
        this.paymentType = paymentType;
    }

    public Card getCardPaidFrom() {
        return cardPaidFrom;
    }

    public Double getBalanceAtTransaction() {
        return balance;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public String getPaymentAccountID() {
        return paymentAccountID;
    }

    public Double getAmount() {
        return paymentAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
