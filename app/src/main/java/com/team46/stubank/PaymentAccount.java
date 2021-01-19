package com.team46.stubank;

public class PaymentAccount {

    private int paymentActID;
    private int accountID;
    private int userDetailsID;

    private String firstName;
    private String lastName;
    private Double accountNumber;
    private String sortCode;

    public int getPaymentActID(){
        return paymentActID;
    }
    public void setPaymentActID(int paymentActID){
        this.paymentActID = paymentActID;
    }

    public int getAccountID(){
        return accountID;
    }
    public void setAccountID(int accountID){
        this.accountID = accountID;
    }

    public int getUserDetailsID(){
        return userDetailsID;
    }
    public void setUserDetailsID(int userDetailsID){
        this.userDetailsID = userDetailsID;
    }

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        firstName = firstName;
    }

    public String getLastName(){
        return  lastName;
    }
    public void  setLastName(String lastName){
        lastName = lastName;
    }

    public Double getAccountNumber(){
        return accountNumber;
    }
    public void setAccountNumber(Double accountNumber){
        accountNumber = accountNumber;
    }

    public String getSortCode(){
        return sortCode;
    }
    public void setSortCode(String sortCode){
        sortCode = sortCode;
    }

}
