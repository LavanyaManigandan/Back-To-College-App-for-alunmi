package com.example.happy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Happy on 10/1/2017.
 */
public class post_fragment extends Fragment {
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    Button b1;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.layout_post, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        nameValuePairs = new ArrayList<NameValuePair>();
        httpclient = new DefaultHttpClient();
        response = new BasicResponseHandler();
        e1 = (EditText)v.findViewById(R.id.e1);
        e2 = (EditText)v.findViewById(R.id.e2);
        e3 = (EditText)v.findViewById(R.id.e3);
        e4 = (EditText)v.findViewById(R.id.e4);
        e5 = (EditText)v.findViewById(R.id.e5);
        e6 = (EditText)v.findViewById(R.id.e6);
        e7 = (EditText)v.findViewById(R.id.e7);
        e8 = (EditText)v.findViewById(R.id.e8);
        e9 = (EditText)v.findViewById(R.id.e9);
        e10 = (EditText)v.findViewById(R.id.e10);
        b1 = (Button)v.findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String returnedstring;
                nameValuePairs.add(new BasicNameValuePair("name", e1.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("fathername",e2.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("dob",e3.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("address",e4.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("phone",e5.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email",e6.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("dept",e7.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("batch",e8.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("userid",e9.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", e10.getText().toString()));
                httppost = new HttpPost("http://10.100.16.31/studentpro.php");
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                    returnedstring = httpclient.execute(httppost,response);
                    /*Intent i = new Intent(post_fragment.this,MainActivity.class);
                    startActivity(i);*/
                    Toast.makeText(getActivity(),returnedstring,Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    Toast.makeText(getActivity(),e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Post");
    }
}

