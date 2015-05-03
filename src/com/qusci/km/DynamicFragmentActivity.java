package com.qusci.km;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import com.qusci.km.base.RichActivity;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/25/14
 * Time: 11:44 AM
 */
public class DynamicFragmentActivity extends RichActivity {
    boolean toggle;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.dynamic_fragment_activty);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.frag1);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frag1, new Fragment1(1));
            ft.commit();
        }

    }


    private android.view.View.OnClickListener onClickListener() {
        return new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (toggle) {
                    ft.replace(R.id.frag1, new Fragment1(Color.RED));
                } else {
                    ft.replace(R.id.frag1, new Fragment1(Color.GREEN));
                }
                ft.commit();
                toggle = !toggle;

            }
        };
    }

    @Override
    protected void addTabs() {

    }

    @Override
    protected CompoundButton.OnCheckedChangeListener getActionbarSwitcherChangeListener() {
        return new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                if (b) {
                    ft.replace(R.id.frag1, new Fragment1(2));

                } else {
                    ft.replace(R.id.frag1, new Fragment1(1));
                }
                ft.commit();

            }
        };
    }
}
