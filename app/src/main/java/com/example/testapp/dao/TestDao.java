package com.example.testapp.dao;

import android.content.Context;

import com.example.testapp.entity.TestTable;
import com.example.testapp.helper.TestDatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public class TestDao{

    private Dao<TestTable, Long> dao;
    // private RuntimeExceptionDao<TestTable, Long> dao2;

    private TestDao(){}

    public TestDao(Context context) throws SQLException {
        TestDatabaseHelper helper = OpenHelperManager.getHelper(context, TestDatabaseHelper.class);
        dao = helper.getDao(TestTable.class);

    }
    /**
    public TestDao(Context context) throws SQLException{
        TestDatabaseHelper helper = OpenHelperManager.getHelper(context, TestDatabaseHelper.class);
        dao2 = helper.getRuntimeExceptionDao(TestTable.class);
    }
     */

    /**
     * IDで検索
     * @param id
     * @return
     * @throws SQLException
     */
    public TestTable findById(Long id) throws SQLException {
        return dao.queryBuilder().where().eq("id", id).queryForFirst();
    }

    /**
     * 出勤年月日で検索
     * @param attendanceYm
     * @param attendanceDay
     * @return
     * @throws SQLException
     */
    public TestTable findByAttendanceYmd (String attendanceYm, String attendanceDay) throws SQLException{
        return dao.queryBuilder().where().eq("attendanceYm", attendanceYm).and().eq("attendanceDay", attendanceDay).queryForFirst();
    }

    /**
     * 出勤年月で検索;
     * @param attendanceYm
     * @return
     * @throws SQLException
     */
    public List<TestTable> findByAttendanceYm (String attendanceYm) throws SQLException {
        return dao.queryBuilder().where().eq("attendanceYm", attendanceYm).query();
    }

    /**
     * 新規or更新
     * @param testTable
     * @throws SQLException
     */
    public void createOrUpdate(TestTable testTable) throws SQLException {
        dao.createOrUpdate(testTable);
    }

    /**
     * 削除
     * @param id
     * @throws SQLException
     */
    public void delete(Long id) throws SQLException {
        dao.deleteById(id);
    }
}
