package com.tbg.www.thebutterflycorner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setTitle("Result");
        

        ArrayList<ModelResult> listImages = (ArrayList<ModelResult>)getIntent().getSerializableExtra("list");

        RecyclerView recyclerView = findViewById(R.id.rvImages);
        ResultAdapter resultAdapter = new ResultAdapter(this,listImages);
        resultAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(resultAdapter);
    }
}
