package com.tbg.www.thebutterflycorner;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    Animation fromBottom;
    TextView textView1,textView2;

    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.i("ShaGodG", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        CardView Butterfly = findViewById(R.id.butterflydb);
        CardView Play = findViewById(R.id.playbtn);
        textView1=findViewById(R.id.text);
        textView2=findViewById(R.id.textViewGame);

        CardView couponBtn =  findViewById(R.id.couponbtn);
        CardView contactUs = findViewById(R.id.contactBtn);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        //textView1.animate().translationY(50).setDuration(800).setStartDelay(300);
        //textView2.animate().translationY(50).setDuration(800).setStartDelay(300);
        Butterfly.setAnimation(fromBottom);
        Play.setAnimation(fromBottom);
        couponBtn.setAnimation(fromBottom);
        contactUs.setAnimation(fromBottom);


        Butterfly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ButterfluyActivity.class);
                startActivity(i);
            }
        });
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,GameActivity.class);
                startActivity(i);

            }
        });


        couponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CouponActivity.class);
                startActivity(i);

            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ContactActivity.class);
                startActivity(i);
            }
        });

    }
}
