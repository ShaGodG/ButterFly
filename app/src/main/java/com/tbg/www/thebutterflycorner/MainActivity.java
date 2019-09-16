package com.tbg.www.thebutterflycorner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class MainActivity extends AppCompatActivity {
    Animation fromBottom;
    TextView textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        CardView Butterfly = findViewById(R.id.butterflydb);
        CardView Play = findViewById(R.id.playbtn);
        CardView SignUp = findViewById(R.id.signUp);
        CardView menu = findViewById(R.id.menuBtn);
        textView1=findViewById(R.id.text);
        textView2=findViewById(R.id.textViewGame);
        CardView couponBtn =  findViewById(R.id.couponbtn);
        CardView contactUs = findViewById(R.id.contactBtn);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        textView1.animate().translationY(50).setDuration(800).setStartDelay(300);
        textView2.animate().translationY(60).setDuration(800).setStartDelay(300);
        Butterfly.setAnimation(fromBottom);
        Play.setAnimation(fromBottom);
        SignUp.setAnimation(fromBottom);
        menu.setAnimation(fromBottom);
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

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MenuActivity.class);
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
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });



    }
}
