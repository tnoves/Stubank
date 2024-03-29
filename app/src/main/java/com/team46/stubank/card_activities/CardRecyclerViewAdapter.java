package com.team46.stubank.card_activities;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.team46.stubank.Card;
import com.team46.stubank.R;
import com.team46.stubank.User;

import java.util.List;

/**
 * CardRecyclerViewAdapter class, adapter for displaying cards to view
 *
 *
 * @author  George Cartridge
 * @version 1.0
 */
public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder> {

    private List<Card> mCards;
    private User mUser;
    private LayoutInflater mInflater;

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardNumber;
        public TextView name;
        public TextView sortCode;
        public TextView accountNumber;
        public Switch simpleSwitch;
        public View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            cardNumber = itemView.findViewById(R.id.cardNumber);
            name = itemView.findViewById(R.id.cardName);
            sortCode = itemView.findViewById(R.id.sortCode);
            accountNumber = itemView.findViewById(R.id.accountNumber);
            simpleSwitch = itemView.findViewById(R.id.switch1);
        }
    }

    public void add(int position, Card item) {
        mCards.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mCards.remove(position);
        notifyItemRemoved(position);
    }

    public void update(List<Card> cards) {
        mCards.clear();
        mCards.addAll(cards);
    }

    CardRecyclerViewAdapter(List<Card> data, User user) {
        this.mCards = data;
        this.mUser = user;
    }

    // inflates the card layout from xml when needed
    @Override
    public CardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.card_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // grab card at this position and replace contents of view with data
        Card card = mCards.get(position);

        Switch simpleSwitch = holder.simpleSwitch;

        TextView cardNumber = holder.cardNumber;
        TextView name = holder.name;
        TextView sortCode = holder.sortCode;
        TextView accountNumber = holder.accountNumber;

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardNumber.setText(card.getCardNumber());
                    sortCode.setText(card.getSortCode());
                    accountNumber.setText(card.getAccountNum());
                }
                else{
                    cardNumber.setText("0000-0000-0000-0000");
                    name.setText("John Doe");
                    sortCode.setText("11-11-11");
                    accountNumber.setText("03725748");
                }
            }
        });

        // pass selected card to view card screen
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ViewCard.class);
                intent.putExtra("card", card);
                intent.putExtra("newUser", mUser);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        // toggle displaying hidden details and sensitive card details
        if (simpleSwitch.isChecked()){
            cardNumber.setText(card.getCardNumber());
            sortCode.setText(card.getSortCode());
            accountNumber.setText(card.getAccountNum());
        }
        else{
            cardNumber.setText("0000-0000-0000-0000");
            name.setText("John Doe");
            sortCode.setText("11-11-11");
            accountNumber.setText("03725748");
        }
    }

    // total number of cards
    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
