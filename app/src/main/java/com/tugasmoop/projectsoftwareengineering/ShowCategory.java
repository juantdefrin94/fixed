package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.MainActivity.mode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ShowCategory extends AppCompatActivity implements View.OnClickListener {

    String nameTxt;
    double ratingTxt;
    String currUsernameServicer;
    boolean checkFav = false;
    ImageView backHome;

    RecyclerView servicerList;
    LinearLayoutManager linearLayoutManager;
    AdapterData adapterData;
    public static List<String> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_category);

        mode = 1;

        servicerList = findViewById(R.id.servicerList);

        Log.d("TEST111", "masuk");

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        servicerList.setLayoutManager(linearLayoutManager);

        Log.d("TEST112", "masuk");

        adapterData = new AdapterData(this, listData);
        servicerList.setAdapter(adapterData);

        adapterData.notifyDataSetChanged();

        Log.d("TEST113", "masuk");

        backHome = findViewById(R.id.backHome);
        backHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.backHome)){
            Intent home = new Intent(this, MainPage.class);
            startActivity(home);
        }
    }
}