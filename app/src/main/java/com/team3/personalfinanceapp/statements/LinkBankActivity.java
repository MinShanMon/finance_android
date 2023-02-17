package com.team3.personalfinanceapp.statements;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team3.personalfinanceapp.R;

import java.util.HashSet;
import java.util.Set;

public class LinkBankActivity extends AppCompatActivity {

    private String[] banks;
    private String userId;

    private AutoCompleteTextView bankList;
    private SharedPreferences bankPref;
    private String userBank;
    private TextView accountNoField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_bank);

        banks = getResources().getStringArray(R.array.bank_list);
        bankList = findViewById(R.id.bank_dropdown);
        bankList.setAdapter(new ArrayAdapter<>(this, R.layout.enquiry_item, banks));

        accountNoField = findViewById(R.id.bank_account_num);
        ImageView backArrow = findViewById(R.id.img_backArrow);
        backArrow.setOnClickListener( c -> finish());

        Button submitBtn = findViewById(R.id.submit);
        submitBtn.setOnClickListener( c -> {
            if (submitBank()) {
                finish();
            }
        });

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        userId = String.valueOf(pref.getInt("userid", 0));
        bankPref = getSharedPreferences("user_banklist", MODE_PRIVATE);
        userBank = bankPref.getString(userId, "");
        if (!userBank.isEmpty()) {
            String[] details = userBank.split(":");
            bankList.setText(banks[0]);
            accountNoField.setText(details[1]);
        }

    }

    private boolean submitBank() {

        SharedPreferences.Editor editor = bankPref.edit();

        String bankName = bankList.getText().toString();

        String accountNo = accountNoField.getText().toString();
        TextView accountError = findViewById(R.id.account_num_error);
        if (accountNo.isEmpty()) {
            accountError.setVisibility(View.VISIBLE);
            return false;
        } else {
            accountError.setVisibility(View.GONE);
        }
        String bankAccountNo = bankName + ":" + accountNo;
        editor.putString(userId, bankAccountNo);
        editor.commit();
        return true;
    }






}