package com.linearlistview.sample;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linearlistview.LinearListView.OnItemClickListener;
import com.linearlistview.LinearListView;

public class MainActivity extends Activity {

	private BaseAdapter mAdapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
			}
			((TextView) convertView).setText("Position " + position);
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public int getCount() {
			return mCount;
		}
	};

	OnItemClickListener mListener = new OnItemClickListener() {

		@Override
		public void onItemClick(LinearListView parent, View view, int position,
				long id) {
			Toast.makeText(getApplicationContext(),
					"Tapped position " + position, Toast.LENGTH_SHORT).show();

		}
	};

	private int mCount = 50;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LinearListView horizontal = (LinearListView) findViewById(R.id.horizontal_list);
		LinearListView vertical = (LinearListView) findViewById(R.id.vertical_list);

		vertical.setDividerDrawable(new ColorDrawable(Color.CYAN));
		vertical.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE
				| LinearLayout.SHOW_DIVIDER_BEGINNING);

		horizontal.setDividerDrawable(getResources().getDrawable(R.drawable.divider_vertical_holo_dark));

		horizontal.setDividerThickness(getResources().getDimensionPixelSize(R.dimen.padding_medium));
		vertical.setDividerThickness(getResources().getDimensionPixelSize(R.dimen.padding_small));
		
		horizontal.setAdapter(mAdapter);
		vertical.setAdapter(mAdapter);
		
		horizontal.setOnItemClickListener(mListener);
		vertical.setOnItemClickListener(mListener);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_change) {
			mCount = (int) (Math.random() * 50);
			mAdapter.notifyDataSetChanged();
			return true;
		} else
			return super.onOptionsItemSelected(item);
	}
}
