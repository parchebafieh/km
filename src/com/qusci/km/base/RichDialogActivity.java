package com.qusci.km.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
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
public abstract class RichDialogActivity extends Activity {

    Map<Object, Method> cache = new HashMap<Object, Method>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (cache != null && cache.size() > 0) {
            return;
        }
        super.onCreate(savedInstanceState);
        long i = System.currentTimeMillis();
        setFinishOnTouchOutside(false);
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



        setTitle(getDialogTitle());
        long f = System.currentTimeMillis();
        Log.i("RichActivity", "Time in milis -> " + String.valueOf(f - i) + "cache hash code" + cache.hashCode());
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
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        try {
                            return doMethod(eventHandler, args);
                        } catch (Exception e) {
                            Toast.makeText(RichDialogActivity.this,"Activity not found",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            return null;
                        }
                    }
                });
    }

    <T> T doMethod(Object eventHandler, Object[] args) throws Exception {
        return (T) cache.get(eventHandler).invoke(RichDialogActivity.this, args);
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

    protected abstract String getDialogTitle();
    protected abstract void init(Bundle savedInstanceState);
}
