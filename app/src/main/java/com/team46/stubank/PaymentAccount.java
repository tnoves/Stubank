package com.team46.stubank;

public class PaymentAccount {

    private String firstName;
    private String lastName;
    private Double accountNumber;
    private String sortCode;


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
