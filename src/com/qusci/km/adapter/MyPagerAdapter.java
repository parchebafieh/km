package com.qusci.km.adapter;

import android.content.Context;
import android.renderscript.Sampler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.qusci.km.DeckActivity;
import com.qusci.km.R;
import com.qusci.km.database.DatabaseHelper;
import com.qusci.km.model.Deck;
import com.qusci.km.model.Key;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/25/14
 * Time: pp:09 PM
 */
public class MyPagerAdapter extends PagerAdapter {

    LayoutInflater layoutInflater;

    View page;
    Integer deck;

    List<Key> contents = new ArrayList();

    public MyPagerAdapter(Context context, List contents) {
        this.contents = contents;
        layoutInflater = LayoutInflater.from(context);
    }

    public MyPagerAdapter(Context context, List contents, Integer deck) {
        this.contents = contents;
        layoutInflater = LayoutInflater.from(context);
        this.deck = deck;
    }


    public float getPageWidth(int position) {

        switch (position) {
            case 0:
                return 1f;
            case 1:
                return 1f;
            default:
                return 1f;
        }
    }

    @Override
    public int getCount() {
        return contents.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (View) o;
    }

    TextView textView;
    Context context;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        page = layoutInflater.inflate(R.layout.page_item, container, false);
        context = container.getContext();
        View card = page.findViewById(R.id.myRectangleView);
        View knowBtn = page.findViewById(R.id.knowBtn);
        View notKnowBtn = page.findViewById(R.id.notKnowBtn);
        if (deck != null) {
            knowBtn.setVisibility(View.VISIBLE);
            notKnowBtn.setVisibility(View.VISIBLE);
        }

        knowBtn.setOnClickListener(getOnclickListener());
        notKnowBtn.setOnClickListener(getOnclickListenerNotKnow());

        card.setOnLongClickListener(getOnLongClickListener());

        textView = (TextView) page.findViewById(R.id.textView1);
        card.setTag(contents.get(position));
        knowBtn.setTag(contents.get(position));
        notKnowBtn.setTag(contents.get(position));
        textView.setText(String.valueOf(contents.get(position).getName()));

        container.addView(page);
        return page;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public TextView getTextView1() {
        return (TextView) page.findViewById(R.id.textView1);
    }


    private View.OnClickListener getOnclickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                view.setEnabled(false);
                Key key = (Key) view.getTag();
                key.setDeck(key.getDeck() + 1);

                try {
                    Deck deckEntity = databaseHelper.getDeckDao().queryBuilder().where().eq("order", deck).queryForFirst();

                    if (deckEntity == null) {
                        for (Deck d : DeckActivity.decksStatic) {
                            if (d.equals(new Deck(999, "", key.getDeck()))) {//just deck is important
                                deckEntity = d;
                                break;
                            }
                        }
                    }

                    Calendar currentDate = Calendar.getInstance();
                    currentDate.add(Calendar.DAY_OF_MONTH, deckEntity.getDay());
                    key.setStatus(0);//normal
                    key.setDeadlineDate(currentDate.getTime());
                    key.setLastAnsweredDate(new Date());
                    databaseHelper.getKeyDao().update(key);
                    Toast.makeText(context, key.getName() + " is updated!", Toast.LENGTH_LONG).show();


                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    databaseHelper.close();
                }
            }
        };
    }


    private View.OnClickListener getOnclickListenerNotKnow() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);

                Key key = (Key) view.getTag();
                key.setDeck(0);

                try {

                    Calendar currentDate = Calendar.getInstance();
                    key.setDeadlineDate(currentDate.getTime());
                    key.setLastAnsweredDate(new Date());
                    key.setStatus(1);//you did not know this one
                    databaseHelper.getKeyDao().update(key);
                    Toast.makeText(context, key.getName() + " is updated!", Toast.LENGTH_LONG).show();
                    view.setEnabled(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    databaseHelper.close();
                }
            }
        };
    }


    private android.view.View.OnLongClickListener getOnLongClickListener() {
        return new android.view.View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (view.getTag() == null) {
                    Toast.makeText(context, "Tag is null", Toast.LENGTH_LONG).show();
                    return false;
                }
                Toast.makeText(context, ((Key) view.getTag()).getValue(), Toast.LENGTH_LONG).show();

                return false;
            }
        };
    }


}