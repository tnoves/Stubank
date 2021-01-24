package com.team46.stubank;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.team46.stubank.data_access.TransactionDAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TransactionHandler {
    private TransactionDAO transactionDAO = new TransactionDAO();

    @RequiresApi(api = Build.VERSION_CODES.N)
    // TODO: Integrate this into the TransactionDAO once working again.
    public List<Transaction> sortTransactions(String cardnumber) throws ParseException {
        List<Transaction> transactions = transactionDAO.getCardTransactions(cardnumber);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (Transaction transaction : transactions) {
            transaction.setSortDate(format.parse(transaction.getDateTransaction()));
        }
        transactions.sort(Comparator.comparing(Transaction::getSortDate).reversed());
        return transactions;
    }
}
