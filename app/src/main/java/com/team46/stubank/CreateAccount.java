package com.team46.stubank;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team46.stubank.data_access.UserDAO;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity{

    // REGEX containing every quality the password input must contain
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
    // REGEX containing every quality the username input must contain
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile
                    ("^" + "(?=.*[a-zA-Z])" + "(?=\\S+$)" + ".{3,}");
    // Username can contain any letters, contain no white space, and be at least 3 characters in length

    // REGEX containing every quality the DOB input must contain
    private static final Pattern DOB_PATTERN =
            Pattern.compile
                    ("^\\d{4}-\\d{2}-\\d{2}$");
    // Dob must be in the format yyyy-mm-dd

    EditText firstname, lastname, dob, phone, email, username, password;
    Button submit;
    Boolean userAlreadyExists = false;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        submit = (Button)findViewById(R.id.createAccountSubmit);

        firstname  = (EditText)findViewById(R.id.editFirstName);
        lastname = (EditText)findViewById(R.id.editLastName);
        dob = (EditText)findViewById(R.id.editDateofBirth);
        phone = (EditText)findViewById(R.id.editPhoneNumber);
        email = (EditText)findViewById(R.id.editEmail);
        username = (EditText)findViewById(R.id.editUsername);
        password = (EditText)findViewById(R.id.editPassword);

        //On Click listener for the submit button, handles methods when button is clicked
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

                        // call runnable only when all validations are passed
                        if (validateEmail(email) && validateDob(dob) && validateFirstName(firstname) && validateLastName(lastname)
                                && validatePhone(phone) && validateUsername(username) && validatePassword(password))
                        {
                            ExampleRunnable runnable = new ExampleRunnable();
                            new Thread(runnable).start();
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //System.out.println(validateUserExistence(username));
                            System.out.println(userAlreadyExists);
                            if (!userAlreadyExists){displayMainMenu(view);
                            System.out.println("Username available");}
                            else{System.out.println("Username Taken!");}
                        }
                    }
                });
    }

    //addUser method gets user input, calls hashpassword function and creates user object
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

    // checks if username already exists in the database
    public boolean validateUserExistence(EditText username) {
        String usernameInput = username.getText().toString();
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(usernameInput);
            if(user.getUsername() != null){
                return true;
            }
            else{
                return false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    //checks if firstname input isnt null and isnt greater than 20 charaters
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

    //checks is lastname input is not null and not greater than 20 characters
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

    //checks if dob matches the regex and isnt null
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

    //checks if phone matches regex and isnt null
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

    //checks if email matches regex and is not null
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

    // checks if username matches regex and isnt null
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

    // checks if password matches regex and isnt null
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

    // creates a new intent, taking user to main menu. Also, puts user object as an extra
    public void displayMainMenu(View view){
        Intent intent = new Intent(this, ViewMainMenu.class);
        intent.putExtra("newUser", user);
        startActivity(intent);
    }

    // calls getSHA and toHexString to create a hash of the user's inputted password
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

    // creates Example Runnable class and implements Runnable in order to create a secondary thread
    class ExampleRunnable implements Runnable{
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            validateUserExistence(username);
            if (!validateUserExistence(username)){addUser(firstname, lastname, dob, phone, email, username, password);
                UserDAO userDAO = new UserDAO();
                userDAO.insertUser(user);
                userAlreadyExists = false;
            }
            else{userAlreadyExists = true;}
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

    // converts bytearry to String
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