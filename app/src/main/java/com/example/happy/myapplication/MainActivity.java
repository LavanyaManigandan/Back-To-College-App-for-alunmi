package com.example.happy.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView acc;
    Spinner s;
    Button sub;
    final String[] ch = {"student/alumni", "eventmanager"};
    String spin;
    ArrayAdapter<String> a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s = (Spinner) findViewById(R.id.spinner);
        acc = (TextView) findViewById(R.id.textView2);
        sub = (Button) findViewById(R.id.button);
        a = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, ch);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(a);
        try {
            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(MainActivity.this, DetailActivity.class);
                    startActivity(i);

                }
            });
            acc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent n = new Intent(MainActivity.this, EveMag.class);
                    startActivity(n);
                }
            });

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
