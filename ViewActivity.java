package com.example.happy.myapplication;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
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

public class ViewActivity extends AppCompatActivity {
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    List<String> allNames;
    TextView ed1, ed2, ed3, ed4, ed5;
    SharedPreferences prefs;
    String id = "";
    Button b1;
    private GoogleApiClient client;

    JSONArray jsonResponse ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ed1 = (TextView) findViewById(R.id.ed1);
        ed2 = (TextView) findViewById(R.id.ed2);
        ed3 = (TextView) findViewById(R.id.ed3);
        ed4 = (TextView) findViewById(R.id.ed4);
        ed5 = (TextView) findViewById(R.id.ed5);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        prefs = getSharedPreferences("userid", MODE_PRIVATE);
        id = prefs.getString("userid", null);
        //  new profileview.ExampleAsync().execute();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ViewActivity page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
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
                    String add = actor.getString("address");
                    String Area = actor.getString("Area");
                    String City =actor.getString("City");
                    String phone = actor.getString("phone");

                    allNames.add(name);
                    allNames.add(add);

                    allNames.add(Area);
                    allNames.add(City);
                    allNames.add(phone);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ed1.setText(allNames.get(0).toString());
            ed2.setText(allNames.get(1).toString());
            ed3.setText(allNames.get(2).toString());
            ed4.setText(allNames.get(3).toString());
            ed5.setText(allNames.get(4).toString());


            Toast.makeText(ViewActivity.this, allNames.get(0).toString(), Toast.LENGTH_SHORT).show();
        }
    }



}

