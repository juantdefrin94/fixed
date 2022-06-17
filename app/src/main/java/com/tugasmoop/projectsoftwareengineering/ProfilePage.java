package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUser;
import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUsername;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {

    TextView nameProfile, usernameProfile, emailProfile, phoneNumberProfile, addressProfile, dobProfile;
    ImageView bEmail, bPhoneNumber, bAddress, bDob, profilePicture;
    EditText emailProfileEdit, phoneProfileEdit, addressProfileEdit, dobProfileEdit;
    Boolean buttonToggleEmail = false, buttonTogglePhone = false, buttonToggleAddress = false, buttonToggleDob = false;
    Button offer, logout;
    LinearLayout home, favorite, history;
    String usernameTxt, nameTxt, emailTxt, passwordTxt, phoneNumTxt, addressTxt, dobTxt;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");
    boolean check = false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //di import dari main activity(currentUsername) --> String
        usernameTxt = currentUsername;

        //mengambil data user dari main activity(currentUser) --> Object User
        nameTxt = currentUser.getName();
        emailTxt = currentUser.getEmail();
        phoneNumTxt = currentUser.getPhoneNumber();
        addressTxt = currentUser.getAddress();
        dobTxt = currentUser.getDOB();
        passwordTxt = currentUser.getPassword();

        //mengambil variabel dari xml dan diubah datanya menjadi data yang sudah diambil sebelumnya
        nameProfile = findViewById(R.id.nameProfile);
        nameProfile.setText(nameTxt);

        usernameProfile = findViewById(R.id.usernameProfile);
        usernameProfile.setText(usernameTxt);

        emailProfile = findViewById(R.id.emailProfile);
        emailProfile.setText(emailTxt);

        phoneNumberProfile = findViewById(R.id.phoneNumberProfile);
        phoneNumberProfile.setText(phoneNumTxt);

        addressProfile = findViewById(R.id.addressProfile);
        addressProfile.setText(addressTxt);

        dobProfile = findViewById(R.id.dobProfile);
        dobProfile.setText(dobTxt);

        bEmail = findViewById(R.id.bEmail);
        bPhoneNumber = findViewById(R.id.bPhoneNumber);
        bAddress = findViewById(R.id.bAddress);
        bDob = findViewById(R.id.bDob);

        emailProfileEdit = findViewById(R.id.emailProfileEdit);
        phoneProfileEdit = findViewById(R.id.phoneProfileEdit);
        addressProfileEdit = findViewById(R.id.addressProfileEdit);
        dobProfileEdit = findViewById(R.id.dobProfileEdit);

        offer = findViewById(R.id.offer);
        logout = findViewById(R.id.logout);

        home = findViewById(R.id.home);
        favorite = findViewById(R.id.favorite);
        history = findViewById(R.id.history);

        profilePicture = findViewById(R.id.profilePicture);

        //mengambil storage dari cloud storage firebase
        storageReference = storage.getReference();

        //error jika user baru, tidak ada filenya
//        try {
//            final File localFile = File.createTempFile(currentUser,"jpg");
//            storageReference.child("images/" + currentUser + "/profile.jpg").getFile(localFile)
//                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Uri userUri = Uri.fromFile(localFile);
//                            profilePicture.setImageURI(userUri);
//                        }
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //button ditekan
        bEmail.setOnClickListener(this);
        bPhoneNumber.setOnClickListener(this);
        bAddress.setOnClickListener(this);
        bDob.setOnClickListener(this);
        offer.setOnClickListener(this);
        logout.setOnClickListener(this);
        home.setOnClickListener(this);
        favorite.setOnClickListener(this);
        profilePicture.setOnClickListener(this);
        history.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.bEmail)){
            if(!buttonToggleEmail){
                buttonToggleEmail = true;
                emailProfile.setVisibility(View.GONE);
                emailProfileEdit.setVisibility(View.VISIBLE);
            }
            else{
                check = false;// buat ngecek unik ato ga
                String newEmail;
                newEmail = emailProfileEdit.getText().toString().trim();// buat ngambil email yang barunya apa

                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                        Log.d("TEST", "TEST");//buat ngecek
                        for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                            Log.d("TEST94", data.child("email").getValue(String.class));
                            if(data.child("email").getValue(String.class).equals(newEmail)) {//liat ada ga email di datanya
                                Log.d("TEST3", "TEST");
                                //kalo email nya sama
                                check = true;//ga unik
                                return;
                            }
                        }

                        if(check == false){//ini unik
                            Log.d("TEST4", "TEST");
                            buttonToggleEmail  = false;
                            emailProfile.setVisibility(View.VISIBLE);
                            emailProfileEdit.setVisibility(View.GONE);

                            emailProfile.setText(newEmail);//email di UI jadi email baru
                            emailTxt = newEmail;//email lama diganti baru

                            currentUser.setEmail(newEmail);

                            HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                            newUser.put("email", newEmail);

                            databaseReference.child("Users").child(usernameTxt).updateChildren(newUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProfilePage.this, "Successfully update", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfilePage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            Log.d("TEST2", emailTxt);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        else if(view == findViewById(R.id.bPhoneNumber)){
            if(!buttonTogglePhone){
                buttonTogglePhone = true;
                phoneNumberProfile.setVisibility(View.GONE);
                phoneProfileEdit.setVisibility(View.VISIBLE);
            }
            else{
                check = false;// buat ngecek unik ato ga
                String newPhoneNum;
                newPhoneNum = phoneProfileEdit.getText().toString().trim();// buat ngambil phone yang barunya apa

                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                        for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                            if(data.child("phoneNumber").getValue(String.class).equals(newPhoneNum)) {//liat ada ga phone di datanya
                                Toast.makeText(ProfilePage.this, "Phone number already exists", Toast.LENGTH_SHORT).show();//kalo phone nya sama
                                check = true;//ga unik
                                return;
                            }
                        }

                        if(check == false){//ini unik
                            buttonTogglePhone  = false;
                            phoneNumberProfile.setVisibility(View.VISIBLE);
                            phoneProfileEdit.setVisibility(View.GONE);

                            phoneNumberProfile.setText(newPhoneNum);//phone di UI jadi phone baru
                            phoneNumTxt = newPhoneNum;//phone lama diganti baru

                            currentUser.setPhoneNumber(newPhoneNum);

                            HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                            newUser.put("phoneNumber", newPhoneNum);

                            databaseReference.child("Users").child(usernameTxt).updateChildren(newUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProfilePage.this, "Successfully update", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfilePage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        else if(view == findViewById(R.id.bAddress)){
            if(!buttonToggleAddress){
                buttonToggleAddress = true;
                addressProfile.setVisibility(View.GONE);
                addressProfileEdit.setVisibility(View.VISIBLE);
            }
            else {
                String newAddress;
                newAddress = addressProfileEdit.getText().toString().trim();// buat ngambil address yang barunya apa

                buttonToggleAddress = false;
                addressProfile.setVisibility(View.VISIBLE);
                addressProfileEdit.setVisibility(View.GONE);

                addressProfile.setText(newAddress);//address di UI jadi address baru
                addressTxt = newAddress;//address lama diganti baru

                currentUser.setAddress(newAddress);

                HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                newUser.put("address", newAddress);

                databaseReference.child("Users").child(usernameTxt).updateChildren(newUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfilePage.this, "Successfully update", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfilePage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            }
        else if(view == findViewById(R.id.bDob)){
            if(!buttonToggleDob){
                buttonToggleDob = true;
                dobProfile.setVisibility(View.GONE);
                dobProfileEdit.setVisibility(View.VISIBLE);
            }
            else {
                String newDob;
                newDob = dobProfileEdit.getText().toString().trim();// buat ngambil dob yang barunya apa

                buttonToggleDob = false;
                dobProfile.setVisibility(View.VISIBLE);
                dobProfileEdit.setVisibility(View.GONE);

                dobProfile.setText(newDob);//dob di UI jadi dob baru
                dobTxt = newDob;//dob lama diganti baru

                currentUser.setDOB(newDob);

                HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                newUser.put("DOB", newDob);

                databaseReference.child("Users").child(usernameTxt).updateChildren(newUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfilePage.this, "Successfully update", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfilePage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        else if(view == findViewById(R.id.logout)){
            finish();
            Intent logout = new Intent(this, MainActivity.class);
            startActivity(logout);
        }
        else if(view == findViewById(R.id.home)){
            Intent home = new Intent(this, MainPage.class);
            startActivity(home);
        }
        else if(view == findViewById(R.id.offer)){
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    status = snapshot.child(currentUsername).child("servicer").child("status").getValue(Integer.class);
                    if(status == 1){
                        Intent offer = new Intent(ProfilePage.this, ServicerProfilePage.class);
                        startActivity(offer);
                    }
                    else if(status == 0){
                        Intent upload = new Intent(ProfilePage.this, UploadDataPage.class);
                        startActivity(upload);
                    }
                    else{
                        Intent pending = new Intent(ProfilePage.this, PendingPage.class);
                        startActivity(pending);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(view == findViewById(R.id.profilePicture)){
            Intent showProfile = new Intent(this, ShowProfile.class);
            startActivity(showProfile);
        }
        else if(view == findViewById(R.id.favorite)){
            Intent favorite = new Intent(this, ShowFavorite.class);
            startActivity(favorite);
        }
        else if(view == findViewById(R.id.history)){
            Intent history = new Intent(this, ShowHistory.class);
            startActivity(history);
        }
    }
}