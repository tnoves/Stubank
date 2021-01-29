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
import com.team46.stubank.Settings;
import com.team46.stubank.User;
import com.team46.stubank.data_access.CardDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DisplayCards class, displays cards from database within the application
 *
 *
 * @author  George Cartridge
 * @version 1.0
 */
public class DisplayCards extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardRecyclerViewAdapter cardAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private User user;
    private ProgressBar loading;
    public static List<Card> cards = new ArrayList<Card>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);
        cards.clear();

        // get logged in user from previous activity
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("newUser");

        recyclerView = (RecyclerView) findViewById(R.id.card_rv);
        recyclerView.setHasFixedSize(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        loading = findViewById(R.id.progressBar);
        cardAdapter = new CardRecyclerViewAdapter(cards, user);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // pass card adapter to the recycler view
        recyclerView.setAdapter(cardAdapter);

        // show create card popup if user has pressed the FAB button
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

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Intent intent = getIntent();
            user = (User) intent.getSerializableExtra("newUser");

            loading = findViewById(R.id.progressBar);
            cardAdapter = new CardRecyclerViewAdapter(cards, user);

            // pass card adapter to the recycler view
            recyclerView.setAdapter(cardAdapter);

            // create new thread
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            loading.setVisibility(View.VISIBLE);

            // retrieve user's cards in background thread
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    CardDao cardDao = new CardDao();
                    cards.addAll(cardDao.getAllCards(user.getUserID()));

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cardAdapter.notifyDataSetChanged();
                            loading.setVisibility(View.GONE);

                            // show prompt if no cards exist
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewSettings(View v){
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("newUser", user);
        startActivity(intent);
    }
}