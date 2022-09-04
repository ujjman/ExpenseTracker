package com.ujjman.money.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class paymentcreate extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageButton allPaymentsButton,myPaymentsButton,transactionHistoryButton,createNewEntryButton;
    private ImageView logoButton;
    private LinearLayout ll,ll2,ll5,ll6;
    private TextView pendingAllPaymentsTextview;
    private TextView pendingMyPaymentsTextview;
    private TextView transactionDoneTextview;
    private String myPhoneNumber;
    long pendingAllPayments=0;
    long pendingMyPayments=0;
    long transactionsDone=0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentcreate);
        fullScreencall();
        allPaymentsButton=findViewById(R.id.all_payments_button);
        myPaymentsButton=findViewById(R.id.my_payments_button);
        transactionHistoryButton=findViewById(R.id.transaction_history_button);
        createNewEntryButton=findViewById(R.id.create_new_entry_button);
        pendingAllPaymentsTextview=findViewById(R.id.textView_pending_allpayments);
        pendingMyPaymentsTextview=findViewById(R.id.textView_pending_mypayments);
        transactionDoneTextview=findViewById(R.id.transactions_history_textview);
        logoButton=findViewById(R.id.logo_button);
        ll=findViewById(R.id.linearLayout3);
        ll2=findViewById(R.id.linearLayout2);
        ll5=findViewById(R.id.linearLayout5);
        ll6=findViewById(R.id.linearLayout6);
        mAuth = FirebaseAuth.getInstance();
        setNumberOfTransactions();
        allPaymentsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                    ll.startAnimation(up);
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim2);
                    ll.startAnimation(up);
                    Handler mHandler = new Handler();
                    Runnable mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),AllPayments.class);
                            startActivity(intent);
                        }
                    };

                    // its trigger runnable after 4000 millisecond.
                    mHandler.postDelayed(mRunnable,150);
                }


                return true;
            }
        });
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                    ll.startAnimation(up);
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim2);
                    ll.startAnimation(up);
                    Handler mHandler = new Handler();
                    Runnable mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),AllPayments.class);
                            startActivity(intent);
                        }
                    };

                    // its trigger runnable after 4000 millisecond.
                    mHandler.postDelayed(mRunnable,200);
                }


                return true;
            }
        });

        myPaymentsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                    ll2.startAnimation(up);
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim2);
                    ll2.startAnimation(up);
                    Handler mHandler = new Handler();
                    Runnable mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),MyPayments.class);
                            startActivity(intent);
                        }
                    };

                    // its trigger runnable after 4000 millisecond.
                    mHandler.postDelayed(mRunnable,150);
                }
                return true;
            }
        });
        ll2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                    ll2.startAnimation(up);
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim2);
                    ll2.startAnimation(up);
                    Handler mHandler = new Handler();
                    Runnable mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),MyPayments.class);
                            startActivity(intent);
                        }
                    };

                    // its trigger runnable after 4000 millisecond.
                    mHandler.postDelayed(mRunnable,150);
                }
                return true;
            }
        });

        transactionHistoryButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                    ll5.startAnimation(up);
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim2);
                    ll5.startAnimation(up);
                    Handler mHandler = new Handler();
                    Runnable mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),TransactionHistory.class);
                            startActivity(intent);
                        }
                    };

                    // its trigger runnable after 4000 millisecond.
                    mHandler.postDelayed(mRunnable,150);
                }
                return true;
            }
        });
        ll5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                    ll5.startAnimation(up);
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim2);
                    ll5.startAnimation(up);
                    Handler mHandler = new Handler();
                    Runnable mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),MyPayments.class);
                            startActivity(intent);
                        }
                    };
                    // its trigger runnable after 4000 millisecond.
                    mHandler.postDelayed(mRunnable,150);
                }
                return true;
            }
        });

        createNewEntryButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                    ll6.startAnimation(up);
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim2);
                    ll6.startAnimation(up);
                    Handler mHandler = new Handler();
                    Runnable mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),NewPayment.class);
                            startActivity(intent);
                        }
                    };

                    // its trigger runnable after 4000 millisecond.
                    mHandler.postDelayed(mRunnable,150);
                }
                return true;
            }
        });
        ll6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                    ll6.startAnimation(up);
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    Animation up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim2);
                    ll6.startAnimation(up);
                    Handler mHandler = new Handler();
                    Runnable mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),NewPayment.class);
                            startActivity(intent);
                        }
                    };

                    // its trigger runnable after 4000 millisecond.
                    mHandler.postDelayed(mRunnable,150);
                }
                return true;
            }
        });
    }

    public void setNumberOfTransactions()
    {
        DatabaseReference databaseUsers= FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myPhoneNumber=dataSnapshot.child(mAuth.getCurrentUser().getUid()).getValue(User.class).phoneNumber;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseUsers.addValueEventListener(valueEventListener);
        DatabaseReference databaseAllPayments= FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("publicPayments");
        ValueEventListener valueEventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    pendingAllPayments=dataSnapshot.getChildrenCount();
                    PaymentEntry p=ds.getValue(PaymentEntry.class);
                    if(p.getSenderPhone().equals(myPhoneNumber) || p.getReceiverPhone().equals(myPhoneNumber))
                    {
                        pendingMyPayments++;
                    }
                }
                pendingMyPaymentsTextview.setText(pendingMyPayments+" Transactions Pending");
                pendingAllPaymentsTextview.setText(pendingAllPayments+" Transactions Pending");
                pendingMyPayments=0;
                pendingAllPayments=0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseAllPayments.addValueEventListener(valueEventListener2);
        DatabaseReference databaseTransactionHistory= FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("transactionHistory");
        ValueEventListener valueEventListener3 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionsDone=dataSnapshot.getChildrenCount();
                transactionDoneTextview.setText(transactionsDone+" Transactions Done");
                transactionsDone=0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseTransactionHistory.addValueEventListener(valueEventListener3);


    }


    public void onClickLogoButton(View view)
    {
        PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), logoButton);
        dropDownMenu.getMenu().add("Log Out");
        dropDownMenu.getMenu().add("Signed in as : "+mAuth.getCurrentUser().getDisplayName());
        dropDownMenu.getMenuInflater().inflate(R.menu.drop_down_menu, dropDownMenu.getMenu());
        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals("Log Out"))
                {
                    mAuth.signOut();
                    Toast.makeText(getApplicationContext(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                    finish();
                }
                if(item.getTitle().toString().startsWith("Signed in as :"))
                {
                    String email="Signed in as : "+mAuth.getCurrentUser().getEmail();
                    Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        dropDownMenu.show();
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
}