package com.team46.stubank.card_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.team46.stubank.Card;
import com.team46.stubank.DisplayPay;
import com.team46.stubank.R;
import com.team46.stubank.Transaction;
import com.team46.stubank.budget_activities.DisplayBudget;
import com.team46.stubank.data_access.CardDao;
import com.team46.stubank.data_access.TransactionDAO;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewCard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionRecyclerViewAdapter transactionAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Card card;
    private Locale locale;
    public static List<Transaction> transactions = new ArrayList<Transaction>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        transactions.clear();

        /*
        Intent intent = getIntent();
        card = (Card) intent.getSerializableExtra("card");
         */

        TextView cardNumber = findViewById(R.id.viewCardNumber);
        TextView balance = findViewById(R.id.viewCardBalance);

        cardNumber.setText(card.getCardNumber());
        switch (card.getCardType()) {
            case ("GBP"):
                locale = Locale.UK;
            case ("EUR"):
                locale = Locale.GERMANY;
            case ("USD"):
                locale = Locale.US;
        }
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        balance.setText(numberFormat.format(card.getBalance()));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        ProgressBar loading = findViewById(R.id.transactionProgressBar);
        transactionAdapter = new TransactionRecyclerViewAdapter(transactions);

        executor.submit(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.VISIBLE);

                // TODO: Remove this once intent is implemented.
                CardDao cardDAO = new CardDao();
                card = cardDAO.getCard("0342480786061418");

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

        recyclerView = (RecyclerView) findViewById(R.id.transaction_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(transactionAdapter);
    }

    public void viewBudget(View view) {
        Intent intent = new Intent(this, DisplayBudget.class);
        startActivity(intent);
    }

    // TODO: Unsure if this is the correct class to be calling.
    public void makePayment(View view) {
        Intent intent = new Intent(this, DisplayPay.class);
        startActivity(intent);
    }
}