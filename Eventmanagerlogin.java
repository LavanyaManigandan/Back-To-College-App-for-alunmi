package com.example.happy.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * Created by Hp on 08-02-2017.
 */

public class Eventmanagerlogin extends AppCompatActivity {
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    String returnedstring;
    EditText user = null;
    EditText pass = null;
    TextView back;
    String username = "";
    String password = null;
    Button sub;
    String studentStatus = "1";

    public static Context contextOfApplication;

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evtlogin);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        contextOfApplication = getApplicationContext();
        user = (EditText) findViewById(R.id.e1);
        pass = (EditText) findViewById(R.id.e2);
        sub = (Button) findViewById(R.id.b5);
       // post = (Button) findViewById(R.id.post);
        back = (TextView) findViewById(R.id.tv7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Eventmanagerlogin.this,Studentlogin.class);
                startActivity(i);
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = user.getText().toString();

                password = pass.getText().toString();
                // Toast.makeText(Eventmanagerlogin.this, username + password, Toast.LENGTH_SHORT).show();

                new LoginNetwork(username, password).execute();
//               Intent i=new Intent(Eventmanagerlogin.this,BackToCollege.class);
//                startActivity(i);
            }
        });
    }

    public void login(View v) {


        //   new LoginNetwork(username, password).execute();


//        if ( username.equals("") || password.equals("")) {
//            Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
//        } else {

//        }

            /*Intent i = new Intent(register.this, CombinedActivity.class);
            startActivity(i);*/
    }

    public class LoginNetwork extends AsyncTask<Void, Void, String> {

        private String username;
        private String password;

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
            String[] split=s.split(",");
            if(split[0].trim().equals("true")){
                SharedPreferences.Editor prefs=getSharedPreferences("userid",MODE_PRIVATE).edit();
                prefs.putString("userid",split[1]);
                prefs.commit();

            //if (s.equals("true")) {
                Intent intent = new Intent(Eventmanagerlogin.this, Empprofileview.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Not Registered yet Please Register", Toast.LENGTH_LONG).show();
            }
        }
    }
}