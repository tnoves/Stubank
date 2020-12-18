package com.team46.stubank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewCard extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        //get the spinner from the xml.
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"Day", "Week", "Month", "Year"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();


        listAdapter = new com.team46.stubank.ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);


    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Transaction one");
        listDataHeader.add("Transaction two");
        listDataHeader.add("Transaction three");
        listDataHeader.add("Transaction four");

        // Adding child data
        List<String> transaction_one = new ArrayList<String>();
        transaction_one.add("-£25.00");
        transaction_one.add("-£15.00");
        transaction_one.add("-£44.00");
        transaction_one.add("-£11.50");
        transaction_one.add("+£5.00");
        transaction_one.add("+£30.00");
        transaction_one.add("-£2.00");

        List<String> transaction_two = new ArrayList<String>();
        transaction_two.add("-£20.00");
        transaction_two.add("+£11.00");
        transaction_two.add("-£150.00");
        transaction_two.add("+£77.00");
        transaction_two.add("+£12.75");
        transaction_two.add("+£4.00");

        List<String> transaction_three = new ArrayList<String>();
        transaction_three.add("-£12.00");
        transaction_three.add("-£52.00");
        transaction_three.add("-£19.00");
        transaction_three.add("-£14.50");
        transaction_three.add("+£16.20");

        List<String> transactionfour = new ArrayList<>();
        transactionfour.add("-£12.00");
        transactionfour.add("-£17.25");
        transactionfour.add("+£44.00");
        transactionfour.add("+£200.00");

        listDataChild.put(listDataHeader.get(0), transaction_one); // Header, Child data
        listDataChild.put(listDataHeader.get(1), transaction_two);
        listDataChild.put(listDataHeader.get(2), transaction_three);
        listDataChild.put(listDataHeader.get(3), transactionfour);
    }
}