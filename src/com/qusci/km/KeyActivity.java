package com.qusci.km;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.qusci.km.base.EventHandler;
import com.qusci.km.base.RichDialogActivity;
import com.qusci.km.database.DatabaseHelper;
import com.qusci.km.model.Category;
import com.qusci.km.model.Key;


import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/5/14
 * Time: 10:12 PM
 */
public class KeyActivity extends RichDialogActivity {


    Category category;
    boolean isEditMode;

    Key key;

    @Override
    protected String getDialogTitle() {
        if (!isEditMode)
            return "Add Word To " + category.getName();
        else
            return "Edit " + key.getName();


    }

    @Override
    protected void init(Bundle savedInstanceState) {
        key = null;
        setContentView(R.layout.key);
        if (getIntent().getExtras() != null)
            category = (Category) getIntent().getExtras().get("category");
        if (getIntent().getExtras().get("key") != null) {
            isEditMode = true;
            key = (Key) getIntent().getExtras().get("key");
            EditText name = (EditText) findViewById(R.id.keyName);
            EditText description = (EditText) findViewById(R.id.keyValue);

            name.setText(key.getName());
            description.setText(key.getValue());


            Button button = (Button) findViewById(R.id.btnAdd);
            if (isEditMode)
                button.setText("Update");
        }
    }


    @EventHandler(R.id.btnAdd)
    public void btnAddOnClickHandler(View view) {
        EditText name = (EditText) findViewById(R.id.keyName);
        EditText description = (EditText) findViewById(R.id.keyValue);

        if (name.getText().toString().isEmpty()) {
            return;
        }

        if (key == null)
            key = new Key();

        key.setValue(description.getText().toString());
        key.setName(name.getText().toString().trim());
        key.setCategoryId(category.getId());
        key.setLastAnsweredDate(new Date());
        if (key.getDeck() == null)
            key.setDeck(0);
        DatabaseHelper db = new DatabaseHelper(this);
        try {


            if (key.getId() == 0) {

                if (0 != db.getKeyDao().queryBuilder().where().eq("name", key.getName()).countOf()) {
                    Toast.makeText(this, "Duplicate '" + name.getText().toString() + "'", Toast.LENGTH_LONG).show();
                    return;
                }
                key.setSubmissionDate(new Date());
                db.getKeyDao().create(key);

            } else  {


                db.getKeyDao().update(key);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            db.close();
            Toast.makeText(this, name.getText().toString() + " is not saved", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, name.getText().toString() + " is saved", Toast.LENGTH_LONG).show();
        db.close();
        finish();

    }


}

