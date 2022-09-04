package com.ujjman.money.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class EditPaymentEntry extends AppCompatActivity {
    MaterialAutoCompleteTextView fromName;
    MaterialAutoCompleteTextView fromNumber;
    MaterialAutoCompleteTextView toName;
    MaterialAutoCompleteTextView toNumber;
    TextInputEditText amountTextView;
    TextInputEditText descriptionTextView;
    TextView no_textview;
    TextView yes_textview;
    FirebaseAuth mAuth;
    DatabaseReference database;
    Intent intent;
    int check = 0;
    PaymentEntry thisPaymentEntry;
    SwitchCompat switchPaymentDone;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment_entry);
        fullScreencall();
        intent = getIntent();
        initialize();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("publicPayments");
    }

    public void initialize() {
        fromName = (MaterialAutoCompleteTextView) findViewById(R.id.from_editpayment_textview);
        fromNumber = (MaterialAutoCompleteTextView) findViewById(R.id.sender_phoneno_editpayment_textview);
        toName = (MaterialAutoCompleteTextView) findViewById(R.id.to_editpayment_textview);
        toNumber = (MaterialAutoCompleteTextView) findViewById(R.id.receiver_phoneno_editpayment_textview);
        amountTextView = (TextInputEditText) findViewById(R.id.amount_editpayment_textview);
        descriptionTextView = (TextInputEditText) findViewById(R.id.description_editpayment_textview);
        switchPaymentDone = findViewById(R.id.switch_paymentdone);
        no_textview = findViewById(R.id.no_switch_textview);
        yes_textview = findViewById(R.id.yes_switch_textview);
        no_textview.setVisibility(View.VISIBLE);
        yes_textview.setVisibility(View.INVISIBLE);
        fromName.setText(intent.getStringExtra("senderName"));
        toName.setText(intent.getStringExtra("receiverName"));
        fromNumber.setText(intent.getStringExtra("senderPhone"));
        toNumber.setText(intent.getStringExtra("receiverPhone"));
        amountTextView.setText(intent.getStringExtra("amount"));
        descriptionTextView.setText(intent.getStringExtra("description"));
    }

    public void onClickSwitch(View v) {
        if (switchPaymentDone.isChecked()) {
            no_textview.setVisibility(View.INVISIBLE);
            yes_textview.setVisibility(View.VISIBLE);
        } else {
            no_textview.setVisibility(View.VISIBLE);
            yes_textview.setVisibility(View.INVISIBLE);
        }

    }

    public void onClickUpdateButton(View view) {
        String paymentId = intent.getStringExtra("paymentId");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PaymentEntry p = ds.getValue(PaymentEntry.class);
                    if (p.getPaymentId().equals(paymentId)) {
                        thisPaymentEntry = p;
                        if (check == 0) {
                            updateEntry(paymentId, view);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        database.addListenerForSingleValueEvent(eventListener);
    }

    public void updateEntry(String paymentId, View view) {
        if (view == null)
            return;
        String senderName;
        String receiverPhone;
        String senderPhone;
        String receiverName;
        String amount;
        senderName = fromName.getText().toString().trim();
        receiverName = toName.getText().toString().trim();
        senderPhone = fromNumber.getText().toString().trim();
        receiverPhone = toNumber.getText().toString().trim();
        try {
            amount = amountTextView.getText().toString().trim();
            long l = Long.parseLong(amount);
        } catch (Exception e) {
            toast = Toast.makeText(this, "Please enter only number in amount field", Toast.LENGTH_SHORT);

            toast.show();
            amountTextView.setBackgroundColor(Color.rgb(252, 134, 134));
            return;
        }
        String description = descriptionTextView.getText().toString().trim();
        String createdBy = mAuth.getCurrentUser().getDisplayName();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        PaymentEntry py = new PaymentEntry(senderName, senderPhone, receiverName, receiverPhone, amount, description, currentDate, currentTime, createdBy, paymentId);
        database.child(paymentId).setValue(py);
        toast = Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onClickDeleteButton(View view) {
        String paymentId = intent.getStringExtra("paymentId");
        database.child(paymentId).removeValue();
        if (check == 0) {
            toast = Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT);
            toast.show();
            backButtonOnClick(view);
        }
    }

    public void onClickPaytmButton(View view) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            toast = Toast.makeText(this, "App Not Installed", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onClickGpayButton(View view) {

        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.nbu.paisa.user");
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            toast = Toast.makeText(this, "App Not Installed", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void fullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void backButtonOnClick(View view) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        if (switchPaymentDone.isChecked()) {
            check = 1;
            onClickUpdateButton(view);
            onClickDeleteButton(view);
            Handler h = new Handler();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    database = FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("transactionHistory");
                    String key = database.push().getKey();
                    database.child(key).setValue(thisPaymentEntry);
                }
            };
            h.postDelayed(r, 2000);
        }
        Intent i = null;
        if (intent.getStringExtra("className").equals(AllPayments.class.getSimpleName())) {
            i = new Intent(this, AllPayments.class);
        } else if (intent.getStringExtra("className").equals(MyPayments.class.getSimpleName())) {
            i = new Intent(this, MyPayments.class);
        }
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        backButtonOnClick(this.amountTextView);
    }
}


