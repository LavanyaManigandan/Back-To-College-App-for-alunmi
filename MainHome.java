package com.example.happy.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainHome extends AppCompatActivity {
    EditText mes;
    Spinner year;
    Button post,view;
    Connection conn;
    String mes1, year1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mes = (EditText) findViewById(R.id.editTextpost);
        year = (Spinner) findViewById(R.id.spinnerpost);
        post = (Button) findViewById(R.id.buttonpost);
        view = (Button) findViewById(R.id.buttonviewpost);
        String type3[] = { "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013",
                "2014"};

        ArrayAdapter<String> type2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, type3);
        type2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(type2);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mes1 = mes.getText().toString();
                year1 = year.getSelectedItem().toString();

                try {
                    new QuerySQL().execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainHome.this,ViewPost.class);
                startActivity(intent);
            }
        });



    }

    public class QuerySQL extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog;
        Exception error;
        ResultSet rs;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainHome.this);
            pDialog.setTitle("Registration");
            pDialog.setMessage("Registering your credentials...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {

            try {

                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(
                        "jdbc:mysql://103.10.235.220:3306/post", "root",
                        "password");
            } catch (SQLException se) {
                Log.e("ERRO1", se.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("ERRO2", e.getMessage());
            } catch (Exception e) {
                Log.e("ERRO3", e.getMessage());
            }

            try {

                Statement statement = conn.createStatement();
                int success = statement
                        .executeUpdate("insert into postdetails values('"
                                + mes1+ "','" + year1 + "')");

                if (success >= 1) {
                    // successfully created product

                    return true;
                    // closing this screen
                    // finish();
                } else {
                    // failed to create product
                    return false;
                }

                // Toast.makeText(getBaseContext(),
                // "Successfully Inserted.", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                error = e;
                return false;
                // Toast.makeText(getBaseContext(),"Successfully Registered...",
                // Toast.LENGTH_LONG).show();
            }

        }

        private String Double(String string) {
            // TODO Auto-generated method stub
            return null;
        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(Boolean result1) {
            pDialog.dismiss();
            if (result1) {

                Toast.makeText(getBaseContext(),
                        "Successfully Posted...",
                        Toast.LENGTH_LONG).show();

                // System.out.println("ELSE(JSON) LOOP EXE");
                try {// try3 open

                    Intent i = new Intent(getApplicationContext(),
                            MainHome.class);
                    startActivity(i);

                } catch (Exception e1) {
                    Toast.makeText(getBaseContext(), e1.toString(),
                            Toast.LENGTH_LONG).show();

                }

            } else {
                if (error != null) {
                    Toast.makeText(getApplicationContext(), error.toString(),
                            Toast.LENGTH_LONG).show();
                    Log.d("Error not null...", error.toString());
                } else {
                    Toast.makeText(getBaseContext(),
                            "Not crreated your credentials!!!",
                            Toast.LENGTH_LONG).show();
                }
            }
            super.onPostExecute(result1);
        }
    }


}
