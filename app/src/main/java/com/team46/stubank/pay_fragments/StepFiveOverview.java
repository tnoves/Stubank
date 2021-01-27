package com.team46.stubank.pay_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.team46.stubank.Card;
import com.team46.stubank.DisplayPay;
import com.team46.stubank.PaymentAccount;
import com.team46.stubank.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepFiveOverview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFiveOverview extends Fragment {

    public StepFiveOverview() {
        // Required empty public constructor
    }

    public static StepFiveOverview newInstance() {
        StepFiveOverview fragment = new StepFiveOverview();
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
        View view = inflater.inflate(R.layout.fragment_step_five_overview, container, false);

        TextView fromCardNumber = view.findViewById(R.id.fromCardNumberText);
        TextView fromCurrency = view.findViewById(R.id.reviewFromCurrency);
        TextView toName = view.findViewById(R.id.toPaymentAccountNameText);
        TextView toAccountNum = view.findViewById(R.id.toPaymentAccountNumberText);
        TextView paymentAmount = view.findViewById(R.id.finalPaymentAmountText);

        Card card = ((DisplayPay) getActivity()).getSelectedCard();
        PaymentAccount paymentAccount = ((DisplayPay) getActivity()).getPaymentAccount();

        fromCardNumber.setText(card.getCardNumber());
        fromCurrency.setText(card.getCardType());
        toName.setText(paymentAccount.getFirstName());
        toAccountNum.setText(paymentAccount.getAccountNumber());
        paymentAmount.setText( String.valueOf(((DisplayPay) getActivity()).getPaymentAmount()) );

        return view;
    }
}