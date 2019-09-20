package com.tbg.www.thebutterflycorner;

import android.content.Intent;
import android.os.Bundle;
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
    FloatingActionButton Play;

    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Bhenchod", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        CardView Butterfly = findViewById(R.id.butterflydb);
        Play = findViewById(R.id.playbtn);
       // CardView SignUp = findViewById(R.id.signUp);
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
       // SignUp.setAnimation(fromBottom);
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
        /*SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSignInIntent();
            }
        });*/
    }
    /*public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        //new AuthUI.IdpConfig.FacebookBuilder().build(),
        // new AuthUI.IdpConfig.TwitterBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }*/
}
