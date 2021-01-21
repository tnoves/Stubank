package com.team46.stubank.card_activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.team46.stubank.R;

import java.util.List;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    CardRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_element, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Switch simpleSwitch = holder.itemView.findViewById(R.id.switch1);

        TextView cardNumber = holder.itemView.findViewById(R.id.cardNumber);
        TextView name = holder.itemView.findViewById(R.id.cardName);
        TextView sortCode = holder.itemView.findViewById(R.id.sortCode);
        TextView accountNumber = holder.itemView.findViewById(R.id.accountNumber);

        if (simpleSwitch.isChecked()){
            cardNumber.setText("card.getCardNumber");
            name.setText("user.getName");
            sortCode.setText("card.getSortCode");
            accountNumber.setText("card.getAccountNumber");
            //needs to call getter methods from card and user classes
        }
        else{
            cardNumber.setText("0000-0000-0000-0000");
            name.setText("Joe Bloggs");
            sortCode.setText("11-11-11");
            accountNumber.setText("3411-4885-6324-7195");
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvAnimalName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
