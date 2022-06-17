package com.tugasmoop.projectsoftwareengineering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register;
    EditText username, password;
    Button login;
    public static int mode;
    public static String currentUsername;
    public static User currentUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);
        register = findViewById(R.id.register);
        login = findViewById(R.id.buttonLogin);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.buttonLogin)){
            //variable untuk isi dari inputan user
            String usernameTxt = username.getText().toString().trim();
            String passwordTxt = password.getText().toString().trim();

            if(usernameTxt.equals("") || passwordTxt.equals("")){
                Toast.makeText(this, "All fields must be field in", Toast.LENGTH_SHORT).show();
            }
            else{
                //firebase
                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //kalau username ditemukan
                        if(snapshot.hasChild(usernameTxt)){
                            //ambil passwordnya dan cek
                            String getPassword = snapshot.child(usernameTxt).child("password").getValue(String.class);
                            if(passwordTxt.equals(getPassword)){
                                currentUsername = usernameTxt;

                                //main attribute
                                String name = snapshot.child(usernameTxt).child("name").getValue(String.class);
                                String email = snapshot.child(usernameTxt).child("email").getValue(String.class);
                                String username = snapshot.child(usernameTxt).child("username").getValue(String.class);
                                String password = snapshot.child(usernameTxt).child("password").getValue(String.class);
                                String phoneNumber = snapshot.child(usernameTxt).child("phoneNumber").getValue(String.class);
                                String address = snapshot.child(usernameTxt).child("address").getValue(String.class);
                                String DOB = snapshot.child(usernameTxt).child("DOB").getValue(String.class);
                                int favCount = snapshot.child(usernameTxt).child("favCount").getValue(Integer.class);
                                int transactionCount = snapshot.child(usernameTxt).child("transactionCount").getValue(Integer.class);

                                Log.d("TEST", "masukkkk");
                                //servicer attribute
                                int status = snapshot.child(usernameTxt).child("servicer").child("status").getValue(Integer.class);
                                String category = snapshot.child(usernameTxt).child("servicer").child("category").getValue(String.class);
                                int wallet = snapshot.child(usernameTxt).child("servicer").child("wallet").getValue(Integer.class);
                                String NIK = snapshot.child(usernameTxt).child("servicer").child("NIK").getValue(String.class);
                                String servicerName = snapshot.child(usernameTxt).child("servicer").child("name").getValue(String.class);
                                String noRek = snapshot.child(usernameTxt).child("servicer").child("noRek").getValue(String.class);
                                String bank = snapshot.child(usernameTxt).child("servicer").child("bank").getValue(String.class);
                                String description = snapshot.child(usernameTxt).child("servicer").child("description").getValue(String.class);
                                int rating = snapshot.child(usernameTxt).child("servicer").child("rating").getValue(Integer.class);
                                int custCount = snapshot.child(usernameTxt).child("servicer").child("custCount").getValue(Integer.class);
                                int price = snapshot.child(usernameTxt).child("servicer").child("price").getValue(Integer.class);
                                int transactionCountServicer = snapshot.child(usernameTxt).child("servicer").child("transactionCount").getValue(Integer.class);


                                //make object user just for data (loading)
                                Servicer servicer = new Servicer();
                                servicer.setStatus(status);
                                servicer.setCategory(category);
                                servicer.setWallet(wallet);
                                servicer.setNIK(NIK);
                                servicer.setName(servicerName);
                                servicer.setNoRek(noRek);
                                servicer.setBank(bank);
                                servicer.setDescription(description);
                                servicer.setRating(rating);
                                servicer.setCustCount(custCount);
                                servicer.setPrice(price);
                                servicer.setTransactionCount(transactionCountServicer);

                                currentUser = new User(name, email, username, password, phoneNumber);
                                currentUser.setAddress(address);
                                currentUser.setDOB(DOB);
                                currentUser.setFavCount(favCount);
                                currentUser.setServicer(servicer);
                                currentUser.setTransactionCount(transactionCount);

                                List<String> favServicer = new ArrayList<>();
                                for(int i = 0; i < favCount; i++){//buat looping fav servicer
                                    String fav = snapshot.child(usernameTxt).child("favServicer").child(String.valueOf(i)).getValue(String.class);
                                    favServicer.add(fav);//nambahin favorit
                                    Log.d("teslagi", fav);
                                }
                                currentUser.setFavServicer(favServicer);

                                databaseReference.child("Orders").child(currentUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        List<Transaction> transactionList = new ArrayList<>();
                                        for(int i = 0; i < transactionCount; i++){
                                            int status = snapshot.child(String.valueOf(i)).child("status").getValue(Integer.class);
                                            String customerUsername = snapshot.child(String.valueOf(i)).child("customerUsername").getValue(String.class);
                                            String servicerUsername = snapshot.child(String.valueOf(i)).child("servicerUsername").getValue(String.class);

                                            Log.d("TESTStatusTransaction", String.valueOf(status));

                                            if(status != -99){
                                                Transaction newTransaction = new Transaction(status, customerUsername, servicerUsername);

                                                //masukin data-data di orders
                                                int day = snapshot.child(String.valueOf(i)).child("day").getValue(Integer.class);
                                                int month = snapshot.child(String.valueOf(i)).child("month").getValue(Integer.class);
                                                int year = snapshot.child(String.valueOf(i)).child("year").getValue(Integer.class);
                                                int hour = snapshot.child(String.valueOf(i)).child("hour").getValue(Integer.class);
                                                int minute = snapshot.child(String.valueOf(i)).child("minute").getValue(Integer.class);
                                                newTransaction.setDay(day);
                                                newTransaction.setMonth(month);
                                                newTransaction.setYear(year);
                                                newTransaction.setHour(hour);
                                                newTransaction.setMinute(minute);

                                                Log.d("TestMasukinData", "success");

                                                transactionList.add(newTransaction);
                                            }
                                        }

                                        currentUser.setTransactionList(transactionList);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                //ambil orders di servicer
                                List<Transaction>transactionList = new ArrayList<>();
                                Log.d("TEST transaction", String.valueOf(transactionCountServicer));
                                for(int i = 0; i < transactionCountServicer; i++){
                                    status = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("status").getValue(Integer.class);
                                    String customerUsername = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("customerUsername").getValue(String.class);
                                    String customerServicer = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("servicerUsername").getValue(String.class);
                                    Transaction newTransaction = new Transaction(status, customerUsername, customerServicer);

                                    int day = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("day").getValue(Integer.class);
                                    int month = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("month").getValue(Integer.class);
                                    int year = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("year").getValue(Integer.class);
                                    int hour = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("hour").getValue(Integer.class);
                                    int minute = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("minute").getValue(Integer.class);

                                    newTransaction.setDay(day);
                                    newTransaction.setMonth(month);
                                    newTransaction.setYear(year);
                                    newTransaction.setHour(hour);
                                    newTransaction.setMinute(minute);

                                    transactionList.add(newTransaction);

                                }
                                currentUser.getServicer().setTransactionList(transactionList);

                                Toast.makeText(MainActivity.this, "Successfully login", Toast.LENGTH_SHORT).show();
                                Intent mainPage = new Intent(MainActivity.this, MainPage.class);
                                startActivity(mainPage);
                            }else{
                                Toast.makeText(MainActivity.this, "Username or Password are wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        }
        else{
            Intent register = new Intent(this, Register.class);
            startActivity(register);
        }
    }
}