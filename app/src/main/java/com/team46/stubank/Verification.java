package com.team46.stubank;

public class Verification {

    Card card;
    PaymentAccount paymentAccount;
    User user;
    String twoFactorCode;
    String username;

    public boolean checkCardBalance(Card card){
        return card.getBalance() != 0.0;
    }

    public boolean checkHasCard(Card card){
        return card.getCardNumber() != null;
    }

    public boolean checkCardActive(Card card){
        return card.getActive();
    }

    public boolean check2FA(User user){
       return user.getPhoneNumber().equals(twoFactorCode);
    }

    public String checkUserType(PaymentAccount paymentAccount){
        return paymentAccount.getSortCode();
        // check user type
    }

    public boolean checkUserExists(User user){
        return user.getUsername() != null;
    }

    public boolean checkPassword(String password, User user){
        return password.equals(user.getPassword());
    }

}
