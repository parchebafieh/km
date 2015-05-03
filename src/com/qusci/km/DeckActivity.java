package com.qusci.km;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.qusci.km.base.RichActivity;
import com.qusci.km.database.DatabaseHelper;
import com.qusci.km.model.Category;
import com.qusci.km.model.Deck;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/17/14
 * Time: 6:14 AM
 */
public class DeckActivity extends RichActivity implements ActionBar.TabListener {

    List<String> tabs = new ArrayList<String>();
    Category currentCategory;
    public static List<Deck> decksStatic=new ArrayList<Deck>();

    static {
        decksStatic.add(new Deck(0, "Deck 1", 0));
        decksStatic.add(new Deck(1, "Deck 2", 1));
        decksStatic.add(new Deck(2, "Deck 3", 2));
        decksStatic.add(new Deck(4, "Deck 4", 3));
        decksStatic.add(new Deck(8, "Deck 5", 4));
        decksStatic.add(new Deck(16, "Deck 6", 5));
        decksStatic.add(new Deck(32, "Deck 7", 5));

    }
    Deck currentDeck;



    protected boolean makeNormalOptionMenu() {
        super.makeNormalOptionMenu();
        menu.removeItem(1);
        return true;
    }


    @Override
    protected void addTabs() {
        List<Deck> decks = null;
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        try {
            decks = databaseHelper.getDeckDao().queryForAll();
            if (decks == null || decks.size() == 0) {
                decks = decksStatic;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Deck deck : decks) {
            ActionBar.Tab tab = getSupportActionBar().newTab();
            tab.setText(deck.getName()).setTabListener(this);
            tab.setTag(deck);
            getSupportActionBar().addTab(tab);
        }

        getSupportActionBar().setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.deck_activity);

        currentCategory = (Category) getIntent().getExtras().get("category");
//        getSupportActionBar().setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

//        ft.replace(R.id.categoryListView, new KeyFragment(currentCategory, 0));
//        ft.commit();

    }

    @Override
    protected CompoundButton.OnCheckedChangeListener getActionbarSwitcherChangeListener() {
        return null;
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();

        currentDeck = (Deck) tab.getTag();

        ftt.replace(R.id.content_frame, new KeyFragment(currentCategory, currentDeck.getOrder()));
        ftt.commit();

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
            getGlobalIntent().putExtra("deck", currentDeck.getOrder());
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

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
