package com.team46.stubank;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.team46.stubank.card_activities.DisplayCards;
import com.team46.stubank.data_access.UserDAO;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    int loginAttempts = 0;
    Boolean validCredentials = false;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        //creates OnclickListener to handler methods when button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creates instance of Example runnable class in order to create a new thread
                    ExampleRunnable runnable = new ExampleRunnable();
                    new Thread(runnable).start();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (validCredentials){
                        viewCards(v);}
                    else{openDialog();}
            }
        });
    }
    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "alert");
    }

    //checks if user exists in the database and that the input from the user matches what is in the database
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean validateUserExists(EditText password, EditText username) {

        String hashedPassword = hashPassword(password);
        String usernameInput = username.getText().toString();

        try {

            UserDAO userDAO = new UserDAO();
            User user1 = userDAO.getUserByUsername(usernameInput);

            if (user1.getUsername().equals(usernameInput) && user1.getPassword().equals(hashedPassword)) {
                user = user1;
                return true;

            } else {
                Toast.makeText(this, "Invalid user credentials!", Toast.LENGTH_SHORT).show();
                return false;
            }

        } catch (NullPointerException e) {
           /* Toast.makeText(this, "User does not exist!", Toast.LENGTH_SHORT).show();*/
            loginAttempts += 1;
            e.printStackTrace();
            return false;
        }
    }

    // creates new intent taking user to the main menu
    public void viewCards(View view) {

        if (validCredentials = true){
            Intent intent = new Intent(this, DisplayCards.class);
            intent.putExtra("newUser", user);
            startActivity(intent);
        }
        else{System.out.println("No menu for you");}
    }

    //creates new intent taking user to the create account screen
    public void viewCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    //creates new example runnable calss and implements runnable
    class ExampleRunnable implements Runnable {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run()
        {
            validCredentials = validateUserExists(password, username);
        }
    }

    // calls getSHA and toHexString methods to create a hash of user's password input
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

    // turns byte array into string
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