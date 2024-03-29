package com.team46.stubank.data_access;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team46.stubank.Transaction;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Transaction Database access object class, retrieves Data from API connected to database.
 *
 *
 * @author  Ben McIntyre
 * @version 1.0
 * @since   2020-12-10
 */

public class TransactionDAO {

    public boolean insertTransaction(Transaction transaction) {
        HttpURLConnection conn = null;
        try {
            // Make connection to the StuBank API - insert transaction endpoint.
            URL url = new URL("http://10.0.2.2:5000/transaction/");

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();

            // Builds the json file to be sent to the API with required properties.
            json.addProperty("card_number", transaction.getCardNumber());
            json.addProperty("balance", transaction.getBalanceAtTransaction());
            json.addProperty("date", transaction.getDateTransaction());
            json.addProperty("payment_account_id", transaction.getPaymentAccountID());
            json.addProperty("payment_amount", transaction.getAmount());
            json.addProperty("payment_type", transaction.getPaymentType());

            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(json.toString());
            dataOutputStream.close();

            // If the insert method is successful, the id assigned to the transaction by the database is returned in a response and assigned to the transaction.
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HttpResponseCode: " + conn.getErrorStream());
            } else {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    String output;
                    StringBuffer response = new StringBuffer();
                    while ((output = reader.readLine()) != null) {
                        response.append(output);
                    }

                    JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();

                    transaction.setTransactionID(responseJson.get("id").getAsString());

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

    public Transaction getTransaction(String transactionID) {
        HttpURLConnection conn = null;
        try {
            // Make connection to the StuBank API - get transaction endpoint.
            URL url = new URL(String.format("http://10.0.2.2:5000/transaction/%s", transactionID));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            // If the transaction with corresponding id is found in the database, it is returned.
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
                Transaction transaction = new Transaction(
                        json.get("card_number").getAsString(),
                        json.get("balance").getAsDouble(),
                        json.get("date").getAsString(),
                        json.get("payment_account_id").getAsInt(),
                        json.get("payment_amount").getAsDouble(),
                        json.get("payment_type").getAsString());
                transaction.setTransactionID(transactionID);
                return transaction;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Transaction> getCardTransactions(String cardID) {
            HttpURLConnection conn = null;
            try {
                // Make connection to the StuBank API - get all transaction endpoint
                URL url = new URL(String.format("http://10.0.2.2:5000/transaction/card/%s", cardID));

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.connect();

                // Get all transactions from an account.
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
                    List<Transaction> transactionList = new ArrayList<>();

                    // Iterates through the elements in the returned json.
                    for (JsonElement transactions : json) {
                        JsonObject transactionObj = transactions.getAsJsonObject();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        // Creates a transaction from the json response.
                        Transaction transaction = new Transaction(
                                transactionObj.get("card_number").getAsString(),
                                transactionObj.get("balance").getAsDouble(),
                                transactionObj.get("date").getAsString(),
                                transactionObj.get("payment_account_id").getAsInt(),
                                transactionObj.get("payment_amount").getAsDouble(),
                                transactionObj.get("payment_type").getAsString());
                        transaction.setSortDate(format.parse(transaction.getDateTransaction()));
                        transaction.setTransactionID(transactionObj.get("id").getAsString());

                        // Adds constructed transaction to the list of transactions from the provided card.
                        transactionList.add(transaction);
                    }
                    transactionList.sort(Comparator.comparing(Transaction::getSortDate).reversed());
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
            URL url = new URL(String.format("http://10.0.2.2:5000/transaction/%s", transactionID));

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
