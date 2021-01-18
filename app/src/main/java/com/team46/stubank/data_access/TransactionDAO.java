package com.team46.stubank.data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team46.stubank.Card;
import com.team46.stubank.Transaction;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class TransactionDAO {

    public boolean insertTransaction(Transaction transaction) {
        HttpURLConnection conn = null;
        try {
            // Make connection to the StuBank API - insert transaction endpoint.
            URL url = new URL(String.format("http://127.0.0.1:5000/transaction/"));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();

            json.addProperty("card_number", transaction.getCardNumber());
            json.addProperty("balance", transaction.getBalanceAtTransaction());
            json.addProperty("date", transaction.getDateTransaction().toString());
            json.addProperty("payment_account_id", transaction.getPaymentAccountID());
            json.addProperty("payment_amount", transaction.getAmount());
            json.addProperty("payment_type", transaction.getPaymentType());

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

    public Transaction getTransaction(String transactionID) {
        HttpURLConnection conn = null;
        try {
            // Make connection to the StuBank API - get transaction endpoint.
            URL url = new URL(String.format("http://127.0.0.1:5000/transaction/%s", transactionID));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.connect();

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

                // Returns a constructed Transaction object.
                return new Transaction(
                        json.get("card_number").getAsString(),
                        json.get("balance").getAsDouble(),
                        json.get("date").getAsString(),
                        json.get("payment_account_id").getAsString(),
                        json.get("payment_amount").getAsDouble(),
                        json.get("payment_type").getAsString(),
                        json.get("id").getAsString());
            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public List<Transaction> getCardTransactions(String cardID) {
            HttpURLConnection conn = null;
            try {
                // Make connection to the StuBank API - get all transaction endpoint
                URL url = new URL(String.format("http://127.0.0.1:5000/transaction/card/%s", cardID));

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.connect();

                // Get all transactions from an account.
                // TODO: this only fetches transactions coming from the provided card ID, need another endpoint to fetch transactions going into the account. Alternative method is to create 2 opposite transactions when they're made.
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
                    List<Transaction> transactionList = null;

                    for (JsonElement transactions : json) {
                        JsonObject transactionObj = transactions.getAsJsonObject();

                        // Creates a transaction from the json response.
                        Transaction transaction = new Transaction(
                                transactionObj.get("card_number").getAsString(),
                                transactionObj.get("balance").getAsDouble(),
                                transactionObj.get("date").getAsString(),
                                transactionObj.get("payment_account_id").getAsString(),
                                transactionObj.get("payment_amount").getAsDouble(),
                                transactionObj.get("payment_type").getAsString(),
                                transactionObj.get("id").getAsString());

                        // Adds constructed transaction to the list of transactions from the provided card.
                        transactionList.add(transaction);
                    }
                    return transactionList;
                }

            } catch (Exception e) {
                e.printStackTrace();

                return null;
            } finally {
                if (conn != null)
                    conn.disconnect();
            }

    }

    public boolean deleteTransaction(String transactionID) {
        HttpURLConnection conn = null;
        try {
            // Make connection to the StuBank API - delete transaction endpoint.
            URL url = new URL(String.format("http://127.0.0.1:5000/transaction/%s", transactionID));

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
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

}
