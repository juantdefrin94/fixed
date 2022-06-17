package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.AdapterDataHistory.currentHistoryServicer;
import static com.tugasmoop.projectsoftwareengineering.AdapterDataHistory.idOrderCustomer;
import static com.tugasmoop.projectsoftwareengineering.AdapterDataHistory.idOrderServicer;
import static com.tugasmoop.projectsoftwareengineering.AdapterDataHistory.payed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransactionHistory extends AppCompatActivity implements View.OnClickListener {

    Button bBackHistory, bDonePayment;
    TextView servicerFee, totalCost;
    LinearLayout pay;
    int fee;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        servicerFee = findViewById(R.id.servicerFee);
        totalCost = findViewById(R.id.totalCost);



        databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("price").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                servicerFee.setText("Rp" + String.valueOf(snapshot.getValue(Integer.class)));
                fee = snapshot.getValue(Integer.class);
                totalCost.setText("TOTAL : Rp" + (fee + 20000));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bBackHistory = findViewById(R.id.bBackHistory);
        bDonePayment = findViewById(R.id.bDonePayment);

        pay = findViewById(R.id.pay);
        if(payed){
            pay.setVisibility(View.GONE);
            bDonePayment.setVisibility(View.GONE);
        }else{
            pay.setVisibility(View.VISIBLE);
        }

        bBackHistory.setOnClickListener(this);
        bDonePayment.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.bDonePayment)){
//            databaseReference
//            ubah status di database
            Toast.makeText(this, "Your transaction will be checked, please wait.", Toast.LENGTH_SHORT).show();
            databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("orders").child(String.valueOf(idOrderServicer)).child("status").setValue(-1);
            databaseReference.child("Orders").child(MainActivity.currentUsername).child(String.valueOf(idOrderCustomer)).child("status").setValue(-1);
            MainActivity.currentUser.getTransactionList().get(idOrderCustomer).setStatus(-1);
        }
        finish();
    }
}