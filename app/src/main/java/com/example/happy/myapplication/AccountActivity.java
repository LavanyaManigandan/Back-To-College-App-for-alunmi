package com.example.happy.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "account";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_NAME = "name";
    private static final String KEY_FNAME = "fathername";
    private static final String KEY_DOB = "dob";
    private static final String KEY_ADDR = "address";
    private static final String KEY_PH = "ph";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DEPT = "dept";
    private static final String KEY_BATCH = "batch";
    private static final String KEY_ID = "userid";
    private static final String KEY_PASS = "pass";

    }
