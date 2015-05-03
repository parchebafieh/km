package com.qusci.km;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.j256.ormlite.dao.Dao;
import com.qusci.km.adapter.CategoryAdapter;
import com.qusci.km.base.EventHandler;
import com.qusci.km.base.RichActivity;
import com.qusci.km.database.DatabaseHelper;
import com.qusci.km.model.Category;
import com.qusci.km.model.Key;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 8/10/12
 * Time: 6:09 PM
 */
public class CategoryListActivity extends RichActivity {


    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.categorylist);
        Category category = new Category();

        List<Category> categories = new ArrayList<Category>();

        DatabaseHelper db = new DatabaseHelper(this);
        try {

            List<Category> tempCategory = db.getCategoryDao().queryForAll();
            Dao<Key, Integer> keyDao = db.getKeyDao();

            for (Category c : tempCategory) {

                long count = keyDao.queryBuilder().where().eq("categoryId", c.getId()).countOf();
                c.setName(c.getName() + "(" + count + ")");
                categories.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        while (cursor.moveToNext()) {
//            Category cat = new Category();
//            cat.setId(Long.valueOf(cursor.getString(cursor.getColumnIndex(CategoryTable.CAT_ID))));
//            cat.setName(cursor.getString(cursor.getColumnIndex(CategoryTable.CAT_NAME)));
//
//            categories.add(cat);
//        }
//        cursor.close();
        CategoryAdapter adapter = new CategoryAdapter(this, R.layout.category_item, categories);
        ListView listView = (ListView) findViewById(R.id.categoryList);
        listView.setAdapter(adapter);

    }

    @EventHandler(value = R.id.categoryList, eventType = AdapterView.OnItemClickListener.class)
    public void haminjuri(AdapterView adapterView, View v, Integer position, Long id) {
        TextView tv = (TextView) v.findViewById(R.id.categoryId);
        Toast.makeText(this, "Id= " + tv.getText() + " pos:" + position, Toast.LENGTH_SHORT).show();

    }


    @EventHandler(value = R.id.categoryList, eventType = AdapterView.OnItemLongClickListener.class)
    public boolean longClick(AdapterView parent, final View view, Integer position, Long id) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        TextView tv = (TextView) view.findViewById(R.id.categoryId);

        alert.setTitle("Warning");
        alert.setMessage("Are you sure to delete this category?");
        final EditText input = new EditText(this);
//            alert.setView(input);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                CategoryListActivity.this.isOk(dialog, whichButton, view);
            }
        });
        alert.setIcon(R.drawable.category);


        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                CategoryListActivity.this.isCancell(dialog, whichButton);
            }
        });

        alert.show();
        return true;
    }


    public void isOk(DialogInterface dialog, int whichButton, View view) {
        Log.i(this.getClass().getSimpleName(), "OK is pushed from isOk " + whichButton);
//        CategoryTable categoryTable = new CategoryTable(this);
//        SQLiteDatabase database = categoryTable.getWritableDatabase();
//        TextView tv = (TextView) view.findViewById(com.qusci.android.km.R.id.categoryId);
//        database.execSQL("delete from TBL_CATEGORY where _id=?", new Integer[]{Integer.valueOf(tv.getText().toString())});
        this.recreate();

    }

    public void isCancell(DialogInterface dialog, int whichButton) {
        Log.i(this.getClass().getSimpleName(), "CANCEL is pushed from isCancel " + whichButton);
    }

    @Override
    protected void addTabs() {

    }

    @Override
    protected CompoundButton.OnCheckedChangeListener getActionbarSwitcherChangeListener() {
        return null;
    }
}
