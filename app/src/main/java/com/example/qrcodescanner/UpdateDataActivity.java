package com.example.qrcodescanner;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nameTextView = (TextView) findViewById(R.id.displayNameTextView);
        TextView balanceTextView = (TextView) findViewById(R.id.displayBalanceTextView);

        EditText fareEditView = (EditText) findViewById(R.id.editTextFare);
        fareEditView.setFilters(new InputFilter[]{new ComparisonMethods("1", "5000")});

        Button submitFareButton = (Button) findViewById(R.id.authenticatePaymentButton);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");

        final ArrayList<String> list = new ArrayList<>();

        // Creating a DAOPassenger object to access its methods.
        DAOPassenger daoPassenger = DAOPassenger.getInstance(userID);

        daoPassenger.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.exists()) {
                        list.add(snapshot.getValue().toString());
                    }
                }
                if (dataSnapshot.exists()) {
                    balanceTextView.setText("Balance : LKR " + list.get(0) + ".00");
                    nameTextView.setText("Customer Name : " + list.get(1));
                } else {
                    balanceTextView.setText("No Data Available");
                    nameTextView.setText("No Data Available");
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        submitFareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float currentBalance = Float.parseFloat(list.get(0));
                float currentFare = Float.parseFloat(fareEditView.getText().toString());

                ComparisonMethods comparisonMethods = new ComparisonMethods();
                float newBalance = comparisonMethods.getNewBalance(Float.parseFloat(list.get(0)), currentFare);

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", list.get(1).toString());
                hashMap.put("balance", String.valueOf(Math.round(newBalance)));

                if (Math.round(newBalance) >= 0.0) {
                    daoPassenger.getDatabaseReference().updateChildren(hashMap).addOnSuccessListener(suc ->
                    {
                        new SweetAlertDialog(UpdateDataActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Payment Successful")
                                .setContentText("You charged " + String.valueOf(currentFare) + "0 from " + list.get(1))
                                .setCancelText("Revert")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        hashMap.put("balance", String.valueOf(Math.round(currentBalance)));
                                        daoPassenger.getDatabaseReference().updateChildren(hashMap);
                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                        UpdateDataActivity.this.finish();
                                    }
                                })
                                .show();
                    }).addOnFailureListener(er ->
                    {
                        new SweetAlertDialog(UpdateDataActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Payment Failed")
                                .setContentText("Payment Processing Failed!")
                                .show();
                    });
                } else {
                    new SweetAlertDialog(UpdateDataActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Payment Failed")
                            .setContentText("Insufficient Funds!")
                            .show();
                }
            }
        });

    }
}