package com.qusci.km;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import com.j256.ormlite.stmt.Where;
import com.qusci.km.adapter.MyPagerAdapter;
import com.qusci.km.base.RichActivity;
import com.qusci.km.database.DatabaseHelper;
import com.qusci.km.model.Category;
import com.qusci.km.model.Deck;
import com.qusci.km.model.Key;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/6/14
 * Time: 1:02 AM
 */
public class ReviewActivity extends RichActivity {


    ViewPager viewPager;

    @Override
    protected void addTabs() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.review);
        DatabaseHelper db = new DatabaseHelper(this);
        Integer deck = null;
        List<Key> keys = null;
        try {

            Category category = (Category) getIntent().getExtras().get("category");
            deck = (Integer) getIntent().getExtras().get("deck");


            if (category != null) {
                if (deck != null) {
                    Deck deckEntity = db.getDeckDao().queryBuilder().where().eq("order", deck).queryForFirst();

                    if (deckEntity == null) {
                        for (Deck d : DeckActivity.decksStatic) {
                            if (d.equals(new Deck(999, "", deck))) {//just deck is important
                                deckEntity = d;
                                break;
                            }
                        }
                    }

                    Calendar currentDate = Calendar.getInstance();
                    Where<Key, Integer> where = db.getKeyDao().queryBuilder().where();

                    Where<Key, Integer> deadlineDate = where.isNull("deadlineDate").or().le("deadlineDate", currentDate.getTime());

                    Where<Key, Integer> categoryIdWhere = where.eq("categoryId", category.getId());
                    keys = where.and(deadlineDate, categoryIdWhere).query();
                } else
                    keys = db.getKeyDao().queryBuilder().where().eq("categoryId", category.getId())
                            .query();

            } else
                keys = db.getKeyDao().queryForAll();
        } catch (SQLException e) {
            db.close();
            e.printStackTrace();
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager1);
        MyPagerAdapter myAdapter = new MyPagerAdapter(this, keys, deck);

//        viewPager.setOnPageChangeListener(getSwipeListener());
        viewPager.setAdapter(myAdapter);
        viewPager.setOffscreenPageLimit(3);
//        viewPager.setOnGenericMotionListener(getOnGenericMotionListener());
//        viewPager.setOnDragListener(gtOnDragListener());
//        viewPager.setOnPageChangeListener(getOnPageChangeListener());
//        viewPager.setPageMargin(-300);
//        viewPager.setHorizontalFadingEdgeEnabled(true);
//        viewPager.setFadingEdgeLength(30);


    }

    @Override
    protected CompoundButton.OnCheckedChangeListener getActionbarSwitcherChangeListener() {
        return null;
    }
}
