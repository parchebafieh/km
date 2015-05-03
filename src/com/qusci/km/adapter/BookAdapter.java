package com.qusci.km.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.qusci.km.R;
import com.qusci.km.model.Book;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/4/14
 * Time: 12:28 AM
 */
public class BookAdapter extends BaseAdapter {

    List<Book> books;
    Activity activity;
    private LayoutInflater inflater;


    public BookAdapter(Activity activity, List<Book> books) {
        this.books = books;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        View row = view;
        Book holder = null;

        BookHolder bookHolder;

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.book, null);
            bookHolder = new BookHolder();


            bookHolder.bookName = (TextView) view.findViewById(R.id.bookNameTextView);
            bookHolder.authorNames = (TextView) view.findViewById(R.id.authorNames);

            view.setTag(bookHolder);

        } else {
            bookHolder = (BookHolder) view.getTag();
        }


        Book book = books.get(i);
        bookHolder.bookName.setText(book.getName());
        bookHolder.authorNames.setText(book.getAuthors());

        return view;
    }


    static class BookHolder {
        TextView bookName;
        TextView authorNames;
    }
}
