package com.qusci.km.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qusci.km.R;


public class MenuListAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	String[] mTitle;

	int[] mIcon;
	LayoutInflater inflater;

	public MenuListAdapter(Context context, String[] title, int[] icon) {
		this.context = context;
		this.mTitle = title;

		this.mIcon = icon;
	}

	@Override
	public int getCount() {
		return mTitle.length;
	}

	@Override
	public Object getItem(int position) {
		return mTitle[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView txtTitle;
		TextView txtSubTitle;
		ImageView imgIcon;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;

        {


		 itemView = inflater.inflate(R.layout.category_item, parent,false);

		// Locate the TextViews in drawer_list_item.xml
		txtTitle = (TextView) itemView.findViewById(R.id.categoryName);


		// Locate the ImageView in drawer_list_item.xml
		imgIcon = (ImageView) itemView.findViewById(R.id.categoryIcon);

		// Set the results into TextViews
		txtTitle.setText(mTitle[position]);


		// Set the results into ImageView
		imgIcon.setImageResource(mIcon[position]);

        }
        return itemView;
	}

}
