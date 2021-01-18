package com.team46.stubank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.team46.stubank.card_activities.DisplayCards;

public class ViewMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewCard(View view){
        Intent intent = new Intent(this, DisplayCards.class);
        startActivity(intent);
    }
}
