package com.example.happy.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewPost extends Activity {
	EditText name, no, email;
	Button send;
	Spinner type/*, dis, state*/;
	ListView view;
	Connection conn;
	String name1, no1, email1, type1, dis1, state1;
	HashMap<String, String> usersList1 = null;
	ArrayList<HashMap<String, String>> usersList2 = new ArrayList<HashMap<String, String>>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpost);
		type = (Spinner) findViewById(R.id.spinner1);
		view=(ListView)findViewById(R.id.listview);
//		dis = (Spinner) findViewById(R.id.spinner2);
//		state = (Spinner) findViewById(R.id.spinner3);
		send = (Button) findViewById(R.id.button1);

		String type3[] = { "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013",
				"2014"};

		ArrayAdapter<String> type2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, type3);
		type2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(type2);
//
//		String bookcity[] = { "Ariyalur", "Chennai", "Coimbatore", "Cuddalore",
//				"Dharmapuri", "Dindigul", "Erode", "Kancheepuram",
//				"Kanyakumari", "Karur", "Krishnagiri", "Madurai",
//				"Nagapattinam", "Namakkal", "Perambalur", "Pudukottai",
//				"Ramanathapuram", "Salem", "Sivaganga", "Thanjavur",
//				"The Nilgiris", "Theni", "Thirunelveli", "Thiruvallur",
//				"Thiruvannamalai", "Thiruvarur", "Tiruppur", "Trichirappalli",
//				"Tuticorin", "Vellore", "Villupuram", "Virudhunagar" };
//
//		ArrayAdapter<String> bookcity2 = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, bookcity);
//		bookcity2
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		dis.setAdapter(bookcity2);
//
//		String state3[] = { "Tamil Nadu" };
//
//		ArrayAdapter<String> state2 = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, state3);
//		state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		state.setAdapter(state2);

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				type1 = type.getSelectedItem().toString();
new QuerySQL().execute();


			}
		});

	}
	public class QuerySQL extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog pDialog;

		Exception error;

		ResultSet rs;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ViewPost.this);
			pDialog.setTitle("User Post Details");
			pDialog.setMessage("Wait Getting User Post  Details...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Object... args) {

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
				// String
				// COMANDOSQL1="select * from services where reqsender='"+sendername+"' || reqrecv='"+sendername+"' && status='accept'";
				// Statement statement1 = conn.createStatement();
				// ResultSet rs1 = statement1.executeQuery(COMANDOSQL1);
				// if(rs1.next()){
				String COMANDOSQL = "select * from postdetails where year='"
						+ type1
						+ "'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				while (rs.next()) {
					usersList1 = new HashMap<String, String>();
					usersList1.put("message", rs.getString(1));

					Log.d("Friend List Map :", usersList1.toString());

					usersList2.add(usersList1);

				}
				// }

				return true;
				// Toast.makeText(getBaseContext(),
				// "Successfully Inserted.", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				error = e;
				return false;
				// Toast.makeText(getBaseContext(),"Successfully Registered...",
				// Toast.LENGTH_LONG).show();
			}

		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Boolean result1) {
			pDialog.dismiss();
			if (result1) {

				// System.out.println("ELSE(JSON) LOOP EXE");
				try {// try3 open

					view.setAdapter(new CustomBaseAdapter(
							ViewPost.this, usersList2));

				} catch (Exception e1) {
					Toast.makeText(getBaseContext(), e1.toString(),
							Toast.LENGTH_LONG).show();

				}

			} else {
				if (error != null) {
					Toast.makeText(getBaseContext(),
							error.getMessage().toString(), Toast.LENGTH_LONG)
							.show();
				}
			}
			super.onPostExecute(result1);
		}
	}
}