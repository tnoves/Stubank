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
 * StepFourAmount class, fragment for fourth step in payment - setting payment amount
 *
 *
 * @author  George Cartridge
 * @version 1.0
 */
public class StepFourAmount extends Fragment {

    private String lastString = "";

    public StepFourAmount() {
        // required empty public constructor
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
        try {
            // inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_step_four_amount, container, false);

            EditText paymentAmount = view.findViewById(R.id.paymentAmount);
            TextView currencySymbol = view.findViewById(R.id.currencySymbol);

            String cardType = ((DisplayPay) getActivity()).getSelectedCard().getCardType();

            // set currency based on card's currency
            if (cardType.equals("GBP")) {
                currencySymbol.setText("£");
            } else if (cardType.equals("EUR")) {
                currencySymbol.setText("€");
            } else {
                currencySymbol.setText("$");
            }

            // when payment amount is entered, pass this data to container display class for use
            // in overview and making payment
            paymentAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length()>0 && !s.equals(lastString)) {
                        ((DisplayPay) getActivity()).setPaymentAmount(Float.valueOf(s.toString()));
                        lastString = s.toString();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        ((DisplayPay) getActivity()).setPaymentAmount(Float.valueOf(s.toString()));
                    }
                }
            });

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            // on viewing screen, update currency
            TextView currencySymbol = getView().findViewById(R.id.currencySymbol);
            String cardType = ((DisplayPay) getActivity()).getSelectedCard().getCardType();

            if (cardType.equals("GBP")) {
                currencySymbol.setText("£");
            } else if (cardType.equals("EUR")) {
                currencySymbol.setText("€");
            } else {
                currencySymbol.setText("$");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}