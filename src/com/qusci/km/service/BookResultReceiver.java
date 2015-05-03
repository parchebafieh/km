package com.qusci.km.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/4/14
 * Time: 1:05 AM
 */
public class BookResultReceiver extends ResultReceiver {


    private Receiver receiver;

    public BookResultReceiver(Handler handler) {
        super(handler);
    }


    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }

    public static interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);

    }


    public Receiver getReceiver() {
        return receiver;
    }
}
