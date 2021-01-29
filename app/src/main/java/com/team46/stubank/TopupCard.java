package com.team46.stubank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team46.stubank.card_activities.ViewCard;
import com.team46.stubank.data_access.PaymentAccountDAO;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TopupCard extends AppCompatActivity {

    private View previousView = null;
    private Context _context;
    private Card _card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_card);
    }

    public void showPopupWindow(Context mContext, View view, Card card, User user) {
        LayoutInflater inflater = (LayoutInflater)
                view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_topup_card, null);

        _context = mContext;
        _card = card;

        previousView = view;

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // Initialise dropdown with currencies
        EditText paymentAmount = popupView.findViewById(R.id.topupAmount);
        ProgressBar loading = popupView.findViewById(R.id.topupProgressBar);

        // Topup balance
        Button submitButton = popupView.findViewById(R.id.topupCardSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                loading.setVisibility(View.VISIBLE);
                loading.bringToFront();

                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // retrieve payment account for this card
                            PaymentAccountDAO paymentAccountDAO = new PaymentAccountDAO();
                            PaymentAccount thisAccount = null;
                            ArrayList<PaymentAccount> allAccounts = paymentAccountDAO.getAllPaymentAccount(user.getUserDetailsID());
                            for (PaymentAccount paymentAccount: allAccounts) {
                                System.out.println(paymentAccount);
                                if (paymentAccount.getAccountID() == Integer.parseInt(user.getAccountID())) {
                                    thisAccount = paymentAccount;
                                    break;
                                }
                            }

                            // payment account doesn't exist for own card, so create one
                            if (thisAccount == null) {
                                PaymentAccount newPaymentAccount = new PaymentAccount();
                                newPaymentAccount.setAccountID(Integer.parseInt(user.getAccountID()));
                                newPaymentAccount.setUserDetailsID(user.getUserDetailsID());

                                paymentAccountDAO.insertPaymentAccount(newPaymentAccount);
                                thisAccount = newPaymentAccount;
                            }

                            boolean madePayment = card.topup(Double.parseDouble(paymentAmount.getText().toString()), user);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    loading.setVisibility(View.GONE);

                                    Intent intent = new Intent(v.getContext(), ViewCard.class);
                                    intent.putExtra("newUser", user);
                                    intent.putExtra("card", _card);
                                    v.getContext().startActivity(intent);

                                    popupWindow.dismiss();

                                    if (madePayment == true) {
                                        Toast.makeText(v.getContext(), "You topped up your account!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(v.getContext(), "Your topup failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            executor.shutdown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
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