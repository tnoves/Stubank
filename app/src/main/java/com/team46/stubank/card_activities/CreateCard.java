package com.team46.stubank.card_activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.team46.stubank.R;

public class CreateCard extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        // Populate dropdown with pre-defined currencies
        Spinner dropDown = findViewById(R.id.selectCurrency);
        String[] currencies = new String[]{ "GBP", "EUR", "USD" };

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies);

        dropDown.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Populate dropdown with pre-defined currencies
        Spinner dropDown = findViewById(R.id.selectCurrency);
        String[] currencies = new String[]{ "GBP", "EUR", "USD" };

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies);

        dropDown.setAdapter(adapter);
    }

    public void showPopupWindow(View view) {
        LayoutInflater inflater = (LayoutInflater)
                view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_create_card, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = false;

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // Initialise dropdown with currencies
        Spinner dropDown = popupView.findViewById(R.id.selectCurrency);

        String[] currencies = new String[]{ "GBP", "EUR", "USD" };

        adapter = new ArrayAdapter<>(popupView.getContext(),
                android.R.layout.simple_spinner_dropdown_item, currencies);

        dropDown.setAdapter(adapter);

        // Create new card
        Button submitButton = popupView.findViewById(R.id.createCardSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Create new card --> https://medium.com/@evanbishop/popupwindow-in-android-tutorial-6e5a18f49cc7
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}