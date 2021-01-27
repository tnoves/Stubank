package com.team46.stubank.data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team46.stubank.Card;
import com.team46.stubank.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CardDao {

    public Card getCard(String cardNumber) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get card endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/card/%s", cardNumber));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            // get card for specified card number
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());
            } else {
                String response = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    response += scanner.nextLine();
                }

                scanner.close();

                JsonObject json = JsonParser.parseString(response).getAsJsonObject();

                // assign card json response to card object and return
                Card card = new Card();
                AccountDAO account = new AccountDAO();

                card.setUserId(json.get("user_id").getAsInt());
                card.setCardNumber(json.get("card_number").getAsString());
                card.setBalance(json.get("balance").getAsDouble());
                card.setCardType(json.get("card_type").getAsString());
                card.setAccountId(json.get("account_id").getAsInt());
                card.setAccountNum(account.getAccountNumber(json.get("account_id").getAsString()));
                card.setSortCode(account.getSortCodeNumber(account.getSortCodeId(json.get("account_id").getAsString())));
                card.setCvcCode(json.get("cvc_code").getAsString());
                card.setExpiryEnd(json.get("expiry_date").getAsString());
                card.setPaymentProcessor(json.get("payment_processor").getAsString());
                card.setActive(json.get("active").getAsBoolean());

                return card;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public ArrayList<Card> getAllCards(int userID) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get all card endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/card/user/%s", userID));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            // get all cards for specified user
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());
            } else {
                String response = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    response += scanner.nextLine();
                }

                scanner.close();

                JsonArray json = JsonParser.parseString(response).getAsJsonArray();
                ArrayList<Card> cardList = new ArrayList<Card>();

                for (JsonElement cards : json) {
                    JsonObject cardObj = cards.getAsJsonObject();

                    // assign card json response to card object and return
                    Card card = new Card();
                    AccountDAO account = new AccountDAO();

                    card.setUserId(cardObj.get("user_id").getAsInt());
                    card.setCardNumber(cardObj.get("card_number").getAsString());
                    card.setBalance(cardObj.get("balance").getAsDouble());
                    card.setCardType(cardObj.get("card_type").getAsString());
                    card.setAccountId(cardObj.get("account_id").getAsInt());
                    card.setAccountNum(account.getAccountNumber(cardObj.get("account_id").getAsString()));
                    card.setSortCode(account.getSortCodeNumber(account.getSortCodeId(cardObj.get("account_id").getAsString())));
                    card.setCvcCode(cardObj.get("cvc_code").getAsString());
                    card.setExpiryEnd(cardObj.get("expiry_date").getAsString());
                    card.setPaymentProcessor(cardObj.get("payment_processor").getAsString());
                    card.setActive(cardObj.get("active").getAsBoolean());

                    // add to the list of cards
                    cardList.add(card);
                }
                return cardList;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public boolean insertCard(Card card, User user) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - insert card endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/card/"));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(10 * 1000);
            conn.setDoOutput(true);
            conn.connect();

            JsonObject json = new JsonObject();
            json.addProperty("account_id", user.getAccountID());
            json.addProperty("active", card.getActive());
            json.addProperty("balance", card.getBalance());
            json.addProperty("cvc_code", card.getCvcCode());
            json.addProperty("card_type", card.getCardType());
            json.addProperty("expiry_date", card.getExpiryEnd());
            json.addProperty("payment_processor", card.getPaymentProcessor());
            json.addProperty("user_id", user.getUserID());

            try (DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream())) {
                dataOutputStream.writeBytes(json.toString());
            }

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HttpResponseCode: " + conn.getErrorStream());
            } else {
                AccountDAO accountDAO = new AccountDAO();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    String output;
                    StringBuffer response = new StringBuffer();
                    while ((output = reader.readLine()) != null) {
                        response.append(output);
                    }

                    JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();

                    card.setCardNumber(responseJson.get("card_number").getAsString());
                    card.setAccountNum(accountDAO.getAccountNumber(responseJson.get("account_id").getAsString()));
                    card.setSortCode(accountDAO.getSortCodeNumber(accountDAO.getSortCodeId(responseJson.get("account_id").getAsString())));
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public boolean updateCard(Card card, User user) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - update card endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/card/%s", card.getCardNumber()));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            json.addProperty("account_id", user.getAccountID());
            json.addProperty("active", card.getActive());
            json.addProperty("balance", card.getBalance());
            json.addProperty("cvc_code", card.getCvcCode());
            json.addProperty("card_type", card.getCardType());
            json.addProperty("expiry_date", card.getExpiryEnd());
            json.addProperty("payment_processor", card.getPaymentProcessor());
            json.addProperty("user_id", user.getUserID());

            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(json.toString());
            dataOutputStream.close();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HttpResponseCode: " + conn.getErrorStream());
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public boolean deleteCard(Card card) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - delete card endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/card/%s", card.getCardNumber()));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HttpResponseCode: " + conn.getErrorStream());
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }
}
