package com.team46.stubank.card_activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team46.stubank.Card;
import com.team46.stubank.R;
import com.team46.stubank.User;
import com.team46.stubank.data_access.CardDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisplayCards extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardRecyclerViewAdapter cardAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private User user;
    public static List<Card> cards = new ArrayList<Card>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);
        cards.clear();

        // Get logged in user from previous activity
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("newUser");

        recyclerView = (RecyclerView) findViewById(R.id.card_rv);
        recyclerView.setHasFixedSize(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Create new thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        ProgressBar loading = findViewById(R.id.progressBar);
        cardAdapter = new CardRecyclerViewAdapter(cards, user);

        // Retrieve user and user's cards in background thread
        executor.submit(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.VISIBLE);

                CardDao cardDao = new CardDao();
                cards.addAll(cardDao.getAllCards(user.getUserID()));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cardAdapter.notifyDataSetChanged();
                        loading.setVisibility(View.GONE);

                        if (cards.size() <= 0) {
                            builder.setMessage("Your first card").setTitle("Your first card");

                            builder.setMessage("Please create your first card by pressing the add " +
                                    "button in the bottom right corner")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert = builder.create();
                            alert.setTitle("Your first card");
                            alert.show();
                        }
                    }
                });

                executor.shutdown();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Pass card adapter to the recycler view
        recyclerView.setAdapter(cardAdapter);

        // Listen to add card button press event
        FloatingActionButton addCardButton = findViewById(R.id.addCardButton);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cards.size() >= 3) {
                    builder.setMessage("Card limit reached").setTitle("Card limit reached");

                    builder.setMessage("You cannot create more than 3 cards")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.setTitle("Card limit reached");
                    alert.show();
                } else {
                    CreateCard createCardPopup = new CreateCard();
                    createCardPopup.showPopupWindow(view, user);
                }
            }
        });
    }
}