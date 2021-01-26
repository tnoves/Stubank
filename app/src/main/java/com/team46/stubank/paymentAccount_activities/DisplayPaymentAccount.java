package com.team46.stubank.paymentAccount_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.team46.stubank.PaymentAccount;
import com.team46.stubank.R;
import com.team46.stubank.User;
import com.team46.stubank.data_access.PaymentAccountDAO;
import com.team46.stubank.data_access.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DisplayPaymentAccount extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PayActRecycler payActAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private User user;
    private static List<PaymentAccount> paymentAccounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_paymentacount);
        paymentAccounts.clear();

        // Get logged in user from previous activity
        // Intent intent = getIntent();
        // user = (User) intent.getSerializableExtra("user");

        // Create new thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        payActAdapter = new PayActRecycler(paymentAccounts);

        // Retrieve user and user's PaymentAccount in background thread
        executor.submit(new Runnable() {
            @Override
            public void run() {

                UserDAO userDAO = new UserDAO();
                user = userDAO.getUser(1497);

                PaymentAccountDAO paymentAccountDAO = new PaymentAccountDAO();
                paymentAccounts.addAll(paymentAccountDAO.getAllPaymentAccount(user.getUserDetailsID()));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        payActAdapter.notifyDataSetChanged();
                    }
                });
                executor.shutdown();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.paymentAccount_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Pass PaymentAccount adapter to the recycler view
        recyclerView.setAdapter(payActAdapter);

    }
}
