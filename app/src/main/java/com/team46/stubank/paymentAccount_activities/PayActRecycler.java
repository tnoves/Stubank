package com.team46.stubank.paymentAccount_activities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team46.stubank.R;

public class PayActRecycler extends RecyclerView.Adapter<PayActRecycler.ViewHolder> {

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
