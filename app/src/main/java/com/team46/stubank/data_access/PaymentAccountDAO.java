package com.team46.stubank.data_access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team46.stubank.PaymentAccount;
import com.team46.stubank.User;

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
                String accountID = json.get("account_id").getAsString();
                Integer userID = (json.get("user_details_id").getAsInt());

                PaymentAccount paymentAccount = new PaymentAccount();
                AccountDAO accountDAO = new AccountDAO();
                UserDAO userDAO = new UserDAO();

                Integer sortCodeId = accountDAO.getSortCodeId(accountID);

                User user = new User();
                userDAO.getUser(userID);

                paymentAccount.setSortCode((accountDAO.getSortCodeNumber(sortCodeId)));

                paymentAccount.setPaymentActID((json.get("payment_account_id").getAsString()));
                paymentAccount.setFirstName(user.getFirstName());
                paymentAccount.setLastName(user.getLastName());
                paymentAccount.setAccountNumber(accountDAO.getAccountNumber(accountID));
                paymentAccount.setSortCode(accountDAO.getSortCodeNumber(userID));

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



    //Database Access code
}
