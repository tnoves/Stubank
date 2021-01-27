package com.team46.stubank.data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team46.stubank.PaymentAccount;
import com.team46.stubank.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * PaymentAccount Database object class, retrieves Data from API connected to database
 *
 *
 * @author  Douglas Gray
 * @version 1.0
 * @since   2021-01-20
 */

public class PaymentAccountDAO {

    public PaymentAccount getPaymentAccount(int paymentActId) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get Payment account endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/payment_account/%s", paymentActId));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
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
                System.out.println(response);

                JsonObject json = JsonParser.parseString(response).getAsJsonObject();

                // assign card json response to PaymentAccount object and return
                PaymentAccount paymentAccount = new PaymentAccount();
                AccountDAO accountDAO = new AccountDAO();
                UserDAO userDAO = new UserDAO();
                User user;

                paymentAccount.setPaymentActID((json.get("payment_account_id").getAsInt()));
                paymentAccount.setUserDetailsID(json.get("user_details_id").getAsInt());
                paymentAccount.setAccountID(json.get("account_id").getAsInt());

                String accountID = json.get("account_id").getAsString();
                Integer userID = (json.get("user_details_id").getAsInt());
                Integer sortCodeId = accountDAO.getSortCodeId(accountID);
                user = userDAO.getUserDetails(userID);

                paymentAccount.setFirstName(user.getFirstName());
                paymentAccount.setLastName(user.getLastName());

                paymentAccount.setAccountNumber(accountDAO.getAccountNumber(accountID));
                paymentAccount.setSortCode((accountDAO.getSortCodeNumber(sortCodeId)));

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
            // make connection to the StuBank api - update Payment Account endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/payment_account/%s", paymentAccount.getPaymentActID()));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.connect();

            JsonObject json = new JsonObject();

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

    public boolean insertPaymentAccount(PaymentAccount paymentAccount) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - insert PaymentAccount endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/payment_account/"));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.connect();

            JsonObject json = new JsonObject();
            json.addProperty("account_id", paymentAccount.getAccountID());
            json.addProperty("user_details_id", paymentAccount.getUserDetailsID());

            try (DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream())){
                dataOutputStream.writeBytes(json.toString());
            }

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
                    paymentAccount.setPaymentActID(responseJson.get("payment_account_id").getAsInt());
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

    public ArrayList<PaymentAccount> getAllPaymentAccount(int userDetailsId) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get Payment account endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/payment_account/user_details/%s", userDetailsId));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
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

                //JsonObject json2 = JsonParser.parseString(response).getAsJsonObject();

                JsonArray json = JsonParser.parseString(response).getAsJsonArray();
                ArrayList<PaymentAccount> paymentAccountList = new ArrayList<>();

                for (JsonElement paymentAccounts : json) {

                    JsonObject paymentActObj = paymentAccounts.getAsJsonObject();

                    // assign card json response to PaymentAccount object and return
                    PaymentAccount paymentAccount = new PaymentAccount();
                    AccountDAO accountDAO = new AccountDAO();
                    UserDAO userDAO = new UserDAO();
                    User user;

                    paymentAccount.setPaymentActID((paymentActObj.get("payment_account_id").getAsInt()));
                    paymentAccount.setUserDetailsID(paymentActObj.get("user_details_id").getAsInt());
                    paymentAccount.setAccountID(paymentActObj.get("account_id").getAsInt());

                    String accountID = paymentActObj.get("account_id").getAsString();
                    Integer userID = (paymentActObj.get("user_details_id").getAsInt());
                    Integer sortCodeId = accountDAO.getSortCodeId(accountID);
                    user = userDAO.getUserDetails(userID);

                    paymentAccount.setFirstName(user.getFirstName());
                    paymentAccount.setLastName(user.getLastName());

                    paymentAccount.setAccountNumber(accountDAO.getAccountNumber(accountID));
                    paymentAccount.setSortCode((accountDAO.getSortCodeNumber(sortCodeId)));

                    paymentAccountList.add(paymentAccount);
                }
                return paymentAccountList;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public boolean deletePaymentAccount(PaymentAccount paymentAccount) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - delete PaymentAccount endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/payment_account/%s", paymentAccount.getPaymentActID()));

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
