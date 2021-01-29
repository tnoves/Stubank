package com.team46.stubank.card_activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.team46.stubank.Card;
import com.team46.stubank.R;
import com.team46.stubank.Transaction;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * ViewTransaction class to handle the view_transaction activity screen.
 *
 *
 * @author  Ben McIntyre
 * @version 1.0
 * @since   2021-01-25
 */

public class ViewTransaction extends AppCompatActivity {
    private Transaction transaction;
    private Locale locale;
    private Card card;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);


        // Fetches card and transaction object from the intent.
        Intent intent = getIntent();
        card = (Card) intent.getSerializableExtra("card");
        transaction = (Transaction) intent.getSerializableExtra("transaction");

        // Fetches elements on activity which will be updated.
        ImageView transactionImage = findViewById(R.id.transactionImage);
        TextView transactionDirection = findViewById(R.id.transactionDirection);
        TextView transactionName = findViewById(R.id.transactionName);
        TextView transactionAmount = findViewById(R.id.transactionAmount);
        TextView transactionType = findViewById(R.id.transactionType);

        // Uses the currency on the card and sets a formatter to a corresponding location.
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

        // Sets elements on the transaction screen to be red if the amount is negative.
        if (transaction.getAmount() < 0) {
            transactionImage.setImageTintList(ColorStateList.valueOf(Color.parseColor("#db5353")));
            transactionDirection.setText("OUT");
            transactionDirection.setTextColor(Color.parseColor("#db5353"));
            transactionAmount.setTextColor(Color.parseColor("#db5353"));
        }
        // Sets remaining elements on the transaction screen to have the details from the transaction.
        transactionAmount.setText(numberFormat.format(transaction.getAmount()));
        transactionName.setText(transaction.getPaymentAccountID());
        transactionType.setText(transaction.getPaymentType());


    }
}