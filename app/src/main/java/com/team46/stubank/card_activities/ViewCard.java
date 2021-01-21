package com.team46.stubank.card_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.team46.stubank.DisplayPay;
import com.team46.stubank.R;
import com.team46.stubank.budget_activities.DisplayBudget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewCard extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String[] items;
    Spinner spinner;
    String week,month,year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        //get the spinner from the xml.
        spinner = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        items = new String[]{"Week", "Month", "Year"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);

        // get the listview
        expListView = findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new com.team46.stubank.card_activities.ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);


        String selectedItem = spinner.getSelectedItem().toString();

        if(selectedItem.equals("Week")){
            getTransactionsPerInterval(week);
       }
       else if (selectedItem.equals("Month")){
           getTransactionsPerInterval(month);

      }
       else if (selectedItem.equals("Year")){
           getTransactionsPerInterval(year);
       }

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Week1");
        listDataHeader.add("Week2");

        // Adding child data
        List<String> day_one = new ArrayList<String>();
        day_one.add("+520.00");

        List<String> day_two = new ArrayList<String>();
        day_two.add("-£20.00");
        day_two.add("+£11.00");
        day_two.add("-£150.00");
        day_two.add("+£77.00");
        day_two.add("+£12.75");
        day_two.add("+£4.00");

        listDataChild.put(listDataHeader.get(0), day_one); // Header, Child data
        listDataChild.put(listDataHeader.get(1), day_two);

    }

    public void getTransactionsPerInterval(String interval){
        //transaction.getTransactions(interval)
    }

    public void viewBudget(View view){
        Intent intent = new Intent(this, DisplayBudget.class);
        startActivity(intent);
    }
    public void viewPay(View view){
        Intent intent = new Intent(this, DisplayPay.class);
        startActivity(intent);
    }
}