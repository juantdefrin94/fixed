package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ShowOrder extends AppCompatActivity {

    RecyclerView orderView;
    LinearLayoutManager linearLayoutManager;
    AdapterDataOrder adapterDataOrder;

    public static List<Transaction> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        orderList.clear();
        int transactionCount = currentUser.getServicer().getTransactionCount();

        Log.d("TEST", String.valueOf(transactionCount));
        for(int i = 0; i < transactionCount; i++){
            Log.d("TEST111", "Masokk for loop");
            if(currentUser.getServicer().getTransactionList().get(i).getStatus() != -99){
                Log.d("TEST123", "masuk for loop if");
                Transaction newTransaction = currentUser.getServicer().getTransactionList().get(i);
                orderList.add(newTransaction);
            }
        }

        orderView = findViewById(R.id.orderList);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderView.setLayoutManager(linearLayoutManager);

        adapterDataOrder = new AdapterDataOrder(this, orderList);
        orderView.setAdapter(adapterDataOrder);

        adapterDataOrder.notifyDataSetChanged();

    }

    public void toServicerProfile(View view) {
        Intent toServicerProfile = new Intent(this, ServicerProfilePage.class);
        startActivity(toServicerProfile);
    }
}