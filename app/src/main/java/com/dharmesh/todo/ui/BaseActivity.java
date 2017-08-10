package com.dharmesh.todo.ui;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.dharmesh.todo.R;
import com.dharmesh.todo.data.AppDatabase;


public class BaseActivity extends AppCompatActivity {
    public Toolbar toolbar;
    private ActionBar actionBar;
    protected AppDatabase db;
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "todo-db").build();

    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
        }
    }

    public void setTitle(@StringRes int title) {
        actionBar.setTitle(title);
    }

    public void enableToolbarBackButton() {
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void startActivity(Class klass) {
        startActivity(new Intent(this, klass));
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void toast(@StringRes int resId) {
        toast(getResources().getString(resId));
    }

    protected String getText(EditText et) {
        return et.getText().toString().trim();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuFile = getMenuFile();
        if (menuFile < 1) {
            return false;
        }
        getMenuInflater().inflate(menuFile, menu);
        return true;
    }

    public int getMenuFile() {
        return -1;
    }

}

