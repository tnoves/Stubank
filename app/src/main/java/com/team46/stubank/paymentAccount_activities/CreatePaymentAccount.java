package com.team46.stubank.paymentAccount_activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team46.stubank.PaymentAccount;
import com.team46.stubank.R;
import com.team46.stubank.User;
import com.team46.stubank.data_access.PaymentAccountDAO;
import com.team46.stubank.data_access.UserDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CreatePaymentAccount class, creates a new PaymentAccount within the application
 *
 *
 * @author  Douglas Gray
 * @version 1.0
 * @since   2021-01-20
 */

public class CreatePaymentAccount extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private View view;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_payact);
        view = (View) findViewById(R.id.txtCreationMessage);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        Button submitButton = findViewById(R.id.btnCreatePayact);
        submitButton.setOnClickListener(v -> {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.submit(() -> {
                try {
                    PaymentAccountDAO paymentAccountDAO = new PaymentAccountDAO();
                    PaymentAccount paymentAccount = new PaymentAccount();

                    paymentAccount.setAccountID(Integer.parseInt(user.getAccountID()));
                    paymentAccount.setUserDetailsID(user.getUserDetailsID());

                    paymentAccountDAO.insertPaymentAccount(paymentAccount);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                handler.post(() -> {
                    Toast toast = Toast.makeText(view.getRootView().getContext(), "New paymentAccount added", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                });
               executor.shutdown();

            });
        });
    }
}
