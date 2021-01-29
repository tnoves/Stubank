package com.team46.stubank;

import java.io.Serializable;
import java.util.Date;
/**
 *  Transaction class object to hold the data for an individual transaction.
 *
 *
 * @author  Ben McIntyre
 * @version 1.0
 * @since   2020-12-09
 */

public class Transaction implements Serializable {
    private String cardNumber;
    private Double balance;
    private String dateTransaction;
    private int paymentActID;
    private Double paymentAmount;
    private String paymentType;
    private String transactionID;
    private Date sortDate;

    public Transaction(String cardNumber, Double balance, String dateTransaction, int paymentActID, Double paymentAmount, String paymentType) {
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

    public int getPaymentAccountID() {
        return paymentActID;
    }

    public Double getAmount() {
        return paymentAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getTransactionID() { return transactionID; }
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Date getSortDate() {
        return sortDate;
    }
    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
    }

}
