package com.example.happy.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class Studentlogin extends AppCompatActivity {


    TextView acc, evt;
    Spinner s;
    Button sub;
    final String[] ch = {"Create an new studentprofile - SignUp?", "student/alumni", "eventmanager"};
    String spin;
    EditText user, pass;
    CharSequence text = "Valid User";
    CharSequence text1 = "Invalid User";
    ArrayAdapter<String> a;
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    String username, password;
    String studentStatus = "0";
    public static Context contextOfApplication;

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentlogin);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        contextOfApplication = getApplicationContext();
        s = (Spinner) findViewById(R.id.spinner);
        acc = (TextView) findViewById(R.id.textView);
        evt = (TextView) findViewById(R.id.textView2);
        sub = (Button) findViewById(R.id.button);
        user = (EditText) findViewById(R.id.ed1);
        pass = (EditText) findViewById(R.id.ed2);

        a = new ArrayAdapter<String>(Studentlogin.this, android.R.layout.simple_spinner_item, ch);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        s.setAdapter(a);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(Studentlogin.this, item, Toast.LENGTH_SHORT).show();
                if (position == 1) {
                    Intent i = new Intent(Studentlogin.this, StudentProfile.class);
                    startActivity(i);
                    studentStatus = "0";
                } else if (position == 2) {
                    Intent n = new Intent(Studentlogin.this, Eventmanagerprofile.class);
                    startActivity(n);
                    studentStatus = "1";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            /*    Intent i=new Intent(Studentlogin.this,BackToCollege.class);
                startActivity(i);*/
            }
        });

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Studentlogin.this, forgot.class);
                startActivity(i);
            }
        });
        evt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(Studentlogin.this, Eventmanagerlogin.class);
                startActivity(i);
            }
        });


    }

    public void login(View v) {


        username = user.getText().toString();
        password = pass.getText().toString();

        new LoginNetwork(username, password).execute();

//        if ( username.equals("") || password.equals("")) {
//            Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
//        } else {

//        }

            /*Intent i = new Intent(register.this, CombinedActivity.class);
            startActivity(i);*/
    }

    public class LoginNetwork extends AsyncTask<Void, Void, String> {

        public String username;
        public String password;

        public LoginNetwork(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            String returnedstring = null;
            nameValuePairs = new ArrayList<NameValuePair>();
            httpclient = new DefaultHttpClient();
            response = new BasicResponseHandler();
            nameValuePairs.add(new BasicNameValuePair("userid", username));
            nameValuePairs.add(new BasicNameValuePair("pass", password));
            nameValuePairs.add(new BasicNameValuePair("status", studentStatus));


            httppost = new HttpPost("http://10.0.2.2/stulogin.php");
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                returnedstring = httpclient.execute(httppost, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedstring;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            String[] split = s.split(",");
            if (split[0].trim().equals("true")) {
                SharedPreferences.Editor prefs = getSharedPreferences("userid", MODE_PRIVATE).edit();
                prefs.putString("userid", split[1]);
                prefs.commit();
                Intent i = new Intent(Studentlogin.this, profileview.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Not Registered yet Please Register", Toast.LENGTH_LONG).show();
            }
        }
    }

}
