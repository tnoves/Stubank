package com.team46.stubank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class displayCards extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);
        //simpleSwitch.setChecked(false);
    }

    public void viewCard(View view){
        Intent intent = new Intent(this, ViewCard.class);
        startActivity(intent);
    }


    @SuppressLint("SetTextI18n")
    public void toggleDetails(View view){
        Switch simpleSwitch = (Switch) findViewById(R.id.switch1);

        TextView tv = (TextView)findViewById(R.id.textView4);
        TextView tv1 = (TextView)findViewById(R.id.textView);
        TextView tv2 = (TextView)findViewById(R.id.textView5);
        TextView tv3 = (TextView)findViewById(R.id.textView6);

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