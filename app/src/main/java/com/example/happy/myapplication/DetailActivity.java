package com.example.happy.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailActivity extends AppCompatActivity {
    Button pro;
    Button post;
    Button noti;
    Button detil;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        pro=(Button)findViewById(R.id.button4);
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DetailActivity.this, AccountActivity.class);
                startActivity(i);
                           }
        });
    }
}
