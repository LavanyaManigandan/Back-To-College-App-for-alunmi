package com.example.happy.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EveMag extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6;
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    String name, phone, dept, email, userid, password;
    String returnedstring;
    String eventStatus = "1";
    CharSequence t2="invalid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eve_mag_activity);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        e3 = (EditText) findViewById(R.id.e3);
        e4 = (EditText) findViewById(R.id.e4);
        e5 = (EditText) findViewById(R.id.e5);
        e6 = (EditText) findViewById(R.id.e6);

        nameValuePairs = new ArrayList<NameValuePair>();
        httpclient = new DefaultHttpClient();
        response = new BasicResponseHandler();
    }

    public void Reload(View v) {


        name = e1.getText().toString();
        dept = e2.getText().toString();
        phone = e3.getText().toString();
        email = e4.getText().toString();

        userid = e5.getText().toString();
        password = e6.getText().toString();

        if (name.equals("") || phone.equals("") || password.equals("") || email.equals("") || dept.equals("") || userid.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
        } else {
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("dept", dept));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("userid", userid));
            nameValuePairs.add(new BasicNameValuePair("pass", password));
            nameValuePairs.add(new BasicNameValuePair("status", eventStatus));


            httppost = new HttpPost("http://10.0.2.2/stu.php");
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                returnedstring = httpclient.execute(httppost, response);
                Intent i = new Intent(EveMag.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), returnedstring, Toast.LENGTH_LONG).show();
                if (returnedstring.equals("false")) {
                    Intent s = new Intent(EveMag.this, MainActivity.class);
                    startActivity(s);

                } else {
                    Toast.makeText(getApplicationContext(), t2, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
}
