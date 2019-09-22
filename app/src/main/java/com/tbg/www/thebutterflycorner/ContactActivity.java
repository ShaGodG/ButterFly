package com.tbg.www.thebutterflycorner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setTitle("Contact Us");

        FloatingActionButton sharebt = findViewById(R.id.sharebtn);
        final Context contextshare = this;
        sharebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                String sharebody = "https://play.google.com/store/apps/details?id=com.tbg.www.thebutterflycorner";
                String sharesubject = "Send Via";
                shareintent.putExtra(Intent.EXTRA_SUBJECT, sharesubject);
                shareintent.putExtra(Intent.EXTRA_TEXT, sharebody);
                startActivity(Intent.createChooser(shareintent , "Share Using"));
            }
        });

        CircleImageView fb_btn= findViewById(R.id.fblogo);
        fb_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/TheButterflyCornerPattaya/?ref=search&__tn__=%2Cd%2CP-R&eid=ARAdcKJRU09shGuenHkXNRM6p9EqbkqQzKg-FlL7tNK-zyIlqGdGn-ZvS-Lnexg8qOX_o0X5VJMC0Dur"));
                startActivity(intent);
            }
        });

        CircleImageView tweet_btn = findViewById(R.id.twitterlogo);
        tweet_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.twitter.com/search?q=TheButterflyCornerPattaya&src=typed_query"));
                startActivity(intent);
            }
        });

        CircleImageView ig_btn = findViewById(R.id.iglogo);
        ig_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/thebutterflycornerpattaya/"));
                startActivity(intent);
            }
        });
    }
}
