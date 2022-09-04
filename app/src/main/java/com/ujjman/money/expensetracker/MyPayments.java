package com.ujjman.money.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPayments extends AppCompatActivity {

    RecyclerView recyclerView;
    PaymentListAdapter adapter;
    ArrayList<PaymentEntry> list;
    String phoneNo;
    DatabaseReference rootRef;
    ValueEventListener eventListener;
    AutoCompleteTextView searchTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payments);
        fullScreencall();
        searchTextview=findViewById(R.id.search_my_payments_textview);
        recyclerView=findViewById(R.id.myPaymentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        rootRef = FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("publicPayments");
        addPayments();
    }
    public void addPayments()
    {
        DatabaseReference database = FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("users");
        String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        ValueEventListener eventListener1 = new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String phoneNumber=dataSnapshot.getValue(User.class).phoneNumber;
                phoneNo=phoneNumber;
                rootRef.addValueEventListener(eventListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PaymentEntry p=ds.getValue(PaymentEntry.class);
                    if(p.getSenderPhone().equals(phoneNo.trim()) || p.getReceiverPhone().equals(phoneNo.trim())) {
                        list.add(p);
                    }
                }
                adapter=new PaymentListAdapter(MyPayments.this,list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                findViewById(R.id.loadingPanel_myPayments).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        database.child(userId).addValueEventListener(eventListener1);
        searchTextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().toLowerCase());
            }
        });


    }

    public void filter(String text){
        ArrayList<PaymentEntry> temp = new ArrayList();
        for(PaymentEntry d: list){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getSenderPhone().toLowerCase().contains(text) || d.getSenderName().toLowerCase().contains(text) || d.getReceiverPhone().toLowerCase().contains(text) ||
                    d.getReceiverName().toLowerCase().contains(text) || d.getTime().toLowerCase().contains(text) || d.getDate().toLowerCase().contains(text) ||
                    d.getCreatorName().toLowerCase().contains(text) || d.getAmount().toLowerCase().contains(text) || d.getDescription().toLowerCase().contains(text)
            ){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
    }

    public void backButtonOnClick(View view)
    {
        finish();
    }
    public void fullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
