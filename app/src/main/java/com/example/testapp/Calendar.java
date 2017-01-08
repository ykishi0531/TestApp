package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 *
 */

public class Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // インテントの生成
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_stamping:
                intent = new Intent();
                intent.setClassName("com.example.testapp", "com.example.testapp.Stamping");
                break;

            case R.id.action_calendar:
                intent = new Intent();
                intent.setClassName("com.example.testapp", "com.example.testapp.Calendar");
                break;
        }

        if( intent != null) {
            // SubActivity の起動
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
