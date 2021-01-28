package com.team46.stubank.pay_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.team46.stubank.DisplayPay;
import com.team46.stubank.PaymentAccount;
import com.team46.stubank.R;
import com.team46.stubank.paymentAccount_activities.PayActSelectorAdapter;

import java.util.List;
import java.util.ArrayList;

/**
 * StepTwoPaymentAct, Displays payment accounts to GUI and -
 * passes on selected paymentAccount to next step
 *
 * @author  Douglas Gray
 * @version 1.0
 * @since   2021-01-28
 */


public class StepTwoPaymentAct extends Fragment {

    private PaymentAccount selectedPayment;
    private List<PaymentAccount> paymentAccountList;
    
    public StepTwoPaymentAct(){
        
    }

    public static StepTwoPaymentAct newInstance (PaymentAccount paymentAccount, ArrayList<PaymentAccount> paymentAccounts){
        StepTwoPaymentAct fragment = new StepTwoPaymentAct();
        Bundle args = new Bundle();
        args.putSerializable("paymentAccount", paymentAccount);
        args.putSerializable("paymentAccounts", paymentAccounts);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            selectedPayment = (PaymentAccount) getArguments().getSerializable("paymentAccount");
            paymentAccountList = (ArrayList<PaymentAccount>) getArguments().getSerializable("paymentAccounts");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        // Inflate the layout for fragment
        View view = inflater.inflate(R.layout.fragment_step_two_payact, container, false);
        PayActSelectorAdapter adapter = new PayActSelectorAdapter(view.getContext(), paymentAccountList);
        Spinner mPayActDropdown = view.findViewById(R.id.payActDropDown);
        mPayActDropdown.setAdapter(adapter);
        mPayActDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PaymentAccount paymentAccount = (PaymentAccount) parent.getSelectedItem();
                ((DisplayPay) getActivity()).setPaymentAccount(paymentAccount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }
}
