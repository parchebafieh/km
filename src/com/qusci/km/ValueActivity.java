package com.qusci.km;

import android.os.Bundle;
import android.widget.TextView;
import com.qusci.km.base.RichDialogActivity;
import com.qusci.km.model.Key;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/6/14
 * Time: 12:00 AM
 */
public class ValueActivity extends RichDialogActivity {

    Key currentKey;

    @Override
    protected String getDialogTitle() {
        return currentKey.getName();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        setContentView(R.layout.value);

       currentKey= (Key) getIntent().getExtras().get("key");
        TextView textView= (TextView) findViewById(R.id.textView1);
        textView.setText(currentKey.getValue());

        setFinishOnTouchOutside(true);
    }
}
