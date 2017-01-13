package com.example.testapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.testapp.common.Const;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

        CustomCalendarView calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);

        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
               SimpleDateFormat sdf = new SimpleDateFormat(Const.ymdJP);
               Toast.makeText(Calendar.this, sdf.format(date), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat(Const.ymdJP);
                Toast.makeText(Calendar.this, sdf.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        List<DayDecorator> decorator = new ArrayList<DayDecorator>();
        decorator.add(new ChangeBgOfDay(Arrays.asList("20170120", "20170129")));
        calendarView.setDecorators(decorator);
        calendarView.refreshCalendar(java.util.Calendar.getInstance());
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

    private class ChangeBgOfDay implements DayDecorator{
        private List<String> eventDayList;

        public ChangeBgOfDay(){
            this.eventDayList = new ArrayList<String>();
        }
        public ChangeBgOfDay(List<String> eventDayList) {
            this.eventDayList = eventDayList;
        }
        @Override
        public void decorate(DayView dayView) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
                if (eventDayList.contains(sdf.format(dayView.getDate()))) {
                    int color = Color.parseColor("#ddaa55");
                    dayView.setBackgroundColor(color);
                }
            }
            catch(Exception e) {

            }
        }
    }
}
