package com.team46.stubank;
import java.io.Serializable;

/**
 * PaymentAccount object, Object with variables related to a payment account
 *
 * @author  Douglas Gray
 * @version 1.0
 * @since   2021-01-27
 */

public class PaymentAccount implements Serializable {

    private int paymentActID;
    private int accountID;
    private int userDetailsID;

    private String firstName;
    private String lastName;
    private String accountNumber;
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
        this.firstName = firstName;
    }

    public String getLastName(){
        return  lastName;
    }
    public void  setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getAccountNumber(){
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public String getSortCode(){
        return sortCode;
    }
    public void setSortCode(String sortCode){
        this.sortCode = sortCode;
    }

}
