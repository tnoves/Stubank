package com.team46.stubank.data_access;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team46.stubank.User;

public class UserDAO {

    public User getUser(int userID){
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get card endpoint
            URL url = new URL(String.format("http://127.0.0.1:5000/user/%s", userID));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
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
                User user = new User();
                AccountDAO accountDAO = new AccountDAO();

                user.setUsername(json.get("username").getAsString());
                user.setPassword(json.get("password").getAsString());
                user.setAccountID(accountDAO.getAccountNumber(json.get("account_id").getAsString()).intValue());
                user.setUserDetailsID(json.get("user_details_id").getAsInt());

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public boolean insertUser(User user) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - insert card endpoint
            URL url = new URL(String.format("http://127.0.0.1:5000/user/"));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();

            json.addProperty("username", user.getUsername());
            json.addProperty("password", user.getPassword());
            json.addProperty("user_details_id", user.getUserDetailsID());
            json.addProperty("account_id", user.getAccountID());

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

    public boolean insertUserDetails(User user){
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - insert card endpoint
            URL url = new URL(String.format("http://127.0.0.1:5000/user/details/"));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            AccountDAO accountDAO = new AccountDAO();

            json.addProperty("firstname", user.getFirstName());
            json.addProperty("lastname", user.getLastName());
            json.addProperty("user_details_id", user.getUserDetailsID());
            json.addProperty("email", user.getEmail());
            json.addProperty("phone", user.getPhoneNumber());
            json.addProperty("dob", user.getDob());

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

    public boolean updateUser(User user){
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - insert card endpoint
            URL url = new URL(String.format("http://127.0.0.1:5000/user/"));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            AccountDAO accountDAO = new AccountDAO();

            json.addProperty("username", user.getUsername());
            json.addProperty("password", user.getPassword());
            json.addProperty("user_details_id", user.getUserDetailsID());
            json.addProperty("account_id", (accountDAO.getAccountNumber(json.get("account_id").getAsString()).intValue()));
//user.getAccountID
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

    public User getUserDetails(int userDetailsId){
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get card endpoint
            URL url = new URL(String.format("http://127.0.0.1:5000//user/details//%s", userDetailsId));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
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
                User user = new User();

                user.setFirstName(json.get("firstname").getAsString());
                user.setLastName(json.get("lastname").getAsString());
                user.setEmail(json.get("email").getAsString());
                user.setDob(json.get("dob").getAsString());
                user.setPhoneNumber(json.get("phone").getAsString());
                user.setUserDetailsID(json.get("user_details_id").getAsInt());

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public boolean deleteUser(User user){
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - delete card endpoint
            URL url = new URL(String.format("http://127.0.0.1:5000/user/%s", user.getUserID()));

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
