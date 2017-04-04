package com.example.happy.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomBaseAdapter extends BaseAdapter {
	Context con;
	LayoutInflater layoutInflater;
	ArrayList<HashMap<String, String>> listvalue;

	public CustomBaseAdapter(ViewPost viewprofile,
							 ArrayList<HashMap<String, String>> usersList) {
		// TODO Auto-generated constructor stub
		con = viewprofile;
		listvalue = usersList;
		layoutInflater = LayoutInflater.from(viewprofile);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listvalue.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listvalue.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.listview2, null);
			viewHolder = new ViewHolder();
			viewHolder.txtUsername = (TextView) convertView
					.findViewById(R.id.textView2);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txtUsername.setText(listvalue.get(position).get("message")
				.toString());

		return convertView;

	}

	class ViewHolder {
		TextView txtUsername;


	}

}
