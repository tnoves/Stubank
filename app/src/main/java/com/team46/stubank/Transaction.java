package com.team46.stubank;

import java.util.Date;

public class Transaction {
    Card cardPaidFrom;
    PaymentAccount paymentAccount;
    Double amount;
    Date dateTransaction;
    String paymentType;

    public Transaction(Card cardPaidFrom, PaymentAccount paymentAccount, Double amount, Date dateTransaction, String paymentType) {
        this.cardPaidFrom = cardPaidFrom;
        this.paymentAccount = paymentAccount;
        this.amount = amount;
        this.dateTransaction = dateTransaction;
        this.paymentType = paymentType;
    }

    public Card getCardPaidFrom() {
        return cardPaidFrom;
    }

    public PaymentAccount getPaymentAccount() {
        return paymentAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
