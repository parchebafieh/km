package com.qusci.km.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.qusci.km.app.AppController;
import com.qusci.km.database.SQLiteHelper;
import com.qusci.km.model.Book;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/3/14
 * Time: 9:48 PM
 */
public class DownloadIntentService extends IntentService {

    MediaPlayer player;
    ArrayList< Book> books=new ArrayList();

    ResultReceiver resultReceiver;
    public final static String URL = "https://raw.githubusercontent.com/parchebafieh/kmanager/master/books.json";
    public final static String TAG = DownloadIntentService.class.getSimpleName();


    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String dataString = intent.getDataString();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       /* if (player != null && player.isPlaying()) {
            player.stop();
        }

        player = MediaPlayer.create(this, R.raw.fil);
        player.setLooping(true);
        player.setVolume(100, 100);
        player.start();*/

        resultReceiver = (ResultReceiver)intent.getParcelableExtra("receiver");

        books = new ArrayList<Book>();

        JsonArrayRequest movieReq = new JsonArrayRequest(URL,getJSONListener()
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(DownloadIntentService.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        );
        AppController.getInstance().addToRequestQueue(movieReq);


        return 1;
    }

    private Response.Listener<JSONArray> getJSONListener(){
        final SQLiteHelper sqLiteHelper=new SQLiteHelper(this);

        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());


                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject obj = response.getJSONObject(i);
                        Book book = new Book();
                        book.setName(obj.getString("name"));
                        book.setAuthors(obj.getJSONObject("authors").get("author1") + ", " + obj.getJSONObject("authors").get("author2").toString());

                        books.add(book);
                        sqLiteHelper.addBook(book);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("books", books);
                resultReceiver.send(0,bundle);
                Toast.makeText(DownloadIntentService.this,"Done",Toast.LENGTH_LONG).show();

            }
        };
    }


    @Override
    public boolean stopService(Intent name) {
        Toast.makeText(this, "stop is called", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "onUnRebind is called", Toast.LENGTH_LONG).show();
        return true;

    }

    @Override
    public void onRebind(Intent intent) {
        Toast.makeText(this, "onRebind is called", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Destroy is called!", Toast.LENGTH_LONG).show();
        if (player == null || !player.isPlaying())
            return;
        player.stop();
        player.release();
    }


    @Override
    public void unbindService(ServiceConnection conn) {
        Toast.makeText(this, "unbindService is called", Toast.LENGTH_LONG).show();
    }


    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind is called", Toast.LENGTH_LONG).show();
        return super.onBind(intent);
    }
}
