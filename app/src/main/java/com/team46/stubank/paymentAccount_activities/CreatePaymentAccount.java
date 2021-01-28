package com.team46.stubank.paymentAccount_activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team46.stubank.PaymentAccount;
import com.team46.stubank.R;
import com.team46.stubank.User;
import com.team46.stubank.data_access.PaymentAccountDAO;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_payact);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
    }

    public void showPopupWindow(View view, User user) {

        LayoutInflater inflater = (LayoutInflater)
                view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_create_payact, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = false;

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 20);

        Button submitButton = popupView.findViewById(R.id.btnCreatePaymentAccount);

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

        // Dismiss popup if click outside of window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

    }
}
