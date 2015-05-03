package com.qusci.km;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.dao.Dao;
import com.qusci.km.adapter.CategoryAdapter;
import com.qusci.km.adapter.MenuListAdapter;
import com.qusci.km.base.RichActivity;
import com.qusci.km.blur.BlurBehind;
import com.qusci.km.database.DatabaseHelper;
import com.qusci.km.model.Category;
import com.qusci.km.model.Key;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends RichActivity {

    List<Category> categories;
    CategoryAdapter categoryAdapter;
    ListView categoryListView = null;
    DrawerLayout drawerLayout;
    Category currentCategory;

    Key key;


    private void fetchCategories() {
        categories = new ArrayList<Category>();
        DatabaseHelper db = new DatabaseHelper(this);
        try {
            Dao<Key, Integer> keyDao = db.getKeyDao();

            List<Category> temp = db.getCategoryDao().queryForAll();
            for (Category c : temp) {

                long count = keyDao.queryBuilder().where().eq("categoryId", c.getId()).countOf();
                c.setName(c.getName() + " (" + count + ")");
                categories.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            db.close();
        }
        db.close();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
       /*getSupportActionBar().setLogo(R.drawable.almanden);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setTitle("salam");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ImageButton ib=(ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.ggg);
        ib.setOnClickListener(getOnclickListener());*/


        setContentView(R.layout.main);
        fetchCategories();
        categoryListView = (ListView) findViewById(R.id.categoryListView);
        categoryAdapter = new CategoryAdapter(this, R.layout.category_item, categories);

        View headerView = View.inflate(this, R.layout.category_item_header, null);

        ((ImageView) headerView.findViewById(R.id.categoryIcon)).setImageResource(R.drawable.ic_action_new);
        ((TextView) headerView.findViewById(R.id.categoryName)).setText("Add Category");
        ((TextView) headerView.findViewById(R.id.categoryId)).setText(String.valueOf(-1));


        categoryListView.addHeaderView(headerView);
        categoryListView.setAdapter(categoryAdapter);


        categoryListView.setOnItemClickListener(getOnItemClickListener());
        categoryListView.setLongClickable(true);
        categoryListView.setOnLongClickListener(getOnLongClickListener());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(getDrawerListener());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        BlurBehind.getInstance()
                .withAlpha(120)
                .setBackground(this);
    }

    private android.view.View.OnLongClickListener getOnLongClickListener() {
        return new android.view.View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setBackgroundColor(Color.GREEN);
                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                CategoryAdapter.CategoryHolder categoryHolder = (CategoryAdapter.CategoryHolder) view.getTag();
                try {
                    db.getCategoryDao().delete(new Category(Integer.valueOf(categoryHolder.getId().toString())));
                } catch (SQLException e) {
                    db.close();
                    e.printStackTrace();
                }
                db.close();




                return false;
            }
        };
    }


    private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {

        public CustomActionBarDrawerToggle(Activity mActivity, DrawerLayout mDrawerLayout) {
            super(
                    mActivity,               /* host Activity */
                    mDrawerLayout,           /* DrawerLayout object */
                    R.drawable.ic_action_attach,    /* nav drawer icon to replace 'Up' caret */
                    R.string.category_name,   /* "open drawer" description */
                    R.string.category_list); /* "close drawer" description */
        }


        /**
         * Called when a drawer has settled in a completely closed state.
         */
        @Override
        public void onDrawerClosed(View view) {
            getSupportActionBar().setTitle("salam");
        }

        /**
         * Called when a drawer has settled in a completely open state.
         */
        @Override
        public void onDrawerOpened(View drawerView) {
            getSupportActionBar().setTitle("Close");
        }
    }


    private DrawerLayout.DrawerListener getDrawerListener() {
        return new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View view, float v) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View view) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int i) {
                supportInvalidateOptionsMenu();
            }
        };
    }


    private ListView.OnItemClickListener getOnItemClickListener() {
        return new ListView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                CategoryAdapter.CategoryHolder categoryHolder = (CategoryAdapter.CategoryHolder) view.getTag();
                if (categoryHolder == null) {
                    Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                    startActivityForResult(intent, 1);
                    return;
                }
                String selectedItem = categoryHolder.getId().getText().toString();
                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                try {
                    currentCategory = db.getCategoryDao().queryForId(Integer.valueOf(selectedItem));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                getSupportActionBar().setTitle(getActivityTitle() + " > " + currentCategory.getName());

                getGlobalIntent().putExtra("category", currentCategory);

                ft.replace(R.id.content_frame, new KeyFragment(currentCategory, getOnKeySelectionListener()));
                ft.commit();
                db.close();

                MainActivity.this.drawerLayout.closeDrawer(categoryListView);
            }
        };
    }


    private void refreshKeyFragment() {
        new Handler().post(new Runnable() {
            public void run() {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new KeyFragment(currentCategory, getOnKeySelectionListener()));
                ft.commit();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            fetchCategories();
            categoryAdapter.clear();
            categoryAdapter.addAll(categories);
            categoryAdapter.notifyDataSetChanged();
        } else if (requestCode == 2) {
            makeNormalOptionMenu();
            refreshKeyFragment();
        } else if (requestCode == 3) {
            refreshKeyFragment();
            if (resultCode == 0) {
                return;
            }
            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
            try {
                databaseHelper.getKeyDao().delete(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            makeNormalOptionMenu();
            refreshKeyFragment();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {

    }


    private Key.OnKeySelectionListener getOnKeySelectionListener() {
        return new Key.OnKeySelectionListener() {
            @Override
            public boolean onSelection(Key key) {
                getMenu().clear();
                MainActivity.this.key = key;
                getGlobalIntent().putExtra("key", key);

                getMenu().add(3, 3, 3, "EDIT").setIcon(R.drawable.ic_action_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                getMenu().add(2, 2, 2, "DELETE").setIcon(R.drawable.ic_action_discard).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                getMenu().add(1, 1, 1, "DONE").setIcon(R.drawable.ic_action_accept).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                return false;
            }
        };
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this, "Hi.......", Toast.LENGTH_LONG).show();

        }
    }

    //    @EventHandler(R.id.viewPagerRunner)
    public void btnViewPagerRunnerOnClickHandler(View view) {
        Intent intent = new Intent(this, TabesViewPagerActivity.class);
        startActivity(intent);
    }


    //    @EventHandler(R.id.fragmentRunner)
    public void btnFragmentOnClickHandler(View view) {
        Intent intent = new Intent(this, DynamicFragmentActivity.class);
        startActivity(intent);
    }

    //    @EventHandler(R.id.jsonDownloadRunner)
    public void btnIntentServicetOnClickHandler(View view) {
        Intent intent = new Intent(this, BooksDoanloadActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    protected void addTabs() {

    }

    protected CompoundButton.OnCheckedChangeListener getActionbarSwitcherChangeListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {

                } else {

                }

            }
        };
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getTitle().equals("EDIT")) {
            getGlobalIntent().setClass(this, KeyActivity.class);
            startActivityForResult(getGlobalIntent(), 2);
            makeNormalOptionMenu();

        } else if (item.getTitle().equals("DECKS")) {

            if (currentCategory == null) {
                Toast.makeText(this, "Please select a category", Toast.LENGTH_LONG).show();
                return false;
            }
            getGlobalIntent().setClass(this, DeckActivity.class);
            getGlobalIntent().putExtra("category", currentCategory);
            startActivity(getGlobalIntent());

        } else if (item.getTitle().equals("DONE")) {

            makeNormalOptionMenu();
        } else if (item.getTitle().equals("DELETE")) {
            getGlobalIntent().setClass(this, YesNoActivity.class);
            startActivityForResult(getGlobalIntent(), 3);
            makeNormalOptionMenu();

        } else if (item.getTitle().equals("REVIEW")) {
            getGlobalIntent().setClass(this, ReviewActivity.class);
            getGlobalIntent().putExtra("category", currentCategory);
            startActivity(getGlobalIntent());

        } else if (item.getTitle().equals("NEW")) {
            if (currentCategory == null) {
                Toast.makeText(this, "Please select a category", Toast.LENGTH_LONG).show();
                return false;
            }
            if (getGlobalIntent().hasExtra("key")) {
                getGlobalIntent().removeExtra("key");
            }
            getGlobalIntent().setClass(this, KeyActivity.class);
            startActivityForResult(getGlobalIntent(), 2);
        }

        return super.onOptionsItemSelected(item);
    }
}
