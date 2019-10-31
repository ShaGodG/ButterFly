package com.tbg.www.thebutterflycorner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public abstract class HelpActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_help);

        addFragment(new Step.Builder()
        .setTitle("How To Play")
        .setSummary("Click On capture button to capture the image of butterfly")
        .setContent("If the image is blur or not in focus, kindly reset it using the reset button")
        .setBackgroundColor(Color.parseColor("#00bcd4"))
        .setDrawable(R.drawable.screen_1).build());

        addFragment(new Step.Builder()
                .setTitle("How To Play")
                .setSummary("Capture Total of 5 images")
                .setContent("Compare the captured image with the desired species of butterfly, repeat the process 5 times")
                .setBackgroundColor(Color.parseColor("#8bc34a"))
                .setDrawable(R.drawable.screen_2).build());

        addFragment(new Step.Builder()
                .setTitle("How To Play")
                .setSummary("Generate Coupon Code")
                .setContent("If significant matches are correct, you might get a coupon code which can be availed at our outlets. for any further queries, contact admin")
                .setBackgroundColor(Color.parseColor("#ffc107"))
                .setDrawable(R.drawable.screen_3).build());
    }
}
