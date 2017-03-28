package com.example.happy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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


/**
 * Created by Hp on 08-02-2017.
 */

public class evtlogin extends MainActivity {
//    Button b5;
//    EditText e1, e2;
//    HttpClient httpclient;
//    HttpPost httppost;
//    ResponseHandler<String> response;
//    List<NameValuePair> nameValuePairs;
//    String username, password;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.evtlogin);
//
//        b5 = (Button) findViewById(R.id.b5);
//        e1 = (EditText) findViewById(R.id.e1);
//        e2 = (EditText) findViewById(R.id.e2);
//        nameValuePairs = new ArrayList<NameValuePair>();
//        httpclient = new DefaultHttpClient();
//        response = new BasicResponseHandler();
//
//
//
//
//                username = e1.getText().toString();
//                password = e2.getText().toString();
//
//                if (username.equals("") || password.equals("")) {
//                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
//                } else {
//                    nameValuePairs.add(new BasicNameValuePair("userid", username));
//                    nameValuePairs.add(new BasicNameValuePair("pass", password));
//                  //  nameValuePairs.add(new BasicNameValuePair("status", studentStatus));
//
//
//                    httppost = new HttpPost("http://10.0.2.2/stulogin.php");
//                    try {
//                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
//                        String returnedstring = httpclient.execute(httppost, response);
//
//                        //   Toast.makeText(getApplicationContext(), returnedstring, Toast.LENGTH_LONG).show();
//                        if (returnedstring.equals("true")) {
//                            Intent s = new Intent(evtlogin.this, forgot.class);
//
//                            startActivity(s);
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Not Registered yet Please Register", Toast.LENGTH_LONG).show();
//                        }
//                    } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }
//
//            /*Intent i = new Intent(register.this, CombinedActivity.class);
//            startActivity(i);*/
//            }
//
//
//    /**
//     * Created by Hp on 09-02-2017.
//     */
//
//    public static class evtlogin {
//    }
}
