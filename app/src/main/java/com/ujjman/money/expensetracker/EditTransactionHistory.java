package com.ujjman.money.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class EditTransactionHistory extends AppCompatActivity {
    TextView fromName;
    TextView fromNumber;
    TextView toName;
    TextView toNumber;
    TextView amountTextView;
    TextView descriptionTextView;
    TextView createdBy;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transation_history);
        fullScreencall();
        intent=getIntent();
        initialize();

    }

    public void initialize()
    {
        fromName=findViewById(R.id.fromname_transactionhistory_textview);
        fromNumber=findViewById(R.id.fromphone_transactionhistory_textview);
        toName=findViewById(R.id.toname_transactionhistory_textview);
        toNumber=findViewById(R.id.tophone_transactionhistory_textview);
        amountTextView=findViewById(R.id.amount_transactionhistory_textview);
        descriptionTextView=findViewById(R.id.description_transactionhistory_textview);
        createdBy=findViewById(R.id.createdby_transactionhistory_textview);
        fromName.setText(intent.getStringExtra("senderName"));
        toName.setText(intent.getStringExtra("receiverName"));
        fromNumber.setText("("+intent.getStringExtra("senderPhone")+")");
        toNumber.setText("("+intent.getStringExtra("receiverPhone")+")");
        amountTextView.setText("Rs. "+intent.getStringExtra("amount"));
        descriptionTextView.setText(intent.getStringExtra("description"));
        createdBy.setText("Created on "+intent.getStringExtra("date")+" "+intent.getStringExtra("time")+" by "+intent.getStringExtra("creatorName"));
    }

    public void backButtonOnClick(View view)
    {
        Intent i=new Intent(this,TransactionHistory.class);
        startActivity(i);
        finish();
    }

    public void fullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}