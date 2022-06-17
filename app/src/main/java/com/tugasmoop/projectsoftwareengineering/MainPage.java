package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.ShowCategory.listData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity implements View.OnClickListener {

    LinearLayout profile, favorite, history, television, refrigerator, airConditioner, dispenser, washingMachine, fan, speaker, computer, laptop, handphone;
    TextView welcome;
    public static int categoryClick = 0;
    public static String currCategory;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(this);

        favorite = findViewById(R.id.favorite);
        favorite.setOnClickListener(this);

        television = findViewById(R.id.television);
        television.setOnClickListener(this);

        refrigerator = findViewById(R.id.refrigerator);
        refrigerator.setOnClickListener(this);

        airConditioner = findViewById(R.id.airConditioner);
        airConditioner.setOnClickListener(this);

        dispenser = findViewById(R.id.dispenser);
        dispenser.setOnClickListener(this);

        washingMachine = findViewById(R.id.washingMachine);
        washingMachine.setOnClickListener(this);

        fan = findViewById(R.id.fan);
        fan.setOnClickListener(this);

        speaker = findViewById(R.id.speaker);
        speaker.setOnClickListener(this);

        computer = findViewById(R.id.computer);
        computer.setOnClickListener(this);

        laptop = findViewById(R.id.laptop);
        laptop.setOnClickListener(this);

        handphone = findViewById(R.id.handphone);
        handphone.setOnClickListener(this);

        history = findViewById(R.id.history);
        history.setOnClickListener(this);

        welcome = findViewById(R.id.welcome);
        welcome.setText("Welcome, " + currentUsername + " ! ");
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.profile)){
            Intent profile = new Intent(this, ProfilePage.class);
            startActivity(profile);
        }
        else if(view == findViewById(R.id.television)){
            categoryClick = 1;
            currCategory = "television";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    Log.d("TEST", "TEST");//buat ngecek
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        Log.d("TEST94", data.child("email").getValue(String.class));
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            Log.d("TEST95", "success - " + data.child("username").getValue(String.class));
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                            Log.d("TEST999999", listData.get(0));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.refrigerator)){
            categoryClick = 2;
            currCategory = "refrigerator";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.airConditioner)){
            categoryClick = 3;
            currCategory = "air conditioner";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.dispenser)){
            categoryClick = 4;
            currCategory = "dispenser";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.washingMachine)){
            categoryClick = 5;
            currCategory = "washing machine";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.fan)){
            categoryClick = 6;
            currCategory = "fan";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.speaker)){
            categoryClick = 7;
            currCategory = "speaker";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.computer)){
            categoryClick = 8;
            currCategory = "computer";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.laptop)){
            categoryClick = 9;
            currCategory = "laptop";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.handphone)){
            categoryClick = 10;
            currCategory = "handphone";
            listData.clear();
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("servicer").child("category").getValue(String.class).equals(currCategory)) {//liat ada ga email di datanya
                            String temp = data.child("username").getValue(String.class);
                            listData.add(temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //ini error karena waktu ambil ke database butuh waktu sedang thread berjalan secara asynchronous
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showCategory = new Intent(this, ShowCategory.class);
            startActivity(showCategory);
        }
        else if(view == findViewById(R.id.favorite)){
            Intent favorite = new Intent(this, ShowFavorite.class);
            startActivity(favorite);
        }
        else if(view == findViewById(R.id.history)){
            Intent history = new Intent(this, ShowHistory.class);
            startActivity(history);
            Log.d("toHistory", "toHistory");
        }
    }
}