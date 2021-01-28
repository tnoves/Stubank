package com.team46.stubank.card_activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team46.stubank.Card;
import com.team46.stubank.PaymentAccount;
import com.team46.stubank.Transaction;
import com.team46.stubank.R;
import com.team46.stubank.User;
import com.team46.stubank.data_access.PaymentAccountDAO;
import com.team46.stubank.data_access.UserDAO;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder>{

    private List<Transaction> mTransactions;
    private LayoutInflater mInflater;
    private Card mCard;
    private int paymentAccountID;
    private User externalUser;
    private PaymentAccount paymentAccount;
    private String accountName;

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

    TransactionRecyclerViewAdapter(List<Transaction> data, Card card) {
        this.mTransactions = data;
        this.mCard = card;
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

        UserDAO userDAO = new UserDAO();
        PaymentAccountDAO paymentAccountDAO = new PaymentAccountDAO();

        paymentAccountID = transaction.getPaymentAccountID();
        paymentAccount = paymentAccountDAO.getPaymentAccount(paymentAccountID);
        externalUser = userDAO.getUserDetails(paymentAccount.getUserDetailsID());
        accountName = externalUser.getFirstName()+" "+externalUser.getLastName();

        // TODO: Fetch the users name that matches the payment account.
        transactionElementAccount.setText(accountName);
        transactionElementAmount.setText(getNumberFormat(mCard.getCardType()).format(transaction.getAmount()));
        transactionElementDate.setText(transaction.getSortDate().getDate()+"-"+months[transaction.getSortDate().getMonth()]+"-"+(transaction.getSortDate().getYear()+1900));

        // When a transaction is selected, open new activity window after including specific transaction in the intent.
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

    public NumberFormat getNumberFormat(String currency){
        Locale locale;
        switch (mCard.getCardType()) {
            case ("GBP"):
                locale = Locale.UK;
                break;
            case ("EUR"):
                locale = Locale.GERMANY;
                break;
            case ("USD"):
                locale = Locale.US;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mCard.getCardType());
        }
        return NumberFormat.getCurrencyInstance(locale);
    }
}
