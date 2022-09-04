package com.ujjman.money.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class NewPayment extends AppCompatActivity {
    private HashMap<String,String> nameWithPhoneNo;
    MaterialAutoCompleteTextView fromName;
    MaterialAutoCompleteTextView fromNumber;
    MaterialAutoCompleteTextView toName;
    MaterialAutoCompleteTextView toNumber;
    TextInputEditText amountTextView;
    TextInputEditText descriptionTextView;
    DatabaseReference rootRef;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_payment);
        fullScreencall();
        List<String> nameList=new LinkedList<String>();
        List<String> numberList=new LinkedList<String>();
        mAuth=FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        fromName = (MaterialAutoCompleteTextView) findViewById(R.id.from_textview);
        fromNumber = (MaterialAutoCompleteTextView) findViewById(R.id.sender_phoneno_textview);
        toName = (MaterialAutoCompleteTextView) findViewById(R.id.to_textview);
        toNumber = (MaterialAutoCompleteTextView) findViewById(R.id.receiver_phoneno_textview);
        amountTextView = (TextInputEditText) findViewById(R.id.amount_textview);
        descriptionTextView = (TextInputEditText) findViewById(R.id.description_textview);
        DatabaseReference usersdRef = rootRef.child("users");
        nameWithPhoneNo=new HashMap<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String phoneNo = ds.child("phoneNumber").getValue(String.class);
                    nameList.add(name);
                    numberList.add(phoneNo);
                    nameWithPhoneNo.put(name,phoneNo);
                }
                setNameAdapter(nameList);
                setNumberAdapter(numberList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);
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

    private void setNameAdapter(List<String> list)
    {
        String temp[]=new String[list.size()];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, list.toArray(temp));
        fromName.setAdapter(adapter);
        toName.setAdapter(adapter);
    }

    private void setNumberAdapter(List<String> list)
    {
        String temp[]=new String[list.size()];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, list.toArray(temp));
        fromNumber.setAdapter(adapter);
        toNumber.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
    public void onClickSave(View view)
    {
        DatabaseReference db=rootRef.child("publicPayments");
        String key=db.push().getKey();
        PaymentEntry paymentEntry=makePaymentEntry(key);
        if(paymentEntry==null)
            return;
        db.child(key).setValue(paymentEntry);
        finish();
    }

    public PaymentEntry makePaymentEntry(String key)
    {
        String senderName;
        String receiverPhone;
        String senderPhone;
        String receiverName;
        String amount;
        senderName=fromName.getText().toString().trim();
        receiverName=toName.getText().toString().trim();
        senderPhone=fromNumber.getText().toString().trim();
        receiverPhone=toNumber.getText().toString().trim();
        try {
            amount = amountTextView.getText().toString().trim();
            long l=Long.parseLong(amount);
        }catch(Exception e)
        {
            Toast.makeText(this, "Please enter only number in amount field", Toast.LENGTH_SHORT).show();
            amountTextView.setBackgroundColor(Color.rgb(252,134,134));
            return null;
        }
        String description=descriptionTextView.getText().toString().trim();
        String createdBy=mAuth.getCurrentUser().getDisplayName();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        PaymentEntry py=new PaymentEntry(senderName,senderPhone,receiverName,receiverPhone,amount,description,currentDate,currentTime,createdBy,key);
        return py;
    }
}