package com.team46.stubank.card_activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team46.stubank.Transaction;
import com.team46.stubank.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder>{

    private List<Transaction> mTransactions;
    private LayoutInflater mInflater;

    String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView transactionElementAccount;
        public TextView transactionElementAmount;
        public TextView transactionElementDate;
        public View layout;

        ViewHolder(View itemView) {
            super(itemView);
            transactionElementAccount = itemView.findViewById(R.id.transactionElementAccount);
            transactionElementAmount = itemView.findViewById(R.id.transactionElementAmount);
            transactionElementDate = itemView.findViewById(R.id.transactionElementDate);
        }
    }

    public void add(int position, Transaction transaction) {
        mTransactions.add(position, transaction);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mTransactions.remove(position);
        notifyItemRemoved(position);
    }

    public void update(List<Transaction> transactions) {
        mTransactions.clear();
        mTransactions.addAll(transactions);
    }

    TransactionRecyclerViewAdapter(List<Transaction> data) {
        this.mTransactions = data;
    }

    @Override
    public TransactionRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.transaction_element, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = mTransactions.get(position);

        TextView transactionElementAccount = holder.transactionElementAccount;
        TextView transactionElementAmount = holder.transactionElementAmount;
        TextView transactionElementDate = holder.transactionElementDate;

        // TODO: Fetch the users name that matches the payment account.
        transactionElementAccount.setText(transaction.getPaymentAccountID());
        transactionElementAmount.setText("£"+(String.valueOf(transaction.getAmount())));
        transactionElementDate.setText(transaction.getSortDate().getDate()+"-"+months[transaction.getSortDate().getMonth()]+"-"+(transaction.getSortDate().getYear()+1900));


    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(holder.itemView.getContext(), ViewTransaction.class);
            intent.putExtra("transaction", transaction);
            holder.itemView.getContext().startActivity(intent);
        }
    });
    };

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }
}
