package com.qusci.km;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import com.qusci.km.base.EventHandler;
import com.qusci.km.base.RichActivity;
import com.qusci.km.base.RichDialogActivity;
import com.qusci.km.database.DatabaseHelper;
import com.qusci.km.model.Category;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: pedram
 * Date: 8/8/14
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryActivity extends RichDialogActivity {

    @Override
    protected String getDialogTitle() {
       return "Add Category";
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.category);
    }


    @EventHandler(value = R.id.btnAdd, eventType = View.OnLongClickListener.class)
    public boolean btnAddLongOnClickHandler(View view) {
        Log.i(this.getClass().getSimpleName(), "salam az Long click");
        Toast.makeText(this, "You Long clicked the button", Toast.LENGTH_LONG).show();
        return true;
    }


    @EventHandler(R.id.btnAdd)
    public void btnAddOnClickHandler(View view) {
        Log.i(this.getClass().getSimpleName(), "salam az onclick");

        EditText name = (EditText) findViewById(R.id.catName);
        EditText description = (EditText) findViewById(R.id.catDescription);

        if (name.getText().toString().isEmpty()) {
            return;
        }

        Category category = new Category();
        category.setDescription(description.getText().toString());
        category.setName(name.getText().toString());

        DatabaseHelper db = new DatabaseHelper(this);
        try {
            db.getCategoryDao().create(category);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Toast.makeText(this, name.getText().toString() + " is not saved", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, name.getText().toString() + " is saved", Toast.LENGTH_LONG).show();
        Intent showList = new Intent(this, CategoryListActivity.class);
        finish();
//        startActivity(showList);
    }


   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
            Log.i(this.getClass().getSimpleName(), "back button is pushed");
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            Log.i(this.getClass().getSimpleName(), "Menu button is pushed");
        }
        return super.onKeyDown(keyCode, event);
    }
*/

}
