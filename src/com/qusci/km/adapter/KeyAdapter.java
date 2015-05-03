package com.qusci.km.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.qusci.km.R;
import com.qusci.km.ValueActivity;
import com.qusci.km.database.DatabaseHelper;
import com.qusci.km.model.Key;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/7/12
 * Time: 2:11 PM
 */
public class KeyAdapter extends ArrayAdapter<Key> {

    List<Key> keys;
    Context context;
    int layoutResourceId;
    View.OnClickListener onClickListener;

    public KeyAdapter(Context context, int textViewResourceId, List<Key> objects) {
        super(context, textViewResourceId, objects);
        keys = objects;
        this.context = context;
        layoutResourceId = textViewResourceId;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        CategoryHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CategoryHolder();
            //       holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView) row.findViewById(R.id.categoryName);
            holder.imageView = (ImageView) row.findViewById(R.id.categoryIcon);
            holder.id = (TextView) row.findViewById(R.id.categoryId);


            row.setTag(holder);
        } else {
            holder = (CategoryHolder) row.getTag();
        }

        Key category = keys.get(position);
        holder.txtTitle.setText(category.getName());
        holder.id.setText(String.valueOf(category.getId()));


        if (keys.get(position).getDeadlineDate() == null || keys.get(position).getDeadlineDate().before(Calendar.getInstance().getTime())) {
            holder.imageView.setImageResource(R.drawable.ic_action_star);
        }else
            holder.imageView.setImageResource(R.drawable.misc_57);

        if(keys.get(position).getStatus()==1){
            holder.imageView.setImageResource(R.drawable.star_delete);
        }

            return row;
    }


    public static class CategoryHolder {
        TextView txtTitle;
        TextView id;
        ImageView imageView;
        String color;

        public TextView getId() {
            return id;
        }

        public void setId(TextView id) {
            this.id = id;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public void setTxtTitle(TextView txtTitle) {
            this.txtTitle = txtTitle;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
