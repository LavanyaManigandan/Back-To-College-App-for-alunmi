package com.example.happy.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 02-03-2017.
 */

public class Empprofileview extends AppCompatActivity {
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    List<String> allNames;
    TextView ed1, ed2, ed3, ed4, ed5, ed6;
    SharedPreferences prefs;
    String id = "";
Button b1;


    JSONArray jsonResponse ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empprofileview);
        ed1 = (TextView) findViewById(R.id.e1);
        ed2 = (TextView) findViewById(R.id.e2);
        ed3 = (TextView) findViewById(R.id.e3);
        ed4 = (TextView) findViewById(R.id.e4);
        ed5 = (TextView) findViewById(R.id.e5);
        ed6 = (TextView) findViewById(R.id.e6);
//        ed7 = (TextView) findViewById(R.id.e7);
//        ed8 = (TextView) findViewById(R.id.e8);
//        ed9 = (TextView) findViewById(R.id.e9);
//        ed10 = (TextView) findViewById(R.id.e10);

        //  System.out.print(userid);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        prefs = getSharedPreferences("userid", MODE_PRIVATE);
        id = prefs.getString("userid", null);
        // ed1.setText(id);
//        ed2.setText(id);
//        ed3.setText(id);
//        ed4.setText(id);
//        ed5.setText(id);
//        ed6.setText(id);
//        ed7.setText(id);
//        ed8.setText(id);
//        ed9.setText(id);
//        ed10.setText(id);
        new ExampleAsync().execute();

    }

    public class ExampleAsync extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... params) {

            String returnedstring = "";
            b1=(Button)findViewById(R.id.b1);


            try {
                nameValuePairs = new ArrayList<NameValuePair>();
                httpclient = new DefaultHttpClient();
                response = new BasicResponseHandler();
               nameValuePairs.add(new BasicNameValuePair("userid", id));
                httppost = new HttpPost("http://10.0.2.2/Eventmanagerprofile.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                returnedstring = httpclient.execute(httppost, response);

                allNames = new ArrayList<String>();

                System.out.println(returnedstring);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedstring;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s);
           try {
                jsonResponse = new JSONArray(s);

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

            ed1.setText(allNames.get(0).toString());
            ed2.setText(allNames.get(1).toString());
            ed3.setText(allNames.get(2).toString());
            ed4.setText(allNames.get(3).toString());
            ed5.setText(allNames.get(4).toString());
            ed6.setText(allNames.get(5).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(Empprofileview.this,Empedit.class);
                    startActivity(i);
                }
            });
//            ed7.setText(allNames.get(6).toString());
//            ed8.setText(allNames.get(7).toString());
//            ed9.setText(allNames.get(8).toString());
//            ed10.setText(allNames.get(9).toString());

           // Toast.makeText(Empprofileview.this, allNames.get(0).toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
