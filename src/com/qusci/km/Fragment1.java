package com.qusci.km;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qusci.km.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/25/14
 * Time: 12:01 PM
 */
public class Fragment1 extends Fragment {

    int type;
    ViewPager viewPager;
    View view;
    List contents = new ArrayList();


    public Fragment1(int type) {
        this.type = type;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (type == 1) {

            View view = inflater.inflate(R.layout.tabesviewpageractivity, container, false);
            this.view = view;

            contents.add("View pp");
            contents.add("View 2");
            contents.add("View 3");
            contents.add("View 4");
            contents.add("View 5");
            contents.add("View 6");
            viewPager = (ViewPager) view.findViewById(R.id.viewPager1);
            MyPagerAdapter myAdapter = new MyPagerAdapter(getActivity(), contents);
            viewPager.setAdapter(myAdapter);
            viewPager.setOffscreenPageLimit(3);

        } else {

            View view = inflater.inflate(R.layout.fragment1, container, false);
            this.view = view;
        }

        return view;
    }
}
