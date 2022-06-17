package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.AdapterData.currentDetailServicer;
import static com.tugasmoop.projectsoftwareengineering.AdapterDataHistory.currentHistoryServicer;
import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUser;
import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.SchedulePage.day;
import static com.tugasmoop.projectsoftwareengineering.SchedulePage.hour;
import static com.tugasmoop.projectsoftwareengineering.SchedulePage.minute;
import static com.tugasmoop.projectsoftwareengineering.SchedulePage.month;
import static com.tugasmoop.projectsoftwareengineering.SchedulePage.year;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TransactionPage extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");
    String cNameTxt, cAddressTxt, cPhoneNumTxt, sNameTxt, sPhoneNumTxt, sCategoryTxt;
    int transactionCount = 0, transactionCountServicer = 0;
    TextView servicerFee, totalCost, dateTimeTransaction;
    ImageView backTransaction;
    int fee;
    TextView customerName, customerAddress, customerPhoneNum, servicerName, servicerPhoneNum, servicerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_page);

        customerName = findViewById(R.id.customerName);
        customerAddress = findViewById(R.id.customerAddress);
        customerPhoneNum = findViewById(R.id.customerPhone);

        servicerName = findViewById(R.id.servicerName);
        servicerPhoneNum = findViewById(R.id.servicerPhone);
        servicerCategory = findViewById(R.id.servicerCategory);

        backTransaction = findViewById(R.id.backTransaction);
        backTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Log.d("Test1", currentUsername);

        //ambil data customer
        databaseReference.child("Users").child(currentUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Test2", "masukkkk");
                cNameTxt = snapshot.child("name").getValue(String.class);
                cAddressTxt = snapshot.child("address").getValue(String.class);
                cPhoneNumTxt = snapshot.child("phoneNumber").getValue(String.class);
                transactionCount = snapshot.child("transactionCount").getValue(Integer.class);


                customerName.setText(cNameTxt);
                customerAddress.setText(cAddressTxt);
                customerPhoneNum.setText(cPhoneNumTxt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //ambil data servicer
        databaseReference.child("Users").child(currentDetailServicer).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Test3", "masukkkk");
                sNameTxt = snapshot.child("servicer").child("name").getValue(String.class);
                sPhoneNumTxt = snapshot.child("phoneNumber").getValue(String.class);
                sCategoryTxt = snapshot.child("servicer").child("category").getValue(String.class);
                transactionCountServicer = snapshot.child("servicer").child("transactionCount").getValue(Integer.class);


                servicerName.setText(sNameTxt);
                servicerPhoneNum.setText(sPhoneNumTxt);
                servicerCategory.setText(sCategoryTxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("Test5", "masukkkk");
        servicerFee = findViewById(R.id.servicerFee);
        totalCost = findViewById(R.id.totalCost);
        Log.d("Test6", "masukkkk");

        databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("price").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Test4", "masukkkk");

                servicerFee.setText("Rp" + String.valueOf(snapshot.getValue(Integer.class)));
                fee = snapshot.getValue(Integer.class);
                totalCost.setText("TOTAL : Rp" + (fee + 20000));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String[] strMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Ags", "Sept", "Okt", "Nov", "Dec"};
        String dateAndTime = String.valueOf(day) + " " + strMonth[month - 1] + " " + year + " at " + String.format("%02d:%02d", hour, minute);

        dateTimeTransaction = findViewById(R.id.dateTimeTransaction);
        dateTimeTransaction.setText(dateAndTime);
    }

    public void processOrder(View view) {
        //masukin db orders customer dengan status 0 (pending)
        Transaction newTransaction = new Transaction(0, currentUsername, currentDetailServicer);

        newTransaction.setDay(day);
        newTransaction.setMonth(month);
        newTransaction.setYear(year);
        newTransaction.setHour(hour);
        newTransaction.setMinute(minute);

        Log.d("TESTDATE", String.valueOf(day) + " - " + String.valueOf(month) + " - " + String.valueOf(year));

        currentUser.getTransactionList().add(newTransaction);
        int transactionCount = currentUser.getTransactionCount();
        currentUser.setTransactionCount(transactionCount + 1);

        newTransaction.setIdOrderCustomer(transactionCount);
        newTransaction.setIdOrderServicer(transactionCountServicer);

        databaseReference.child("Orders").child(currentUsername).child(String.valueOf(transactionCount)).setValue(newTransaction);

        HashMap newTransactionCount = new HashMap();
        newTransactionCount.put("transactionCount", currentUser.getTransactionCount());

        databaseReference.child("Users").child(currentUsername).updateChildren(newTransactionCount);

        //masukin ke db orders servicer
        databaseReference.child("Users").child(currentDetailServicer).child("servicer").child("orders").child(String.valueOf(transactionCountServicer)).setValue(newTransaction);

        HashMap newTransactionCountServicer = new HashMap();
        newTransactionCountServicer.put("transactionCount", transactionCountServicer + 1);

        databaseReference.child("Users").child(currentDetailServicer).child("servicer").updateChildren(newTransactionCountServicer);

        Toast.makeText(this, "Transaction has been recorded", Toast.LENGTH_SHORT).show();
        Intent main = new Intent(this, ShowHistory.class);
        startActivity(main);
    }
}