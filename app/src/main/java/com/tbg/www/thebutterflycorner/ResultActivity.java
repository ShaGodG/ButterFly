package com.tbg.www.thebutterflycorner;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    Button showCoupon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setTitle("Result");
        showCoupon = findViewById(R.id.btnCoupon);


        ArrayList<ModelResult> listImages = (ArrayList<ModelResult>)getIntent().getSerializableExtra("list");

        RecyclerView recyclerView = findViewById(R.id.rvImages);
        ResultAdapter resultAdapter = new ResultAdapter(this,listImages);
        resultAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView,new RecyclerView.State(),5);
        recyclerView.setAdapter(resultAdapter);

        showCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ResultActivity.this, CouponActivity.class);
                startActivity(i);
            }
        });
    }
}
