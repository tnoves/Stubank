package com.team46.stubank.data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team46.stubank.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Harry Alexander
 **/


public class UserDAO {

    // getUser method used to retrieve a user from the database.
    public User getUser(int userID) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get User endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/user/%s", userID));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

           // if http response code isnt 200 throw exception
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

                User user = new User();
                // set user attributes to data from json object

                user.setUserID(json.get("id").getAsInt());
                user.setUserDetailsID(json.get("user_details_id").getAsInt());
                user.setUsername(json.get("username").getAsString());
                user.setAccountID(json.get("account_id").getAsString());

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

    // getUser method used to retrieve a user from the database.
    public User getUserByDetails(int userDetailsID) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get User endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/user/from/details/%s", userDetailsID));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            // if http response code isnt 200 throw exception
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

                User user = new User();
                // set user attributes to data from json object

                user.setUserID(json.get("id").getAsInt());
                user.setUserDetailsID(json.get("user_details_id").getAsInt());
                user.setUsername(json.get("username").getAsString());
                user.setAccountID(json.get("account_id").getAsString());

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
            // make connection to the StuBank api - insert User endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/user/"));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(10 * 1000);
            conn.setDoOutput(true);
            conn.connect();

            // adds user variables to jsonObject and then inserts that data into the database
            JsonObject json = new JsonObject();
            json.addProperty("username", user.getUsername());
            json.addProperty("password", user.getPassword());
            json.addProperty("dob", user.getDob());
            json.addProperty("email", user.getEmail());
            json.addProperty("firstname", user.getFirstName());
            json.addProperty("lastname", user.getLastName());
            json.addProperty("phone", user.getPhoneNumber());

            try (DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream())) {
                dataOutputStream.writeBytes(json.toString());
            }
            //// if http response code isnt 200 throw exception
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

                    //gets new randomly generated ids from database and sets them to user objects ids
                    user.setUserID((responseJson.get("id").getAsInt()));
                    user.setAccountID((responseJson.get("account_id").getAsString()));
                    user.setUserDetailsID((responseJson.get("user_details_id").getAsInt()));
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

    public boolean updateUserDetails(User user) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - insert User endpoint
            URL url = new URL(String.format("http://127.0.0.1:5000/user/details/%s", user.getUserDetailsID()));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();

            // adds user variables to jsonObject and then inserts that data into the database
            json.addProperty("firstname", user.getFirstName());
            json.addProperty("lastname", user.getLastName());
            json.addProperty("email", user.getEmail());
            json.addProperty("phone", user.getPhoneNumber());
            json.addProperty("dob", user.getDob());

            // gets output stream from API and converts it to string.
            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(json.toString());
            dataOutputStream.close();

            //// if http response code isnt 200 throw exception
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

    public boolean updateUser(User user) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - insert User endpoint
            URL url = new URL(String.format("http://127.0.0.1:5000/user/%s", user.getUserID()));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            // adds user variables to jsonObject and then inserts that data into the database
            json.addProperty("id", user.getUserID());
            json.addProperty("account_id", user.getAccountID());
            json.addProperty("user_details_id", user.getUserDetailsID());
            json.addProperty("username", user.getUsername());
            // gets output stream from API and converts it to string.
            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(json.toString());
            dataOutputStream.close();
            //// if http response code isnt 200 throw exception
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

    public User getUserDetails(int userdetailsID) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get user endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000//user/details/%s", userdetailsID));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            //// if http response code isnt 200 throw exception
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

                User user = new User();
                // set user attributes to data from json object
                user.setDob(json.get("dob").getAsString());
                user.setEmail(json.get("email").getAsString());
                user.setFirstName(json.get("firstname").getAsString());
                user.setLastName(json.get("lastname").getAsString());
                user.setPhoneNumber(json.get("phone").getAsString());
                user.setUserDetailsID(json.get("user_details_id").getAsInt());
                // user.setDob(json.get("dob").getAsString());

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

    public boolean deleteUser(User user) {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - delete user endpoint
            URL url = new URL(String.format("http://10.0.2.2:5000/user/%s", user.getUserID()));

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

    public ArrayList<User> getAllUsers() {
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api - get all users in user table
            URL url = new URL(String.format("http://10.0.2.2:5000/user/all/"));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            // if http response code is not 200 thrown exception
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
                ArrayList<User> userList = new ArrayList<User>();

                for (JsonElement users : json) {
                    JsonObject userObj = users.getAsJsonObject();

                    // assign card json response to card object and return
                    User user = new User();

                    user.setUserID(userObj.get("id").getAsInt());
                    user.setUserDetailsID(userObj.get("user_details_id").getAsInt());
                    user.setUsername(userObj.get("username").getAsString());
                    user.setAccountID(userObj.get("account_id").getAsString());

                    // add to the list of cards
                    userList.add(user);
                }
                return userList;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }


    public User getUserByUsername(String username){
        HttpURLConnection conn = null;
        try {
            // make connection to the StuBank api -
            URL url = new URL(String.format("http://10.0.2.2:5000/user/username/%s", username));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            //conn.setDoOutput(true);
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
                // create json object and parse the response string from api
                JsonObject json = JsonParser.parseString(response).getAsJsonObject();

                User user = new User();

                user.setUserID(json.get("id").getAsInt());
                user.setUserDetailsID(json.get("user_details_id").getAsInt());
                user.setUsername(json.get("username").getAsString());
                user.setAccountID(json.get("account_id").getAsString());
                user.setPassword(json.get("password").getAsString());

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
}
