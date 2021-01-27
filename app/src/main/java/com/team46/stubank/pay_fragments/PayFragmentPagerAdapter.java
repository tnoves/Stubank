package com.team46.stubank.pay_fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.team46.stubank.Card;

import java.util.ArrayList;

public class PayFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 2;
    private final ArrayList<Card> mCards;

    public PayFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Card> cards) {
        super(fm, behavior);

        this.mCards = cards;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StepOneCards.newInstance(mCards.get(0), mCards);
            case 1:
                return StepFourAmount.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
