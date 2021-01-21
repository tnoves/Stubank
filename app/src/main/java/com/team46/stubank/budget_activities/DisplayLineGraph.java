package com.team46.stubank.budget_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.team46.stubank.R;
import com.team46.stubank.card_activities.ViewCard;

import java.util.ArrayList;

public class DisplayLineGraph extends AppCompatActivity {

    private LineChart mChart;
    private int budget = 0;
    private EditText budgetInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_line_graph);

        //createSpinner();

       /* mChart = findViewById(R.id.LineChart1);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        LimitLine upperLimit = new LimitLine(50, "BUDGET");
        upperLimit.setLineWidth(8f);
        upperLimit.enableDashedLine(10f,10f,10f);
        upperLimit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upperLimit.setTextSize(20f);
        upperLimit.setTextColor(Color.RED);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(upperLimit);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f,10f,0);
        leftAxis.setDrawLimitLinesBehindData(true);


        mChart.getAxisRight().setEnabled(false);
        // mChart.getAxisLeft().setEnabled(false);

        LineDataSet set1 = new LineDataSet(getEntries(),"Spending Per Month");

        set1.setFillAlpha(110);
        set1.setColor(Color.GRAY);
        set1.setLineWidth(6f);
        set1.setValueTextSize(20f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);

        String[] xValues = new String[] {"Jan", "Feb", "Mar", "Apr", "May",
                "Jun", "Jul", "Aug", "Sept", "Oct", "Nov"};

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new DisplayLineGraph.MyXAxisValueFormatter(xValues));
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(20f);
    }

    public class MyXAxisValueFormatter extends ValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values){
            this.mValues = values;
        }

    }

    public ArrayList getEntries(){
        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0, 60f));
        yValues.add(new Entry(1, 50f));
        yValues.add(new Entry(2, 70f));
        yValues.add(new Entry(3, 30f));
        yValues.add(new Entry(4, 80f));
        yValues.add(new Entry(5, 100f));

        return yValues;
    }

    public void createSpinner(){
        //get the spinner from the xml.
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        //create a list of items for the spinner.
        String[] items = new String[]{"Week", "Month", "Year"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);
    }

    public int setBudget(){
        budgetInput = (EditText)findViewById(R.id.budgetInput);
        String input = budgetInput.toString();
        budget = Integer.parseInt(input);
        return budget;
    }*/

    }
}