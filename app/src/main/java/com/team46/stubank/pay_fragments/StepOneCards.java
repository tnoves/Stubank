package com.team46.stubank.pay_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.team46.stubank.Card;
import com.team46.stubank.DisplayPay;
import com.team46.stubank.R;
import com.team46.stubank.card_activities.CardPaySelectorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepOneCards#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepOneCards extends Fragment {

    private Card selectedCard;
    private List<Card> cardList;

    public StepOneCards() {
        // Required empty public constructor
    }

    public static StepOneCards newInstance(ArrayList<Card> cards) {
        StepOneCards fragment = new StepOneCards();
        Bundle args = new Bundle();
        args.putSerializable("cards", cards);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedCard = (Card) getArguments().getSerializable("selectedCard");
            cardList = (ArrayList<Card>) getArguments().getSerializable("cards");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_one_cards, container, false);
        try {
            if (cardList.size() > 0) {
                selectedCard = cardList.get(0);
            }

            CardPaySelectorAdapter adapter = new CardPaySelectorAdapter(view.getContext(), cardList);
            Spinner mCardDropdown = view.findViewById(R.id.cardDropDown);
            mCardDropdown.setAdapter(adapter);
            mCardDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Card card = (Card) parent.getSelectedItem();
                    ((DisplayPay) getActivity()).setSelectedCard(card);
                    selectedCard = card;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    ((DisplayPay) getActivity()).setSelectedCard(selectedCard);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }
}