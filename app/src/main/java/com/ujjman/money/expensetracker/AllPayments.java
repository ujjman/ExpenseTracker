package com.ujjman.money.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllPayments extends AppCompatActivity {
    RecyclerView recyclerView;
    PaymentListAdapter adapter;
    ArrayList<PaymentEntry> list;
    AutoCompleteTextView searchTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_payments);
        fullScreencall();
        recyclerView=findViewById(R.id.allPaymentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        searchTextview=findViewById(R.id.search_payments_textview);
        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("publicPayments");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PaymentEntry p=ds.getValue(PaymentEntry.class);
                    list.add(p);
                }
                adapter=new PaymentListAdapter(AllPayments.this,list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                findViewById(R.id.loadingPanel_allPayments).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        rootRef.addValueEventListener(eventListener);

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