package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ShowHistory extends AppCompatActivity implements View.OnClickListener {

    LinearLayout home, favorite, profile;
    RecyclerView historyList;
    LinearLayoutManager linearLayoutManager;
    AdapterDataHistory adapterDataHistory;

    public static List<Transaction>transactionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);

        transactionList.clear();
        int transactionCount = currentUser.getTransactionCount();
        Log.d("TEST123", String.valueOf(transactionCount));
        for(int i = 0; i < transactionCount; i++){
            Log.d("TEST111", "Masokk for loop");
            if(currentUser.getTransactionList().get(i).getStatus() != -99){
                Log.d("TEST123", "masuk for loop if");
                Transaction newTransaction = currentUser.getTransactionList().get(i);
                transactionList.add(newTransaction);
            }
        }

        historyList = findViewById(R.id.historyList);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        historyList.setLayoutManager(linearLayoutManager);

        adapterDataHistory = new AdapterDataHistory(this, transactionList);
        historyList.setAdapter(adapterDataHistory);

        adapterDataHistory.notifyDataSetChanged();

        home = findViewById(R.id.home);
        favorite = findViewById(R.id.favorite);
        profile = findViewById(R.id.profile);

        home.setOnClickListener(this);
        favorite.setOnClickListener(this);
        profile.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.profile)){
            Intent profile = new Intent(this, ProfilePage.class);
            startActivity(profile);
        }else if(view == findViewById(R.id.favorite)){
            Intent history = new Intent(this, ShowFavorite.class);
            startActivity(history);
        }else if(view == findViewById(R.id.home)){
            Intent home = new Intent(this, MainPage.class);
            startActivity(home);
        }
    }
}