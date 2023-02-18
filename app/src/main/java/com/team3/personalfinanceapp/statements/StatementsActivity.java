package com.team3.personalfinanceapp.statements;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.card.MaterialCardView;
import com.team3.personalfinanceapp.R;
import com.team3.personalfinanceapp.model.BankStatementResponse;
import com.team3.personalfinanceapp.utils.APIClient;
import com.team3.personalfinanceapp.utils.BankAPIInterface;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatementsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statements);
        ImageView backBtn = findViewById(R.id.img_backArrow);
        backBtn.setOnClickListener(e -> finish());
        getAllStatementsAndDisplay();
    }

    private void getAllStatementsAndDisplay() {

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        SharedPreferences bankPref = getSharedPreferences("user_banklist", MODE_PRIVATE);
        String bankAccount =
                bankPref.getString(String.valueOf(pref.getInt("userid", 0)), "");

        if (bankAccount.isEmpty()) {
            return;
        }

        String[] bankDetail = bankAccount.split(":");

        if (bankDetail.length <= 1) {
            return;
        }

        BankAPIInterface bankAPIInterface = APIClient.getBankClient().create(BankAPIInterface.class);
        Call<BankStatementResponse> bankStatementResponseCall =
                bankAPIInterface.getStatementDetails(getString(R.string.ocbc_auth_header), bankDetail[1]);

        bankStatementResponseCall.enqueue(new Callback<BankStatementResponse>() {
            @Override
            public void onResponse(Call<BankStatementResponse> call, Response<BankStatementResponse> response) {
                BankStatementResponse bankStatementResponse = response.body();
                List<BankStatementResponse.ActivityDetails> activityDetailsList = bankStatementResponse.getResults().getActivityDetails();
                displayStatements(activityDetailsList);
            }

            @Override
            public void onFailure(Call<BankStatementResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void displayStatements(List<BankStatementResponse.ActivityDetails> activityDetailsList) {

        ListView listView = findViewById(R.id.bank_statement_listview);
        listView.setAdapter(new StatementListAdapter(this, activityDetailsList));
    }
}