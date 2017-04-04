package com.example.happy.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

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
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {
    TextView loced;
    Button find;
    ArrayList<LatLng> locationLatLong = null;
    String returnedstring;
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    List<String> allNames;
    TextView ed1;
    String Area;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
       // converter = (Button) findViewById(R.id.converter);
        find = (Button) findViewById(R.id.locbutton);
        locationLatLong = new ArrayList<>();
        String returnedstring = null;
        String id="";
        getLocationLatLon("Saravanampatti");
        /////////////////////////////////////////
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
//        converter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LocationActivity.this, ViewActivity.class);
//                startActivity(intent);
//            }
//        });


    }

    public void getLocationLatLon(String locationName) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        new ExampleAsync().execute();

    }

    class ExampleAsync extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... params) {

            if (Geocoder.isPresent())

            {
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
                        String Area = actor.getString("Area");
                        allNames.add(Area);
                       // String fathername = actor.getString("fathername");

                        Geocoder gc = new Geocoder(LocationActivity.this, Locale.getDefault());
                        List<Address> addresses = gc.getFromLocationName(Area, 5);
                        if (addresses != null && addresses.size() > 0) {

                            for (Address a : addresses) {
                                if (a.hasLatitude() && a.hasLongitude()) {
                                    locationLatLong.add(new LatLng(a.getLatitude(), a.getLongitude()));
                                }
                            }
                        }


                    }


                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("Print:"+allNames);

        }
    }
}





