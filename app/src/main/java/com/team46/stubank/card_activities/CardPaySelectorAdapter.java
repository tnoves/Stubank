package com.team46.stubank.card_activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.team46.stubank.Card;
import com.team46.stubank.DisplayPay;
import com.team46.stubank.R;

import java.util.List;

public class CardPaySelectorAdapter extends ArrayAdapter {

    private List<Card> mCards;
    private Context mContext;

    public class ViewHolder {
        public TextView cardNumber;
        public TextView name;
        public TextView sortCode;
        public TextView accountNumber;
        public Switch simpleSwitch;
        public View layout;
    }

    public CardPaySelectorAdapter(@NonNull Context context, List<Card> mCards) {
        super(context, R.layout.card_element);
        this.mCards = mCards;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            ViewHolder mViewHolder = new ViewHolder();
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) mContext.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.card_element, parent, false);

                mViewHolder.cardNumber = (TextView) convertView.findViewById(R.id.cardNumber);
                mViewHolder.name = (TextView) convertView.findViewById(R.id.cardName);
                mViewHolder.sortCode = (TextView) convertView.findViewById(R.id.sortCode);
                mViewHolder.accountNumber = (TextView) convertView.findViewById(R.id.accountNumber);
                mViewHolder.simpleSwitch = (Switch) convertView.findViewById(R.id.switch1);

                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            mViewHolder.cardNumber.setText(mCards.get(position).getCardNumber());
            mViewHolder.sortCode.setText(mCards.get(position).getSortCode());
            mViewHolder.accountNumber.setText(mCards.get(position).getAccountNum());
            mViewHolder.simpleSwitch.setClickable(false);
            mViewHolder.simpleSwitch.setEnabled(false);
            mViewHolder.simpleSwitch.setVisibility(View.INVISIBLE);

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
        DisplayPay.selectedCard = mCards.get(position);
        return mCards.get(position);
    }

    @Override
    public int getCount() {
        return mCards.size();
    }
}
