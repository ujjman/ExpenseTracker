package com.ujjman.money.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView anim;
    private static final int NUM_PAGES=2;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    Animation animation;
    FirebaseAuth mAuth;
    int ch=0;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fullScreencall();
        checkDatabase();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        anim=findViewById(R.id.animation);
        viewPager=findViewById(R.id.pager);
        pagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        animation= AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.startAnimation(animation);
        anim.animate().translationY(1400).setDuration(900).setStartDelay(4000);

    }
    public void checkDatabase()
    {
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance("https://expense-tracker-a6ae5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String s = dataSnapshot.child(mAuth.getCurrentUser().getUid()).getValue(User.class).phoneNumber;
                }
                catch (Exception e)
                {
                    ch=1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseUsers.addValueEventListener(valueEventListener);
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
    public void onClickSkip(View view)
    {
        Intent i=new Intent(this,Login.class);
        Intent j=new Intent(this,paymentcreate.class);
        if(currentUser==null || ch==1)
        {
            ch=0;
            startActivity(i);
            finish();
        }
        else {
            startActivity(j);
            finish();
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    StartFragment1 tab1=new StartFragment1();
                    return tab1;
                    case 1:
                    StartFragment2 tab2=new StartFragment2();
                    return tab2;

            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}