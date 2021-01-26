package com.team46.stubank;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.team46.stubank.data_access.CardDao;
import com.team46.stubank.data_access.UserDAO;
import com.team46.stubank.pay_fragments.PayFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DisplayPay extends AppCompatActivity {

    private ArrayList<Card> cards = new ArrayList<>();
    private PayFragmentPagerAdapter payFragmentPagerAdapter;
    private User user;
    public static Card selectedCard = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pay);

        // cards = (ArrayList<Card>) getIntent().getSerializableExtra("cards");

        ProgressBar loading = findViewById(R.id.progressBar2);

        payFragmentPagerAdapter = new PayFragmentPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, cards);

        // Create new thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        // Retrieve user's cards in background thread
        executor.submit(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.VISIBLE);

                UserDAO userDAO = new UserDAO();
                user = userDAO.getUser(28);

                CardDao cardDao = new CardDao();
                cards.addAll(cardDao.getAllCards(user.getUserID()));

                selectedCard = cards.get(0);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        payFragmentPagerAdapter.notifyDataSetChanged();
                        loading.setVisibility(View.GONE);
                    }
                });

                executor.shutdown();
            }
        });

        while(!executor.isTerminated()) {
            try {
                executor.awaitTermination(120, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ViewPager viewPager = findViewById(R.id.payViewPager);
        viewPager.setAdapter(payFragmentPagerAdapter);
    }
}