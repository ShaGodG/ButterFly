package com.tbg.www.thebutterflycorner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CouponActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        getSupportActionBar().setTitle("Coupons");
    }
}
