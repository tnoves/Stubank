package com.team46.stubank;

import com.team46.stubank.data_access.CardDao;
import com.team46.stubank.data_access.UserDAO;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class CardDaoTest extends TestCase {
    private CardDao cardDao;
    private UserDAO userDAO;

    @Test
    public void testGetCard() {
        userDAO = new UserDAO();
        cardDao = new CardDao();

        User user = userDAO.getUser(25);
        Card card = new Card();
        card.setCardNumber("");
        card.setBalance(0);
        card.setCardType("GBP");
        card.setAccountNum(57305520);
        card.setSortCode("112345");
        card.setCvcCode("123");
        card.setExpiryEnd("0001-01-01");
        card.setPaymentProcessor("Visa");
        card.setActive(true);

        cardDao.insertCard(card, user);

        try {
            Card newCard = cardDao.getCard(card.getCardNumber());
            Assert.assertEquals(card.getCardNumber(), newCard.getCardNumber());
            Assert.assertEquals(card.getAccountNum(), newCard.getAccountNum());

            cardDao.deleteCard(card);
        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testInsertCard() {
        userDAO = new UserDAO();
        cardDao = new CardDao();

        User user = userDAO.getUser(25);
        Card card = new Card();
        card.setCardNumber("");
        card.setBalance(0);
        card.setCardType("GBP");
        card.setAccountNum(57305520);
        card.setSortCode("112345");
        card.setCvcCode("123");
        card.setExpiryEnd("0001-01-01");
        card.setPaymentProcessor("Visa");
        card.setActive(true);

        cardDao.insertCard(card, user);

        Card newCard = cardDao.getCard(card.getCardNumber());

        Assert.assertEquals(card.getCardNumber(), newCard.getCardNumber());
        Assert.assertEquals(card.getAccountNum(), newCard.getAccountNum());

        cardDao.deleteCard(newCard);
    }

    @Test
    public void testDeleteCard() {
        userDAO = new UserDAO();
        cardDao = new CardDao();

        User user = userDAO.getUser(25);
        Card card = new Card();
        card.setCardNumber("");
        card.setBalance(0);
        card.setCardType("GBP");
        card.setAccountNum(57305520);
        card.setSortCode("112345");
        card.setCvcCode("123");
        card.setExpiryEnd("0001-01-01");
        card.setPaymentProcessor("Visa");
        card.setActive(true);

        cardDao.insertCard(card, user);

        cardDao.deleteCard(card);

        try {
            Card newCard = cardDao.getCard(card.getCardNumber());
        } catch (final NullPointerException e) {
            Assert.assertTrue(true);
        }
    }
}