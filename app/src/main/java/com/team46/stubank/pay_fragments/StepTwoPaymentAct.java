package com.team46.stubank.pay_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.team46.stubank.DisplayPay;
import com.team46.stubank.PaymentAccount;
import com.team46.stubank.R;
import com.team46.stubank.User;
import com.team46.stubank.paymentAccount_activities.CreatePaymentAccount;
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

    private static User mUser;
    private List<PaymentAccount> paymentAccountList;

    public StepTwoPaymentAct(){
        
    }

    public static StepTwoPaymentAct newInstance (ArrayList<PaymentAccount> paymentAccounts, User user){
        StepTwoPaymentAct fragment = new StepTwoPaymentAct();
        Bundle args = new Bundle();
        args.putSerializable("paymentAccounts", paymentAccounts);
        mUser = user;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            paymentAccountList = (ArrayList<PaymentAccount>) getArguments().getSerializable("paymentAccounts");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        // Inflate the layout for fragment
        View view = inflater.inflate(R.layout.fragment_step_two_payact, container, false);
        try {
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

            Button addPaymentAct = view.findViewById(R.id.btnAddPaymentAct);
            addPaymentAct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreatePaymentAccount createPaymentAccountPopup = new CreatePaymentAccount();
                    createPaymentAccountPopup.showPopupWindow(view, mUser);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;

    }
}
