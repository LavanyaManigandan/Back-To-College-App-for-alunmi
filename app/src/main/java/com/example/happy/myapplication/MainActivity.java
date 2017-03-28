package com.example.happy.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView acc,evt;
    Spinner s;
    Button sub;
    final String[] ch = {"Create an new account - SignUp?", "student/alumni", "eventmanager"};
    String spin;
    EditText ed1, ed2;
    CharSequence text = "Valid User";
    CharSequence text1 = "Invalid User";
    ArrayAdapter<String> a;
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    String username,password;
    String studentStatus ="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s = (Spinner) findViewById(R.id.spinner);
        acc = (TextView) findViewById(R.id.textView);
        evt = (TextView) findViewById(R.id.textView2);
        sub = (Button) findViewById(R.id.button);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        nameValuePairs = new ArrayList<NameValuePair>();
        httpclient = new DefaultHttpClient();
        response = new BasicResponseHandler();
        a = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, ch);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        s.setAdapter(a);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                if (item.equals("student/alumni")) {
                    Intent i = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(i);
                    studentStatus ="0";
                } else {
                    Intent n = new Intent(MainActivity.this, EveMag.class);
                    startActivity(n);
                    studentStatus="1";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // login(v);
                Intent i=new Intent(MainActivity.this,BackToCollege.class);
                startActivity(i);
            }
        });

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, forgot.class);
                startActivity(i);
            }
        });

      evt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, evtlogin.class);
                startActivity(a);
            }
       });


    }


    public void login(View v) {



        username = ed1.getText().toString();
        password = ed2.getText().toString();

        if ( username.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
        } else {
            nameValuePairs.add(new BasicNameValuePair("userid", username));
            nameValuePairs.add(new BasicNameValuePair("pass", password));
            nameValuePairs.add(new BasicNameValuePair("status", studentStatus));


            httppost = new HttpPost("http://10.0.2.2/stulogin.php");
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
               String returnedstring = httpclient.execute(httppost, response);

             //   Toast.makeText(getApplicationContext(), returnedstring, Toast.LENGTH_LONG).show();
                if(returnedstring.equals("true"))
                {
                    Intent s = new Intent(MainActivity.this, stuprofile_fragment.class);
                    startActivity(s);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Not Registered yet Please Register" , Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }

            /*Intent i = new Intent(register.this, CombinedActivity.class);
            startActivity(i);*/
    }

}
