package com.team46.stubank.data_access;

import com.google.gson.JsonObject;
import com.team46.stubank.Transaction;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TransactionDAO {

    public boolean insertTransaction(Transaction transaction) {
        HttpURLConnection conn = null;
        try {
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
}
