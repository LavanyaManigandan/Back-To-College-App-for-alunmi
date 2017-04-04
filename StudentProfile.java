package com.example.happy.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;

public class StudentProfile extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    String name, father, dob, add, phone, dept, batch, email, userid, password;
    String returnedstring;
    String studentStatus ="0";
    CharSequence t1="invalid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentprofile);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        SharedPreferences.Editor prefs;// = getSharedPreferences("userid", MODE_PRIVATE).edit();

        prefs = getSharedPreferences("myprefs", MODE_PRIVATE).edit();
        prefs.putString("userid", "xyz");
        prefs.commit();
        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        e3 = (EditText) findViewById(R.id.e3);
        e4 = (EditText) findViewById(R.id.e4);
        e5 = (EditText) findViewById(R.id.e5);
        e6 = (EditText) findViewById(R.id.e6);
        e7 = (EditText) findViewById(R.id.e7);
        e8 = (EditText) findViewById(R.id.e8);
        e9 = (EditText) findViewById(R.id.e9);
        e10 = (EditText) findViewById(R.id.e10);
        nameValuePairs = new ArrayList<NameValuePair>();
        httpclient = new DefaultHttpClient();
        response = new BasicResponseHandler();
    }

    public void Reload(View v) {


        name = e1.getText().toString();
        father = e2.getText().toString();
        dob = e3.getText().toString();
        add = e4.getText().toString();
        phone = e5.getText().toString();
        email = e6.getText().toString();
        dept = e7.getText().toString();
        batch = e8.getText().toString();
        userid = e9.getText().toString();
        password = e10.getText().toString();

        if (name.equals("") ||  email.equals("") || dept.equals("") || batch.equals("") || userid.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
        } else {
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("father", father));
            nameValuePairs.add(new BasicNameValuePair("dob", dob));
            nameValuePairs.add(new BasicNameValuePair("add", add));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("dept", dept));
            nameValuePairs.add(new BasicNameValuePair("batch", batch));
            nameValuePairs.add(new BasicNameValuePair("userid", userid));
            nameValuePairs.add(new BasicNameValuePair("pass", password));
            nameValuePairs.add(new BasicNameValuePair("status", studentStatus));


            httppost = new HttpPost("http://10.0.2.2/stu.php");
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                returnedstring = httpclient.execute(httppost, response);
                Intent i = new Intent(StudentProfile.this, Studentlogin.class);
                startActivity(i);
                String[] split = returnedstring.split(",");
                String s1 = split[0];
                String s2 = split[1];
                System.out.print("res" + returnedstring);
                Toast.makeText(getApplicationContext(), returnedstring, Toast.LENGTH_LONG).show();
                if(s1.equals("true2"))
                {
                    Intent s = new Intent(StudentProfile.this, forgot.class);
                    startActivity(s);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "" , Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }

            /*Intent i = new Intent(register.this, CombinedActivity.class);
            startActivity(i);*/
    }

}
