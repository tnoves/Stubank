package com.team46.stubank.pay_fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.team46.stubank.Card;
import com.team46.stubank.PaymentAccount;

import java.util.ArrayList;

public class PayFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 4;
    private final ArrayList<Card> mCards;
    private final ArrayList<PaymentAccount> mPaymentAccounts;


    public PayFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Card> cards, ArrayList<PaymentAccount> paymentAccounts) {
        super(fm, behavior);

        this.mCards = cards;
        this.mPaymentAccounts = paymentAccounts; 
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StepOneCards.newInstance(mCards.get(0), mCards);
            case 1:
                return StepTwoPaymentAct.newInstance(mPaymentAccounts);
            case 2:
                return StepFourAmount.newInstance();
            case 3:
                return StepFiveOverview.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
