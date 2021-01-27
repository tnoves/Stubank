package com.team46.stubank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team46.stubank.card_activities.ViewCard;
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
    private Card selectedCard;
    private PaymentAccount paymentAccount;
    private float paymentAmount = 0;

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card card) {
        this.selectedCard = card;
    }

    public PaymentAccount getPaymentAccount() { return paymentAccount; }

    public void setPaymentAccount(PaymentAccount paymentAccount) { this.paymentAccount = paymentAccount; }

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pay);

        FloatingActionButton backButton = findViewById(R.id.backButton);
        FloatingActionButton forwardButton = findViewById(R.id.forwardButton);

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

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == (payFragmentPagerAdapter.getCount() - 1)) {
                    // Make payment
                    selectedCard.makePayment(paymentAmount, user, paymentAccount);
                    Toast.makeText(viewPager.getContext(), "Your payment has been sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(viewPager.getContext(), ViewCard.class);
                    viewPager.getContext().startActivity(intent);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }

                // Change icon of button to tick if on the review payment screen
                if (viewPager.getCurrentItem() == (payFragmentPagerAdapter.getCount() - 1)) {
                    forwardButton.setImageResource(android.R.drawable.ic_menu_send);
                    forwardButton.setScaleX(1);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);

                if (viewPager.getCurrentItem() != (payFragmentPagerAdapter.getCount() - 1)) {
                    forwardButton.setImageResource(R.drawable.abc_vector_test);
                    forwardButton.setScaleX(-1);
                }
            }
        });
    }
}