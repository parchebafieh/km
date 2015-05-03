package com.qusci.km.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import com.qusci.km.model.Category;
import com.qusci.km.model.Deck;
import com.qusci.km.model.Key;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: pedram
 * Date: 8/8/14
 * Time: pp:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "KMAN.db";
    private static final int DATABASE_VERSION = 3;

    private Dao<Category, Integer> categoryDao;
    private Dao<Key, Integer> keyDao;
    private Dao<Deck, Integer> deckDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, com.j256.ormlite.support.ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Key.class);
            TableUtils.createTable(connectionSource, Deck.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, com.j256.ormlite.support.ConnectionSource connectionSource, int oldVersion, int newVersion) {

        if (oldVersion == 1) {
            database.execSQL("alter table key add column lastAnsweredDate DATETIME; ");
            database.execSQL("alter table key add column deadlineDate DATETIME; ");
            database.execSQL("alter table key add column deck INTEGER default 0 not null;");
            try {
                TableUtils.createTable(connectionSource, Deck.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (oldVersion == 2) {
            database.execSQL("alter table key add column status INTEGER default 0 not null;");
        }
    }


    public Dao<Category, Integer> getCategoryDao() throws SQLException {
        if (categoryDao == null)
            categoryDao = DaoManager.createDao(this.connectionSource, Category.class);
        return categoryDao;
    }

    public Dao<Key, Integer> getKeyDao() throws SQLException {
        if (keyDao == null)
            keyDao = DaoManager.createDao(this.connectionSource, Key.class);
        return keyDao;
    }

    public Dao<Deck, Integer> getDeckDao() throws SQLException {
        if (deckDao == null)
            deckDao = DaoManager.createDao(this.connectionSource, Deck.class);
        return deckDao;
    }

}
