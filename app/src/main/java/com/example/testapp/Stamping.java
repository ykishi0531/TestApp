package com.example.testapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.testapp.common.Const;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Stamping extends AppCompatActivity {

    private Handler mHandler;
    private Timer mTimer;
    private static SimpleDateFormat ymd = new SimpleDateFormat(Const.ymdJP);
    private static SimpleDateFormat hms = new SimpleDateFormat(Const.hhmmss);

    private static String stampingDivision = Const.STAMPING_DIVISION_NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler(getMainLooper());
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String nowYmd = ymd.format(System.currentTimeMillis());
                        String nowDate = hms.format(System.currentTimeMillis());

                        ((TextView) findViewById(R.id.ymd)).setText(nowYmd);
                        ((TextView) findViewById(R.id.hms)).setText(nowDate);

                    }
                });
            }
        },0,1000);

        findViewById(R.id.stamp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nowDate = hms.format(System.currentTimeMillis());
                if( Const.STAMPING_DIVISION_NONE.equals(stampingDivision) ) {
                    ((TextView) findViewById(R.id.attendance)).setText(nowDate);
                    stampingDivision = Const.STAMPING_DIVISION_ATTENDANCE;
                    ((TextView) findViewById(R.id.stamp)).setText(Const.LEAVING_TEXT);
                    findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp_attendanced);
                }
                else if( Const.STAMPING_DIVISION_ATTENDANCE.equals(stampingDivision)){
                    ((TextView) findViewById(R.id.leaving)).setText(nowDate);
                    stampingDivision = Const.STAMPING_DIVISION_LEAVING;
                    ((TextView) findViewById(R.id.stamp)).setText(Const.LEAVED_TEXT);
                    findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp_reaved);
                }
                // 以下デバッグ用
                else {
                    ((TextView) findViewById(R.id.attendance)).setText("--");
                    ((TextView) findViewById(R.id.leaving)).setText("--");
                    stampingDivision = Const.STAMPING_DIVISION_NONE;
                    ((TextView) findViewById(R.id.stamp)).setText("出勤");
                    findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stamping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
