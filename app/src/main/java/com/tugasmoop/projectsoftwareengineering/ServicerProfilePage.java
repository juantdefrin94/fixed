package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUser;
import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUsername;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ServicerProfilePage extends AppCompatActivity implements View.OnClickListener {

    ImageView backProfile, profilePictureServicer, bCategory, bPrice;
    TextView description, nameServicerProfile, categoryProfile, emailServicerProfile, phoneServicerProfile, addressServicerProfile, dobServicerProfile, rating, priceProfile;
    EditText categoryProfileEdit, priceProfileEdit;
    Boolean buttonToggleCategory = false, buttonTogglePrice = false;
    String usernameTxt, nameTxt, emailTxt, phoneNumTxt, addressTxt, dobTxt, categoryTxt, descriptionTxt;
    int ratingTxt, custCountTxt, priceTxt, walletTxt;
    double totalRating;
    Button walletProfile;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");
    LinearLayout order;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText popUpDescription;
    private Button bCancel, bSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicer_profile_page);

        usernameTxt = currentUsername;

        nameTxt = currentUser.getServicer().getName();
        emailTxt = currentUser.getEmail();
        phoneNumTxt = currentUser.getPhoneNumber();
        addressTxt = currentUser.getAddress();
        dobTxt = currentUser.getDOB();
        categoryTxt = currentUser.getServicer().getCategory();
        descriptionTxt = currentUser.getServicer().getDescription();
        ratingTxt = currentUser.getServicer().getRating();
        custCountTxt = currentUser.getServicer().getCustCount();
        priceTxt = currentUser.getServicer().getPrice();
        walletTxt = currentUser.getServicer().getWallet();

        emailServicerProfile = findViewById(R.id.emailServicerProfile);
        emailServicerProfile.setText(emailTxt);

        phoneServicerProfile = findViewById(R.id.phoneServicerProfile);
        phoneServicerProfile.setText(phoneNumTxt);

        addressServicerProfile = findViewById(R.id.addressServicerProfile);
        addressServicerProfile.setText(addressTxt);

        dobServicerProfile = findViewById(R.id.dobServicerProfile);
        dobServicerProfile.setText(dobTxt);

        categoryProfile = findViewById(R.id.categoryProfile);
        categoryProfile.setText(categoryTxt);

        priceProfile = findViewById(R.id.priceProfile);
        priceProfile.setText("Rp. " + String.valueOf(priceTxt));

        walletProfile = findViewById(R.id.walletProfile);
        walletProfile.setText("Rp. " + String.valueOf(walletTxt));

        profilePictureServicer = findViewById(R.id.profilePictureServicer);

        description = findViewById(R.id.description);
        nameServicerProfile = findViewById(R.id.nameServicerProfile);
        phoneServicerProfile = findViewById(R.id.phoneServicerProfile);
        addressServicerProfile = findViewById(R.id.addressServicerProfile);
        dobServicerProfile = findViewById(R.id.dobServicerProfile);
        categoryProfileEdit = findViewById(R.id.categoryProfileEdit);
        priceProfileEdit = findViewById(R.id.priceProfileEdit);

        nameServicerProfile.setText(nameTxt);

        rating = findViewById(R.id.rating);
        if(custCountTxt == 0){
            rating.setText("0/5");
        }
        else{
            totalRating = (double) ratingTxt/ (double) custCountTxt;
        }
        rating.setText(totalRating + "/5");

        bCategory = findViewById(R.id.bCategory);
        bPrice = findViewById(R.id.bPrice);
        backProfile = findViewById(R.id.backProfile);
        order = findViewById(R.id.order);

        bCategory.setOnClickListener(this);
        backProfile.setOnClickListener(this);
        description.setOnClickListener(this);
        bPrice.setOnClickListener(this);
        order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.backProfile)){
            Intent backProfile = new Intent(this, ProfilePage.class);
            startActivity(backProfile);
        }
        else if(view == findViewById(R.id.bCategory)){
            if(!buttonToggleCategory){
                buttonToggleCategory = true;
                categoryProfile.setVisibility(View.GONE);
                categoryProfileEdit.setVisibility(View.VISIBLE);
            }
            else{
                String newCategory, currentCategory;
                boolean categoryCheck = false;
                String arr[] = {"television","refrigerator","air conditioner","dispenser","washing machine","fan","speaker","computer","laptop","handphone"};
                newCategory = categoryProfileEdit.getText().toString().trim();// buat ngambil category yang barunya apa
                for(int i = 0; i < 10; i++){
                    if(newCategory.equalsIgnoreCase(arr[i])){
                        categoryCheck = true;
                        break;
                    }
                }
                if(categoryCheck == false){
                    Toast.makeText(this, "Please fill in the available categories", Toast.LENGTH_SHORT).show();
                }
                else{
                    buttonToggleCategory = false;
                    categoryProfile.setVisibility(View.VISIBLE);
                    categoryProfileEdit.setVisibility(View.GONE);

                    categoryProfile.setText(newCategory);//category di UI jadi category baru
                    categoryTxt = newCategory;//category lama diganti baru

                    currentUser.getServicer().setCategory(newCategory);

                    HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                    newUser.put("category", newCategory);

                    databaseReference.child("Users").child(usernameTxt).child("servicer").updateChildren(newUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ServicerProfilePage.this, "Successfully update", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ServicerProfilePage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        }
        else if(view == findViewById(R.id.bPrice)){
            if(!buttonTogglePrice){
                buttonTogglePrice = true;
                priceProfile.setVisibility(View.GONE);
                priceProfileEdit.setVisibility(View.VISIBLE);
            }
            else{
                String newPrice;
                newPrice = priceProfileEdit.getText().toString().trim();

                buttonTogglePrice = false;
                priceProfile.setVisibility(View.VISIBLE);
                priceProfileEdit.setVisibility(View.GONE);

                priceProfile.setText("Rp. " + newPrice);//category di UI jadi category baru
                priceTxt = Integer.valueOf(newPrice);//category lama diganti baru

                currentUser.getServicer().setPrice(Integer.valueOf(newPrice));

                HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                newUser.put("price", priceTxt);

                databaseReference.child("Users").child(usernameTxt).child("servicer").updateChildren(newUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ServicerProfilePage.this, "Successfully update", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ServicerProfilePage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        else if(view == findViewById(R.id.description)){
            createNewDescriptionDialog();
        }
        else if(view == findViewById(R.id.order)){
            Intent toOrder = new Intent(this, ShowOrder.class);
            startActivity(toOrder);
        }
    }

    public void  createNewDescriptionDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View descriptionPopUpView = getLayoutInflater().inflate(R.layout.popup, null);
        popUpDescription = (EditText) descriptionPopUpView.findViewById(R.id.popUpDescription);

        bSave = (Button) descriptionPopUpView.findViewById(R.id.bSave);
        bCancel = (Button) descriptionPopUpView.findViewById(R.id.bCancel);

        popUpDescription.setText(descriptionTxt);

        dialogBuilder.setView(descriptionPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newDescription;
                newDescription = popUpDescription.getText().toString().trim();// buat ngambil address yang barunya apa

                popUpDescription.setText(descriptionTxt);
                descriptionTxt = newDescription;

                currentUser.getServicer().setDescription(newDescription);

                HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                newUser.put("description", newDescription);

                databaseReference.child("Users").child(usernameTxt).child("servicer").updateChildren(newUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ServicerProfilePage.this, "Successfully update", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ServicerProfilePage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.hide();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
    }
}