package com.qusci.km.database;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/21/12
 * Time: 10:14 AM
 */
public class KeyTable/* extends SQLiteOpenHelper*/ {
/*
    public static final String DATABASE_NAME = "KMANAGER";
    public static final int DATABASE_VERSION = pp;

    Properties getProperties() {
        Properties p = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("com.qusci.android.database.key.properties");
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }


    public KeyTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("TAG_LOG", "DATABASE UPGRADE OLDVERSION=" + oldVersion + " NEWVERSION=" + newVersion);
        db.execSQL((String)getProperties().get("drop"));
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("TAG_LOG", "DATABASE ONCREATE");
        db.execSQL((String) getProperties().get("create"));
        db.close();
    }*/
}
