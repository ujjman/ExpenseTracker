package com.ujjman.money.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText email;
    private TextInputEditText pass;
    private TextInputEditText name;
    private TextInputEditText phoneNumber;
    private DatabaseReference mDatabase;
    private Button signUp;
    private Button already;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullScreencall();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        email=findViewById(R.id.signup_email_textview);
        pass=findViewById(R.id.signup_password_textview);
        phoneNumber=findViewById(R.id.signup_phone_textview);
        name=findViewById(R.id.signup_name_textview);
        signUp=findViewById(R.id.signup_button);
        already=findViewById(R.id.already_button);
        setAnimation(email,1);
        setAnimation(pass,2);
        setAnimation(name,3);
        setAnimation(phoneNumber,4);
        setAnimation(signUp,5);
        setAnimation(already,6);

    }
    public void setAnimation(View v, int i) {
        Animation anim;
        if (i % 2 == 0)
            anim = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        else
            anim = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        v.startAnimation(anim);
    }
    public void registerButton(View v)
    {
        String e=email.getText().toString();
        String p=pass.getText().toString();
        String ph=phoneNumber.getText().toString();
        String n=name.getText().toString();
        Log.d("ujjman",e+"     "+p);
        if(e.isEmpty() || p.isEmpty() || ph.isEmpty() || n.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter valid email/password,name/phone number",Toast.LENGTH_LONG).show();
        }
        else
        {
            createAccount(e,p,n,ph);
        }
    }

    private void createAccount(String email,String password,String userName,String userPhoneNumber)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("ujjman", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            User user1=new User(email,password,userName,userPhoneNumber);
                            Log.d("ujjman",mDatabase.toString());
                            mDatabase.child("users").child(user.getUid()).setValue(user1);
                            user.updateProfile(new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName).build());
                        } else {
                            Log.w("ujjman", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Error occurred "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }});
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff0000"));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1aff00"));
            }
        });
        alertDialog.show();
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

    public void onClickAlready(View view)
    {
        Intent i=new Intent(this,Login.class);

        Pair pairs[]=new Pair[6];
        pairs[0]=new Pair<View,String>(email,"email_trans");
        pairs[1]=new Pair<View,String>(pass,"password_trans");
        pairs[2]=new Pair<View,String>(phoneNumber,"loginbutton_trans");
        pairs[3]=new Pair<View,String>(already,"newsuer_trans");
        pairs[4]=new Pair<View,String>(name,"password_trans");
        pairs[5]=new Pair<View,String>(signUp,"loginbutton_trans");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this,pairs);
        startActivity(i,options.toBundle());
    }
}