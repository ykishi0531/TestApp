package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.testapp.common.Const;
import com.example.testapp.dao.TestDao;
import com.example.testapp.entity.TestTable;

import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Stamping extends AppCompatActivity {
    private Handler mHandler;
    private Timer mTimer;
    private static SimpleDateFormat ymd = new SimpleDateFormat(Const.ymdJP);
    private static SimpleDateFormat ymdStr = new SimpleDateFormat(Const.ymdStr);
    private static SimpleDateFormat hms = new SimpleDateFormat(Const.hhmmss);

    private static String stampingDivision = Const.STAMPING_DIVISION_NONE;

    private TestDao dao;
    private TestTable hoge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 打刻状況を取得
        try {
            dao = new TestDao(this);
            String ymdString = ymdStr.format(System.currentTimeMillis());
            hoge = dao.findByAttendanceYmd(ymdString.substring(0,6), ymdString.substring(6));
        } catch( SQLException e) {
            e.printStackTrace();
        }

        if( hoge != null) {
            String setDate;
            if( hoge.getAttendanceDate() != null) {
                setDate = hms.format(hoge.getAttendanceDate());
                ((TextView) findViewById(R.id.attendance)).setText(setDate);
                stampingDivision = Const.STAMPING_DIVISION_ATTENDANCE;
                ((TextView) findViewById(R.id.stamp)).setText(Const.LEAVING_TEXT);
                findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp_attendanced);
            }
            if( hoge.getLeavingDate() != null) {
                setDate = hms.format(hoge.getLeavingDate());
                ((TextView) findViewById(R.id.leaving)).setText(setDate);
                stampingDivision = Const.STAMPING_DIVISION_LEAVING;
                ((TextView) findViewById(R.id.stamp)).setText(Const.LEAVED_TEXT);
                findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp_reaved);
            }
        }


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
                Date now = new Date(System.currentTimeMillis());
                String ymdString = ymdStr.format(now);
                String nowDate = hms.format(now);

                try{
                    // 出勤
                    if( hoge == null || hoge.getAttendanceYm() == null ) {
                        ((TextView) findViewById(R.id.attendance)).setText(nowDate);
                        stampingDivision = Const.STAMPING_DIVISION_ATTENDANCE;
                        ((TextView) findViewById(R.id.stamp)).setText(Const.LEAVING_TEXT);
                        findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp_attendanced);

                        // 出勤日、出勤時間を登録する
                        hoge = new TestTable(ymdString.substring(0,6), ymdString.substring(6), now, null);
                        dao.createOrUpdate(hoge);
                        hoge = dao.findByAttendanceYmd(ymdString.substring(0,6), ymdString.substring(6));
                    }
                    // 退勤
                    else if( hoge.getLeavingDate() == null){
                        ((TextView) findViewById(R.id.leaving)).setText(nowDate);
                        stampingDivision = Const.STAMPING_DIVISION_LEAVING;
                        ((TextView) findViewById(R.id.stamp)).setText(Const.LEAVED_TEXT);
                        findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp_reaved);

                        // 退勤時間を登録する
                        hoge.setLeavingDate(now);
                        System.out.println(hoge.getAttendanceDate());
                        System.out.println(hoge.getLeavingDate());
                        System.out.println(hoge.getId());
                        dao.createOrUpdate(hoge);
                    }
                    // 以下デバッグ用。
                    // 打刻状態をリセットして、レコードを削除する
                    else {
                        ((TextView) findViewById(R.id.attendance)).setText("--");
                        ((TextView) findViewById(R.id.leaving)).setText("--");
                        stampingDivision = Const.STAMPING_DIVISION_NONE;
                        ((TextView) findViewById(R.id.stamp)).setText("出勤");
                        findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp);

                        dao.delete(hoge.getId());
                        hoge = null;
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
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

        // インテントの生成
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_stamping:
                intent = new Intent(this, Stamping.class);
                // intent.setClassName("com.example.testapp", "com.example.testapp.Stamping");
                break;

            case R.id.action_calendar:
                intent = new Intent(this, Calendar.class);
                // intent.setClassName("com.example.testapp", "com.example.testapp.Calendar");
                break;
        }

        if( intent != null) {
            // SubActivity の起動
            startActivity(intent);
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
