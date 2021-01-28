package com.team46.stubank.card_activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team46.stubank.Card;
import com.team46.stubank.DisplayPay;
import com.team46.stubank.R;
import com.team46.stubank.TopupCard;
import com.team46.stubank.Transaction;
import com.team46.stubank.User;
import com.team46.stubank.data_access.TransactionDAO;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Ben McIntyre
 **/

public class ViewCard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionRecyclerViewAdapter transactionAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Card card;
    private User user;
    private Locale locale;
    public static List<Transaction> transactions = new ArrayList<Transaction>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        transactions.clear();

        // Fetches card from previous activity.
        Intent intent = getIntent();
        card = (Card) intent.getSerializableExtra("card");
        user = (User) intent.getSerializableExtra("newUser");


        // Fetches elements on activity which will be updated.
        TextView cardNumber = findViewById(R.id.viewCardNumber);
        TextView balance = findViewById(R.id.viewCardBalance);

        // Determines which currency the card is in and sets formatting for that.
        switch (card.getCardType()) {
            case ("GBP"):
                locale = Locale.UK;
                break;
            case ("EUR"):
                locale = Locale.GERMANY;
                break;
            case ("USD"):
                locale = Locale.US;
                break;
        }
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        // Updates elements in the activity to show card number and balance.
        cardNumber.setText(card.getCardNumber());
        balance.setText(numberFormat.format(card.getBalance()));

        // Creates new thread.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        ProgressBar loading = findViewById(R.id.transactionProgressBar);
        //transactionAdapter = new TransactionRecyclerViewAdapter(transactions, card);

        Future future = executor.submit(new Runnable() {
            @Override
            public void run() {
                transactionAdapter = new TransactionRecyclerViewAdapter(transactions, card);
            }
        });

        try {
            future.get(120, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        loading.setVisibility(View.VISIBLE);

        // Retrieves all transactions on the users card.
        executor.submit(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                TransactionDAO transactionDAO = new TransactionDAO();
                transactions.addAll(transactionDAO.getCardTransactions(card.getCardNumber()));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        transactionAdapter.notifyDataSetChanged();
                        loading.setVisibility(View.GONE);
                    }
                });

                executor.shutdown();
            }
        });

        // Fetches element from activity where the transactions will be displayed.
        recyclerView = (RecyclerView) findViewById(R.id.transaction_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(transactionAdapter);

        Button makePaymentButton = findViewById(R.id.makePaymentButton);
        makePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DisplayPay.class);
                intent.putExtra("newUser", user);
                view.getContext().startActivity(intent);
            }
        });

        Button topupButton = findViewById(R.id.topupCardButton);
        topupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopupCard topupCardPopup = new TopupCard();
                topupCardPopup.showPopupWindow(v, card, user);
            }
        });

        Button changeCardButton = findViewById(R.id.changeCardButton);
        changeCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayCards.class);
                startActivity(intent);
            }
        });
    }
}