package com.team46.stubank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccount extends AppCompatActivity {

    EditText mEditText, mEditText1, mEditText2, mEditText3, mEditText4, mEditText5, mEditText6;
    Button mButton;
    String firstName, lastName, dob, phone, email, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mButton = (Button)findViewById(R.id.createAccountSubmit);

        mEditText  = (EditText)findViewById(R.id.editTextTextPersonName2);
        mEditText1 = (EditText)findViewById(R.id.editTextTextPersonName3);
        mEditText2 = (EditText)findViewById(R.id.editTextDate);
        mEditText3 = (EditText)findViewById(R.id.editTextTextPersonName5);
        mEditText4 = (EditText)findViewById(R.id.editTextTextEmailAddress);
        mEditText5 = (EditText)findViewById(R.id.editTextTextPersonName6);
        mEditText6 = (EditText)findViewById(R.id.editTextTextPassword);


        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        firstName = mEditText.getText().toString();
                        lastName = mEditText1.getText().toString();
                        dob = mEditText2.getText().toString();
                        phone = mEditText3.getText().toString();
                        email = mEditText4.getText().toString();
                        username = mEditText5.getText().toString();
                        password = mEditText6.getText().toString();
                    }
                });
    }


    public void addUser(String firstName, String lastName, String dob, String phone, String email, String username, String password){

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}