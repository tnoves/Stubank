package com.team46.stubank;

public class Verification {

    Card card;
    PaymentAccount paymentAccount;
    User user;
    String authCode;
    String username;

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        username = username;
    }

    public Card getCard(){
        return card;
    }
    public void setCard(Card card){
        card = card;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        user = user;
    }

    public PaymentAccount getPaymentAccount() {
        return paymentAccount;
    }
    public void setPaymentAccount(PaymentAccount paymentAccount){
        paymentAccount = paymentAccount;
    }

    public String getAuthCode(){
        return authCode;
    }
    public void setAuthCode(String authCode){
        authCode = authCode;
    }


    public boolean checkCardBalance(Card card){
        return card.getbalace > 0;
    }

    public boolean checkHasCard(Card card){
        if (card.cardType != null){
            return true;
        }
        return false;
    }

    public boolean checkCardActive(Card card){
        return card.getActive;
    }




}
