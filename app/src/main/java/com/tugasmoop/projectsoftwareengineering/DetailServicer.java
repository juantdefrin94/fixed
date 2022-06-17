package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.AdapterData.currentDetailServicer;
import static com.tugasmoop.projectsoftwareengineering.AdapterDataHistory.currentHistoryServicer;
import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUser;
import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUsername;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DetailServicer extends AppCompatActivity implements View.OnClickListener {

    TextView nameDetail, categoryDetail, descriptionDetail, priceDetail, ratingDetail;
    Button bFavorite, bBook;
    String nameTxt, categoryTxt, descriptionTxt;
    ImageView backDetail;
    int priceTxt;
    double ratingTxt;
    int favCount = currentUser.getFavCount();
    boolean favorite = false, clickFav = false;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_servicer);

        currentHistoryServicer = currentDetailServicer;

        databaseReference.child("Users").child(currentDetailServicer).child("servicer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameTxt = snapshot.child("name").getValue(String.class);
                categoryTxt = snapshot.child("category").getValue(String.class);
                descriptionTxt = snapshot.child("description").getValue(String.class);
                priceTxt = snapshot.child("price").getValue(Integer.class);
                if(snapshot.child("custCount").getValue(Integer.class) != 0){
                    ratingTxt = (double) snapshot.child("rating").getValue(Integer.class) / (double) snapshot.child("custCount").getValue(Integer.class);
                }else{
                    ratingTxt = 0.0;
                }

                nameDetail = findViewById(R.id.nameDetail);
                nameDetail.setText(nameTxt);

                categoryDetail = findViewById(R.id.categoryDetail);
                categoryDetail.setText(categoryTxt);

                descriptionDetail = findViewById(R.id.descriptionDetail);
                descriptionDetail.setText(descriptionTxt);

                priceDetail = findViewById(R.id.priceDetail);
                priceDetail.setText(String.valueOf(priceTxt));

                ratingDetail = findViewById(R.id.ratingDetail);
                ratingDetail.setText(String.valueOf(ratingTxt));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bFavorite = findViewById(R.id.bFavorite);
        bBook = findViewById(R.id.bBook);
        backDetail = findViewById(R.id.backDetail);

        for(int i = 0; i < favCount; i++){
            if(currentUser.getFavServicer().get(i).equals(currentDetailServicer)){
                favorite = true;
                break;
            }
        }
        if(favorite == true){
            bFavorite.setText("Delete from favorite");
        }

        bFavorite.setOnClickListener(this);
        bBook.setOnClickListener(this);
        backDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.bFavorite)){
            if(favorite == false) {
                if(clickFav == false){
                    //tinggal update ke array
                    currentUser.getFavServicer().add(currentDetailServicer);
                    favCount++;
                    currentUser.setFavCount(favCount);

                    HashMap newUser = new HashMap();
                    newUser.put("favCount", favCount);

                    Log.d("Tess", String.valueOf(favCount));

                    HashMap newFavorite = new HashMap();
                    newFavorite.put(String.valueOf(favCount - 1), currentDetailServicer);

                    Log.d("Tess", String.valueOf(favCount - 1) + "servicer : " + currentDetailServicer);

                    HashMap newFavorite2 = new HashMap();
                    newFavorite2.put(String.valueOf(favCount), "null");

                    Log.d("Tess", String.valueOf(favCount) + "into null");

                    databaseReference.child("Users").child(currentUsername).updateChildren(newUser);
                    databaseReference.child("Users").child(currentUsername).child("favServicer").child(String.valueOf(favCount - 1)).setValue(currentDetailServicer);
                    databaseReference.child("Users").child(currentUsername).child("favServicer").child(String.valueOf(favCount)).setValue("null");
                }
                else{
                    Toast.makeText(this, "Already added to favorite", Toast.LENGTH_SHORT).show();
                }
                clickFav = true;
            }
            else{
                if(clickFav == false){

                    databaseReference.child("Users").child(currentUsername).child("favServicer").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(int i = 0; i < favCount; i++){
                                Log.d("Tesssss", snapshot.child(String.valueOf(i)).getValue(String.class) + " vs " + currentDetailServicer);
                                Log.d("Tesssss", "Value i : " + String.valueOf(i));
                                if(snapshot.child(String.valueOf(i)).getValue(String.class).equals(currentDetailServicer)){
                                    databaseReference.child("Users").child(currentUsername).child("favServicer").child(String.valueOf(i)).setValue("null");
                                }
                                //jangan di hapus, tapi di set favorite yang sebelumnya jadi null
                                if(currentUser.getFavServicer().get(i).equals(currentDetailServicer)){
                                    currentUser.getFavServicer().set(i, "null");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(this, "Already deleted from favorite", Toast.LENGTH_SHORT).show();
                }
                clickFav = true;
            }
        }
        else if(view == findViewById(R.id.bBook)){
            Intent schedule = new Intent(this, SchedulePage.class);
            startActivity(schedule);
        }
        else if(view == findViewById(R.id.backDetail)){
            Intent home = new Intent(this, ShowCategory.class);
            startActivity(home);
        }
    }
}