package com.team46.stubank.pay_fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

        EditText paymentAmount = view.findViewById(R.id.paymentAmount);
        TextView currencySymbol = view.findViewById(R.id.currencySymbol);

        String cardType = ((DisplayPay) getActivity()).getSelectedCard().getCardType();

        if (cardType.equals("GBP")) {
            currencySymbol.setText("£");
        } else if (cardType.equals("EUR")) {
            currencySymbol.setText("€");
        } else {
            currencySymbol.setText("$");
        }

        paymentAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((DisplayPay) getActivity()).setPaymentAmount(Float.valueOf(s.toString()));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView currencySymbol = getView().findViewById(R.id.currencySymbol);
        String cardType = ((DisplayPay) getActivity()).getSelectedCard().getCardType();

        if (cardType.equals("GBP")) {
            currencySymbol.setText("£");
        } else if (cardType.equals("EUR")) {
            currencySymbol.setText("€");
        } else {
            currencySymbol.setText("$");
        }
    }
}