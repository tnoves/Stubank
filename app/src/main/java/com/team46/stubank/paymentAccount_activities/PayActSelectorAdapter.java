package com.team46.stubank.paymentAccount_activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.team46.stubank.DisplayPay;
import com.team46.stubank.PaymentAccount;
import com.team46.stubank.R;

import java.util.List;

/**
 * PaymentAccount Selector adapter, iterates through PaymentAccounts to display them to GUI
 *
 *
 * @author  Douglas Gray
 * @version 1.0
 * @since   2021-01-28
 */

public class PayActSelectorAdapter extends ArrayAdapter {

    private List<PaymentAccount> mPaymentAccounts;
    private Context mContext;

    public class ViewHolder {
        public TextView name;
        public TextView accountNumber;
        public TextView sortCode;
        public View layout;
    }

    public PayActSelectorAdapter (@NonNull Context context, List<PaymentAccount> mPaymentAccounts){
        super(context, R.layout.paymentact_element);
        this.mPaymentAccounts = mPaymentAccounts;
        this.mContext = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        try{
            ViewHolder mViewHolder = new ViewHolder();
            if(convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.paymentact_element, parent, false);

                mViewHolder.name = (TextView) convertView.findViewById(R.id.txtName);
                mViewHolder.sortCode = (TextView) convertView.findViewById(R.id.txtSortCode);
                mViewHolder.accountNumber = (TextView) convertView.findViewById(R.id.txtAccountNo);

                convertView.setTag(mViewHolder);
            }
            else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            mViewHolder.name.setText(mPaymentAccounts.get(position).getFirstName());
            mViewHolder.sortCode.setText(mPaymentAccounts.get(position).getSortCode());
            mViewHolder.accountNumber.setText(mPaymentAccounts.get(position).getAccountNumber());

            return convertView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        try {
            ((DisplayPay) mContext).setPaymentAccount(mPaymentAccounts.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mPaymentAccounts.get(position);
    }
    @Override
    public int getCount() {
        return mPaymentAccounts.size();
    }

}
