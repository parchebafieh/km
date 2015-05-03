package com.qusci.km;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.qusci.km.adapter.KeyAdapter;
import com.qusci.km.database.DatabaseHelper;
import com.qusci.km.model.Category;
import com.qusci.km.model.Key;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/5/14
 * Time: 9:40 PM
 */
public class KeyFragment extends Fragment {

    Category category;
    android.content.Context context;
    Key key;
    Integer deckNumber;
    Key.OnKeySelectionListener onKeySelectionListener;

    View selectedViewOfList;

    public KeyFragment(Category category, Key.OnKeySelectionListener onKeySelectionListener) {
        this.category = category;

        this.onKeySelectionListener = onKeySelectionListener;
    }

    public KeyFragment(Category category, Integer deckNumber) {
        this.category = category;
        this.deckNumber = deckNumber;
    }
    public KeyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = container.getContext();
        View view = inflater.inflate(R.layout.key_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView1);
        DatabaseHelper db = new DatabaseHelper(container.getContext());
        try {

            List<Key> keyList = null;
            if (deckNumber != null) {
                    keyList = db.getKeyDao().queryBuilder().where().eq("categoryId", category.getId())
                            .and().eq("deck", deckNumber).query();
            } else {
                keyList = db.getKeyDao().queryBuilder().where().eq("categoryId", category.getId()).query();

            }
            KeyAdapter keyAdapter = new KeyAdapter(container.getContext(), R.layout.category_item, keyList);
            listView.setAdapter(keyAdapter);


        } catch (SQLException e) {
            db.close();
            e.printStackTrace();
        }

        listView.setOnItemClickListener(getOnItemClickListener());
        listView.setOnItemLongClickListener(getOnLongClickListener());
        db.close();
        return view;
    }


    private android.widget.AdapterView.OnItemLongClickListener getOnLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

//                view.setBackgroundColor(Color.parseColor("#BEFDB5"));
                DatabaseHelper db = new DatabaseHelper(getContext());
                KeyAdapter.CategoryHolder categoryHolder = (KeyAdapter.CategoryHolder) view.getTag();
                try {
                    key = db.getKeyDao().queryForId(Integer.valueOf(categoryHolder.getId().getText().toString()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                onKeySelectionListener.onSelection(key);

                db.close();
                return true;
            }
        };
    }


    android.content.Context getContext() {
        return context;
    }

    private View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ValueActivity.class);

                KeyAdapter.CategoryHolder categoryHolder = (KeyAdapter.CategoryHolder) view.getTag();

                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                Key key = null;
                try {
                    key = databaseHelper.getKeyDao().queryForId(Integer.valueOf(categoryHolder.getId().getText().toString()));
                } catch (SQLException e) {
                    databaseHelper.close();
                    e.printStackTrace();
                }

                intent.putExtra("key", key);
                databaseHelper.close();
                startActivity(intent);
            }
        };
    }


    private android.widget.AdapterView.OnItemClickListener getOnItemClickListener() {
        return new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ValueActivity.class);

                KeyAdapter.CategoryHolder categoryHolder = (KeyAdapter.CategoryHolder) view.getTag();

                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                try {
                    key = databaseHelper.getKeyDao().queryForId(Integer.valueOf(categoryHolder.getId().getText().toString()));
                } catch (SQLException e) {
                    databaseHelper.close();
                    e.printStackTrace();
                }

                intent.putExtra("key", key);
                databaseHelper.close();
                startActivity(intent);
            }
        };

    }

}
