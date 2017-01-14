package com.example.testapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.testapp.common.Const;
import com.example.testapp.entity.TestTable;
import com.example.testapp.helper.TestDatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

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

    private TestTable hoge;
    private Dao<TestTable, Long> dao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // DB
        TestDatabaseHelper helper = OpenHelperManager.getHelper(this, TestDatabaseHelper.class);
        // RuntimeExceptionDao<TestTable, Long> dao = helper.getRuntimeExceptionDao(TestTable.class);

        try {
            dao = helper.getDao(TestTable.class);
            String ymdString = ymdStr.format(System.currentTimeMillis());
            hoge = dao.queryBuilder().where().eq("attendanceYmd", ymdString).queryForFirst();
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

                    if( hoge == null || hoge.getAttendanceYmd() == null ) {
                        ((TextView) findViewById(R.id.attendance)).setText(nowDate);
                        stampingDivision = Const.STAMPING_DIVISION_ATTENDANCE;
                        ((TextView) findViewById(R.id.stamp)).setText(Const.LEAVING_TEXT);
                        findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp_attendanced);

                        // 永続化
                        hoge = new TestTable(ymdString, now, null);
                        dao.create(hoge);
                    }
                    else if( hoge.getLeavingDate() == null){
                        ((TextView) findViewById(R.id.leaving)).setText(nowDate);
                        stampingDivision = Const.STAMPING_DIVISION_LEAVING;
                        ((TextView) findViewById(R.id.stamp)).setText(Const.LEAVED_TEXT);
                        findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp_reaved);

                        hoge.setLeavingDate(now);
                        dao.update(hoge);
                    }
                    // 以下デバッグ用
                    else {
                        ((TextView) findViewById(R.id.attendance)).setText("--");
                        ((TextView) findViewById(R.id.leaving)).setText("--");
                        stampingDivision = Const.STAMPING_DIVISION_NONE;
                        ((TextView) findViewById(R.id.stamp)).setText("出勤");
                        findViewById(R.id.stamp).setBackgroundResource(R.drawable.shape_rounded_corners_5dp);

                        dao.delete(hoge);
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
        OpenHelperManager.releaseHelper();
    }
}
