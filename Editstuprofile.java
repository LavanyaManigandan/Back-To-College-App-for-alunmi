package com.example.happy.myapplication;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.happy.myapplication.R.id.b1;

public class Editstuprofile extends AppCompatActivity {
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    List<String> allNames;
    EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7, ed8, ed9, ed10,ed11,ed12;
    String name, father, dob, add, Area, City, phone, email, batch, dept, userid, password;
    SharedPreferences prefs;
    String id = "";
    String returnedstring;
    Button update;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstuprofile);
        prefs = getSharedPreferences("userid", MODE_PRIVATE);
        id = prefs.getString("userid", null);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ed1.getText().toString();
                father=ed2.getText().toString();
                dob=ed3.getText().toString();
                add =ed4.getText().toString();
                Area =ed5.getText().toString();
                City =ed6.getText().toString();
                phone =ed7.getText().toString();
                email =ed8.getText().toString();
                batch =ed9.getText().toString();
                dept =ed10.getText().toString();
                userid =ed11.getText().toString();
                password=ed12.getText().toString();
                new UpdateInformation().execute();
            }
        });
        ed1 = (EditText) findViewById(R.id.e1);
        ed2 = (EditText) findViewById(R.id.e2);
        ed3 = (EditText) findViewById(R.id.e3);
        ed4 = (EditText) findViewById(R.id.e4);
        ed5 = (EditText) findViewById(R.id.e5);
        ed6 = (EditText) findViewById(R.id.e6);
        ed7 = (EditText) findViewById(R.id.e7);
        ed8 = (EditText) findViewById(R.id.e8);
        ed9 = (EditText) findViewById(R.id.e9);
        ed10 = (EditText) findViewById(R.id.e10);
        ed11 = (EditText) findViewById(R.id.e11);
        ed11 = (EditText) findViewById(R.id.e12);


        new ExampleAsync().execute();

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
                httppost = new HttpPost("http://10.0.2.2/profile.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                returnedstring = httpclient.execute(httppost, response);

                allNames = new ArrayList<String>();


                JSONArray jsonResponse = new JSONArray(returnedstring);
                for (int k = 0; k < jsonResponse.length(); k++) {
                    JSONObject actor = jsonResponse.getJSONObject(k);
                    String name = actor.getString("name");
                    String fathername = actor.getString("fathername");
                    String dob = actor.getString("dob");
                    String add = actor.getString("address");
                    String Area = actor.getString("Area");
                    String City = actor.getString("City");
                    String phone = actor.getString("phone");
                    String email = actor.getString("email");
                    String dept = actor.getString("dept");
                    String batch = actor.getString("batch");
                    String userid = actor.getString("user_id");
                    String pass = actor.getString("password");
                    allNames.add(name);
                    allNames.add(fathername);
                    allNames.add(dob);
                    allNames.add(add);
                    allNames.add(Area);
                    allNames.add(City);
                    allNames.add(phone);
                    allNames.add(email);
                    allNames.add(dept);
                    allNames.add(batch);
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
    }
    public void updateData() {
        ed1.setText(allNames.get(0).toString());
        ed2.setText(allNames.get(1).toString());
        ed3.setText(allNames.get(2).toString());
        ed4.setText(allNames.get(3).toString());
        ed5.setText(allNames.get(4).toString());
        ed6.setText(allNames.get(5).toString());
        ed7.setText(allNames.get(6).toString());
        ed8.setText(allNames.get(7).toString());
        ed9.setText(allNames.get(8).toString());
        ed10.setText(allNames.get(9).toString());
        ed11.setText(allNames.get(10).toString());
        ed12.setText(allNames.get(11).toString());

    }

    public class UpdateInformation extends AsyncTask<Void,String,String>{

        @Override
        protected String doInBackground(Void... params) {
            onClickData();
            return null;
        }


        public void onClickData() {

            try {
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("father", father));
                nameValuePairs.add(new BasicNameValuePair("dob", dob));
                nameValuePairs.add(new BasicNameValuePair("add", add));
                nameValuePairs.add(new BasicNameValuePair("Area", Area));
                nameValuePairs.add(new BasicNameValuePair("City", City));
                nameValuePairs.add(new BasicNameValuePair("phone", phone));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("dept", dept));
                nameValuePairs.add(new BasicNameValuePair("batch", batch));
                nameValuePairs.add(new BasicNameValuePair("userid", userid));
                nameValuePairs.add(new BasicNameValuePair("pass", password));
                nameValuePairs.add(new BasicNameValuePair("userid", id));
                httppost = new HttpPost("http://10.0.2.2/stu.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                returnedstring = httpclient.execute(httppost, response);



            }
            catch (Exception e) {
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

