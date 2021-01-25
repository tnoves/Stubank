package com.team46.stubank;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.team46.stubank.data_access.PaymentAccountDAO;


public class DisplayPaymentAccount extends AppCompatActivity {

    PaymentAccount paymentAccount = new PaymentAccount();
    TextView firstname, AccountNumber, SortCode;
    Button btn, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_paymentacount);

        btn = findViewById(R.id.btnSelect);
        btn2 = findViewById(R.id.btnSelect2);

        firstname = findViewById(R.id.textName);
        AccountNumber = findViewById(R.id.textAccountNo);
        SortCode = findViewById(R.id.textSortCode);

        ExampleRunnable runnable =  new ExampleRunnable();
        new Thread(runnable).start();

        firstname.setText(paymentAccount.getFirstName());
        AccountNumber.setText(paymentAccount.getAccountNumber());
        SortCode.setText(paymentAccount.getSortCode());


        btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                    }
                });

        btn2.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                    }
                });
    }

    class ExampleRunnable implements Runnable {

        @Override
        public void run() {
            PaymentAccountDAO paymentAccountDAO = new PaymentAccountDAO();
        }
    }

}
