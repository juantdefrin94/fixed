package com.tugasmoop.projectsoftwareengineering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<User> user = new ArrayList<>();
    EditText name, email, username, phoneNumber, password, cPassword;
    Button register;
    TextView login;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        storageReference = storage.getReference();

        name = findViewById(R.id.name);
        username = findViewById(R.id.usernameRegister);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.passwordRegister);
        cPassword = findViewById(R.id.confirmPassword);
        register = findViewById(R.id.buttonRegister);
        login = findViewById(R.id.login);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.buttonRegister)){
            //variable untuk isi dari inputan user
            String nameTxt = name.getText().toString().trim();
            String usernameTxt = username.getText().toString().trim();
            String emailTxt = email.getText().toString().trim();
            String phoneNumTxt = phoneNumber.getText().toString().trim();
            String passwordTxt = password.getText().toString().trim();
            String cPasswordTxt = cPassword.getText().toString().trim();

            //cek jika semua kosong
            if(usernameTxt.equals("") || emailTxt.equals("") || phoneNumTxt.equals("") || passwordTxt.equals("") || cPasswordTxt.equals("")){
                //alert
                Toast.makeText(this, "All fields must be field in", Toast.LENGTH_SHORT).show();
            }
            //cek jika password dan confirm password berbeda
            else if(!password.getText().toString().equals(cPassword.getText().toString())){
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                //ngosongin passnya lagi
                password.setText("");
                cPassword.setText("");
            }
            else{
                //masuk, buat user baru
                User newUser = new User(nameTxt, emailTxt, usernameTxt, passwordTxt, phoneNumTxt);

                //User baru masukin ke database di firebase
                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //cek dulu kalo ada user yang input usernamenya sama
                        if(snapshot.hasChild(usernameTxt)){
                            Toast.makeText(Register.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                            username.setText("");
                            username.requestFocus();
                        }
                        //username is unique
                        else {
                            databaseReference.child("Users").child(usernameTxt).setValue(newUser)
                                    .addOnSuccessListener(success->{
                                        Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(fail->{
                                        Toast.makeText(Register.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                                    });
                            HashMap newFavServicer = new HashMap();
                            newFavServicer.put("0", "null");
                            databaseReference.child("Users").child(usernameTxt).child("favServicer").setValue(newFavServicer);

                            HashMap newTransaction = new HashMap();
                            newTransaction.put("status", -99);//blm di bayar (pending)
                            newTransaction.put("customerUsername", "null");
                            newTransaction.put("servicerUsername", "null");
                            newTransaction.put("day", 0);
                            newTransaction.put("month", 0);
                            newTransaction.put("year", 0);
                            newTransaction.put("hour", 0);
                            newTransaction.put("minute", 0);
                            newTransaction.put("idOrderCustomer", -1);
                            newTransaction.put("idOrderServicer", -1);
                            databaseReference.child("Orders").child(usernameTxt).child("0").setValue(newTransaction);
                            databaseReference.child("Users").child(usernameTxt).child("servicer").child("orders").child("0").setValue(newTransaction);

                        }
                        //isi data default foto
//                        StorageReference mountainImagesRef = storageReference.child("images/" + username + "/profile.jpg");
//                        File f = new File("drawable/user.bmp");
//                        imageUri = Uri.fromFile(f);
//                        mountainImagesRef.putFile(imageUri);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            //            Intent login = new Intent(this, MainActivity.class);
            //            startActivity(login);
            finish();
        }
    }
}