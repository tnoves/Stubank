package com.team46.stubank;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.team46.stubank.data_access.UserDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Harry Alexander
 **/

public class Settings extends AppCompatActivity {

    Button removeAccount, signOut, changeAccount;
    User user;

    //get user object from create Account or login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //gets the user object created in the CreateAccount activity
        user = (User) getIntent().getSerializableExtra("newUser");

        removeAccount = findViewById(R.id.removeAccount);
        signOut = findViewById(R.id.signOut);
        changeAccount = findViewById(R.id.changeAccount);

        //creates OnClickListener to handle methods when the button is clicked
        removeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ExampleRunnable runnable = new ExampleRunnable();
                new Thread(runnable).start(); // deletes user from database
                displayLoginPage(v); //takes user back to the login page
                //perhaps add a prompt?
            }
        });
        // creates OnClickListener to handle methods when the button is clicked
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLoginPage(v);
                // set user object to null
            }
        });
    }

    //takes user to the login screen
    public void displayLoginPage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //invokes userDAO.deleteUser method to remove user entity from database
    public void removeUser(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser(user);
    }

    //secondary thread to handle network request
    class ExampleRunnable implements Runnable{

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
           removeUser(user);
        }
    }
}