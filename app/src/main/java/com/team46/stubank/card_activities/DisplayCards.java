package com.team46.stubank.card_activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team46.stubank.Card;
import com.team46.stubank.R;
import com.team46.stubank.User;
import com.team46.stubank.data_access.CardDao;
import com.team46.stubank.data_access.UserDAO;

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
        // Intent intent = getIntent();
        // user = (User) intent.getSerializableExtra("user");

        // Create new thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        ProgressBar loading = findViewById(R.id.progressBar);
        cardAdapter = new CardRecyclerViewAdapter(cards);

        // Retrieve user and user's cards in background thread
        executor.submit(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.VISIBLE);

                UserDAO userDAO = new UserDAO();
                user = userDAO.getUser(28);

                CardDao cardDao = new CardDao();
                cards.addAll(cardDao.getAllCards(user.getUserID()));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cardAdapter.notifyDataSetChanged();
                        loading.setVisibility(View.GONE);
                    }
                });

                executor.shutdown();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.card_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Pass card adapter to the recycler view
        recyclerView.setAdapter(cardAdapter);

        // Listen to add card button press event
        FloatingActionButton addCardButton = findViewById(R.id.addCardButton);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateCard createCardPopup = new CreateCard();
                createCardPopup.showPopupWindow(view);
            }
        });
    }
}