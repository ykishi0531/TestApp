package com.example.testapp.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class TestTable {

    @DatabaseField(generatedId=true)
    private Long id;
    @DatabaseField
    private String attendanceYm;
    @DatabaseField
    private String attendanceDay;
    @DatabaseField
    private Date attendanceDate;
    @DatabaseField
    private Date leavingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttendanceYm() { return attendanceYm;}

    public void setAttendanceYm(String attendanceYm) {
        this.attendanceYm = attendanceYm;
    }

    public String getAttendanceDay() { return attendanceDay;}

    public void setAttendanceDay(String attendanceDay) {
        this.attendanceDay = attendanceDay;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Date getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
    }


    @SuppressWarnings("unset")
    private TestTable(){}

    public TestTable(String attendanceYm, String attendanceDay, Date attendanceDate, Date leavingDate) {
        this.attendanceYm = attendanceYm;
        this.attendanceDay = attendanceDay;
        this.attendanceDate = attendanceDate;
        this.leavingDate = leavingDate;
    }
}
