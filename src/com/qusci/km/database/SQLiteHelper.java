package com.qusci.km.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.qusci.km.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/4/14
 * Time: 4:42 AM
 */
public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "KMAN.db";

    // Contacts table name
    private static final String TABLE_BOOK = "BOOK";

    // Contacts Table Columns names
    private static final String ID = "id";
    private static final String BOOK_NAME = "name";
    private static final String AUTHORS = "authors";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BOOK + "("
                + ID + " INTEGER PRIMARY KEY," + BOOK_NAME + " TEXT,"
                + AUTHORS + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }


    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, book.getName());
        values.put(AUTHORS, book.getAuthors());
        db.insert(TABLE_BOOK, null, values);
        db.close();
    }


    public List<Book> getAllBooks() {
        List<Book> contactList = new ArrayList<Book>();
        String selectQuery = "SELECT  * FROM " + TABLE_BOOK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setName(cursor.getString(1));
                book.setAuthors(cursor.getString(2));
                contactList.add(book);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BOOK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
