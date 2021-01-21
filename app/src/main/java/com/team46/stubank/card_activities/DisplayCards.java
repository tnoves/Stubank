package com.team46.stubank.card_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.team46.stubank.R;

public class DisplayCards extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);
    }

    public void viewCard(View view){
        Intent intent = new Intent(this, ViewCard.class);
        startActivity(intent);
    }


    @SuppressLint("SetTextI18n")
    public void toggleDetails(View view){
        Switch simpleSwitch = findViewById(R.id.switch1);

        TextView tv = findViewById(R.id.cardNumber);
        TextView tv1 = findViewById(R.id.cardName);
        TextView tv2 = findViewById(R.id.sortCode);
        TextView tv3 = findViewById(R.id.accountNumber);

        if (simpleSwitch.isChecked()){
            tv.setText("card.getCardNumber");
            tv1.setText("user.getName");
            tv2.setText("card.getSortCode");
            tv3.setText("card.getAccountNumber");
            //needs to call getter methods from card and user classes
        }
        else{
            tv.setText("0000-0000-0000-0000");
            tv1.setText("Joe Bloggs");
            tv2.setText("11-11-11");
            tv3.setText("3411-4885-6324-7195");
        }
    }

}