package com.qusci.km;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.CompoundButton;
import com.actionbarsherlock.app.ActionBar;
import com.qusci.km.adapter.MyPagerAdapter;
import com.qusci.km.base.RichActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/19/14
 * Time: 8:19 PM
 */
public class TabesViewPagerActivity extends RichActivity implements ActionBar.TabListener {

    List contents = new ArrayList();
    String[] tabs;
    ViewPager viewPager;


    protected void addTabs() {
        tabs = new String[]
                {
                        "Tab pp",
                        "Tab 2",
                        "Tab 3",
                        "Tab 4"
                };
        for (String tab_name : tabs) {
            getSupportActionBar().addTab(getSupportActionBar().newTab().setText(tab_name).setTabListener(this));
        }

    }

    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.tabesviewpageractivity);


        contents.add("View pp");
        contents.add("View 2");
        contents.add("View 3");
        contents.add("View 4");
        contents.add("View 5");
        contents.add("View 6");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("KMAN");

        getSupportActionBar().setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);


        viewPager = (ViewPager) findViewById(R.id.viewPager1);
        MyPagerAdapter myAdapter = new MyPagerAdapter(this,contents);
        viewPager.setOnPageChangeListener(getSwipeListener());
        viewPager.setAdapter(myAdapter);
        viewPager.setOffscreenPageLimit(3);
//        viewPager.setOnGenericMotionListener(getOnGenericMotionListener());
//        viewPager.setOnDragListener(gtOnDragListener());
        viewPager.setOnPageChangeListener(getOnPageChangeListener());
        viewPager.setPageMargin(-300);
//        viewPager.setHorizontalFadingEdgeEnabled(true);
//        viewPager.setFadingEdgeLength(30);




    }


    List aa = new ArrayList();
    List b = new ArrayList();
    List c = new ArrayList();


    android.support.v4.view.ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new android.support.v4.view.ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
//                ((MyAdapter) viewPager.getAdapter()).getTextView1().setTextSize(20 + v * 30);
                aa.add(new Object[]{i,v,i2});
            }

            @Override
            public void onPageSelected(int i) {
                b.add(i);
                System.out.print("selected");

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                c.add(i);
                System.out.print("scrollStateChanged");

            }
        };
    }

    View.OnDragListener gtOnDragListener() {
        return new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {


                return false;
            }
        };
    }
    public int selectedPage;

    private android.support.v4.view.ViewPager.OnPageChangeListener getSwipeListener() {
        return new android.support.v4.view.ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {


            }
            @Override
            public void onPageSelected(int i) {
            selectedPage=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        };
    }


    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (viewPager != null)
            viewPager.setCurrentItem(tab.getPosition(), true);

    }

    List a = new ArrayList();

    public android.view.View.OnGenericMotionListener getOnGenericMotionListener() {
        return new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View view, MotionEvent motionEvent) {
                a.add(motionEvent.getX());
                return false;
            }
        };
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    protected CompoundButton.OnCheckedChangeListener getActionbarSwitcherChangeListener() {
        return null;
    }
}
