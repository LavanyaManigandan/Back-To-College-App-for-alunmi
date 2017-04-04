package com.example.happy.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pools;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.R.attr.id;
import static com.example.happy.myapplication.R.string.userid;

/**
 * Created by Hp on 01-03-2017.
 */

public class profileview extends AppCompatActivity {
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    List<String> allNames;
    TextView ed1, ed2, ed3, ed4, ed5, ed6, ed7, ed8, ed9, ed10;
    SharedPreferences prefs;
    String id = "";
    Button b1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileview);
        ed1 = (TextView) findViewById(R.id.e1);
        ed2 = (TextView) findViewById(R.id.e2);
        ed3 = (TextView) findViewById(R.id.e3);
        ed4 = (TextView) findViewById(R.id.e4);
        ed5 = (TextView) findViewById(R.id.e5);
        ed6 = (TextView) findViewById(R.id.e6);
        ed7 = (TextView) findViewById(R.id.e7);
        ed8 = (TextView) findViewById(R.id.e8);
        ed9 = (TextView) findViewById(R.id.e9);
        ed10 = (TextView) findViewById(R.id.e10);
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profileview.this, Editstuprofile.class);
                startActivity(i);
            }
        });

        //  System.out.print(userid);
        if (Build.VERSION.SDK_INT > 9) {
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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("profileview Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
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
                    String fathername = actor.getString("fathername");
                    String dob = actor.getString("dob");
                    String add = actor.getString("address");
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

            Toast.makeText(profileview.this, allNames.get(0).toString(), Toast.LENGTH_SHORT).show();
        }
    }


}
