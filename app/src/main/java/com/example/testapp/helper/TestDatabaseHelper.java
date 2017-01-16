package com.example.testapp.helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.testapp.entity.TestTable;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class TestDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "test_db";
    private static final int DATABSE_VERSION = 1;

    public TestDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        System.out.println("Create Database and table");
        try{
            TableUtils.createTable(connectionSource, TestTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        System.out.println("Database version up");
    }
}
