package com.qusci.km.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/21/12
 * Time: 10:14 AM
 */
public class CategoryTable extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "KMANAGER";
    public static final int DATABASE_VERSION = 3;

    public static final String TBL_CATEGORY = "TBL_CATEGORY";
    public static final String CAT_NAME = "CAT_NAME";
    public static final String CAT_ID = "_id";
    public static final String CAT_PARENT = "CAT_PARENT";
    public static final String CAT_DESC = "CAT_DESC";

    public static final String CREATEQUERY = "CREATE TABLE " + TBL_CATEGORY + " ( " + CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            CAT_NAME + " VARCHAR(255),"+CAT_DESC+" VARCHAR(255)"+");";

    public CategoryTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("TAG_LOG", "DATABASE UPGRADE OLDVERSION=" + oldVersion + " NEWVERSION=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+TBL_CATEGORY);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("TAG_LOG", "DATABASE ONCREATE");
        db.execSQL(CREATEQUERY);
        db.close();
    }
}
