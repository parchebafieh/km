package com.qusci.km;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.ListView;
import com.qusci.km.adapter.BookAdapter;
import com.qusci.km.base.RichDialogActivity;
import com.qusci.km.database.SQLiteHelper;
import com.qusci.km.model.Book;
import com.qusci.km.service.BookResultReceiver;
import com.qusci.km.service.DownloadIntentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/4/14
 * Time: 12:24 AM
 */
public class BooksDoanloadActivity extends RichDialogActivity implements BookResultReceiver.Receiver {



    List<Book> bookList = new ArrayList<Book>();
    BookAdapter bookAdapter;
    public BookResultReceiver receiver;

    @Override
    protected String getDialogTitle() {
        return null;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        bookList.addAll((List<Book>) resultData.get("books"));
        bookAdapter.notifyDataSetChanged();
        ListView listView = (ListView) findViewById(R.id.bookList);
        bookAdapter = new BookAdapter(this, bookList);
        listView.setAdapter(bookAdapter);
    }


    protected void init(Bundle savedInstanceState) {

        SQLiteHelper sqLiteHelper = new SQLiteHelper(this);

        setContentView(R.layout.book_download_list_activity);
        ListView listView = (ListView) findViewById(R.id.bookList);

        bookList.addAll(sqLiteHelper.getAllBooks());

        bookAdapter = new BookAdapter(this, bookList);
        listView.setAdapter(bookAdapter);
        setTitle("Add Key");

        receiver = new BookResultReceiver(new Handler());
        receiver.setReceiver(this);
        Intent intent = new Intent(BooksDoanloadActivity.this, DownloadIntentService.class);
        intent.putExtra("receiver", receiver);
        startService(intent);


    }

    protected CompoundButton.OnCheckedChangeListener getActionbarSwitcherChangeListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    Intent intent = new Intent(BooksDoanloadActivity.this, DownloadIntentService.class);
                    intent.putExtra("receiver", receiver);
                    startService(intent);

                } else {
                    Intent intent = new Intent(BooksDoanloadActivity.this, DownloadIntentService.class);
                    stopService(intent);
                }

            }
        };
    }
}
