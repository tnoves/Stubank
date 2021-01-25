package com.team46.stubank;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team46.stubank.data_access.UserDAO;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText username, password, id;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);

        id = (EditText) findViewById(R.id.userId);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetchUser(username);
                ExampleRunnable runnable = new ExampleRunnable();
                new Thread(runnable).start();
               /* if (validateUserExists(id, password, username))*/
                    viewMenu(v);
            }
        });

    }

/*    public Boolean fetchUser(EditText username){
        User user = new User();
        String usernameInput = username.getText().toString();
        UserDAO userDAO = new UserDAO();
        userDAO.getAllUsers();
        String allUsers = Arrays.toString(userDAO.getAllUsers());

        if (allUsers.contains(usernameInput)){

            return true;
        }
        else return false;
    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean validateUserExists(EditText id, EditText password, EditText username) {
        String userIdInput = id.getText().toString();
        int userIdInputInt = Integer.parseInt(userIdInput);

        String hashedPassword = hashPassword(password);
        String usernameInput = username.getText().toString();

        try {
            System.out.println(hashedPassword);
            System.out.println(usernameInput);
            System.out.println(userIdInputInt);
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUser(1253);

            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            System.out.println(user.getUserID());

      /*      if (user.getUserID() == userIdInputInt && user.getUsername().equals(usernameInput) && user.getPassword().equals(passwordInput)) {
                return true;
            } else {
                Toast.makeText(this, "Invalid user credentials!", Toast.LENGTH_SHORT).show();
                return false;
            }*/

        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void viewMenu(View view) {
        Intent intent = new Intent(this, ViewMainMenu.class);
        startActivity(intent);
    }

    public void viewCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    class ExampleRunnable implements Runnable {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            validateUserExists(id, password, username);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String hashPassword(EditText password){
        String hashedPassword = null;
        String passwordInput = password.getText().toString();
        try {
            hashedPassword = toHexString(getSHA(passwordInput));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    // getSHA method gets instance of SHA-256 algorithm and assigns it to messageDigest and returns it
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));
        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}