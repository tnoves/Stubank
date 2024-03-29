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
import com.team46.stubank.data_access.PaymentAccountDAO;
import com.team46.stubank.pay_fragments.PayFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * DisplayPay class, screen that displays payment step fragments in viewpager
 *
 *
 * @author  George Cartridge
 * @version 1.0
 */
public class DisplayPay extends AppCompatActivity {

    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<PaymentAccount> paymentAccounts = new ArrayList<>();

    private PayFragmentPagerAdapter payFragmentPagerAdapter;
    private User user;
    private Card selectedCard;
    private PaymentAccount paymentAccount;
    private float paymentAmount = 0;

    private ProgressBar loading;

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

        try {
            FloatingActionButton backButton = findViewById(R.id.backButton);
            FloatingActionButton forwardButton = findViewById(R.id.forwardButton);

            loading = findViewById(R.id.progressBar2);

            Intent intent = getIntent();
            user = (User) intent.getSerializableExtra("newUser");

            payFragmentPagerAdapter = new PayFragmentPagerAdapter(getSupportFragmentManager(),
                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, cards, paymentAccounts, user);

            // create new thread
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            ViewPager viewPager = findViewById(R.id.payViewPager);
            viewPager.setAdapter(null);

            loading.setVisibility(View.VISIBLE);

            // retrieve user's cards, payment accounts in background thread and pass to adapter
            Future future = executor.submit(new Runnable() {
                @Override
                public void run() {
                    CardDao cardDao = new CardDao();
                    cards.addAll(cardDao.getAllCards(user.getUserID()));

                    PaymentAccountDAO paymentAccountDAO = new PaymentAccountDAO();
                    paymentAccounts.addAll(paymentAccountDAO.getAllPaymentAccount(user.getUserDetailsID()));

                    selectedCard = cards.get(0);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setAdapter(payFragmentPagerAdapter);
                            payFragmentPagerAdapter.notifyDataSetChanged();
                            loading.setVisibility(View.GONE);
                        }
                    });

                }
            });

            forwardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // make payment on last screen
                    if (viewPager.getCurrentItem() == (payFragmentPagerAdapter.getCount() - 1)) {
                        try {
                            executor.submit(new Runnable() {
                                @Override
                                public void run() {
                                    boolean madePayment = selectedCard.makePayment(paymentAmount, user, paymentAccount);

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (madePayment == true) {
                                                Toast.makeText(viewPager.getContext(), "Your payment has been sent", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(viewPager.getContext(), "Your payment failed to send", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    executor.shutdown();
                                }
                            });

                            // wait for thread
                            while (!executor.isTerminated()) {
                                try {
                                    executor.awaitTermination(120, TimeUnit.SECONDS);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            // go back to view card screen after payment made
                            Intent intent = new Intent(viewPager.getContext(), ViewCard.class);
                            intent.putExtra("newUser", user);
                            intent.putExtra("card", selectedCard);
                            viewPager.getContext().startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // move forward when pressed unless payment account screen doesn't include any payment accounts
                        if (viewPager.getCurrentItem() == 1 && paymentAccount == null) {
                            forwardButton.setAlpha(0.5f);
                        } else {
                            forwardButton.setAlpha(1f);
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);

                            if (viewPager.getCurrentItem() == 1 && paymentAccount == null) {
                                forwardButton.setAlpha(0.5f);
                            }
                        }
                    }

                    // change icon of button to paperplane if on the review payment screen
                    if (viewPager.getCurrentItem() == (payFragmentPagerAdapter.getCount() - 1)) {
                        forwardButton.setImageResource(android.R.drawable.ic_menu_send);
                        forwardButton.setScaleX(1);
                    }
                }
            });

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // move backwards through fragments when button pressed
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                    forwardButton.setAlpha(1f);

                    if (viewPager.getCurrentItem() != (payFragmentPagerAdapter.getCount() - 1)) {
                        forwardButton.setImageResource(R.drawable.abc_vector_test);
                        forwardButton.setScaleX(-1);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}