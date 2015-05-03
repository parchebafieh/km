package com.qusci.km.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.qusci.km.KeyActivity;
import com.qusci.km.R;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 8/10/12
 * Time: 2:47 PM
 */
public abstract class RichActivity extends SherlockFragmentActivity {

    Map<Object, Method> cache = new HashMap<Object, Method>();
    Intent intent = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (cache != null && cache.size() > 0) {
            return;
        }
        super.onCreate(savedInstanceState);
        long i = System.currentTimeMillis();
        init(savedInstanceState);
        Method[] methods = this.getClass().getDeclaredMethods();

        for (Method method : methods) {

            EventHandler eventHandler = method.getAnnotation(EventHandler.class);
            if (eventHandler != null) {

                try {
                    cache.put(eventHandler, method);
                    registerEventHandler(eventHandler, method);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if ((getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN )== 0) {
            getSupportActionBar().setTitle(getActivityTitle());
//        getSupportActionBar().setLogo(R.drawable.almanden);
            addTabs();
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }



        long f = System.currentTimeMillis();
        Log.i("RichActivity", "Time in milis -> " + String.valueOf(f - i) + "cache hash code" + cache.hashCode());
    }


    public void createActionBar() {
        getSupportActionBar().setCustomView(R.layout.action_bar);
        Switch switch1 = (Switch) getSupportActionBar().getCustomView().findViewById(R.id.switch1);

        switch1.setOnCheckedChangeListener(getActionbarSwitcherChangeListener());

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ImageButton ib = (ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.ggg);
        ib.setOnClickListener(getOnclickListener());

    }

    private android.view.View.OnClickListener getOnclickListener() {
        return new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RichActivity.this.getSupportActionBar().setCustomView(R.layout.action_bar_search);
                RichActivity.this.getSupportActionBar().getCustomView().findViewById(R.id.ggg).setOnClickListener(getOnclickListener1());
                RichActivity.this.getSupportActionBar().getCustomView().findViewById(R.id.searchText).requestFocus();
//                RichActivity.this.getSupportActionBar().setDisplayUseLogoEnabled(false);
//                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().removeAllTabs();

                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        };
    }


    private android.view.View.OnClickListener getOnclickListener1() {
        return new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                RichActivity.this.getSupportActionBar().setCustomView(R.layout.action_bar);
//                RichActivity.this.getSupportActionBar().getCustomView().findViewById(R.id.ggg).setOnClickListener(getOnclickListener());
//                RichActivity.this.getSupportActionBar().setDisplayUseLogoEnabled(true);
                createActionBar();
//                getSupportActionBar().setDisplayShowHomeEnabled(true);
//                getSupportActionBar().setDisplayShowTitleEnabled(true);
                addTabs();
//                Toast.makeText(MainActivity.this,"Searching...!!!",Toast.LENGTH_LONG).show();
            }
        };
    }

    protected Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        return makeNormalOptionMenu();
    }

    protected boolean makeNormalOptionMenu() {
        menu.clear();
        menu.add(1, 1, 1, "DECKS").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add(2, 2, 2, "REVIEW").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(3, 3, 3, "NEW")
                .setIcon(R.drawable.ic_action_new).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(4, 4, 4, "SEARCH")
                .setIcon(R.drawable.ic_action_search).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    private void registerEventHandler(EventHandler eventHandler, Method method) throws Exception {

        View view = findViewById(eventHandler.value());
        Method registerListenerMethod = view.getClass().getMethod("set" + eventHandler.eventType().getSimpleName(),
                eventHandler.eventType());
        registerListenerMethod.invoke(view, makeHandler(eventHandler));

    }

    public Object makeHandler(final Object eventHandler) {

        return java.lang.reflect.Proxy.newProxyInstance(eventHandler.getClass().getClassLoader(),
                new Class[]{((EventHandler) eventHandler).eventType()},
                new java.lang.reflect.InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args) {
                        try {
                            return doMethod(eventHandler, args);
                        } catch (Exception e) {
                            Toast.makeText(RichActivity.this, "Activity not found", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            return null;
                        }
                    }
                });
    }

    <T> T doMethod(Object eventHandler, Object[] args) throws Exception {
        return (T) cache.get(eventHandler).invoke(RichActivity.this, args);
    }


    protected boolean userPrompt() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Warning");
        alert.setMessage("Are you sure?");


        final EditText input = new EditText(this);
//            alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.i(this.getClass().getSimpleName(), "OK is pushed " + whichButton);
            }
        });
        alert.setIcon(R.drawable.category);


        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.i(this.getClass().getSimpleName(), "CANCEL is pushed " + whichButton);
            }
        });

        alert.show();
        return true;
    }


    protected void putSharedPreferences(String dbName, String key, String value) {
        SharedPreferences sp = getSharedPreferences(dbName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();

    }


    protected Intent getGlobalIntent() {
        return intent;
    }

    protected String getActivityTitle() {
        return "ALMANDEN";
    }

    public Menu getMenu() {
        return menu;
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void addTabs();

    protected abstract CompoundButton.OnCheckedChangeListener getActionbarSwitcherChangeListener();
}
