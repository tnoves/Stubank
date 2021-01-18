package com.team46.stubank;

public class PaymentAccount {

    private String paymentActID;
    private String accountID;
    private String userDetailsID;

    private String firstName;
    private String lastName;
    private Double accountNumber;
    private String sortCode;

    public String getPaymentActID(){
        return paymentActID;
    }
    public void setPaymentActID(String paymentActID){
        paymentActID = paymentActID;
    }

    public String getAccountID(){
        return accountID;
    }
    public void setAccountID(String accountID){
        accountID = accountID;
    }

    public String getUserDetailsID(){
        return userDetailsID;
    }
    public void setUserDetailsID(String userDetailsID){
        userDetailsID = userDetailsID;
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
