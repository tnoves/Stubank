package com.team46.stubank.pay_fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.team46.stubank.Card;
import com.team46.stubank.PaymentAccount;
import com.team46.stubank.User;

import java.util.ArrayList;

/**
 * PayFragmentPagerAdapter class, adapter for displaying payment step fragments in view pager
 *
 *
 * @author  George Cartridge
 * @version 1.0
 */
public class PayFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 4;
    private final ArrayList<Card> mCards;
    private final ArrayList<PaymentAccount> mPaymentAccounts;
    private final User mUser;


    public PayFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Card> cards, ArrayList<PaymentAccount> paymentAccounts, User user) {
        super(fm, behavior);

        this.mCards = cards;
        this.mPaymentAccounts = paymentAccounts;
        this.mUser = user;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        // retrieve correct fragment for each page of viewpager
        switch (position) {
            case 0:
                return StepOneCards.newInstance(mCards);
            case 1:
                return StepTwoPaymentAct.newInstance(mPaymentAccounts, mUser);
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
