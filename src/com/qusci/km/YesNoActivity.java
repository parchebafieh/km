package com.qusci.km;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.qusci.km.base.EventHandler;
import com.qusci.km.base.RichDialogActivity;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/13/14
 * Time: 12:38 PM
 */
public class YesNoActivity extends RichDialogActivity {

    @Override
    protected String getDialogTitle() {
        return "Are you sure?";
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.yesno_activity);
    }


   @EventHandler(R.id.btnYes)
    public void btnYesOnClickHandler(View view) {
       getIntent().putExtra("yesyno",true);
       setResult(1);
       finish();
    }

    @EventHandler(R.id.btnNo)
    public void btnNoOnClickHandler(View view) {
        getIntent().putExtra("yesyno",false);
        setResult(0);
        finish();
    }

}
