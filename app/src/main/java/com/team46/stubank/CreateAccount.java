package com.team46.stubank;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team46.stubank.card_activities.ViewCard;
import com.team46.stubank.data_access.UserDAO;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity{

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile
                    ("^" +
                    "(?=.*[0-9])" +       //at least 1 digit
                    "(?=.*[a-z])" +       //at least 1 lower case letter
                    "(?=.*[A-Z])" +       //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile
                    ("^" + "(?=.*[a-zA-Z])" + "(?=\\S+$)" + ".{3,}");
    // Username can contain any letters, contain no white space, and be at least 3 characters in length

    private static final Pattern DOB_PATTERN =
            Pattern.compile
                    ("^\\d{4}-\\d{2}-\\d{2}$");
    // Dob must be in the format yyyy-mm-dd

    EditText firstname, lastname, dob, phone, email, username, password;
    Button submit;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        submit = (Button)findViewById(R.id.createAccountSubmit);

        firstname  = (EditText)findViewById(R.id.editTextTextPersonName2);
        lastname = (EditText)findViewById(R.id.editTextTextPersonName3);
        dob = (EditText)findViewById(R.id.editTextDate);
        phone = (EditText)findViewById(R.id.editTextTextPersonName5);
        email = (EditText)findViewById(R.id.editTextTextEmailAddress);
        username = (EditText)findViewById(R.id.editTextTextPersonName6);
        password = (EditText)findViewById(R.id.editTextTextPassword);

        submit.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        validateFirstName(firstname);
                        validateLastName(lastname);
                        validateEmail(email);
                        validateDob(dob);
                        validatePhone(phone);
                        validateUsername(username);
                        validatePassword(password);

                        if (validateEmail(email) && validateDob(dob) && validateFirstName(firstname) && validateLastName(lastname)
                                && validatePhone(phone) && validateUsername(username) && validatePassword(password) )
                        {
                            ExampleRunnable runnable = new ExampleRunnable();
                            new Thread(runnable).start();
                            displayMainMenu(view);
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void addUser(EditText firstName, EditText lastName, EditText dob, EditText phone, EditText email, EditText username, EditText password){

        String firstNameInput = firstName.getText().toString();
        String lastNameInput = lastName.getText().toString();
        String dobInput = dob.getText().toString();
        String phoneInput = phone.getText().toString();
        String emailInput = email.getText().toString();
        String usernameInput = username.getText().toString();
        //String passwordInput = password.getText().toString();
        String passwordHashed = hashPassword(password);

        user.setFirstName(firstNameInput);
        user.setLastName(lastNameInput);
        user.setDob(dobInput);
        user.setPhoneNumber(phoneInput);
        user.setEmail(emailInput);
        user.setUsername(usernameInput);
        user.setPassword(passwordHashed);

        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getDob());
        System.out.println(user.getEmail());
        System.out.println(user.getPhoneNumber());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
    }

    public boolean validateFirstName(EditText firstname) {
        String firstNameInput = firstname.getText().toString();
        if (!firstNameInput.isEmpty() && !(firstNameInput.length() > 20)){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid First Name!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateLastName(EditText lastname){
        String lastNameInput = lastname.getText().toString();
        if (!lastNameInput.isEmpty() && !(lastname.length() > 20)) {
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Last Name!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateDob(EditText dob){
        String dobInput = dob.getText().toString();
        if (!dobInput.isEmpty() && DOB_PATTERN.matcher(dobInput).matches()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Date of Birth!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validatePhone(EditText phone){
        String phoneInput = phone.getText().toString();
        if (!phoneInput.isEmpty() && Patterns.PHONE.matcher(phoneInput).matches()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Phone Number!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateEmail(EditText email){
         String emailInput = email.getText().toString();
         if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
             return true;
         }
         else {
             Toast.makeText(this, "Invalid Email Address!", Toast.LENGTH_SHORT).show();
             return false;
         }
    }

    public boolean validateUsername(EditText username){
        String usernameInput = username.getText().toString();
        if (!usernameInput.isEmpty() && USERNAME_PATTERN.matcher(usernameInput).matches()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Username!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validatePassword(EditText password){
        String passwordInput = password.getText().toString();
        if (!passwordInput.isEmpty() && PASSWORD_PATTERN.matcher(passwordInput).matches()){
            return true;
        }
        else {
            Toast.makeText(this, "Invalid Password!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void displayMainMenu(View view){
        Intent intent = new Intent(this, ViewMainMenu.class);
        intent.putExtra("newUser", user);
        startActivity(intent);
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

    class ExampleRunnable implements Runnable{

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            addUser(firstname, lastname, dob, phone, email, username, password);
            UserDAO userDAO = new UserDAO();
            userDAO.insertUser(user);
        }
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