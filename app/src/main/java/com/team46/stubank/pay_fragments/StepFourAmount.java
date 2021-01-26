package com.team46.stubank.pay_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.team46.stubank.DisplayPay;
import com.team46.stubank.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepFourAmount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFourAmount extends Fragment {

    public StepFourAmount() {
        // Required empty public constructor
    }

    public static StepFourAmount newInstance() {
        StepFourAmount fragment = new StepFourAmount();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_four_amount, container, false);

        TextView currencySymbol = view.findViewById(R.id.currencySymbol);

        if (DisplayPay.selectedCard.getCardType().equals("GBP")) {
            currencySymbol.setText("£");
        } else if (DisplayPay.selectedCard.getCardType().equals("EUR")) {
            currencySymbol.setText("€");
        } else {
            currencySymbol.setText("$");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView currencySymbol = getView().findViewById(R.id.currencySymbol);

        if (DisplayPay.selectedCard.getCardType().equals("GBP")) {
            currencySymbol.setText("£");
        } else if (DisplayPay.selectedCard.getCardType().equals("EUR")) {
            currencySymbol.setText("€");
        } else {
            currencySymbol.setText("$");
        }
    }
}