package com.team46.stubank.data_access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team46.stubank.Card;
import com.team46.stubank.PaymentAccount;
import com.team46.stubank.User;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PaymentAccountDAO {


    public PaymentAccount getPaymentAccount(String paymentActId) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get account endpoint
            URL url = new URL(String.format("GET 127.0.0.1:5000/payment_account/<id>", paymentActId));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.connect();

            // get account for specified account ID
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

                // assign card json response to PaymentAccount object and return
                PaymentAccount paymentAccount = new PaymentAccount();
                AccountDAO accountDAO = new AccountDAO();
                UserDAO userDAO = new UserDAO();
                User user;

                String accountID = json.get("account_id").getAsString();
                Integer userID = (json.get("user_details_id").getAsInt());
                Integer sortCodeId = accountDAO.getSortCodeId(accountID);

                paymentAccount.setPaymentActID((json.get("payment_account_id").getAsString()));
                paymentAccount.setUserDetailsID(json.get("user_details_id").getAsString());
                paymentAccount.setPaymentActID(accountID);

                user = userDAO.getUser(userID);
                paymentAccount.setFirstName(user.getFirstName());
                paymentAccount.setLastName(user.getLastName());

                paymentAccount.setAccountNumber(accountDAO.getAccountNumber(accountID));
                paymentAccount.setSortCode((accountDAO.getSortCodeNumber(sortCodeId)));


                /**
                (json.get("payment_account_id").getAsString());
                (json.get("account_id").getAsString());
                (json.get("user_details_id").getAsString());
                 **/

                return paymentAccount;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public boolean updatePaymentAccount(PaymentAccount paymentAccount) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - update card endpoint
            URL url = new URL(String.format("PUT 127.0.0.1:5000/payment_account/<id>", paymentAccount.getPaymentActID()));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            // TODO: json.addProperty("account_id", User.getAccountId()); --> access AccountsDAO

            json.addProperty("payment_account_id", paymentAccount.getPaymentActID());
            json.addProperty("account_id", paymentAccount.getAccountID());
            json.addProperty("user_details_id", paymentAccount.getUserDetailsID());

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

    public boolean deletePaymentAccount(String paymentActId) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - delete card endpoint
            URL url = new URL(String.format("DELETE 127.0.0.1:5000/payment_account/<id>", paymentActId));

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
