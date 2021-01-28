package com.team46.stubank.paymentAccount_activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team46.stubank.PaymentAccount;
import com.team46.stubank.R;

import java.util.List;

public class PayActRecycler extends RecyclerView.Adapter<PayActRecycler.ViewHolder> {

    private List<PaymentAccount> mPaymentAccounts;
    private LayoutInflater mInflater;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView accountNumber;
        public TextView sortCode;
        public Button btnSubmit;
        public View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            name = itemView.findViewById(R.id.txtName);
            sortCode = itemView.findViewById(R.id.txtSortCode);
            accountNumber = itemView.findViewById(R.id.txtAccountNo);
        }
    }

    public void add(int position, PaymentAccount item) {
        mPaymentAccounts.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPaymentAccounts.remove(position);
        notifyItemRemoved(position);
    }

    public void update(List<PaymentAccount> paymentAccounts) {
        mPaymentAccounts.clear();
        mPaymentAccounts.addAll(paymentAccounts);
    }

    PayActRecycler(List<PaymentAccount> data) {this.mPaymentAccounts = data;}

    // inflates the row layout from xml when needed
    @Override
    public PayActRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.paymentact_element, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Grab PaymentAccount at this position and replace contents of view with data
        PaymentAccount paymentAccount = mPaymentAccounts.get(position);

        TextView name = holder.name;
        TextView accountNumber = holder.accountNumber;
        TextView sortCode = holder.sortCode;

        name.setText(paymentAccount.getFirstName());
        accountNumber.setText(paymentAccount.getAccountNumber());
        sortCode.setText(paymentAccount.getSortCode());

    }

    @Override
    public int getItemCount() {
        return mPaymentAccounts.size();
    }

}
