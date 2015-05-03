package com.qusci.km.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qusci.km.R;
import com.qusci.km.model.Category;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/7/12
 * Time: 2:11 PM
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    List<Category> categories;
    Context context;
    int layoutResourceId;

    public CategoryAdapter(Context context, int textViewResourceId, List<Category> objects) {
        super(context, textViewResourceId, objects);
        categories = objects;
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
      /*  if (position == 0) {
            holder.imageView.setImageResource(R.drawable.ic_action_new);
            holder.txtTitle.setText("Add Category");
            holder.id.setText(String.valueOf(-1));
            return row;
        }*/
        Category category = categories.get(position);
        holder.txtTitle.setText(category.getName());
        holder.id.setText(String.valueOf(category.getId()));
        return row;
    }


    public static class CategoryHolder {
        TextView txtTitle;
        TextView id;
        ImageView imageView;

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
    }
}
