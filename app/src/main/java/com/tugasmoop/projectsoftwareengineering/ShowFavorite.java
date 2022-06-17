package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.ShowCategory.listData;
import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUser;
import static com.tugasmoop.projectsoftwareengineering.MainActivity.mode;

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

public class ShowFavorite extends AppCompatActivity implements View.OnClickListener {

    RecyclerView servicerList;
    LinearLayoutManager linearLayoutManager;
    AdapterData adapterData;

    LinearLayout home, history, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorite);

        mode = 2;
        listData.clear();

        for(int i = 0; i < currentUser.getFavCount(); i++){
            if(!currentUser.getFavServicer().get(i).equals("null")){
                listData.add(currentUser.getFavServicer().get(i));//udah masuk semua datanya
            }
        }

        servicerList = findViewById(R.id.servicerList);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        servicerList.setLayoutManager(linearLayoutManager);

        adapterData = new AdapterData(this, listData);
        servicerList.setAdapter(adapterData);

        adapterData.notifyDataSetChanged();

        home = findViewById(R.id.home);
        history = findViewById(R.id.history);
        profile = findViewById(R.id.profile);
        home.setOnClickListener(this);
        history.setOnClickListener(this);
        profile.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.profile)){
            Intent profile = new Intent(this, ProfilePage.class);
            startActivity(profile);
        }else if(view == findViewById(R.id.history)){
            Intent history = new Intent(this, ShowHistory.class);
            startActivity(history);
        }else if(view == findViewById(R.id.home)){
            Intent home = new Intent(this, MainPage.class);
            startActivity(home);
        }
    }
}