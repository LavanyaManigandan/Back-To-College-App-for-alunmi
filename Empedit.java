package com.example.happy.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Empedit extends AppCompatActivity {
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    List<String> allNames;
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
    String name, dept, phone, email, userid, password;
    SharedPreferences prefs;
    String id = "";
    String returnedstring;
    Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empedit);
              /**
         * ATTENTION: This was auto-generated to implement the App Indexing API.
         * See https://g.co/AppIndexing/AndroidStudio for more information.
         */

            prefs = getSharedPreferences("userid", MODE_PRIVATE);
            id = prefs.getString("userid", null);
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ed1 = (EditText) findViewById(R.id.e1);
            ed2 = (EditText) findViewById(R.id.e2);
            ed3 = (EditText) findViewById(R.id.e3);
            ed4 = (EditText) findViewById(R.id.e4);
            ed5 = (EditText) findViewById(R.id.e5);
            ed6 = (EditText) findViewById(R.id.e6);


            update = (Button) findViewById(R.id.update);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = ed1.getText().toString();
                    dept = ed2.getText().toString();
                    phone = ed3.getText().toString();
                    email = ed4.getText().toString();

                    userid = ed5.getText().toString();
                    password = ed6.getText().toString();
              //    new Empedit.UpdateInformation().execute();
                }
            });


            new Empedit.ExampleAsync().execute();

        }


        public class ExampleAsync extends AsyncTask<Void, String, String> {
            @Override
            protected String doInBackground(Void... params) {

                String returnedstring = null;

                try {
                    nameValuePairs = new ArrayList<NameValuePair>();
                    httpclient = new DefaultHttpClient();
                    response = new BasicResponseHandler();
                    nameValuePairs.add(new BasicNameValuePair("userid", id));
                    httppost = new HttpPost("http://10.0.2.2/Eventmanagerprofile.php");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                    returnedstring = httpclient.execute(httppost, response);

                    allNames = new ArrayList<String>();


                    JSONArray jsonResponse = new JSONArray(returnedstring);
                    for (int k = 0; k < jsonResponse.length(); k++) {
                        JSONObject actor = jsonResponse.getJSONObject(k);
                        String name = actor.getString("name");
                        String dept = actor.getString("dept");

                        String phone = actor.getString("phone");
                        String email = actor.getString("email");

                        String userid = actor.getString("user_id");
                        String pass = actor.getString("password");
                        allNames.add(name);
                        allNames.add(dept);
                        allNames.add(phone);
                        allNames.add(email);


                        allNames.add(userid);
                        allNames.add(pass);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                updateData();
            }


            public void updateData() {
                ed1.setText(allNames.get(0).toString());
                ed2.setText(allNames.get(1).toString());
                ed3.setText(allNames.get(2).toString());
                ed4.setText(allNames.get(3).toString());
                ed5.setText(allNames.get(4).toString());
                ed6.setText(allNames.get(5).toString());


            }

            public class UpdateInformation extends AsyncTask<Void, String, String> {

                @Override
                protected String doInBackground(Void... params) {
                    onClickData();
                    return null;
                }


                public void onClickData() {

                    try {
                        SharedPreferences prefs = getSharedPreferences("userid", MODE_PRIVATE);
                        String mUserId = prefs.getString("userid", null);
                        nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("name", name));
                        nameValuePairs.add(new BasicNameValuePair("dept", dept));

                        nameValuePairs.add(new BasicNameValuePair("phone", phone));
                        nameValuePairs.add(new BasicNameValuePair("email", email));
                        nameValuePairs.add(new BasicNameValuePair("userid", userid));
                        nameValuePairs.add(new BasicNameValuePair("pass", password));
                        nameValuePairs.add(new BasicNameValuePair("status", "1"));
                        nameValuePairs.add(new BasicNameValuePair("id", mUserId));

                        httpclient = new DefaultHttpClient();
                        response = new BasicResponseHandler();
                        httppost = new HttpPost("http://10.0.2.2/Empprofileview.php");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        returnedstring = httpclient.execute(httppost, response);

                        System.out.println("Prints:" + returnedstring);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    startActivity(getIntent());
                    finish();

                }
            }
        }
    }
