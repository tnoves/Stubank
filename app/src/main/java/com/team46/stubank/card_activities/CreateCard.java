package com.team46.stubank.card_activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team46.stubank.Card;
import com.team46.stubank.R;
import com.team46.stubank.User;
import com.team46.stubank.data_access.AccountDAO;
import com.team46.stubank.data_access.CardDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CreateCard class, popup window that provides option for user to create a card
 *
 *
 * @author  George Cartridge
 * @version 1.0
 */
public class CreateCard extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        // populate dropdown with pre-defined currencies
        Spinner dropDown = findViewById(R.id.selectCurrency);
        String[] currencies = new String[]{ "GBP", "EUR", "USD" };

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies);

        dropDown.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // populate dropdown with pre-defined currencies
        Spinner dropDown = findViewById(R.id.selectCurrency);
        String[] currencies = new String[]{ "GBP", "EUR", "USD" };

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies);

        dropDown.setAdapter(adapter);
    }

    public void showPopupWindow(View view, User user) {
        // display popup in given view
        LayoutInflater inflater = (LayoutInflater)
                view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_create_card, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = false;

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // initialise dropdown with currencies
        Spinner dropDown = popupView.findViewById(R.id.selectCurrency);

        String[] currencies = new String[]{ "GBP", "EUR", "USD" };

        adapter = new ArrayAdapter<>(popupView.getContext(),
                android.R.layout.simple_spinner_dropdown_item, currencies);

        dropDown.setAdapter(adapter);

        ProgressBar loading = popupView.findViewById(R.id.createCardProgressBar);

        // create new card
        Button submitButton = popupView.findViewById(R.id.createCardSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                loading.setVisibility(View.VISIBLE);
                loading.bringToFront();

                // create card in background and add to database
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CardDao cardDao = new CardDao();
                            AccountDAO accountDAO = new AccountDAO();

                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.YEAR, 2);
                            Date current = cal.getTime();
                            String expiryDate = dateFormat.format(current);

                            String accountNum = accountDAO.getAccountNumber(user.getAccountID());
                            String sortCode = accountDAO.getSortCodeNumber(accountDAO.getSortCodeId(user.getAccountID()));

                            String cvc = "";
                            Random random = new Random();

                            for (int i = 0; i < 3; i++) {
                                cvc += "0123456789".charAt(random.nextInt("0123456789".length()));
                            }

                            Card card = new Card();
                            card.setCardNumber("");
                            card.setBalance(0);
                            card.setCardType(dropDown.getSelectedItem().toString());
                            card.setAccountNum(accountNum);
                            card.setSortCode(sortCode);
                            card.setCvcCode(cvc);
                            card.setExpiryEnd(expiryDate);
                            card.setPaymentProcessor("Visa");
                            card.setActive(true);

                            cardDao.insertCard(card, user);
                            DisplayCards.cards.add(card);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // dismiss popup and return to previous screen with success message
                                loading.setVisibility(View.GONE);
                                popupWindow.dismiss();
                                Toast toast = Toast.makeText(view.getRootView().getContext(), "New card has been added to your account.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            }
                        });
                    }
                });
            }
        });

        // dismiss popup if click outside of window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}