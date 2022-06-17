package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.MainActivity.mode;
import static com.tugasmoop.projectsoftwareengineering.MainPage.currCategory;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {

    List<String> listDataAdapter;
    LayoutInflater inflater; //Masukin tampilan, nge inflate
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    String nameTxt;
    double rating;
    boolean checkFavorite = false;
    public static String currentDetailServicer;
    Context context;

    public AdapterData(Context context, List<String> listData) {
        this.context = context;
        this.listDataAdapter = listData;
        if(listData.size() == 0) return;
        this.inflater = LayoutInflater.from(context);
        Log.d("TEST114", "masuk");
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //menempatkan data ke tampilan (inflate)
        View view = inflater.inflate(R.layout.template_category_servicer, parent, false);//parent --> context
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        //currentServicer
        String currentServicer = listDataAdapter.get(position);
        checkFavorite = false;
        //set hasil
        databaseReference.child("Users").child(currentServicer).child("servicer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameTxt = snapshot.child("name").getValue(String.class);
                if(snapshot.child("custCount").getValue(Integer.class) != 0){
                    rating = (double) snapshot.child("rating").getValue(Integer.class) / (double) snapshot.child("custCount").getValue(Integer.class);
                }else{
                    rating = 0.0;
                }
                String category;

                if(mode == 1) category = currCategory;
                else category = snapshot.child("category").getValue(String.class);

                holder.servicerName.setText(nameTxt);
                holder.rating.setText("Rating : " + rating + "/5");
                holder.servicerCategory.setText(category);
                holder.detailServicer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentDetailServicer = currentServicer;
                        Intent detail = new Intent(context, DetailServicer.class);
                        context.startActivity(detail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{

        TextView servicerName;
        TextView servicerCategory;
        TextView rating;
        ImageView servicerProfile;
        RelativeLayout detailServicer;

        public HolderData(@NonNull View itemView) {
            super(itemView);//parent
            servicerName = itemView.findViewById(R.id.servicerName);
            servicerCategory = itemView.findViewById(R.id.servicerCategory);
            rating = itemView.findViewById(R.id.rating);
            servicerProfile = itemView.findViewById(R.id.servicerProfile);
            detailServicer = itemView.findViewById(R.id.detailServicer);
            Log.d("TEST118", "masuk");
        }
    }
}
